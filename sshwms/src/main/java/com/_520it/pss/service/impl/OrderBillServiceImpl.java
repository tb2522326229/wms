package com._520it.pss.service.impl;

import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import com._520it.pss.dao.OrderBillDao;
import com._520it.pss.dao.impl.OrderBillDaoImpl;
import com._520it.pss.entity.OrderBill;
import com._520it.pss.entity.OrderBillItem;
import com._520it.pss.query.ObjectQuery;
import com._520it.pss.query.PageResult;
import com._520it.pss.service.OrderBillService;
import com._520it.pss.util.UserContext;

import lombok.Getter;
import lombok.Setter;

public class OrderBillServiceImpl implements OrderBillService {
	@Setter
	@Getter
	private OrderBillDao orderBillDao;

	public OrderBillServiceImpl() {
		orderBillDao = new OrderBillDaoImpl();
	}

	@Override
	public void add(OrderBill orderBill) {
		// 1.设置制单人和制单时间
		orderBill.setInputUser(UserContext.getEmployee());
		orderBill.setInputTime(new Date());
		// 2.手动设置单据的未审核状态
		orderBill.setStatus(OrderBill.NORMAL);
		parseItem(orderBill);
		// 6.保存单据
		this.orderBillDao.add(orderBill);
	}

	@Override
	public void update(OrderBill orderBill) {
		if(orderBill.getStatus() == OrderBill.NORMAL){
			parseItem(orderBill);
			this.orderBillDao.update(orderBill);
		}
	}

	@Override
	public void delete(Long id) {
		this.orderBillDao.delete(id);
	}

	@Override
	public OrderBill get(Long id) {
		return this.orderBillDao.get(id);
	}

	@Override
	public List<OrderBill> list() {
		return this.orderBillDao.list();
	}

	@Override
	public PageResult query(ObjectQuery qo) {
		return this.orderBillDao.query(qo);
	}

	@Override
	public void batchDelete(List<Long> ids) {
		this.orderBillDao.batchDelete(ids);
	}

	// 处理明细
	public void parseItem(OrderBill orderBill){
		for (OrderBillItem item : orderBill.getItems()) {
			// 3.处理单据和明细之间的关系
			item.setBill(orderBill);
			// 4.计算单条明细小计(setScale(2, RoundingMode.HALF_UP)：四舍五入)
			item.setAmount(item.getNumber().multiply(item.getCostPrice()).setScale(2, RoundingMode.HALF_UP));
			// 5.计算单据的总数量和总金额
			orderBill.setTotalNumber(orderBill.getTotalNumber().add(item.getNumber()));
			orderBill.setTotalAmount(orderBill.getTotalAmount().add(item.getAmount()));
		}
	}

	@Override
	public void audit(Long billId) {
		OrderBill bill = this.orderBillDao.get(billId);
		if(bill.getStatus() == OrderBill.NORMAL){
			bill.setStatus(OrderBill.AUDIT);
			bill.setAuditor(UserContext.getEmployee());
			bill.setAuditTime(new Date());
		}
		this.orderBillDao.update(bill);
	}
}
