package com._520it.pss.web.action;

import com._520it.pss.entity.Supplier;
import com._520it.pss.query.SupplierObjectQuery;
import com._520it.pss.service.SupplierService;
import com._520it.pss.util.RequiredPermission;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import lombok.Getter;
import lombok.Setter;

public class SupplierAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Setter
	@Getter
	private SupplierService supplierService;
	// 接受前台传过来的参数
	@Setter
	@Getter
	private SupplierObjectQuery qo = new SupplierObjectQuery();
	// 接收前台保存或修改的对象
	@Setter
	@Getter
	private Supplier supplier = new Supplier();

	@RequiredPermission("Supplier列表")
	@InputConfig(methodName = "input")
	public String execute() throws Exception  {
		try {
			this.addContext("pageResult", this.supplierService.query(qo));
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return LIST;
	}

	// 进入input.jsp
	@RequiredPermission("转到新增/修改Supplier界面")
	public String input() throws Exception  {
		if (supplier.getId() != null) {
			supplier = supplierService.get(supplier.getId());
		}
		return INPUT;
	}

	//
	public void prepareSaveOrUpdate() throws Exception  {
		if (supplier.getId() != null) {
			supplier = this.supplierService.get(supplier.getId());
		}
	}

	// 这个方法最后跳到execute方法中
	@RequiredPermission("新增Supplier")
	public String saveOrUpdate() throws Exception  {
		try {
			if (supplier.getId() != null) {// 如果用户不为空，则是修改操作
				this.supplierService.update(supplier);
				addActionMessage("修改成功");
			} else {// 如果用户为空则是新增操作
				this.supplierService.add(supplier);
				addActionMessage("新增成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}

	@RequiredPermission("删除Supplier")
	public String delete() throws Exception  {
		if (supplier.getId() != null) {
			this.supplierService.delete(supplier.getId());
			putResponseText("删除成功");
		}
		return NONE;
	}

}
