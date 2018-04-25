package com._520it.pss.entity;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
/**
 * 货品库存
 */
@Getter @Setter
public class ProductStock extends BaseDomain {
	private BigDecimal storeNumber;// 库存数量
	private BigDecimal price;// 库存价格，采用移动加权平均计算
	private BigDecimal amount;// 库存总金额
	private Product product;// 哪一个货品
	private Depot depot;// 哪一个仓库
}
