package com._520it.pss.dao;

import com._520it.pss.entity.ProductStock;

public interface ProductStockDao extends GenericDao<ProductStock>{

	/**
	 * 根据仓库id和商品id查询某类商品是否存在于仓库中（是否存在库存信息）
	 * @param depotId 仓库id
	 * @param productId 商品id
	 * @return
	 */
	ProductStock getByDepotAndProduct(Long depotId, Long productId);

}
