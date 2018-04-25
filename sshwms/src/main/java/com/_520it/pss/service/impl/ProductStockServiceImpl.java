package com._520it.pss.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com._520it.pss.dao.ProductStockDao;
import com._520it.pss.dao.impl.ProductStockDaoImpl;
import com._520it.pss.entity.Depot;
import com._520it.pss.entity.Product;
import com._520it.pss.entity.ProductStock;
import com._520it.pss.query.ObjectQuery;
import com._520it.pss.query.PageResult;
import com._520it.pss.service.ProductStockService;

import lombok.Getter;
import lombok.Setter;


public class ProductStockServiceImpl implements ProductStockService {
	@Setter @Getter
	private ProductStockDao productStockDao;

	public ProductStockServiceImpl() {
		productStockDao = new ProductStockDaoImpl();
	}

	@Override
	public void add(ProductStock productStock) {
		this.productStockDao.add(productStock);
	}

	@Override
	public void update(ProductStock productStock) {
		this.productStockDao.update(productStock);
	}

	@Override
	public void delete(Long id) {
		this.productStockDao.delete(id);
	}
	
	@Override
	public void batchDelete(List<Long> ids) {
		this.productStockDao.batchDelete(ids);
	}

	@Override
	public ProductStock get(Long id) {
		return this.productStockDao.get(id);
	}

	@Override
	public List<ProductStock> list() {
		return this.productStockDao.list();
	}

	@Override
	public PageResult query(ObjectQuery qo) {
		return this.productStockDao.query(qo);
	}

	@Override
	public void audit(Long id) {
		
	}

	@Override
	public void stockIncome(Depot depot, Product product, BigDecimal number, BigDecimal costPrice) {
		// 1.判断某个货品是否在某个库存中，是否存在库存
		ProductStock ps = productStockDao.getByDepotAndProduct(depot.getId(),product.getId());
		// 如果仓库中有该类商品
		BigDecimal amount = number.multiply(costPrice).setScale(2, RoundingMode.HALF_UP);
		if(ps != null){
			// 重新计算库存数量，库存价格，库存金额
			ps.setStoreNumber(ps.getStoreNumber().add(number));
			ps.setAmount(ps.getAmount().add(amount));
			ps.setPrice(ps.getAmount().divide(ps.getStoreNumber(),2,RoundingMode.HALF_UP));
			productStockDao.update(ps);
		}else{// 如果没有该类商品
			// 创建仓库对象，设置仓库，货品，计算库存数量，库存金额，库存价格
			ps = new ProductStock();
			ps.setDepot(depot);
			ps.setProduct(product);
			ps.setStoreNumber(number);
			ps.setPrice(costPrice);
			ps.setAmount(amount);
			productStockDao.add(ps);
		}
	}

	@Override
	public BigDecimal stockOutcome(Depot depot, Product product, BigDecimal number) {
		ProductStock ps = productStockDao.getByDepotAndProduct(depot.getId(),product.getId());
		// 当前需要出库的数量：number
		// 实际库存量：ps.getStoreNumber()
		if (ps == null || ps.getStoreNumber().compareTo(number) < 0) {
			throw new RuntimeException(product.getName() + "库存不足" + number);
		}
		ps.setStoreNumber(ps.getStoreNumber().subtract(number));
		ps.setAmount(ps.getStoreNumber().multiply(ps.getPrice()));
		productStockDao.update(ps);
		return ps.getPrice();
	}

}
