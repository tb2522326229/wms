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
<script type="text/javascript" src="/js/fancybox/jquery.fancybox.pack.js"></script>
<link rel="stylesheet" type="text/css" href="/js/fancybox/jquery.fancybox.css" media="screen">
<script type="text/javascript">
	$(function(){
		$('.fancybox').fancybox();
	});
	
	$(function(){
		$(".btn_search").click(function(){
			// 设置回传的参数
			// window.returnValue = $(this).data("product");
			$.ajax({
				type:"post",
				url:"/WEB-INF/views/stockIncomeBill/input.jsp",
				data:$(this).data("product"),
				dataType: "json"
			});
			// 关闭当前窗口
			// window.close();
		});
		
	});
</script>
<title>PSS-商品管理</title>
<style>
.alt td {
	background: black !important;
}
</style>

</head>

<body>
	<%@ include file="/WEB-INF/views/common/msg.jsp"%>
	<form id="searchForm" action="product_selectProductList.action" method="post">
		<div id="container">
			<div class="ui_content">
				<div id="box_border">
						<div id="box_top">搜索</div>
						<div id="box_center">
							关键字
							<s:textfield name="qo.keyword" cssClass="ui_input_txt02"/>
							品牌
							<s:select list="#brands" name="qo.brand_id" listKey="id" listValue="name" headerKey="-1" headerValue="请选择品牌" cssClass="ui_select01"></s:select>
						</div>
						<div id="box_bottom">
							<input type="button" value="查询" class="ui_input_btn01 btn_page" /> 
						</div>
					</div>
				</div>
			</div>
			<div class="ui_content">
				<div class="ui_tb">
					<table class="table" cellspacing="0" cellpadding="0" width="100%"
						align="center" border="0">
						<tr>
							<th width="30"><input type="checkbox" id="all" /></th>
							<th>货品图片</th>
							<th>货品名称</th>
							<th>货品编码</th>
							<th>货品品牌</th>
							<th>成本价</th>
							<th>销售价</th>
							<th>操作</th>
						</tr>
						<tbody>
						<s:iterator value="#pageResult.result">
							<tr>
								<td><input type="checkbox" name="IDCheck" autocomplete="off" class="acb" data-eid="<s:property value="id" />" /></td>
								<td>
									<a class="fancybox" href="<s:property value="imagePath"/>" title="<s:property value="name"/>">
										<img alt="<s:property value="name"/>" src="<s:property value="smailImagePath"/>" class="list_img">
									</a>
								</td>
									<td><s:property value="name"/></td>
									<td><s:property value="sn"/></td>
									<td><s:property value="brand.name"/></td>
									<td><s:property value="costPrice"/></td>
									<td><s:property value="salePrice"/></td>
								<td>
									<input type="button" value="选择该商品 " class="left2right btn_search" data-product="<s:property value="productJsonString"/>"/>
								</td>
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
