<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="/js/jquery/jquery.js"></script>
<script type="text/javascript" src="/js/Highcharts/highcharts.js"></script>
<title>PSS-采购报表-图表分析</title>
<script type="text/javascript">
	$(function() {
		$('#container').highcharts({
			chart : {
				plotBackgroundColor : null,
				plotBorderWidth : 1, //null,
				plotShadow : false
			},
			title : {
				text : '销售报表饼图 按照[ <s:property value="#groupBy"/> ] 分组'
			},
			tooltip : {
				pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
			},
			plotOptions : {
				pie : {
					allowPointSelect : true,
					cursor : 'pointer',
					dataLabels : {
						enabled : true,
						format : '<b>{point.name}</b>: {point.percentage:.1f} %',
						style : {
							color : (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
						}
					}
				}
			},
			series : [ {
				type : 'pie',
				name : '所占销售总金额',
				data : <s:property value="#datas" escapeHtml="false"/>
			} ]
		});
	});
</script>
</head>

<body>
	<div id="container"
		style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto"></div>
</body>
</html>
