package com._520it.pss.service;

import java.util.List;

import com._520it.pss.entity.OrderBill;
import com._520it.pss.query.ObjectQuery;
import com._520it.pss.query.PageResult;


public interface OrderBillService {
	void add(OrderBill orderBill);// 新增

	void update(OrderBill orderBill);// 修改

	void delete(Long id);// 删除

	OrderBill get(Long id);// 通过id获取相应的结果

	List<OrderBill> list();// 获取相应实体类下的一切结果

	PageResult query(ObjectQuery qo);// 根据条件进行分页查询

	void batchDelete(List<Long> ids);

	/**
	 * 审核
	 * @param billId 被审核的单据的id
	 */
	void audit(Long billId);
}
