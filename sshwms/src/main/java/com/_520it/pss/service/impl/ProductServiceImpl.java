package com._520it.pss.service.impl;

import java.util.List;

import com._520it.pss.dao.ProductDao;
import com._520it.pss.dao.impl.ProductDaoImpl;
import com._520it.pss.entity.Product;
import com._520it.pss.query.ObjectQuery;
import com._520it.pss.query.PageResult;
import com._520it.pss.service.ProductService;

import lombok.Getter;
import lombok.Setter;


public class ProductServiceImpl implements ProductService {
	@Setter @Getter
	private ProductDao productDao;

	public ProductServiceImpl() {
		productDao = new ProductDaoImpl();
	}

	@Override
	public void add(Product product) {
		this.productDao.add(product);
	}

	@Override
	public void update(Product product) {
		this.productDao.update(product);
	}

	@Override
	public void delete(Long id) {
		this.productDao.delete(id);
	}

	@Override
	public Product get(Long id) {
		return this.productDao.get(id);
	}

	@Override
	public List<Product> list() {
		return this.productDao.list();
	}

	@Override
	public PageResult query(ObjectQuery qo) {
		return this.productDao.query(qo);
	}

	@Override
	public void batchDelete(List<Long> ids) {
		this.productDao.batchDelete(ids);
	}

}
