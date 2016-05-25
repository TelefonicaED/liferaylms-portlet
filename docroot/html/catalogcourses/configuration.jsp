<%@page import="java.util.Enumeration"%>
<%@page import="com.liferay.portal.kernel.util.KeyValuePairComparator"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.liferay.portlet.asset.NoSuchVocabularyException"%>
<%@page import="com.liferay.portlet.asset.model.AssetVocabulary"%>
<%@page import="com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.KeyValuePair"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.liferay.portal.service.PortletPreferencesLocalServiceUtil"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="com.liferay.portal.kernel.util.StringPool"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@include file="/init.jsp"%>

<%
PortletPreferences preferences = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}
long[] groupIds = {themeDisplay.getScopeGroupId()};

long[] assetVocabularyIds = StringUtil.split(preferences.getValue("assetVocabularyIds", ""),",", 0L);

List<AssetVocabulary> listAssetVocabulary = AssetVocabularyLocalServiceUtil.getGroupsVocabularies(groupIds);

long[] availableAssetVocabularyIdsSet = new long[listAssetVocabulary.size()];
	
for(int i = 0; i < listAssetVocabulary.size(); i++){
	availableAssetVocabularyIdsSet[i] = listAssetVocabulary.get(i).getVocabularyId();
}


List<KeyValuePair> typesLeftList = new ArrayList<KeyValuePair>();

for (long vocabularyId : assetVocabularyIds) {
	try {
		AssetVocabulary vocabulary = AssetVocabularyLocalServiceUtil.getVocabulary(vocabularyId);

		vocabulary = vocabulary.toEscapedModel();

		typesLeftList.add(new KeyValuePair(String.valueOf(vocabularyId), vocabulary.getTitle(themeDisplay.getLocale())));
	}
	catch (NoSuchVocabularyException nsve) {
	}
}

// Right list

List<KeyValuePair> typesRightList = new ArrayList<KeyValuePair>();

Arrays.sort(assetVocabularyIds);

for (long vocabularyId : availableAssetVocabularyIdsSet) {
	if (Arrays.binarySearch(assetVocabularyIds, vocabularyId) < 0) {
		AssetVocabulary vocabulary = AssetVocabularyLocalServiceUtil.getVocabulary(vocabularyId);

		vocabulary = vocabulary.toEscapedModel();

		typesRightList.add(new KeyValuePair(String.valueOf(vocabularyId), vocabulary.getTitle(themeDisplay.getLocale())));
	}
}

typesRightList = ListUtil.sort(typesRightList, new KeyValuePairComparator(false, true));

boolean showVocabularies = GetterUtil.getBoolean(preferences.getValue("showVocabularies", StringPool.FALSE));
boolean showTags = GetterUtil.getBoolean(preferences.getValue("showTags", StringPool.FALSE));
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="preferences--assetVocabularyIds--" type="hidden" />
	
	<aui:input name="preferences--showVocabularies--" type="checkbox" value="<%=showVocabularies %>" label="show-vocabularies"/>
	
	<liferay-ui:input-move-boxes
				leftBoxName="currentAssetVocabularyIds"
				leftList="<%= typesLeftList %>"
				leftTitle="current"
				rightBoxName="availableAssetVocabularyIds"
				rightList="<%= typesRightList %>"
				rightTitle="available"
			/>
			
	<aui:input name="preferences--showTags--" type="checkbox" value="<%=showTags %>" label="show-tags"/>
	
	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />saveConfiguration',
		function(){
			if (document.<portlet:namespace />fm.<portlet:namespace />assetVocabularyIds) {
				document.<portlet:namespace />fm.<portlet:namespace />assetVocabularyIds.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentAssetVocabularyIds);
			}

			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);

</aui:script>
