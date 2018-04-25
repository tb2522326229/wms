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
<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
<title>PSS-商品出库管理</title>
<style>
.alt td {
	background: black !important;
}
</style>
<script type="text/javascript">
	$(function(){
		$("input[name='qo.beginDate']").addClass("Wdate").click(function(){
			WdatePicker({
				maxDate:$("input[name='qo.endDate']").val() || new Date()// 设置最大日期不能超过今天
			});
		});
		$("input[name='qo.endDate']").addClass("Wdate").click(function(){
			WdatePicker({
				minDate:$("input[name='qo.beginDate']").val(),// 设置最小值不能超过开始时间
				maxDate:new Date()// 设置最大日期不能超过今天
			});
		});
	});
	
</script>
</head>

<body>
	<%@ include file="/WEB-INF/views/common/msg.jsp"%>
	<form id="searchForm" action="stockOutcomeBill.action" method="post">
		<div id="container">
			<div class="ui_content">
				<div class="ui_text_indent">
					<div id="box_border">
						<div id="box_top">搜索</div>
						<div id="box_center">
                        业务时间
                        <s:date name="qo.beginDate" format="yyyy-MM-dd" var="bd"/>
                        <s:textfield name="qo.beginDate" cssClass="ui_input_txt02" value="%{bd}"/>
                        ~
                        <s:date name="qo.endDate" format="yyyy-MM-dd" var="ed"/>
                        <s:textfield name="qo.endDate" cssClass="ui_input_txt02" value="%{#ed}"/>
                       仓库
                        <s:select list="#deopts" name="qo.depotId" listKey="id" listValue="name"
                                  headerKey="-1" headerValue="全部" cssClass="ui_select01"/>
                       客户
                        <s:select list="#clients" name="qo.clientId" listKey="id" listValue="name"
                                  headerKey="-1" headerValue="全部" cssClass="ui_select01"/>
                        订单状态
                        <s:select name="qo.status" list="#{-1:'全部',0:'未审核',1:'已审核'}" cssClass="ui_select01"/>

                    </div>
						<div id="box_bottom">
							<input type="button" value="查询" class="ui_input_btn01 btn_page" data-page="1"/>
							<input type="button" value="新增" class="ui_input_btn01 btn_input"
								data-url='<s:url namespace="/" action="stockOutcomeBill_input.action"/>' />
							<input type="button" value="批量删除" class="ui_input_btn01 btn_batch_delete" data-url="<s:url namespace="/" action="stockOutcomeBill_batchDelete.action"/>" />
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
							<th>订单编号</th>
                        	<th>业务时间</th>
                       	 	<th>仓库</th>
                       	 	<th>客户</th>
                        	<th>出库总数量</th>
                        	<th>出库总金额</th>
                        	<th>录入人</th>
                        	<th>审核人</th>
                        	<th>审核状态</th>
							<th></th>
						</tr>
						<tbody>
						<s:iterator value="#pageResult.result">
							<tr>
								<td><input type="checkbox" name="IDCheck" autocomplete="off" class="acb" data-eid="<s:property value="id" />" /></td>
									<td><s:property value="sn"/></td>
									<td><s:date name="vdate" format="yyyy-MM-dd"/> </td>
									<td><s:property value="depot.name"/></td>
									<td><s:property value="client.name"/></td>
									<td><s:property value="totalNumber"/></td>
									<td><s:property value="totalAmount"/></td>
									<td><s:property value="inputUser.name"/></td>
									<td><s:property value="auditor.name"/></td>
									<td>
										<s:if test="status == 0"><span style="color : red">未审核</span> </s:if>
										<s:elseif test="status == 1"><span style="color : green">已审核</span> </s:elseif>
									</td>
								<td>
								<s:if test="status == 1">
									<s:url var="showUrl" namespace="/" action="stockOutcomeBill_show">
										<s:param name="stockOutcomeBill.id" value="id"/>
									</s:url>
									<a href="<s:property value="#showUrl"/>">查看</a> 
								</s:if>
								
								<s:else>
									<s:url var="auditUrl" namespace="/" action="stockOutcomeBill_audit">
										<s:param name="stockOutcomeBill.id" value="id"/>
									</s:url>
									<a href="<s:property value="#auditUrl"/>">审核</a> 
									
									<s:url var="inputUrl" namespace="/" action="stockOutcomeBill_input">
										<s:param name="stockOutcomeBill.id" value="id" />
									</s:url> 
									<a href="<s:property value="#inputUrl"/>">编辑</a> 
										
									<s:url var="deleteUrl" namespace="/" action="stockOutcomeBill_delete">
										<s:param name="stockOutcomeBill.id" value="id" />
									</s:url> 
									<a href="javascript:;" class="btn_delete" data-url='<s:property value="#deleteUrl"/>'>删除</a> 
									</s:else>
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
