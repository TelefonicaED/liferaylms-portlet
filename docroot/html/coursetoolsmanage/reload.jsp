<%@include file="/init.jsp"%>

<portlet:renderURL var="recargarURL"></portlet:renderURL>

<script>
location.href="<%= recargarURL %>";
</script>
