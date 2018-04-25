package com._520it.pss.web.action;

import com._520it.pss.entity.StockIncomeBill;
import com._520it.pss.entity.StockIncomeBillItem;
import com._520it.pss.query.PageResult;
import com._520it.pss.query.StockIncomeBillObjectQuery;
import com._520it.pss.service.DepotService;
import com._520it.pss.service.StockIncomeBillService;

import java.util.ArrayList;
import java.util.List;

import com._520it.pss.util.RequiredPermission;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import lombok.Getter;
import lombok.Setter;

public class StockIncomeBillAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Setter
	@Getter
	private StockIncomeBillService stockIncomeBillService;
	@Setter
	@Getter
	private DepotService depotService;
	// 接受前台传过来的参数
	@Setter
	@Getter
	private StockIncomeBillObjectQuery qo = new StockIncomeBillObjectQuery();
	// 接收前台保存或修改的对象
	@Setter
	@Getter
	private StockIncomeBill stockIncomeBill = new StockIncomeBill();
	// 接收前台传入的id集合进行批量删除
	@Setter
	@Getter
	private List<Long> ids = new ArrayList<Long>();

	@RequiredPermission("到货入库单列表")
	@InputConfig(methodName = "input")
	public String execute() throws Exception  {
		try {
			addContext("depots", depotService.list());
			addContext("pageResult", stockIncomeBillService.query(qo));
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return LIST;
	}

	// 进入input.jsp
	@RequiredPermission("转到新增/修改到货入库单界面")
	public String input() throws Exception  {
		addContext("depots", depotService.list());
		if (stockIncomeBill.getId() != null) {
			stockIncomeBill = stockIncomeBillService.get(stockIncomeBill.getId());
		}
		return INPUT;
	}

	//
	public void prepareSaveOrUpdate() throws Exception  {
		if (stockIncomeBill.getId() != null) {
			stockIncomeBill = stockIncomeBillService.get(stockIncomeBill.getId());
			stockIncomeBill.setDepot(null);
			stockIncomeBill.getItems().clear();
		}
	}

	// 这个方法最后跳到execute方法中
	@RequiredPermission("新增到货入库单")
	public String saveOrUpdate() throws Exception  {
		try {
			System.out.println(stockIncomeBill.getItems() == null);
			if (stockIncomeBill.getId() != null) {// 如果用户不为空，则是修改操作
				stockIncomeBillService.update(stockIncomeBill);
				addActionMessage("修改成功");
			} else {// 如果用户为空则是新增操作
				stockIncomeBillService.add(stockIncomeBill);
				addActionMessage("新增成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}

	@RequiredPermission("删除到货入库单")
	public String delete() throws Exception  {
		if (stockIncomeBill.getId() != null) {
			stockIncomeBillService.delete(stockIncomeBill.getId());
			putResponseText("删除成功");
		}
		return NONE;
	}
	@RequiredPermission("审核到货入库单")
	public String audit() throws Exception  {
		if (stockIncomeBill.getId() != null) {
			stockIncomeBillService.audit(stockIncomeBill.getId());
			addActionMessage("审核成功");
		}
		return SUCCESS;
	}
	@RequiredPermission("批量删除到货入库单")
	public String batchDelete() throws Exception {
		if (ids.size() > 0) {
			stockIncomeBillService.batchDelete(ids);
		}
		return NONE;
	}
	
	@RequiredPermission("查看已经审核完的库存")
	public String show() throws Exception{
		if(stockIncomeBill != null){
			stockIncomeBill = stockIncomeBillService.get(stockIncomeBill.getId());
		}
		return "show";
	}
}
