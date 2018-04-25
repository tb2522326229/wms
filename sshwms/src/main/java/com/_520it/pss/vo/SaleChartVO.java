package com._520it.pss.vo;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * 封装销售报表查询对象
 */
@Setter
@Getter
public class SaleChartVO {
	private String groupType;// 分组信息
	private BigDecimal totalNumber;// 订单总数量
	private BigDecimal totalAmounnt;// 订单总价格
	private BigDecimal grossProfit;// 毛利润

	public SaleChartVO(String groupType, BigDecimal totalNumber, BigDecimal totalAmounnt, BigDecimal grossProfit) {
		this.groupType = groupType;
		this.totalNumber = totalNumber;
		this.totalAmounnt = totalAmounnt;
		this.grossProfit = grossProfit;
	}

	public SaleChartVO() {
	}

}
