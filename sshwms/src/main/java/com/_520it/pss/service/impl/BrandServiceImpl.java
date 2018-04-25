package com._520it.pss.service.impl;

import java.util.List;

import com._520it.pss.dao.BrandDao;
import com._520it.pss.dao.impl.BrandDaoImpl;
import com._520it.pss.entity.Brand;
import com._520it.pss.query.ObjectQuery;
import com._520it.pss.query.PageResult;
import com._520it.pss.service.BrandService;

import lombok.Getter;
import lombok.Setter;


public class BrandServiceImpl implements BrandService {
	@Setter @Getter
	private BrandDao brandDao;

	public BrandServiceImpl() {
		brandDao = new BrandDaoImpl();
	}

	@Override
	public void add(Brand brand) {
		this.brandDao.add(brand);
	}

	@Override
	public void update(Brand brand) {
		this.brandDao.update(brand);
	}

	@Override
	public void delete(Long id) {
		this.brandDao.delete(id);
	}

	@Override
	public Brand get(Long id) {
		return this.brandDao.get(id);
	}

	@Override
	public List<Brand> list() {
		return this.brandDao.list();
	}

	@Override
	public PageResult query(ObjectQuery qo) {
		return this.brandDao.query(qo);
	}

}
