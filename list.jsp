<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/include/include.Taglib.jsp"%>
<html>
<head>
<title></title>
<s:include value="/WEB-INF/pages/include/include.Scripts.jsp" />
<script type="text/javascript" src="<s:url value="/jquery/ui/jquery.ui.datepicker.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/jquery/jquery.alphanumeric.js"/>"></script>
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.gridList.plugin.js"/>"></script>
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.popupWindow.plugin.js"/>"></script>	
<script type="text/javascript" src="<s:url value="/js/ddsc.input.js"/>"></script>
<script type="text/javascript">
function getParameter() {
	var param = "labFilmMst.filmId=" + $("#tblGrid").getSelectedRow().find('td').eq(2).text();
	return param;
}
$(document).ready(function() {
	$("#tblGrid").initGrid({lines:3});
	$('#tb').initPopupWindow({dailogWidth:'960', dailogHeight:'640'});
	
});
</script>
</head>
<body> 
<s:form id="frm0A002K" theme="simple" action="%{progAction}" >
	<div class="progTitle">
  		<s:include value="/WEB-INF/pages/include/include.Title.jsp" />
	</div>
	<div id="tb">
		<fieldset id="listFieldset">
		<table width="100%" border="0" cellpadding="2" cellspacing="0">
			<tr class="trBgOdd">
				<td width="20%" class="colNameAlign">&nbsp;<s:text name="filmId"/>：</td>
				<td width="30%"><s:textfield name="labFilmMst.filmId" cssClass="enKey" maxlength="32" size="16"/></td>
				<td width="20%" class="colNameAlign">&nbsp;<s:text name="filmName"/>：</td>
				<td width="30%"><s:textfield name="labFilmMst.filmName" maxlength="32" size="32"/></td>
			</tr>
			<tr class="trBgEven">
				<td width="20%" class="colNameAlign">&nbsp;<s:text name="filmHot"/>：</td>
				<td width="30%">
					<input type="checkbox" id="filmHotY" name="filmHotList" value="Y"
					<s:if test="labFilmMst.filmHot == \"Y\"">checked</s:if> />
					<s:label for="filmHotY"><s:text name="filmHot.Y" /></s:label>
				</td>
			</tr>
			<tr class="trBgOdd">
				<td width="20%" class="colNameAlign">&nbsp;<s:text name="filmCate"/>：</td>
				<td colspan="3">
					<s:iterator value="flimCateList" status="status" var="obj">
						<input type="checkbox" id="filmCate_<s:property value="#status.index" />" name="flimCateList[<s:property value="#status.index" />]" value="<s:property value="optCde" />"
						<s:if test="flimCateList[#status.index] == optCde">checked</s:if> />
						<s:property value="#obj.optCdeNam" />
					</s:iterator>
				</td>
			</tr>
		</table>
		<!-- 按鍵組合 --><s:include value="/WEB-INF/pages/include/include.ListButton.jsp" /><!-- 按鍵組合 --> 
		</fieldset>
		<table id="tblGrid" width="100%" border="0" cellpadding="2" cellspacing="1">
			<thead>
				<tr align="center" bgcolor="#e3e3e3">
					<th width="3%"><s:text name="fix.00164" /></th>
					<th width="10%"><s:text name="fix.00090" /></th>
					<th width="14%"><s:text name="filmId" /></th>   
					<th width="14%"><s:text name="filmName" /></th> 
					<th width="14%"><s:text name="filmGrade" /></th>
					<th width="14%"><s:text name="filmHot" /></th>
					<th><s:text name="filmCate" /></th>
				</tr>
			 </thead>
			 <tbody>
				 <s:iterator value="labFilmMstList" status="status">
				 	<tr>
						<td width="3%" id="sn" align="center"><s:property value="#status.index+1" /></td>
						<!-- 表單按鍵 --> 
						<td width="10%"><s:include value="/WEB-INF/pages/include/include.actionButton.jsp" /></td>
						<!-- 表單按鍵 -->
						<td width="14%"><label><s:property value="FILM_ID" /></label></td>
						<td width="14%"><label><s:property value="FILM_NAME" /></label></td>
						<td width="14%"><label><s:property value="OPT_GRADE_CDE" /></label>&nbsp;-&nbsp;<label><s:property value="OPT_GRADE_CDE_NAME" /></label></td>	
						<td width="14%">
							<label><s:property value="FILM_HOT" /></label>&nbsp;-&nbsp;
								<s:if test="FILM_HOT == \"N\"" ><s:text name="filmHot.N" /></s:if>
								<s:elseif test="FILM_HOT == \"Y\""><s:text name="filmHot.Y" /></s:elseif>
							</td>
						<td><label><s:property value="OPT_CATE_CDE" /></label>&nbsp;-&nbsp;<label><s:property value="OPT_CATE_CDE_NAME" /></label></td>
					</tr>
				 </s:iterator>
			 </tbody>
		</table>
	</div>
	<!-- 分頁按鍵列 --><s:include value="/WEB-INF/pages/include/include.PaginationBar.jsp" /><!-- 分頁按鍵列 -->
</s:form>
</body>
</html>