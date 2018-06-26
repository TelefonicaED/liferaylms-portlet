<%@include file="/init-min.jsp" %>

<liferay-ui:search-toggle
	buttonLabel="search"
	displayTerms="${displayTerms}"
	id="${renderResponse.getNamespace()}toggle_register_search"
>
	<%@include file="/html/search/userSearch.jsp" %>
	
</liferay-ui:search-toggle>
