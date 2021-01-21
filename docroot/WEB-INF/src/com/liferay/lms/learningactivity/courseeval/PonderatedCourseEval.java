package com.liferay.lms.learningactivity.courseeval;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.CourseResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.util.LmsConstant;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

public class PonderatedCourseEval extends BaseCourseEval {

    @Override
    public void updateCourse(Course course, ModuleResult mresult) throws SystemException {              
        updateCourse(course, mresult.getUserId());              
    }
    
    public static long getScore(Course course) throws DocumentException, PortalException, SystemException {
        long score=0;
        String extraData=course.getCourseExtraData();
        if(extraData!=null &&extraData.startsWith("<?xml")){
            Document document = SAXReaderUtil.read(extraData);
            Element rootElement=document.getRootElement();
            Element scoreElement=rootElement.element("score");
            if(scoreElement!=null){
                score=Long.valueOf(scoreElement.attributeValue("value"));
            }
        }
        return score;
    }
    
    public static List<Long> getRequiredActivities(Course course) throws DocumentException, PortalException, SystemException{
        List<Long> result=new java.util.ArrayList<Long>();
        String extraData=course.getCourseExtraData();
        if(extraData!=null &&extraData.startsWith("<?xml")){
            Document document = SAXReaderUtil.read(extraData);
            Element rootElement=document.getRootElement();
            java.util.List<Element> reqElements=rootElement.elements("required");
            for(Element reqElement:reqElements){
                long actId=Long.parseLong(reqElement.attributeValue("actId"));
                LearningActivity larn=LearningActivityLocalServiceUtil.fetchLearningActivity(actId);
                if(larn!=null&& larn.getGroupId()==course.getGroupCreatedId()&& larn.getWeightinmodule()>0){
                    result.add(actId);
                }
            }
        }
        return result;
    }
    public static Map<Long, Float> getActivitiesWeight(Course course) throws PortalException, SystemException, DocumentException{
        Map<Long, Float> result = new HashMap<Long, Float>();
        String extraData=course.getCourseExtraData();
        if(extraData!=null &&extraData.startsWith("<?xml")){
            Document document = SAXReaderUtil.read(extraData);
            Element rootElement=document.getRootElement();
            List<Element> reqElements=rootElement.elements("weight");
            for(Element reqElement:reqElements){
                long actId=Long.parseLong(reqElement.attributeValue("actId"));
                float ponderation=Float.parseFloat(reqElement.attributeValue("ponderation"));
                LearningActivity larn=LearningActivityLocalServiceUtil.fetchLearningActivity(actId);
                if(larn!=null&& larn.getGroupId()==course.getGroupCreatedId()&& larn.getWeightinmodule()>0){
                    result.put(actId,ponderation);
                }
            }
        }
        return result;
    }
        
