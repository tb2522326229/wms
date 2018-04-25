package com._520it.pss.web.action;

import com._520it.pss.entity.StockOutcomeBill;
import com._520it.pss.query.StockOutcomeBillObjectQuery;
import com._520it.pss.service.ClientService;
import com._520it.pss.service.DepotService;
import com._520it.pss.service.StockOutcomeBillService;

import java.util.ArrayList;
import java.util.List;

import com._520it.pss.util.RequiredPermission;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import lombok.Getter;
import lombok.Setter;

public class StockOutcomeBillAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Setter
	@Getter
	private StockOutcomeBillService stockOutcomeBillService;
	@Setter
	@Getter
	private ClientService clientService;
	@Setter
	@Getter
	private DepotService depotService;
	// 接受前台传过来的参数
	@Setter
	@Getter
	private StockOutcomeBillObjectQuery qo = new StockOutcomeBillObjectQuery();
	// 接收前台保存或修改的对象
	@Setter
	@Getter
	private StockOutcomeBill stockOutcomeBill = new StockOutcomeBill();
	// 接收前台传入的id集合进行批量删除
	@Setter
	@Getter
	private List<Long> ids = new ArrayList<Long>();

	@RequiredPermission("到货入库单列表")
	@InputConfig(methodName = "input")
	public String execute() throws Exception {
		try {
			this.addContext("clients", this.clientService.list());
			this.addContext("deopts", this.depotService.list());
			this.addContext("pageResult", this.stockOutcomeBillService.query(qo));
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return LIST;
	}

	// 进入input.jsp
	@RequiredPermission("转到新增/修改到货入库单界面")
	public String input() throws Exception {
		this.addContext("depots", this.depotService.list());
		this.addContext("clients", this.depotService.list());
		if (stockOutcomeBill.getId() != null) {
			stockOutcomeBill = stockOutcomeBillService.get(stockOutcomeBill.getId());
		}
		return INPUT;
	}

	//
	public void prepareSaveOrUpdate() throws Exception {
		if (stockOutcomeBill.getId() != null) {
			stockOutcomeBill = this.stockOutcomeBillService.get(stockOutcomeBill.getId());
			stockOutcomeBill.setDepot(null);
			stockOutcomeBill.setClient(null);
		}
		stockOutcomeBill.getItems().clear();
	}

	// 这个方法最后跳到execute方法中
	@RequiredPermission("新增到货入库单")
	public String saveOrUpdate() throws Exception {
		try {
			if (stockOutcomeBill.getId() != null) {// 如果用户不为空，则是修改操作
				this.stockOutcomeBillService.update(stockOutcomeBill);
				addActionMessage("修改成功");
			} else {// 如果用户为空则是新增操作
				this.stockOutcomeBillService.add(stockOutcomeBill);
				addActionMessage("新增成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}

	@RequiredPermission("删除到货入库单")
	public String delete() throws Exception {
		if (stockOutcomeBill.getId() != null) {
			this.stockOutcomeBillService.delete(stockOutcomeBill.getId());
			putResponseText("删除成功");
		}
		return NONE;
	}

	@RequiredPermission("审核到货入库单")
	public String audit() {
		try {
			if (stockOutcomeBill.getId() != null) {
				this.stockOutcomeBillService.audit(stockOutcomeBill.getId());
				addActionMessage("审核成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addActionMessage(e.getMessage());
		}
		return SUCCESS;
	}

	@RequiredPermission("批量删除到货入库单")
	public String batchDelete() throws Exception {
		if (ids.size() > 0) {
			stockOutcomeBillService.batchDelete(ids);
		}
		return NONE;
	}

	@RequiredPermission("查看已经审核完的库存")
	public String show() throws Exception {
		if (stockOutcomeBill != null) {
			stockOutcomeBill = this.stockOutcomeBillService.get(stockOutcomeBill.getId());
		}
		return "show";
	}
}
