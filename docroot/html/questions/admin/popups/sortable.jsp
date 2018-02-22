<%@ include file="/init.jsp" %>
<%
Long iterator = ParamUtil.getLong(request, "iterator", -1);
%>

	<div id="<portlet:namespace />answerError_new<%=iterator%>" class="aui-helper-hidden portlet-msg-error">
		<liferay-ui:message key="answer-test-required"/>
	</div>
	<liferay-ui:panel id="<%=\"panel_new\"+iterator %>" title="<%=iterator+\")\" %>" collapsible="true" extended="true" defaultState="open" persistState="false">
		<div class="leftSideAnswer">
			<aui:input  type="hidden" name="answerId" value="<%=\"new\"+iterator %>"></aui:input>
			<aui:input  type="hidden" name="iterator" value="<%=iterator%>"></aui:input>
			<aui:input  type="hidden" name="<%= \"correct_new\"+iterator %>" label="correct" value="true"></aui:input>			
			<script type="text/javascript">
				function <portlet:namespace />onChangeTextAnswer_new<%=iterator %>(val) {
		        	var A = AUI();
					A.one('#<portlet:namespace />answer_new<%=iterator %>').set('value',val);
		        }
			</script>
			<aui:field-wrapper label="">
				<liferay-ui:input-editor skipEditorLoading="true"  toolbarSet="actslimmer" height="15"  name="<%=\"answer_new\"+iterator %>" width="80%" onChangeMethod="<%=\"onChangeTextAnswer_new\"+iterator %>" initMethod="<%=\"initEditorAnswer_new\"+iterator %>" />
				<script type="text/javascript">
			        function <portlet:namespace />initEditorAnswer_new<%=iterator %>() { 
			            return "";
			        }
			    </script>
		    </aui:field-wrapper>
			<aui:input type="hidden" cssClass="input-comment" name="<%=\"feedbackCorrect_new\"+iterator %>" label="feedback" size="60">
			</aui:input>
		</div>
		<div class="rightSideAnswer">
			<span class="newitem2"><a href="#" class="newitem2" onclick="<portlet:namespace />deleteNode('testAnswer_new<%=iterator %>');"><liferay-ui:message key="delete"/></a></span>
		</div>
	</liferay-ui:panel>
	