<%@page import="com.liferay.portal.kernel.exception.NestableException"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.language.LanguageUtil"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="com.liferay.portal.kernel.util.StringPool"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>
<%@page import="com.liferay.portal.kernel.xml.Node"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portal.kernel.xml.DocumentException"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@ include file="/init.jsp" %>
<%

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
LearningActivity learningActivity = (LearningActivity)row.getObject();
String typeId=((Map<Long,String>)request.getAttribute("types")).get((long)learningActivity.getTypeId());

List<String> headers= new ArrayList<String>();
List<String> values= new ArrayList<String>();
LearningActivity prefedenceActivity=null;

if(learningActivity.getPrecedence()!=0) {
	try{
		prefedenceActivity=LearningActivityLocalServiceUtil.getLearningActivity(learningActivity.getPrecedence());

		String title=prefedenceActivity.getTitle(themeDisplay.getLocale());
		int maxNameLength=GetterUtil.getInteger(LanguageUtil.get(pageContext, "coursestats.modulestats.large.name.length"),20);
		
		if(title.length()>maxNameLength) {
			title="<span title='"+title+"'>"+LanguageUtil.format(pageContext, "coursestats.modulestats.large.name", new Object[]{title.subSequence(0, maxNameLength+1)},false)+"</span>";
		}
		
		headers.add(LanguageUtil.get(pageContext, "coursestats.modulestats.dependencies"));
		values.add(title);
	}
	catch(NestableException e){}
}

if((learningActivity.getExtracontent()!=null)&&(learningActivity.getExtracontent().length()!=0)&&(learningActivity.getTypeId()!=8)) {
	try{
	
		Iterator<Element> nodes= SAXReaderUtil.read(learningActivity.getExtracontent()).getRootElement().elementIterator();
		
		while(nodes.hasNext()){
			Element node =(Element)nodes.next();
			if(node.getText().length()!=0) {
				String headerKey="coursestats.modulestats."+typeId+"."+node.getName();
				headers.add(LanguageUtil.get(pageContext, headerKey));
				String extraFieldType=LanguageUtil.get(pageContext, headerKey+".type");
				String value=null;
				if("seconds".equals(extraFieldType)) {
					try{
						long seconds = Long.parseLong(node.getTextTrim());
						NumberFormat timeNumberFormat = NumberFormat.getInstance(locale);
						timeNumberFormat.setMinimumIntegerDigits(2);
						value=timeNumberFormat.format(seconds / 3600)+StringPool.COLON+
							  timeNumberFormat.format((seconds % 3600) / 60)+StringPool.COLON+
							  timeNumberFormat.format(seconds % 60);
					}
					catch(NumberFormatException e) {
						value=LanguageUtil.get(pageContext, node.getTextTrim());
					}
				}
				else {
					value=LanguageUtil.get(pageContext, node.getTextTrim());
				}
					
				
				values.add(value);
			}
		}
	}
	catch(DocumentException e){}
}


if(headers.size()!=0) {
%>

<script type="text/javascript">
<!--

AUI().ready('aui-aria', 'aui-overlay-context-panel', 'aui-overlay-manager', function(A) {
	/*
	* Simple ContextPanel
	*/
	var contextPanel1 = new A.OverlayContextPanel({
		bodyContent: '<table ><tbody><tr class="results-header">'+
					  <%for(String header:headers) { %>
						'<th><%=header%></th>'+
						<% } %>
			         '</tr><tr class="results-row">'+
					  <% for(String value:values) { %>
						'<td class="align-left valign-middle"><%=value%></td>'+
						<% } %>
			         '</tr>'+
			         '</tbody></table>',
		trigger: '#<portlet:namespace/>trigger_<%= Long.toString(learningActivity.getActId()) %>',
		boundingBox: '#<portlet:namespace/>panel_<%= Long.toString(learningActivity.getActId()) %>',
		anim: true,
		showOn:'mouseover',
		hideOn:'mouseout',
		align: { node: null, points: [ 'tr', 'br' ] }
	})
	.render();

	});

//-->
</script>



	<img id="<portlet:namespace/>trigger_<%= Long.toString(learningActivity.getActId()) %>" src="${themeDisplay.pathThemeImages}/custom/icono_mas.png"/>
<div id="<portlet:namespace/>panel_<%= Long.toString(learningActivity.getActId()) %>" class="lfr-panel-container"></div>

<%  }
 %>