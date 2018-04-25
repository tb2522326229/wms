<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
<link href="/style/common_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/js/jquery/jquery.js"></script>
<script type="text/javascript" src="/js/artDialog/jquery.artDialog.js?skin=blue"></script>
<script type="text/javascript" src="/js/commonAll.js"></script>
<title>PSS-采购订单管理</title>
<style>
.alt td {
	background: black !important;
}
</style>
<script type="text/javascript">
	$(function(){
		$(":input[name='qo.keyword']").attr("placeholder","货品名称/货品编码")
	});
	
</script>
</head>

<body>
	<form id="searchForm" action="productStock.action" method="post">
		<div id="container">
			<div class="ui_content">
				<div class="ui_text_indent">
					<div id="box_border">
						<div id="box_top">搜索</div>
						<div id="box_center">
							货品：
							<s:textfield name="qo.keyword" cssClass="ui_input_txt02"/>
							仓库：
	                        <s:select name="qo.depotId" list="#depots" listKey="id" listValue="name"
	                                  headerKey="-1" headerValue="全部" cssClass="ui_select01"/>
							品牌：
	                        <s:select name="qo.brandId" list="#brands" listKey="id" listValue="name"
	                                  headerKey="-1" headerValue="全部" cssClass="ui_select01"/>
	                       	阈值
	                        <s:textfield name="qo.storeLimit" cssClass="ui_select01"/>
	                        
							<input type="button" value="查询" class="ui_input_btn01 btn_page" data-page="1"/>
                    	</div>
					</div>
				</div>
			</div>
			<div class="ui_content">
				<div class="ui_tb">
					<table class="table" cellspacing="0" cellpadding="0" width="100%"
						align="center" border="0">
						<tr>
							<th>货品编号</th>
                       	 	<th>仓库</th>
                       	 	<th>货品名称</th>
                        	<th>品牌</th>
                        	<th>库存价格</th>
                        	<th>库存数量</th>
                        	<th>库存总金额</th>
							<th></th>
						</tr>
						<tbody>
						<s:iterator value="#pageResult.result">
							<tr>
									<td><s:property value="id"/></td>
									<td><s:property value="depot.name"/></td>
									<td><s:property value="product.name"/></td>
									<td><s:property value="product.brand.name"/></td>
									<td><s:property value="price"/></td>
									<td><s:property value="storeNumber"/></td>
									<td><s:property value="amount"/></td>
							</tr>
							</s:iterator>
						</tbody>
					</table>
				</div>
				<%@ include file="../common/page.jsp" %>
			</div>
		</div>
	</form>
</body>
</html>
