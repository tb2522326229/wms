package com._520it.pss.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import com._520it.pss.dao.ProductStockDao;
import com._520it.pss.dao.SaleAccountDao;
import com._520it.pss.dao.StockOutcomeBillDao;
import com._520it.pss.dao.impl.StockOutcomeBillDaoImpl;
import com._520it.pss.entity.ProductStock;
import com._520it.pss.entity.SaleAccount;
import com._520it.pss.entity.StockOutcomeBill;
import com._520it.pss.entity.StockOutcomeBillItem;
import com._520it.pss.query.ObjectQuery;
import com._520it.pss.query.PageResult;
import com._520it.pss.service.ProductStockService;
import com._520it.pss.service.StockOutcomeBillService;
import com._520it.pss.util.UserContext;

import lombok.Getter;
import lombok.Setter;

public class StockOutcomeBillServiceImpl implements StockOutcomeBillService {
	@Setter
	@Getter
	private StockOutcomeBillDao stockOutcomeBillDao;
	@Setter
	@Getter
	private ProductStockService productStockService;
	@Setter
	@Getter
	private SaleAccountDao saleAccountDao;
	@Override
	public void add(StockOutcomeBill bill) {
		// 1.设置制单人和制单时间
		bill.setAuditor(UserContext.getEmployee());
		bill.setAuditTime(new Date());
		// 2.手动设置单据的未审核状态
		bill.setStatus(0);
		parseItem(bill);
		// 6.保存单据
		this.stockOutcomeBillDao.add(bill);
	}

	@Override
	public void update(StockOutcomeBill bill) {
		if(bill.getStatus() == StockOutcomeBill.NORMAL){
			parseItem(bill);
			this.stockOutcomeBillDao.update(bill);
		}
	}

	@Override
	public void delete(Long id) {
		this.stockOutcomeBillDao.delete(id);
	}

	@Override
	public void batchDelete(List<Long> ids) {
		this.stockOutcomeBillDao.batchDelete(ids);
	}

	@Override
	public StockOutcomeBill get(Long id) {
		return this.stockOutcomeBillDao.get(id);
	}

	@Override
	public List<StockOutcomeBill> list() {
		return this.stockOutcomeBillDao.list();
	}

	@Override
	public PageResult query(ObjectQuery qo) {
		return this.stockOutcomeBillDao.query(qo);
	}

	public void parseItem(StockOutcomeBill bill) {
		for (StockOutcomeBillItem item : bill.getItems()) {
			// 3.处理单据和明细之间的关系
			item.setBill(bill);
			// 4.计算单条明细小计(setScale(2, RoundingMode.HALF_UP)：四舍五入)
			item.setAmount(item.getAmount().multiply(item.getSalePrice()).setScale(2, RoundingMode.HALF_UP));
			// 5.计算单据的总数量和总金额
			bill.setTotalNumber(bill.getTotalNumber().add(item.getNumber()));
			bill.setTotalAmount(bill.getTotalAmount().add(item.getAmount()));
		}
	}

	@Override
	public void audit(Long id) {
		StockOutcomeBill bill = this.stockOutcomeBillDao.get(id);
		// 判断是否为审核状态
		if (bill.getStatus() == StockOutcomeBill.NORMAL) {
			// 设置审核状态为已经审核
			bill.setStatus(StockOutcomeBill.AUDIT);
			// 设置审核人，审核日期
			bill.setAuditor(UserContext.getEmployee());
			bill.setAuditTime(new Date());
			// 库存管理
			List<StockOutcomeBillItem> items = bill.getItems();
			for (StockOutcomeBillItem item : items) {
				BigDecimal costPrice = productStockService.stockOutcome(bill.getDepot(),item.getProduct(),item.getNumber());
				// 销售帐信息
				SaleAccount sa = new SaleAccount();
				sa.setVdate(item.getBill().getVdate());
				sa.setCostPrice(costPrice);
				sa.setCostAmount(costPrice.multiply(item.getNumber()).setScale(2, RoundingMode.HALF_UP));
				sa.setSalePrice(item.getSalePrice());
				sa.setSaleAmount(item.getAmount());
				sa.setNumber(item.getNumber());
				sa.setProduct(item.getProduct());
				sa.setClient(bill.getClient());
				sa.setSaleman(bill.getInputUser());
				saleAccountDao.add(sa);
			}
			// 更改数据库操作
			this.stockOutcomeBillDao.update(bill);
		}
	}

}
