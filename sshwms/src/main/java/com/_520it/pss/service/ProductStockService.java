package com._520it.pss.service;

import java.math.BigDecimal;
import java.util.List;

import com._520it.pss.entity.Depot;
import com._520it.pss.entity.Product;
import com._520it.pss.entity.ProductStock;
import com._520it.pss.query.ObjectQuery;
import com._520it.pss.query.PageResult;

public interface ProductStockService {
	void add(ProductStock productStock);// 新增

	void update(ProductStock productStock);// 修改

	void delete(Long id);// 删除

	ProductStock get(Long id);// 通过id获取相应的结果

	List<ProductStock> list();// 获取相应实体类下的一切结果

	PageResult query(ObjectQuery qo);// 根据条件进行分页查询

	void batchDelete(List<Long> ids);// 批量删除

	/**
	 * 到货入库单审核
	 * 
	 * @param id
	 */
	void audit(Long id);

	/**
	 * 入库操作
	 * 
	 * @param depot
	 *            存入哪一个仓库
	 * @param product
	 *            存入的是哪一个货品
	 * @param number
	 *            存入多少个
	 * @param costPrice
	 *            成本价
	 */
	void stockIncome(Depot depot, Product product, BigDecimal number, BigDecimal costPrice);

	/**
	 * 出库操作
	 * 
	 * @param depot
	 *            从哪一个仓库出库
	 * @param product
	 *            出库的是哪一个货品
	 * @param number
	 *            出库多少个
	 * @return 库存价格
	 */

	BigDecimal stockOutcome(Depot depot, Product product, BigDecimal number);
}
