<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<title>信息管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="style/basic_layout.css" rel="stylesheet" type="text/css"/>
<link href="style/common_style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/js/jquery/jquery.js"></script>
<script type="text/javascript" src="/js/commonAll.js"/></script>
<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(function(){
		$("input[name='orderBill.vdate']").addClass("Wdate").click(function(){
			WdatePicker({
				maxDate: new Date()// 设置最大日期不能超过今天
			});
		});
	});

	function clearTrData(tr){
		tr.find("[tag=name]").val("");
		tr.find("[tag=pid]").val("");
		tr.find("[tag=remark]").val("");
		tr.find("[tag=number]").val("");
		tr.find("[tag=brand]").text("");
		tr.find("[tag=amount]").text("");
		tr.find("[tag=costPrice]").val("");
	}
	$(function(){
		$("#edit_table_body").on("click",".searchproduct",function(){// 选择采购货品
			var json = window.open("product_selectProductList.action");
			var tr = $(this).closest("tr");
			tr.find("[tag=name]").val(json.name);
			tr.find("[tag=pid]").val(json.id);
			tr.find("[tag=costPrice]").val(json.costPrice);
			tr.find("[tag=brand]").text(json.brandName);
		}).on("change","[tag=costPrice],[tag=number]",function(){// 计算小计
			var tr = $(this).closest("tr");
			var costPrice = parseFloat(tr.find("[tag=costPrice]").val());
			var number = parseFloat(tr.find("[tag=number]").val());
			if(costPrice && number){
				tr.find("[tag=amount]").text((costPrice * number).toFixed(2));
			}
		}).on("click",".removeItem",function(){
			var tr = $(this).closest("tr");
			if($("#edit_table_body tr").size() == 1){
				clearTrData(tr);
			}else{
				tr.remove();
			}
		});
		// 添加明细
		$(".appendRow").click(function(){
			var tr = $("#edit_table_body tr:first").clone();// 克隆一行
			clearTrData(tr);
			tr.appendTo($("#edit_table_body"));
		});
		
		// 保存多条明细
		$(".btn_submit").click(function(){
			$.each($("#edit_table_body tr"),function(index,item){
			var tr = $(item);
			tr.find("[tag=pid]").prop("name","orderBill.items[" + index + "].product.id");
			tr.find("[tag=remark]").prop("name","orderBill.items[" + index + "].product.remark");
			tr.find("[tag=number]").prop("name","orderBill.items[" + index + "].product.number");
			tr.find("[tag=costPrice]").prop("name","orderBill.items[" + index + "].product.costPrice");
			console.debug(tr);
			});
			$("#editForm").submit();
		});
	});
</script>
</head>

<body>
	<s:debug/>
	<form name="editForm" namespace="/" action="orderBill_saveOrUpdate.action" method="post" id="editForm">
	<s:hidden name="orderBill.id"/>
	<div id="container">
		<div id="nav_links">
			<span style="color: #1A5CC6;" >采购订单编辑</span>
			<div id="page_close">
				<a>
					<img src="images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
				</a>
			</div>
		</div>
		<div class="ui_content">
			<table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
				
				<tr>
					<td class="ui_text_rt" width="140">订单编号</td>
					<td class="ui_text_lt">
						<s:textfield name="orderBill.sn" cssClass="ui_input_txt02"/>
					</td>
				</tr>
				<tr>
					<td class="ui_text_rt" width="140">供应商</td>
					<td class="ui_text_lt">
						<s:select list="#suppliers" name="orderBill.supplier.id" listKey="id" listValue="name" cssClass="ui_select01"/>
					</td>
				</tr>
				<tr>
					<td class="ui_text_rt" width="140">业务时间</td>
					<td class="ui_text_lt">
						<s:date name="orderBill.vdate" format="yyyy-MM-dd" var="vd"/>
						<s:textfield name="orderBill.vdate" cssClass="ui_input_txt02" value="%{vd}"/>
					</td>
				</tr>
				<tr>
                    <td></td>
                    <td>
                        <input type="button" value="添加明细" class="ui_input_btn01 appendRow"/>
                        <table class="edit_table" cellspacing="0" cellpadding="0" border="0" style="width: auto">
                            <thead>
                            <tr>
                                <th width="10"></th>
                                <th width="200">货品</th>
                                <th width="120">品牌</th>
                                <th width="80">价格</th>
                                <th width="80">数量</th>
                                <th width="80">金额小计</th>
                                <th width="150">备注</th>
                                <th width="60"></th>
                            </tr>
                            </thead>
                            <s:if test="orderBill.id == null">
                            <tbody id="edit_table_body">
                                <tr>
                                    <td></td>
                                    <td>
                                        <s:textfield disabled="true" readonly="true" cssClass="ui_input_txt02" tag="name"/>
                                        <img src="/images/common/search.png" class="searchproduct"/>
                                        <s:hidden name="orderBill.items.product.id" tag="pid"/>
                                    </td>
                                    <td><span tag="brand"></span></td>
                                    <td><s:textfield tag="costPrice" name="orderBill.items.costPrice"
                                                     cssClass="ui_input_txt04"/></td>
                                    <td><s:textfield tag="number" name="orderBill.items.number"
                                                     cssClass="ui_input_txt04"/></td>
                                    <td><span tag="amount"></span></td>
                                    <td><s:textfield tag="remark" name="orderBill.items.remark"
                                                     cssClass="ui_input_txt02"/></td>
                                    <td>
                                        <a href="javascript:;" class="removeItem">删除明细</a>
                                    </td>
                                </tr>
                            </tbody>
                            </s:if>
                            <s:else>
                            	<s:iterator value="orderBill.items">
                            		<tbody id="edit_table_body">
                                <tr>
                                    <td></td>
                                    <td>
                                        <s:textfield disabled="true" readonly="true" cssClass="ui_input_txt02" tag="name" name="product.name"/>
                                        <img src="/images/common/search.png" class="searchproduct"/>
                                        <s:hidden name="orderBill.items.product.id" tag="pid"/>
                                    </td>
                                    <td><span tag="brand"><s:property value="product.brand.name"/> </span></td>
                                    <td><s:textfield tag="costPrice" name="costPrice"
                                                     cssClass="ui_input_txt04"/></td>
                                    <td><s:textfield tag="number" name="number"
                                                     cssClass="ui_input_txt04"/></td>
                                    <td><span tag="amount"></span></td>
                                    <td><s:textfield tag="remark" name="remark"
                                                     cssClass="ui_input_txt02"/></td>
                                    <td>
                                        <a href="javascript:;" class="removeItem">删除明细</a>
                                    </td>
                                </tr>
                            </tbody>
                            	</s:iterator>
                            </s:else>
                        </table>
                    </td>
                </tr>
				<tr>
					<td>&nbsp;</td>
					<td class="ui_text_lt">
						&nbsp;<input type="button" value="保存编辑" class="ui_input_btn01 btn_submit"/>
						&nbsp;<input id="cancelbutton" type="button" value="重置" class="ui_input_btn01"/>
					</td>
				</tr>
			</table>
		</div>
	</div>
</form>
</body>
</html>
