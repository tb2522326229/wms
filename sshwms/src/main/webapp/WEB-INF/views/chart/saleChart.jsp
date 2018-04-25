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
<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
<title>PSS-销售报表</title>
<style>
.alt td {
	background: black !important;
}
</style>
<script type="text/javascript">
	$(function(){
		$(".type").change(function(){
			var type = $(this).val();
			if(type == "line"){
				$("#searchForm").prop("action","chart_saleChartByLine.action").submit();
			}else{
				$("#searchForm").prop("action","chart_saleChartByPie.action").submit();
			}
		});
	});


	$(function(){
		$(":input[name='sqo.keyword']").attr("placeholder","货品名称/货品编码")
		$("input[name='sqo.beginDate']").addClass("Wdate").click(function(){
			WdatePicker({
				maxDate:$("input[name='sqo.endDate']").val() || new Date()// 设置最大日期不能超过今天
			});
		});
		$("input[name='sqo.endDate']").addClass("Wdate").click(function(){
			WdatePicker({
				minDate:$("input[name='sqo.beginDate']").val(),// 设置最小值不能超过开始时间
				maxDate:new Date()// 设置最大日期不能超过今天
			});
		});
	});
	
	$(function() {
	// 如果鼠标移到行上时，执行函数
	$(".table tr").mouseover(function() {
		$(this).css({
			background : "#CDDAEB"
		});
		$(this).children('td').each(function(index, ele) {
			$(ele).css({
				color : "#1D1E21"
			});
		});
	}).mouseout(function() {
		$(this).css({
			background : "#FFF"
		});
		$(this).children('td').each(function(index, ele) {
			$(ele).css({
				color : "#909090"
			});
		});
	});
});
	
</script>
</head>

<body>
	<%@ include file="/WEB-INF/views/common/msg.jsp"%>
	<form id="searchForm" action="chart_saleChart.action" method="post">
		<div id="container">
			<div class="ui_content">
				<div class="ui_text_indent">
					<div id="box_border">
						<div id="box_top">搜索</div>
						<div id="box_center">
			  关键字 
						<s:textfield name="sqo.keyword" cssClass="ui_input_txt02"/> 
                        业务时间
                        <s:date name="sqo.beginDate" format="yyyy-MM-dd" var="bd"/>
                        <s:textfield name="sqo.beginDate" cssClass="ui_input_txt02" value="%{bd}"/>
                        ~
                        <s:date name="sqo.endDate" format="yyyy-MM-dd" var="ed"/>
                        <s:textfield name="sqo.endDate" cssClass="ui_input_txt02" value="%{#ed}"/>
                        客户
                        <s:select name="sqo.clientId" list="#clients" listKey="id" listValue="name"
                                  headerKey="-1" headerValue="全部" cssClass="ui_select02"/>
                        品牌
                        <s:select name="sqo.brandId" list="#brands" listKey="id" listValue="name"
                                  headerKey="-1" headerValue="全部" cssClass="ui_select02"/>
                        分组
						<s:select name="sqo.groupType" list="#saleGroupByType" listKey="name()" listValue="groupType" cssClass="ui_select02"/>
                        查看图表
						<s:select list="#{'':'请选择图表','line':'线形图','pie':'饼状图'}"  cssClass="ui_select02 type"/>
                    </div>
						<div id="box_bottom">
							<input type="submit" value="查询" class="ui_input_btn01"/>
						</div>
					</div>
				</div>
			</div>
			<div class="ui_content">
				<div class="ui_tb">
					<table class="table" cellspacing="0" cellpadding="0" width="100%"
						align="center" border="0">
						<tr>
							<th>分组类型</th>
                        	<th>销售总数量</th>
                       	 	<th>销售总金额</th>
                       	 	<th>销售毛利润</th>
						</tr>
						<tbody>
						<s:iterator value="#saleCharts">
							<tr>
									<td><s:property value="groupType"/></td>
									<td><s:property value="totalNumber"/></td>
									<td><s:property value="totalAmounnt"/></td>
									<td><s:property value="grossProfit"/></td>
							</tr>
							</s:iterator>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
