package com._520it.pss.service;

import java.util.List;

import com._520it.pss.entity.StockIncomeBill;
import com._520it.pss.query.ObjectQuery;
import com._520it.pss.query.PageResult;


public interface StockIncomeBillService {
	void add(StockIncomeBill stockIncomeBill);// 新增

	void update(StockIncomeBill stockIncomeBill);// 修改

	void delete(Long id);// 删除

	StockIncomeBill get(Long id);// 通过id获取相应的结果

	List<StockIncomeBill> list();// 获取相应实体类下的一切结果

	PageResult query(ObjectQuery qo);// 根据条件进行分页查询
	
	void batchDelete(List<Long> ids);// 批量删除

	void audit(Long id);// 审核库存
}
