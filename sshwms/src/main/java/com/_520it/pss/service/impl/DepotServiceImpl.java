package com._520it.pss.service.impl;

import java.util.List;

import com._520it.pss.dao.DepotDao;
import com._520it.pss.dao.impl.DepotDaoImpl;
import com._520it.pss.entity.Depot;
import com._520it.pss.query.ObjectQuery;
import com._520it.pss.query.PageResult;
import com._520it.pss.service.DepotService;

import lombok.Getter;
import lombok.Setter;


public class DepotServiceImpl implements DepotService {
	@Setter @Getter
	private DepotDao depotDao;

	public DepotServiceImpl() {
		depotDao = new DepotDaoImpl();
	}

	@Override
	public void add(Depot depot) {
		this.depotDao.add(depot);
	}

	@Override
	public void update(Depot depot) {
		this.depotDao.update(depot);
	}

	@Override
	public void delete(Long id) {
		this.depotDao.delete(id);
	}
	
	@Override
	public void batchDelete(List<Long> ids) {
		this.depotDao.batchDelete(ids);
	}

	@Override
	public Depot get(Long id) {
		return this.depotDao.get(id);
	}

	@Override
	public List<Depot> list() {
		return this.depotDao.list();
	}

	@Override
	public PageResult query(ObjectQuery qo) {
		return this.depotDao.query(qo);
	}

}
