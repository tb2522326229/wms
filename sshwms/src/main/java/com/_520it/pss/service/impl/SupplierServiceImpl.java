package com._520it.pss.service.impl;

import java.util.List;

import com._520it.pss.dao.SupplierDao;
import com._520it.pss.dao.impl.SupplierDaoImpl;
import com._520it.pss.entity.Supplier;
import com._520it.pss.query.ObjectQuery;
import com._520it.pss.query.PageResult;
import com._520it.pss.service.SupplierService;

import lombok.Getter;
import lombok.Setter;


public class SupplierServiceImpl implements SupplierService {
	@Setter @Getter
	private SupplierDao supplierDao;

	public SupplierServiceImpl() {
		supplierDao = new SupplierDaoImpl();
	}

	@Override
	public void add(Supplier supplier) {
		this.supplierDao.add(supplier);
	}

	@Override
	public void update(Supplier supplier) {
		this.supplierDao.update(supplier);
	}

	@Override
	public void delete(Long id) {
		this.supplierDao.delete(id);
	}

	@Override
	public Supplier get(Long id) {
		return this.supplierDao.get(id);
	}

	@Override
	public List<Supplier> list() {
		return this.supplierDao.list();
	}

	@Override
	public PageResult query(ObjectQuery qo) {
		return this.supplierDao.query(qo);
	}

}
