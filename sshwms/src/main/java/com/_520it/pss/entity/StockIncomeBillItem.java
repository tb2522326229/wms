package com._520it.pss.entity;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
/**
 * 到货入库单明细
 */
@Setter @Getter
public class StockIncomeBillItem extends BaseDomain {
	private BigDecimal number = BigDecimal.ZERO;// 入科数量
	private BigDecimal costPrice = BigDecimal.ZERO;// 入库价
	private BigDecimal amount = BigDecimal.ZERO;// 明细小计
	private String remark;// 备注
	
	private Product product;// 货品
	private StockIncomeBill bill;// 单据
}
