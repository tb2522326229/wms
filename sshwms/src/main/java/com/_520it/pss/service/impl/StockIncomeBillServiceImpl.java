package com._520it.pss.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import com._520it.pss.dao.StockIncomeBillDao;
import com._520it.pss.entity.StockIncomeBill;
import com._520it.pss.entity.StockIncomeBillItem;
import com._520it.pss.query.ObjectQuery;
import com._520it.pss.query.PageResult;
import com._520it.pss.service.ProductStockService;
import com._520it.pss.service.StockIncomeBillService;
import com._520it.pss.util.UserContext;

import lombok.Getter;
import lombok.Setter;

public class StockIncomeBillServiceImpl implements StockIncomeBillService {
	@Setter
	@Getter
	private StockIncomeBillDao stockIncomeBillDao;
	@Setter
	@Getter
	private ProductStockService productStockService;

	@Override
	public void add(StockIncomeBill bill) {
		// 1.设置制单人和制单时间
		bill.setAuditor(UserContext.getEmployee());
		bill.setAuditTime(new Date());
		parseItem(bill);
		// 6.保存单据
		this.stockIncomeBillDao.add(bill);
	}

	@Override
	public void update(StockIncomeBill bill) {

		if (bill.getStatus() == StockIncomeBill.NORMAL) {
			parseItem(bill);
			this.stockIncomeBillDao.update(bill);
		}
	}

	@Override
	public void delete(Long id) {
		this.stockIncomeBillDao.delete(id);
	}

	@Override
	public void batchDelete(List<Long> ids) {
		this.stockIncomeBillDao.batchDelete(ids);
	}

	@Override
	public StockIncomeBill get(Long id) {
		return this.stockIncomeBillDao.get(id);
	}

	@Override
	public List<StockIncomeBill> list() {
		return this.stockIncomeBillDao.list();
	}

	@Override
	public PageResult query(ObjectQuery qo) {
		return this.stockIncomeBillDao.query(qo);
	}

	public void parseItem(StockIncomeBill bill) {
		// 2.手动设置单据的未审核状态
		bill.setStatus(StockIncomeBill.NORMAL);
		bill.setTotalAmount(BigDecimal.ZERO);
		bill.setTotalNumber(BigDecimal.ZERO);
		for (StockIncomeBillItem item : bill.getItems()) {
			// 3.处理单据和明细之间的关系
			item.setBill(bill);
			// 4.计算单条明细小计(setScale(2, RoundingMode.HALF_UP)：四舍五入)
			item.setAmount(item.getNumber().multiply(item.getCostPrice()).setScale(2, RoundingMode.HALF_UP));
			// 5.计算单据的总数量和总金额
			bill.setTotalNumber(bill.getTotalNumber().add(item.getNumber()));
			bill.setTotalAmount(bill.getTotalAmount().add(item.getAmount()));
		}
	}

	@Override
	public void audit(Long id) {
		StockIncomeBill bill = this.stockIncomeBillDao.get(id);
		// 先判断当前单据是否处于未审核状态
		if (bill.getStatus() == StockIncomeBill.NORMAL) {
			bill.setStatus(StockIncomeBill.AUDIT);
			bill.setAuditor(UserContext.getEmployee());
			bill.setAuditTime(new Date());
			// 操作库存
			List<StockIncomeBillItem> items = bill.getItems();
			for (StockIncomeBillItem item : items) {
				// 操作入库
				productStockService.stockIncome(bill.getDepot(), item.getProduct(), item.getNumber(),
						item.getCostPrice());
			}
			// 更新入库单
			this.stockIncomeBillDao.update(bill);
		}
	}

}
