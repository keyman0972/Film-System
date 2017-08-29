<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include/include.Taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<base target="_self" />
<s:include value="/WEB-INF/pages/include/include.Scripts.jsp" />
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.gridEditList.plugin.js"/>"></script>
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.validation.plugin.js"/>"></script>
<script type="text/javascript" src="<s:url value="/jquery/jquery.alphanumeric.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/ddsc.input.js"/>"></script>
<script language="javascript">
var oTable;
//畫面欄位檢核
function validate() {
	$("#frm0A002K").validate("clearPrompt"); 
	
	
	$("#filmId").validateRequired({fieldText:'<s:text name="filmId" />'});
	$("#filmName").validateRequired({fieldText:'<s:text name="filmName" />'});
	$("#fmCorpId").validateRequired({fieldText:'<s:text name="fmCorpId" />'});
	$("#filmHotY").validateRequired({fieldText:'<s:text name="filmHot" />'});
	$("#filmGrade").validateRequired({fieldText:'<s:text name="filmGrade" />'});
	$("#filmCate").validateRequired({fieldText:'<s:text name="filmCate" />'});
	$("#filmQty").validateRequired({fieldText:'<s:text name="filmQty" />'});
	
	
	for(var i=0;i<$("#tbGrid tr").length;i++){
		$("#rentHotAmt_"+i).validateRequired({fieldText:'<s:text name="rentHotAmt" />', contactMessage:getRowNumText(i+1)});
		$("#rentAmt_"+i).validateRequired({fieldText:'<s:text name="rentAmt" />', contactMessage:getRowNumText(i+1)});
	}
	<%-- --%>
	<%--
	<s:iterator value="membGradeList" status="status" var="obj">
		$("#rentHotAmt_<s:property value="#status.index"/>").validateRequired({fieldText:'<s:text name="rentHotAmt" />', contactMessage:getRowNumText(i+1)});
		$("#rentAmt_<s:property value="#status.index"/>").validateRequired({fieldText:'<s:text name="rentAmt" />', contactMessage:getRowNumText(i+1)});
	</s:iterator>
	 --%>
    return $("#frm0A002K").validate("showPromptWithErrors");
}
function ajaxFmCorpName(){
	var fmCorpId = $("#fmCorpId").val();
	if(fmCorpId != null){
    	$.ajax({
      		type: 'post',
	      	url:'<s:url value="/ajax/ajaxQuery/queryDataByParams.action" />',
	      	data:{queryName: 'findLabfmCorpMstkey', params: '{fmCorpId: "' + fmCorpId + '"}'},
	      	success: function (rtn_data) {
	      		if(rtn_data.results.length == 1 && rtn_data.results[0] != "" && rtn_data.results[0] != null){
			  		$('#fmCorpName').html(rtn_data.results[0][1]);
			  		$("#hiddfmCorpName").val(rtn_data.results[0][1]);
	      		}else{
	      			$('#fmCorpName').html("<s:text name="eC.0037"/>");
	      		}
		 	}
    	});
	}else{
		$('#fmCorpName').html("");
	}
}
$(document).ready(function() {
	oTable = $('#tblGrid').initEditGrid({height:'480'});
	
	$("#fmCorpId").bind("change",ajaxFmCorpName);
	

});
</script>
</head>
<body>
<s:form id="frm0A002K" method="post" theme="simple" action="%{progAction}" target="ifrConfirm">
<s:hidden name="labFilmMst.ver" />
 	<div class="progTitle"> 
		<!-- 程式標題 --> <s:include value="/WEB-INF/pages/include/include.EditTitle.jsp" /> <!-- 程式標題 -->
    </div>
    <div id="tb">
    <table width="100%" border="0" cellpadding="4" cellspacing="0" >
		<tr class="trBgOdd">
			<td width="20%" class="colNameAlign required">*<s:text name="filmId" />：</td>
			<td width="30%">
				<s:textfield id="filmId" name="labFilmMst.filmId" maxlength="16" size="20" cssClass="enKey" />
			</td>
			<td width="20%" class="colNameAlign required">*<s:text name="filmName" />：</td>
			<td width="30%">
				<s:textfield id="filmName" name="labFilmMst.filmName" maxlength="64" size="32" />
			</td>
		</tr>
		<tr class="trBgEven">
			<td width="20%" class="colNameAlign required">*<s:text name="fmCorpId" />：</td>
			<td width="30%">
				<s:textfield id="fmCorpId" name="labFilmMst.labFilmCorpMst.fmCorpId" cssClass="enKey" size="16" maxlength="16" />
				<input type="image" id="imgCorpId" src="<s:url value="/image_icons/search.png"/>" />
				<s:label id="fmCorpName" name="labFilmMst.labFilmCorpMst.fmCorpName" />
				<s:hidden id="hiddfmCorpName" name="%{'labFilmMst.labFilmCorpMst.fmCorpName'}" value="%{labFilmMst.labFilmCorpMst.fmCorpName}" />
			</td>
			<td width="20%" class="colNameAlign required">*<s:text name="filmHot" />：</td>
			<td width="30%">
				<span>
					<input type="radio" id="filmHotY" name="labFilmMst.filmHot" value="Y" <s:if test='labFilmMst.filmHot == "Y"'>checked</s:if> /><s:label for="filmHotY"><s:text name="filmHot.Y" /></s:label>
					<input type="radio" id="filmHotN" name="labFilmMst.filmHot" value="N" <s:if test='labFilmMst.filmHot == "N"'>checked</s:if> /><s:label for="filmHotN"><s:text name="filmHot.N" /></s:label>
				</span>
			</td>
		</tr>
		<tr class="trBgOdd">
			<td width="20%" class="colNameAlign required">*<s:text name="filmGrade" />：</td>
			<td width="30%">
				<s:select id="filmGrade" name="labFilmMst.filmGrade" headerValue="%{getText('fix.00162')}" headerKey=""
					list="flimGradeList" listKey="optCde" listValue="optCde + '-' + optCdeNam" value="labFilmMst.filmGrade" />
			</td>
			<td width="20%" class="colNameAlign required">*<s:text name="filmCate" />：</td>
			<td width="30%">
				<s:select id="filmCate" name="labFilmMst.filmCate.optCde" headerValue="%{getText('fix.00162')}" headerKey=""
					list="flimCateList" listKey="optCde" listValue="optCde + '-' + optCdeNam" value="labFilmMst.filmCate.optCde" />
			</td>
		</tr>
		<tr class="trBgEven">
			<td width="20%" class="colNameAlign required">*<s:text name="filmQty" />：</td>
			<td colspan="3">
				<s:textfield id="filmQty" name="labFilmMst.filmQty" />
			</td>
		</tr>
	</table>
    <fieldset style="-moz-border-radius:4px;">
    <table id="tblGrid" width="100%" border="0" cellpadding="2" cellspacing="1">
        <thead>
            <tr align="center" bgcolor="#e3e3e3">
                <th width="35%"><s:text name="membGrade" /></th>
                <th width="33%"><s:text name="rentHotAmt" /></th>
                <th><s:text name="rentAmt" /></th>
                <th style="display: none;">&nbsp;</th>          
            </tr>
        </thead>
		<tbody id="tbGrid">
	        <s:iterator value="membGradeList" status="status" var="obj">
	        <tr>
	            <td width="35%" id="=td_<s:property value="#status.index" />" >
	            	<label id="optCde_<s:property value="#status.index" />"><s:property value="optCde" /></label>&nbsp;-&nbsp;
	            	<label id="optCdeNam_<s:property value="#status.index" />"><s:property value="optCdeNam" /></label>
	            </td>	
	            <td width="33%">
	            	<s:textfield id="%{'rentHotAmt_' + #status.index}" name="%{'labFilmMst.labFilmRentMstList['+#status.index+'].rentHotAmt'}" value="%{labFilmMst.labFilmRentMstList[#status.index].rentHotAmt}" />
	            </td>
	            <td>
	            	<s:textfield id="%{'rentAmt_' + #status.index}" name="%{'labFilmMst.labFilmRentMstList['+#status.index+'].rentAmt'}" value="%{labFilmMst.labFilmRentMstList[#status.index].rentAmt}" />
	            </td>
	            <td style="display: none;">
	            	<s:hidden name="%{'labFilmMst.labFilmRentMstList['+#status.index+'].membGrade.optCdeOid'}" value="%{#obj.optCdeOid}"  />            
	            </td>
	        </tr>
	        </s:iterator>
		</tbody>
    </table>
    </fieldset>
    </div>
	<!-- 按鍵組合 --> 
	<s:include value="/WEB-INF/pages/include/include.EditButton.jsp" />
	<!-- 按鍵組合 -->
</s:form>
<iframe id="ifrConfirm" name="ifrConfirm" width="100%" height="768" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="display:none; border: 0px none"></iframe>
</body>
</html>