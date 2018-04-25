package com._520it.pss.entity;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * 采购订单明细
 */
@Setter
@Getter
public class OrderBillItem extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private BigDecimal number;// 采购数量
	private BigDecimal costPrice;// 成本价
	private BigDecimal amount;// 明细小计
	private String remark;// 备注
	private Product product;// 商品
	private OrderBill bill;// 关联的单据对象

	public OrderBillItem() {
		number = BigDecimal.ZERO;
		costPrice = BigDecimal.ZERO;
		amount = BigDecimal.ZERO;
	}
}
