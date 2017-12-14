package com.liferay.lms;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.ModuleResultLocalServiceUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Portlet implementation class UserProgress
 */

public class UserProgress extends MVCPortlet {
	
	private final String fileName = "IEC.pdf";
	private final Color rojo = new Color(190,50,28);
	private final Color negro = new Color(0,0,0);
	private final Color blanco = new Color(255,255,255);
	private final Color gris = new Color(222,222,222);
    GradientPaint gradientGray = new GradientPaint(0.0F, 0.0F, Color.GRAY, 0.0F, 0.0F, new Color(208, 208, 208));
    GradientPaint gradientBlue = new GradientPaint(0.0F, 0.0F, Color.BLUE, 0.0F, 0.0F, new Color(3, 8, 111));
    GradientPaint gradientRed = new GradientPaint(0.0F, 0.0F, Color.RED, 0.0F, 0.0F, new Color(3, 8, 111));
	private final int cols = 4;
	private Font fontTitle = new Font();
	private Font fontNormal = new Font();
	private Font fontHeaders = new Font();
	private Font fontTitleModule = new Font();
	private final boolean NONE_PAGINATOR = false;
	private final int TITLE_CHAR_LIMIT = 50;
	
	
	public void serveResource(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws IOException,
			PortletException {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		try {
			
			ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest
					.getAttribute(WebKeys.THEME_DISPLAY);
			
			loadFonts();
			
			write_IEC_Pdf(baos,themeDisplay,PortalUtil.getHttpServletRequest(resourceRequest));
		    
			ServletResponseUtil.sendFile(PortalUtil.getHttpServletRequest(resourceRequest),
					 PortalUtil.getHttpServletResponse(resourceResponse),
					 fileName,
					 baos.toByteArray(), 
					 ContentTypes.APPLICATION_PDF);
				
		} catch (Exception e) {
			e.printStackTrace();
			SessionErrors.add(resourceRequest, "export.pdf.userstats.error");
		} finally{
			baos.close();
		}
		
	}
	
	
	private void write_IEC_Pdf(OutputStream outputStream, ThemeDisplay themeDisplay, HttpServletRequest request) throws Exception {
		
		Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId());
		String titleReport = LanguageUtil.get(themeDisplay.getLocale(), "userprogress.export.pdf.title");
		
		Document document = new Document(PageSize.A4.rotate());
		PdfWriter writer = PdfWriter.getInstance(document, outputStream);
		
		// Header
		String courseTitle = course.getTitle(themeDisplay.getLocale());
		courseTitle = courseTitle != null && courseTitle.length() > TITLE_CHAR_LIMIT ? courseTitle.substring(0, TITLE_CHAR_LIMIT) + "..." : courseTitle;
		
		Chunk reportTitle = new Chunk(titleReport + ": " + courseTitle);
		reportTitle.setFont(fontTitle);
		reportTitle.setBackground(rojo, 4, 4, 475, 5); // extraLeft, extraBottom, extraRight, extraTop
		Paragraph paragraph = new Paragraph(reportTitle);
		paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
		paragraph.setSpacingAfter(5);
		
		PdfPTable tbHeader = new PdfPTable(1);
		tbHeader.setWidthPercentage(100);
	    PdfPCell cellHeaderImg = new PdfPCell(com.lowagie.text.Image.getInstance(getLogoImg(request, themeDisplay).getTextObj()));
	    cellHeaderImg.setBorder(PdfCell.NO_BORDER);
	    cellHeaderImg.setHorizontalAlignment(Element.ALIGN_LEFT);
	    tbHeader.addCell(cellHeaderImg);
	    PdfPCell cellHeaderTxt = new PdfPCell(paragraph);
	    cellHeaderTxt.setBorder(PdfCell.NO_BORDER);
	    cellHeaderTxt.setHorizontalAlignment(Element.ALIGN_LEFT);
	    tbHeader.addCell(cellHeaderTxt);
	    Paragraph paragraphHeader = new Paragraph();
	    paragraphHeader.add(tbHeader);
	    
		HeaderFooter header=new HeaderFooter(paragraphHeader,NONE_PAGINATOR);
		header.setBorder(Rectangle.NO_BORDER);
		header.setAlignment(Element.ALIGN_LEFT);
	    document.setHeader(header);
	    
	    document.open();
        
		// Datos del curso
		PdfPTable table = new PdfPTable(cols);
		table.setWidthPercentage(100);
		table.setSpacingBefore(10f);
        List<Module> modules = ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
        
        // Modulos
        for(Module theModule:modules) {
        	
        	PdfPCell cellTitleModule = new PdfPCell(new Paragraph(theModule.getTitle(themeDisplay.getLocale()), fontTitleModule));
        	cellTitleModule.setColspan(cols);
        	cellTitleModule.setBorder(PdfCell.NO_BORDER);
        	cellTitleModule.setHorizontalAlignment(Element.ALIGN_LEFT);
        	cellTitleModule.setPaddingTop(10);
        	cellTitleModule.setPaddingBottom(10);
        	table.addCell(cellTitleModule);
        	
        	addCellTable(LanguageUtil.get(themeDisplay.getLocale(), "userprogress.export.pdf.header1"), fontHeaders, blanco, table);
        	addCellTable(LanguageUtil.get(themeDisplay.getLocale(), "userprogress.export.pdf.header2"), fontHeaders, blanco, table);
        	addCellTable(LanguageUtil.get(themeDisplay.getLocale(), "userprogress.export.pdf.header3"), fontHeaders, blanco, table);
        	addCellTable(LanguageUtil.get(themeDisplay.getLocale(), "userprogress.export.pdf.header4"), fontHeaders, blanco, table);
        	
        	PdfPCell cellLine = new PdfPCell();
        	cellLine.setBorder(Rectangle.BOTTOM);
        	cellLine.setColspan(cols);
        	table.addCell(cellLine);
        	
            List<LearningActivity> activities=LearningActivityServiceUtil.getLearningActivitiesOfModule(theModule.getModuleId());
            long row = 0;

            // Actividades
            for(LearningActivity activity: activities) {

            	Color color = (row%2 == 0) ? gris : blanco;
            	
            	String score= "-";
				String status="userprogress.export.pdf.act.status.not.started";
				if(LearningActivityResultLocalServiceUtil.existsLearningActivityResult(activity.getActId(), themeDisplay.getUserId())){
					status="userprogress.export.pdf.status.started";
					LearningActivityResult learningActivityResult=LearningActivityResultLocalServiceUtil.getByActIdAndUserId(activity.getActId(), themeDisplay.getUserId());
					score=(learningActivityResult!=null)?LearningActivityResultLocalServiceUtil.translateResult(themeDisplay.getLocale(), learningActivityResult.getResult(), activity.getGroupId()):"";
					
					if(learningActivityResult.getEndDate()!=null) {
							status="not-passed";
					}
					if(learningActivityResult.isPassed()) {
						status="passed";
					}
				}
				
				Calendar calActual = Calendar.getInstance(themeDisplay.getUser().getTimeZone(), themeDisplay.getUser().getLocale());
            	Calendar calActiviy = Calendar.getInstance(themeDisplay.getUser().getTimeZone(), themeDisplay.getUser().getLocale());
            	calActiviy.setTime(activity.getEnddate());
            	
            	StringBuilder time = new StringBuilder();
            	long difMiliseconds = calActiviy.getTimeInMillis() - calActual.getTimeInMillis();
            	
            	if(difMiliseconds > 0) {
            		
            		long dias = Math.abs(difMiliseconds/(3600000*24));
            		long restoHoras = difMiliseconds%(3600000*24);
            		long horas = Math.abs(restoHoras/3600000);
            		long restoMinutos = restoHoras%3600000;
            		long minutos = Math.abs(restoMinutos/(60 * 1000));
            		
        	    	time.append(dias + StringPool.SPACE + LanguageUtil.get(themeDisplay.getLocale(), "userprogress.export.pdf.days"));
        	    	time.append(StringPool.SPACE + horas + StringPool.SPACE + LanguageUtil.get(themeDisplay.getLocale(), "userprogress.export.pdf.hours"));
        	    	time.append(StringPool.SPACE + minutos + StringPool.SPACE + LanguageUtil.get(themeDisplay.getLocale(), "userprogress.export.pdf.minuts"));
            	    
            	} else {
            		time.append(LanguageUtil.get(themeDisplay.getLocale(), "userprogress.export.pdf.status.elapsed"));
            	}
            	long calificationType =    CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId()).getCalificationType();
				String divisor = "/100";
				if(calificationType!=0){
					divisor="/10";
				}	
            	// Titulo
				addCellTable(activity.getTitle(themeDisplay.getLocale()), fontNormal, color, table);
				// Estado
				addCellTable(LanguageUtil.get(themeDisplay.getLocale(), status.toString()) , fontNormal, color, table);
				// Resultado
            	addCellTable((score.trim().equalsIgnoreCase("-")) ? score:  score + divisor, fontNormal, color, table);
            	// Tiempo Restante
            	addCellTable(time.toString(), fontNormal, color, table);

            	row++;
            }
        }
        
