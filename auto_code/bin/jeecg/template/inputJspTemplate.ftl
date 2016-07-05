#set( $elS = '${')
#set( $elE = '}')
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!doctype html>
<html>
<head>
<%@ include file="/common/meta.jsp"%>
<title>${codeName}-编辑</title>
<link rel="stylesheet" type="text/css" href="${ctx}/style/base.css">
<link rel="stylesheet" type="text/css" href="${ctx}/style/page.css">
<script src="${ctx}/script/jquery-1.8.1.min.js"></script>
</head>
<body>

<div class="sitemap">您现在的位置：${codeName}管理 > 编辑</div>

<div class="wrap">
	<form name="editForm" id="editForm_id" action="${ctx}/${entityPackage}/${lowerName}/save.htm" method="post" class="form form2">
		#foreach($po in $!{columnDatas})
		#if($po.key =='PRI')
		<input id="id_id" name="id" type="hidden" value="${vo.id}">
		#elseif($po.columnType =='java.util.Date')
		<div class="item"><label class="lab">${po.columnComment}：</label><div class="info"><input name="${po.voColumnName}" id="${po.voColumnName}_id" value="<fmt:formatDate value="${elS}vo.${po.voColumnName}${elE}" pattern="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker()" readonly="readonly" type="text" class="txt"></div></div>
		#else
		<div class="item"><label class="lab">${po.columnComment}：</label><div class="info"><input name="${po.voColumnName}" id="${po.voColumnName}_id" value="${elS}vo.${po.voColumnName}${elE}" type="text" class="txt"></div></div>
		#end
		#end
		<div class="item pt10">
			<input type="submit" class="btn btnSubmit" value="提交">
		</div>
	</form>
</div>

</body>
</html>
<script src="${ctx}/script/formValid.js"></script>
<script src="${ctx}/script/My97DatePicker/WdatePicker.js"></script>
<%@ include file="/common/jquery.validate.jsp"%>
<script>
$(function(){
	${jspValidate}
});
</script>