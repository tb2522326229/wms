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
<title>PSS-品牌管理</title>
<style>
.alt td {
	background: black !important;
}
</style>

</head>

<body>
	<%@ include file="/WEB-INF/views/common/msg.jsp"%>
	<form id="searchForm" action="brand.action" method="post">
		<div id="container">
			<div class="ui_content">
				<div class="ui_text_indent">
					<div id="box_border">
						<div id="box_top">搜索</div>
						<div id="box_bottom">
							<input type="button" value="新增" class="ui_input_btn01 btn_input"
								data-url='<s:url namespace="/" action="brand_input.action"/>' />
							<input type="button" value="批量删除" class="ui_input_btn01 btn_batch_delete" data-url="<s:url namespace="/" action="brand_batchDelete.action"/>" />
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
							<th>编号</th>
							<th>品牌名称</th>
							<th>品牌编码</th>
							<th></th>
						</tr>
						<tbody>
						<s:iterator value="#pageResult.result">
							<tr>
								<td><input type="checkbox" name="IDCheck" autocomplete="off" class="acb" data-eid="<s:property value="id" />" /></td>
								<td><s:property value="id"/> </td>
									<td><s:property value="name"/></td>
									<td><s:property value="sn"/></td>
								<td>
								<s:url var="inputUrl" namespace="/" action="brand_input">
											<s:param name="brand.id" value="id" />
									</s:url> 
									<a href="<s:property value="#inputUrl"/>">编辑</a> 
									<s:url var="deleteUrl" namespace="/" action="brand_delete">
											<s:param name="brand.id" value="id" />
									</s:url> 
									<a href="javascript:;" class="btn_delete" data-url='<s:property value="#deleteUrl"/>'>删除</a> 
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
