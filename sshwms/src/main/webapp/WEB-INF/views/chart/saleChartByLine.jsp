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
	$(function(){
		    $('#container').highcharts({
		        title: {
		            text: '采购报表折线图',
		            x: -20 //center
		        },
		        subtitle: {
		            text: '按[ <s:property value="#groupBy"/> ]分组',
		            x: -20
		        },
		        xAxis: {
		            categories: <s:property value="#groupByType" escapeHtml="false"/>
		        },
		        yAxis: {
		            title: {
		                text: '采购总金额 (¥)'
		            },
		            plotLines: [{
		                value: 0,
		                width: 1,
		                color: '#808080'
		            }]
		        },
		        tooltip: {
		            valueSuffix: '元'
		        },
		        series: [{
		            name: '采购金额',
		            data:  <s:property value="#totalAmount"/>
		        }]
		    });
		});
</script>
</head>

<body>
<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
</body>
</html>
