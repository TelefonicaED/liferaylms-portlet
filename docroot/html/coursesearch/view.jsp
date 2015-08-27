<%@page import="com.liferay.portal.kernel.search.IndexerRegistryUtil"%>
<%@page import="com.liferay.portal.kernel.search.Indexer"%>
<%@page import="com.liferay.lms.indexer.CourseIndexer"%>
<%@page import="com.liferay.portal.kernel.search.SearchContext"%>
<%@page import="com.liferay.portal.kernel.search.SearchContextFactory"%>
<%@page import="com.liferay.portlet.asset.model.AssetRenderer"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@ include file="/init.jsp" %>

<jsp:include page="/html/coursesearch/searchform.jsp"></jsp:include>
