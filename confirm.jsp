<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include/include.Taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<base target="_self" />
<s:include value="/WEB-INF/pages/include/include.Scripts.jsp" />
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.gridList.plugin.js"/>"></script>
<script language="javascript">
$(document).ready(function() {
    $("#tblGrid").initGrid({height:'480'});   
});
</script>
</head>
<body>
<s:form method="post" theme="simple" action="%{progAction}">
	<s:hidden name="labFilmMst.ver" />
	<div class="progTitle"> 
       <!-- 程式標題 --> <s:include value="/WEB-INF/pages/include/include.ConfirmTitle.jsp" /> <!-- 程式標題 -->
    </div>
    <div id="tb">
    <table width="100%" border="0" cellpadding="4" cellspacing="0" >
			<tr class="trBgOdd">
				<td width="20%" class="colNameAlign required">*<s:text name="filmId" />：</td>
				<td width="30%">
					<s:property  value="labFilmMst.filmId" />
					<s:hidden name="labFilmMst.filmId"/></td>
				<td width="20%" class="colNameAlign required">*<s:text name="filmName" />：</td>
				<td width="30%">
					<s:property value="labFilmMst.filmName" />
					<s:hidden name="labFilmMst.filmName"/></td>
			</tr>
			<tr class="trBgEven">
				<td width="20%" class="colNameAlign required">*<s:text name="fmCorpId" />：</td>
				<td width="30%">
					<s:property  value="labFilmMst.labFilmCorpMst.fmCorpId" />&nbsp;-&nbsp;
					<s:property  value="labFilmMst.labFilmCorpMst.fmCorpName" />
					<s:hidden name="labFilmMst.labFilmCorpMst.fmCorpId"/>
					<s:hidden name="labFilmMst.labFilmCorpMst.fmCorpName"/>
				</td>
				<td width="20%" class="colNameAlign required">*<s:text name="filmHot" />：</td>
				<td width="30%">
					<span>
						<input type="radio" id="filmHotY" name="labFilmMst.filmHot" value="Y" disabled <s:if test='labFilmMst.filmHot == "Y"'>checked</s:if> /><s:label for="filmHotY"><s:text name="filmHot.Y" /></s:label>
						<input type="radio" id="filmHotN" name="labFilmMst.filmHot" value="N" disabled <s:if test='labFilmMst.filmHot == "N"'>checked</s:if> /><s:label for="filmHotN"><s:text name="filmHot.N" /></s:label>
					</span>
					<s:hidden name="labFilmMst.filmHot" />
				</td>
			</tr>
			<tr class="trBgOdd">
				<td width="20%" class="colNameAlign required">*<s:text name="filmGrade" />：</td>
				<td width="30%">
					<s:property  value="labFilmMst.filmGrade" />&nbsp;-&nbsp;
					<s:property value="labFilmMst.filmgrade.optCdeNam" />
					<s:hidden name="labFilmMst.filmGrade"/>
					<s:hidden name="labFilmMst.filmgrade.optCdeNam"/>
				</td>
				<td width="20%" class="colNameAlign required">*<s:text name="filmCate" />：</td>
				<td width="30%">
					<s:property value="labFilmMst.filmCate.optCde" />&nbsp;-&nbsp;
					<s:property value="labFilmMst.filmCate.optCdeNam" />
					<s:hidden name="labFilmMst.filmCate.optCde"/>
					<s:hidden name="labFilmMst.filmCate.optCdeNam" />
				</td>
			</tr>
			<tr class="trBgEven">
				<td width="20%" class="colNameAlign required">*<s:text name="filmQty" />：</td>
				<td colspan="3">
					<s:property  value="labFilmMst.filmQty" />
					<s:hidden name="labFilmMst.filmQty"/>
				</td>
			</tr>
		</table>
	<table width="100%" border="0" cellpadding="4" cellspacing="0" >
		<tbody>
		</tbody> 
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
			<s:iterator value="membGradeList" status="status">
	        <tr>
	            <td width="35%">
	            	<label><s:property value="optCde" /></label>&nbsp;-&nbsp;
	            	<label><s:property value="optCdeNam" /></label>
	            </td>
	            <td width="33%">
		            <s:label id="%{'rentHotAmt_' + #stat.index}" value="%{labFilmMst.labFilmRentMstList[#status.index].rentHotAmt}" />
	            	<s:hidden name="%{'labFilmMst.labFilmRentMstList['+#status.index+'].rentHotAmt'}" />
	            </td>
	            <td>
		            <s:label id="%{'rentAmt_' + #stat.index}" value="%{labFilmMst.labFilmRentMstList[#status.index].rentAmt}" />
	            	<s:hidden name="%{'labFilmMst.labFilmRentMstList['+#status.index+'].rentAmt'}" />
	            </td>
	            <td style="display: none;">
	            	<s:hidden name="%{'labFilmMst.labFilmRentMstList['+#status.index+'].membGrade.optCdeOid'}"  />            
	            </td>
	        </tr>
	        </s:iterator>
	    </tbody>
    </table>
    </fieldset>
    </div>
    <!-- 按鍵組合 -->
        <s:include value="/WEB-INF/pages/include/include.ConfirmButton.jsp" /> 
    <!-- 按鍵組合 -->
</s:form>
</body>
</html>