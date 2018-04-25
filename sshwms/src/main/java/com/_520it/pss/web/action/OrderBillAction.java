package com._520it.pss.web.action;

import java.util.ArrayList;
import java.util.List;

import com._520it.pss.entity.OrderBill;
import com._520it.pss.query.OrderBillObjectQuery;
import com._520it.pss.service.OrderBillService;
import com._520it.pss.service.SupplierService;
import com._520it.pss.util.RequiredPermission;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import lombok.Getter;
import lombok.Setter;

public class OrderBillAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Setter
	@Getter
	private OrderBillService orderBillService;
	@Setter
	@Getter
	private SupplierService supplierService;
	// 接受前台传过来的参数
	@Setter
	@Getter
	private OrderBillObjectQuery qo = new OrderBillObjectQuery();
	// 接收前台保存或修改的对象
	@Setter
	@Getter
	private OrderBill orderBill = new OrderBill();
	@Setter
	@Getter
	private List<Long> ids = new ArrayList<Long>();
	
	@RequiredPermission("采购订单列表")
	@InputConfig(methodName = "input")
	public String execute() throws Exception  {
		try {
			this.addContext("suppliers",supplierService.list());
			this.addContext("pageResult", this.orderBillService.query(qo));
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return LIST;
	}

	// 进入input.jsp
	@RequiredPermission("转到新增/修改采购订单界面")
	public String input() throws Exception  {
		this.addContext("suppliers",supplierService.list());
		if (orderBill.getId() != null) {
			orderBill = orderBillService.get(orderBill.getId());
		}
		return INPUT;
	}

	//
	public void prepareSaveOrUpdate() throws Exception  {
		if (orderBill.getId() != null) {
			orderBill = this.orderBillService.get(orderBill.getId());
			orderBill.setSupplier(null);
			orderBill.getItems().clear();
		}
	}

	// 这个方法最后跳到execute方法中
	@RequiredPermission("新增采购订单")
	public String saveOrUpdate() throws Exception  {
		try {
			if (orderBill.getId() != null) {// 如果用户不为空，则是修改操作
				this.orderBillService.update(orderBill);
				addActionMessage("修改成功");
			} else {// 如果用户为空则是新增操作
				this.orderBillService.add(orderBill);
				addActionMessage("新增成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}

	@RequiredPermission("删除采购订单")
	public String delete() throws Exception  {
		if (orderBill.getId() != null) {
			this.orderBillService.delete(orderBill.getId());
			putResponseText("删除成功");
		}
		return NONE;
	}
	@RequiredPermission("批量删除单据")
	public String batchDelete() throws Exception {
		if (ids.size() > 0) {
			this.orderBillService.batchDelete(ids);
		}
		return NONE;
	}
	
	@RequiredPermission("审核单据")
	public String audit(){
		if (orderBill.getId() != null) {
			this.orderBillService.audit(orderBill.getId());
			addActionMessage("审核成功");
		}
		return SUCCESS;
	}
	
	@RequiredPermission("查看已经审核完的单据")
	public String show(){
		if (orderBill.getId() != null) {
			orderBill = this.orderBillService.get(orderBill.getId());
		}
		return "show";
	}
	
}
