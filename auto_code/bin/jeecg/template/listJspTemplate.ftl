#set( $elS = '${')
#set( $elE = '}')
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html>
<head>
<%@ include file="/common/meta.jsp"%>
<title>${codeName}-列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/style/base.css">
<link rel="stylesheet" type="text/css" href="${ctx}/style/page.css">
<script src="${ctx}/script/jquery-1.8.1.min.js"></script>
</head>
<body>
<div class="sitemap">您现在的位置：${codeName}管理 > ${codeName}列表</div>
<div class="mb20"><a href="#" class="btn_addUser dib" id="addBtn_id">添加</a></div>
<form name="listForm" id="listForm_id" action="${ctx}/${entityPackage}/${lowerName}/list.htm" method="post" class="form_s clearfix mb15">
	<input id="nowPage_id" name="nowPage" type="hidden" value="${page.nowPage}">
	<input id="pageSize_id" name="pageSize" type="hidden" value="${page.pageSize}">
	<em class="tit">搜索</em>
	#foreach($po in $!{columnDatas})
	#if($po.key =='PRI')
	#elseif($po.columnType =='java.util.Date')
	<input name="${po.voColumnName}" id="${po.voColumnName}_id" value="<fmt:formatDate value="${elS}page.${po.voColumnName}${elE}" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text" class="txt fl ml_15" placeholder="${po.columnComment}" onclick="WdatePicker()" readonly="readonly">
	#else
	<input name="${po.voColumnName}" id="${po.voColumnName}_id" value="${elS}page.${po.voColumnName}${elE}" type="text" class="txt fl ml_15" placeholder="${po.columnComment}">
	#end
	#end
    <input type="button" value="搜索" id="searchBtn_id" class="btn fl ml_15">
</form>
<table class="tb tc" width="100%">
	<thead>
    	<tr>
    		<th>序号</th>
    		#foreach($po in $!{columnDatas})
        	#if($po.key =='PRI')
			#else
            <th>${po.columnComment}</th>
			#end
			#end
			<th>操作</th>
        </tr>
    </thead>
    <tbody>
    	<c:forEach items="${page.list}" var="li" varStatus="livs">
    	<tr>
        	<td>${livs.count}</td>
    		#foreach($po in $!{columnDatas})
			#if($po.key =='PRI')
			#elseif($po.columnType =='java.util.Date')
			<td><fmt:formatDate value="${elS}li.${po.voColumnName}${elE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			#else
            <td>${elS}li.${po.voColumnName}${elE}</td>
			#end
			#end
            <td class="opt">
            <a href="javaScript:toEdit('${li.id}')" class="edit">编辑</a>
            <a href="javaScript:toDel('${li.id}')" class="edit">删除</a>
            </td>
        </tr>
    	</c:forEach>
    </tbody>
</table>
<%@ include file="/common/page.jsp"%>
</body>
</html>
<script src="${ctx}/script/popup.js"></script>
<script src="${ctx}/script/common.js"></script>
<script src="${ctx}/script/My97DatePicker/WdatePicker.js"></script>
<script>
function toPage(pageno){
	$("#nowPage_id").val(pageno);
	$("#listForm_id").submit();
}
function toEdit(id){
	document.location.href="${ctx}/${entityPackage}/${lowerName}/input.htm?id="+id;
}
function toDel(id){
	showConfirm(null,"确定","确定删除？",function(){
		document.location.href="${ctx}/${entityPackage}/${lowerName}/delete.htm?id="+id;
	});
}
$(function(){
	$("#searchBtn_id").click(function(){
		toPage(1);
	});
	
	$("#addBtn_id").click(function(){
		document.location.href="${ctx}/${entityPackage}/${lowerName}/input.htm";	
	});
})
</script>