        document.add(table);
        
        // Grafica
        document.newPage();
        document.add(Chunk.NEWLINE);
		PdfContentByte cb = writer.getDirectContent();
		float width = 475;
		float height = PageSize.A4.getHeight() / 3;
		PdfTemplate template = cb.createTemplate(width, height);
		Graphics2D graphics2d = template.createGraphics(width, height, new DefaultFontMapper());
		Rectangle2D r2d2 = new Rectangle2D.Double(0, 0, width, height);
		getIecChart(themeDisplay).draw(graphics2d, r2d2);
		graphics2d.dispose();
		cb.addTemplate(template, 200, 100);
        
        document.close();
    }
	
	
	private void addCellTable(String text, Font font, Color backGroundColor, PdfPTable table) {
		PdfPCell cell = new PdfPCell(new Paragraph(text, font));
		cell.setBackgroundColor(backGroundColor);
    	cell.setBorder(PdfCell.NO_BORDER);
    	cell.setVerticalAlignment(PdfCell.ALIGN_MIDDLE);
    	table.addCell(cell);
	}
	
	
	private JFreeChart getIecChart(ThemeDisplay themeDisplay) throws SystemException {
		
		final CategoryDataset porcentajeModulos = getDataSetPercentajes(themeDisplay);
		
		String chartTitle = LanguageUtil.get(themeDisplay.getLocale(), "userprogress.export.pdf.title");
		String chartLabelLegend = LanguageUtil.get(themeDisplay.getLocale(), "userprogress.export.pdf.chart.legend.label");
		String chartLabellLeft = LanguageUtil.get(themeDisplay.getLocale(), "userprogress.export.pdf.chart.label.left");

        final JFreeChart chart = ChartFactory.createBarChart(
        	chartTitle,					// title
        	chartLabelLegend,           // domain axis label
        	chartLabellLeft,            // range axis label
            porcentajeModulos,          // data
            PlotOrientation.VERTICAL,
            true,                     	// legend
            true,                     	// tooltips
            false                     	// url
        );

        chart.setBackgroundPaint(Color.white);

        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(222,222,222));
        plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
        plot.getRangeAxis(0).setRange(0, 100);
        
        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
        plot.getRenderer().setSeriesPaint(0, gradientGray);
        
        final CategoryDataset horasRestantes = getDataSetTimeLeft(themeDisplay);
        plot.setDataset(1, horasRestantes);
        plot.mapDatasetToRangeAxis(1, 1);
        
        final ValueAxis axis2 = new NumberAxis(LanguageUtil.get(themeDisplay.getLocale(), "userprogress.export.pdf.chart.label.right"));
        plot.setRangeAxis(1, axis2);
        
        final LineAndShapeRenderer renderer2 = new LineAndShapeRenderer();
        renderer2.setBaseLinesVisible(false);
        renderer2.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
        plot.setRenderer(1, renderer2);
        plot.getRenderer(1).setSeriesPaint(0, gradientBlue);
        
        // Mostrar el primer dataset por debajo del resto de dataset
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
        		
		return chart;
	}
	
	
	private DefaultCategoryDataset getDataSetPercentajes(ThemeDisplay themeDisplay) throws SystemException{
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
		List<Module> modules=ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
		if(Validator.isNotNull(modules) && modules.size() > 0){
			String label = LanguageUtil.get(themeDisplay.getLocale(), "userprogress.export.pdf.chart.label.left");
			for (Module module : modules) {
				ModuleResult moduleResult=ModuleResultLocalServiceUtil.getByModuleAndUser(module.getModuleId(),themeDisplay.getUserId());
				double porcentaje = (moduleResult != null && moduleResult.getResult() > 0) ? moduleResult.getResult() : 0; 
				dataset.addValue(porcentaje, label, module.getTitle(themeDisplay.getLocale()));
			}
		}
		
		return dataset;
	}
	
	
	private DefaultCategoryDataset getDataSetTimeLeft(ThemeDisplay themeDisplay) throws SystemException{
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
		List<Module> modules=ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
		if(Validator.isNotNull(modules) && modules.size() > 0){
			String label = LanguageUtil.get(themeDisplay.getLocale(), "userprogress.export.pdf.chart.label.right");
			Calendar calActual = Calendar.getInstance(themeDisplay.getUser().getTimeZone(), themeDisplay.getUser().getLocale());
			Calendar calModule = Calendar.getInstance(themeDisplay.getUser().getTimeZone(), themeDisplay.getUser().getLocale());
			for (Module module : modules) {
				calModule.setTime(module.getEndDate());
            	long difMiliseconds = calModule.getTimeInMillis() - calActual.getTimeInMillis();
            	long dias = 0;
            	if(difMiliseconds > 0) {
            		dias = Math.abs(difMiliseconds/(3600000*24));
            	}
				dataset.addValue(dias, label, module.getTitle(themeDisplay.getLocale()));
			}
		}
		
		return dataset;
	}
	
	
	private void loadFonts() {
		
        ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),"portletClassLoader");
        String pathNunitoDir = classLoader.getResource("default-fonts").toString()
        		+ "nunito" + System.getProperty("file.separator")
        		+ "ttf" + System.getProperty("file.separator");
        
        FontFactory.register(pathNunitoDir + "Nunito-Bold.ttf", "nunito-bold");
        FontFactory.register(pathNunitoDir + "Nunito-Light.ttf", "nunito-light");
        FontFactory.register(pathNunitoDir + "Nunito-Regular.ttf", "nunito-regular");
        
        fontTitle.setFamily("nunito");
        fontTitle.setSize(14);
        fontTitle.setColor(blanco);
        fontTitle.setStyle(Font.BOLD);
        
        fontNormal.setFamily("nunito");
        fontNormal.setSize(12);
        fontNormal.setColor(negro);
        
        fontHeaders.setFamily("nunito");
        fontHeaders.setSize(11);
        fontHeaders.setColor(negro);
        fontHeaders.setStyle(Font.BOLD);
        
        fontTitleModule.setFamily("nunito");
        fontTitleModule.setSize(12);
        fontTitleModule.setColor(rojo);
	}

	
	private Image getLogoImg(HttpServletRequest request, ThemeDisplay themeDisplay){
		Image image = null;
		try {
			Company company = CompanyLocalServiceUtil.fetchCompany(themeDisplay.getCompanyId());
		
			
			if(company!=null) {
				long logoId = company.getLogoId();
				if(logoId!=0) {
					image = ImageLocalServiceUtil.fetchImage(logoId);
					
				}
			}
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
	}
}