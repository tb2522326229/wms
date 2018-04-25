package com._520it.pss.vo;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * 封装订单报表查询对象
 */
@Setter
@Getter
public class OrderChartVO {
	private String groupType;// 分组信息
	private BigDecimal totalNumber;// 订货总数量
	private BigDecimal totalAmounnt;// 订货总价格

	public OrderChartVO(String groupType, BigDecimal totalNumber, BigDecimal totalAmounnt) {
		this.groupType = groupType;
		this.totalNumber = totalNumber;
		this.totalAmounnt = totalAmounnt;
	}

	public OrderChartVO() {}

}
