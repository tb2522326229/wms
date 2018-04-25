package com._520it.pss.dao.impl;

import com._520it.pss.entity.ProductStock;

import org.hibernate.Session;

import com._520it.pss.dao.ProductStockDao;
public class ProductStockDaoImpl extends GenericDaoImpl<ProductStock> implements ProductStockDao {

	@Override
	public ProductStock getByDepotAndProduct(Long depotId, Long productId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "select ps from ProductStock ps where ps.product.id = ? and ps.depot.id = ?";
		return (ProductStock) session.createQuery(hql).setParameter(0, productId).setParameter(1, depotId).uniqueResult();
	}

}
