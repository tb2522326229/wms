package com._520it.pss.service.impl;

import java.util.List;

import com._520it.pss.dao.ClientDao;
import com._520it.pss.dao.impl.ClientDaoImpl;
import com._520it.pss.entity.Client;
import com._520it.pss.query.ObjectQuery;
import com._520it.pss.query.PageResult;
import com._520it.pss.service.ClientService;

import lombok.Getter;
import lombok.Setter;


public class ClientServiceImpl implements ClientService {
	@Setter @Getter
	private ClientDao clientDao;

	public ClientServiceImpl() {
		clientDao = new ClientDaoImpl();
	}

	@Override
	public void add(Client client) {
		this.clientDao.add(client);
	}

	@Override
	public void update(Client client) {
		this.clientDao.update(client);
	}

	@Override
	public void delete(Long id) {
		this.clientDao.delete(id);
	}
	
	@Override
	public void batchDelete(List<Long> ids) {
		this.clientDao.batchDelete(ids);
	}

	@Override
	public Client get(Long id) {
		return this.clientDao.get(id);
	}

	@Override
	public List<Client> list() {
		return this.clientDao.list();
	}

	@Override
	public PageResult query(ObjectQuery qo) {
		return this.clientDao.query(qo);
	}

}
