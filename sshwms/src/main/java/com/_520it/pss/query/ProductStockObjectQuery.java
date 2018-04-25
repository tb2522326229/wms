package com._520it.pss.query;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
@Setter @Getter
public class ProductStockObjectQuery extends ObjectQuery {
	private String keyword;// 关键字
	private Long depotId = -1L;// 仓库id
	private Long brandId = -1L;// 品牌id
	private BigDecimal storeLimit; // 阈值
	
	public void custromQuery() {
		if(hasLength(keyword)){
			super.addQuery("obj.product.name like ? or obj.product.sn like?", "%" + keyword + "%", "%" + keyword + "%");
		}
		if(depotId > 0){
			super.addQuery(" obj.depot.id = ? ", depotId);
		}
		if(brandId > 0){
			super.addQuery(" obj.product.brand.id = ?", brandId);
		}
		if(storeLimit != null){
			super.addQuery(" obj.storeNumber <= ?", storeLimit);
		}
		// 查询即时库存，肯定是存在库存的，也就是说库存量必须 > 0
		super.addQuery(" obj.storeNumber > ?", BigDecimal.ZERO);
	}

}