    @Override
    public boolean updateCourse(Course course, long userId) throws SystemException{

        CourseResult courseResult=CourseResultLocalServiceUtil.getCourseResultByCourseAndUser(course.getCourseId(), userId);

        if(courseResult==null){
            courseResult = CourseResultLocalServiceUtil.addCourseResult(0, course.getCourseId(), userId);
        }

        if(courseResult.getStartDate() == null){
            courseResult = CourseResultLocalServiceUtil.initializeCourseResult(courseResult);
        }
        
        try {
            Map<Long,Float> weights = getActivitiesWeight(course);
            long score = getScore(course);

            boolean passed=true;
            Float result= new Float(0);
            float weight=0f;
            List<LearningActivity> learningActivities = LearningActivityLocalServiceUtil.getMandatoryLearningActivitiesOfGroup(course.getGroupCreatedId());
            List<Long> requiredActivities = getRequiredActivities(course);
            
            //Guardo los resultados de las actividades del usuario en el curso en un hashmap para no tener que acceder a bbdd por cada uno de ellos
            List<LearningActivityResult> lresult = LearningActivityResultLocalServiceUtil.getMandatoryByGroupIdUserId(course.getGroupCreatedId(), userId);
            HashMap<Long, LearningActivityResult> results = new HashMap<Long, LearningActivityResult>();
            for(LearningActivityResult ar:lresult){
                results.put(ar.getActId(), ar);
            }
            
            boolean isFailed=false;
            LearningActivityResult learningActivityResult = null;
            boolean hasTries = false;
            for(LearningActivity act:learningActivities){
                
                float activityWeight = 0f;
                if(weights.containsKey(act.getActId())){
                    activityWeight=weights.get(act.getActId());
                } else if (!PrefsPropsUtil.getBoolean(course.getCompanyId(), LmsConstant.ALLOW_WEIGHTLESS_MANDATORY_ACTIVITIES, false)) {
                    continue;
                }
                
                if(results.containsKey(act.getActId())){
                    learningActivityResult = results.get(act.getActId());
                }else{
                    learningActivityResult = null;
                }
                
                if(learningActivityResult != null){
                    if(learningActivityResult.getEndDate()!=null){                      
                        if(!learningActivityResult.isPassed() && requiredActivities.contains(act.getActId())){
                            passed=false;
                            if(act.getTries() > 0){
                                long  userTries = LearningActivityTryLocalServiceUtil.getLearningActivityTryByActUserCount(act.getActId(), userId);
                                if(userTries >= act.getTries()){
                                    isFailed=true;
                                }else{
                                    hasTries = true;
                                }
                            }else{
                                hasTries = true;
                            }
                        }
                        result=result+(learningActivityResult.getResult()*activityWeight);

                    }else{
                        passed=false;
                        hasTries = true;
                    }
                }else{
                    passed=false;
                    hasTries = true;
                }
                weight+=activityWeight;
            }

            if(result>0&&weight>0){
                result=result/weight;
            }

            if(result<score){
                passed=false;
            }

            if(!hasTries && !passed){
                isFailed = true;
            }
            
            // Si el usuario se ha marcado como isFailed es porque lo tiene suspenso. Se le asigna un passed a false y se marca la fecha de finalización del curso (passedDate).
            courseResult.setPassed(passed && !isFailed);
            // Se almacena el result del resultado del usuario en el curso.
            
            courseResult.setResult(result.longValue());
            if((passed || isFailed) && courseResult.getPassedDate() == null) {
                   courseResult.setPassedDate(new Date());
            }
            CourseResultLocalServiceUtil.update(courseResult);
            return true;        
            
        } catch (PortalException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;    
    }

    @Override
    public boolean updateCourse(Course course) throws SystemException {
        try {
            for(User userOfCourse:UserLocalServiceUtil.getGroupUsers(course.getGroupCreatedId())){
                if(!PermissionCheckerFactoryUtil.create(userOfCourse).hasPermission(course.getGroupCreatedId(), "com.liferay.lms.model",course.getGroupCreatedId(), "VIEW_RESULTS")){
                    updateCourse(course,  userOfCourse.getUserId());    
                }               
            }

            return true;
        } catch (Exception e) {
            throw new SystemException(e);
        }
    }

    @Override
    public String getName() {
        return "ponderated."+getTypeId()+".name";
    }
    
    @Override
    public String getName(Locale locale) {
        return LanguageUtil.get(locale, "ponderated."+getTypeId()+".name");
    }

    @Override
    public long getTypeId() {
        return 3;
    }

    @Override
    public boolean getNeedPassAllModules() {
        return false;
    }

    @Override
    public boolean getFailOnCourseCloseAndNotQualificated() {
        return true;
    }

    @Override
    public boolean getNeedPassPuntuation() {
        return true;
    }
    
    @Override
    public long getPassPuntuation(Course course) throws DocumentException {
        throw new RuntimeException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onOpenCourse(Course course) throws SystemException {
        for(CourseResult courseResult:
            (List<CourseResult>)CourseResultLocalServiceUtil.dynamicQuery(
                CourseResultLocalServiceUtil.dynamicQuery().
                    add(PropertyFactoryUtil.forName("courseId").eq(course.getCourseId())).
                    add(PropertyFactoryUtil.forName("passed").eq(false)))){
            courseResult.setPassedDate(null);
            CourseResultLocalServiceUtil.update(courseResult);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onCloseCourse(Course course) throws SystemException {
        super.onCloseCourse(course);
        for(CourseResult courseResult:
            (List<CourseResult>)CourseResultLocalServiceUtil.dynamicQuery(
                CourseResultLocalServiceUtil.dynamicQuery().
                    add(PropertyFactoryUtil.forName("courseId").eq(course.getCourseId())).
                    add(PropertyFactoryUtil.forName("passedDate").isNull()))){
            courseResult.setPassedDate(course.getModifiedDate());
            CourseResultLocalServiceUtil.update(courseResult);
        }
    }
    
    @Override
    public JSONObject getEvaluationModel(Course course) throws PortalException,
            SystemException, DocumentException, IOException {
        return JSONFactoryUtil.createJSONObject();
    }

    @Override
    public void setEvaluationModel(Course course, JSONObject model)
            throws PortalException, SystemException, DocumentException,
            IOException {   
        Document document = SAXReaderUtil.createDocument();
        Element rootElement = document.addElement("eval");
        rootElement.addElement("courseEval").setText(PonderatedCourseEval.class.getName());     
        course.setCourseExtraData(document.formattedString());
    }
    
    @Override
    public void cloneCourseEval(Course course, Course newCourse, HashMap<Long, Long> correlationModules,
        HashMap<Long, Long> correlationActivities) throws SystemException
    {
        _log.debug("cloneCourseEval");
        
        try {            
            Map<Long, Float> ponderationValues = _getNewCoursePonderation(course, correlationActivities);
            
            if (Validator.isNotNull(ponderationValues) && ponderationValues.size() > 0) {
                String extraData = _getCourseExtraData(course, newCourse, ponderationValues);                
                newCourse.setCourseExtraData(extraData);
                CourseLocalServiceUtil.updateCourse(newCourse);
            }
        } catch (PortalException | DocumentException | IOException e) {
            _log.error(e.getMessage());
        }
    }

    /**
     * Obtiene un map con los pesos de las actividades del nuevo curso cuando se clonan cursos
     * 
     * @param course curso del que se va a clonar la ponderacion
     * @param correlationActivities relacion de los ids de las actividades del curso antiguo con las del nuevo
     * @return map con los pesos de las actividades para el nuevo curso
     * @throws PortalException
     * @throws SystemException
     * @throws DocumentException
     */
    private Map<Long, Float> _getNewCoursePonderation(Course course, HashMap<Long, Long> correlationActivities)
        throws PortalException, SystemException, DocumentException
    {
        Map<Long, Float> ponderationValues = new HashMap<Long, Float>();
        Map<Long, Float> weights = getActivitiesWeight(course);
        for (long activityWeight : correlationActivities.keySet()) {
            if (weights.containsKey(activityWeight)) {
                ponderationValues.put(correlationActivities.get(activityWeight), weights.get(activityWeight));
            }
        }
        return ponderationValues;
    }

    /**
     * Devuelve el extradata del nuevo curso con la ponderacion de las actividades
     * 
     * @param course curso que se va a clonar
     * @param newCourse nuevo curso
     * @param ponderationValues pesos de las actividades para el nuevo curso
     * @return extradata del nuevo curso
     * @throws SystemException
     * @throws IOException
     */
    private String _getCourseExtraData(Course course, Course newCourse, Map<Long, Float> ponderationValues)
        throws SystemException, IOException
    {
        Document document = SAXReaderUtil.createDocument();
        Element rootElement = document.addElement("eval");
        long score = 0;
        try {
            score = PonderatedCourseEval.getScore(course);
        } catch (PortalException | DocumentException e) {
            _log.warn(e.getMessage());
        }
        Element passElement = rootElement.addElement("score");
        passElement.addAttribute("value", Long.toString(score));

        rootElement.addElement("courseEval").setText(PonderatedCourseEval.class.getName());

        for (Map.Entry<Long, Float> entry : ponderationValues.entrySet()) {
            Element weight = rootElement.addElement("weight");
            weight.addAttribute("actId", Long.toString(entry.getKey()));
            weight.addAttribute("ponderation", Float.toString(entry.getValue()));
        }

        return document.formattedString();
        
    }
    
    private static Log _log = LogFactoryUtil.getLog(PonderatedCourseEval.class);
}
