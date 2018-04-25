package com._520it.pss.entity;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 销售帐：每一个销售明细都对应一个销售帐
 */
@Getter
@Setter
public class SaleAccount extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private Date vdate;// 业务时间
	private BigDecimal number;// 库存数量
	private BigDecimal costPrice; // 成本价/库存价格
	private BigDecimal costAmount;// 库存总金额
	private BigDecimal salePrice;// 销售价格
	private BigDecimal saleAmount;// 销售总金额（某个订单的明细小计）

	private Product product;// 哪一个货品
	private Client client;// 客户
	private Employee saleman;// 销售人员

}
