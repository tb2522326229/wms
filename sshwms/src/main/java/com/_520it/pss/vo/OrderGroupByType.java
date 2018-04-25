package com._520it.pss.vo;

import lombok.Getter;

/**
 * 订货报表时分组查询的类型
 */
@Getter
public enum OrderGroupByType {
	EMPLOYEE("obj.bill.inputUser.userName", "obj.bill.inputUser", "订货人员"), 
	PRODUCT("obj.product.name", "obj.product", "货品名称"), 
	BRAND("obj.product.brand.name", "obj.product.brand", "品牌名称"), 
	SUPPLIER("obj.bill.supplier.name", "obj.bill.supplier", "供应商"), 
	MONTH("date_format(obj.bill.vdate,'%Y-%m')", "date_format(obj.bill.vdate,'%Y-%m')", "订货时期（月）"), 
	DAY("date_format(obj.bill.vdate,'%Y-%m-%d')", "date_format(obj.bill.vdate,'%Y-%m-%d')","订货时期（日）");

	private String groupValue;// 分组名称 如obj.bill.inputUser.name
	private String groupBy;// 分组查询 如obj.bill.inputUser
	private String groupType;// 分组类型：分组的中文名称

	private OrderGroupByType(String groupValue, String groupBy, String groupType) {
		this.groupValue = groupValue;
		this.groupBy = groupBy;
		this.groupType = groupType;
	}
	
}
