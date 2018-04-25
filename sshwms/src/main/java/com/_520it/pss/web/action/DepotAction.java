package com._520it.pss.web.action;

import com._520it.pss.entity.Depot;
import com._520it.pss.query.DepotObjectQuery;
import com._520it.pss.service.DepotService;

import java.util.ArrayList;
import java.util.List;

import com._520it.pss.util.RequiredPermission;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import lombok.Getter;
import lombok.Setter;

public class DepotAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Setter
	@Getter
	private DepotService depotService;
	// 接受前台传过来的参数
	@Setter
	@Getter
	private DepotObjectQuery qo = new DepotObjectQuery();
	// 接收前台保存或修改的对象
	@Setter
	@Getter
	private Depot depot = new Depot();
	// 接收前台传入的id集合进行批量删除
	@Setter
	@Getter
	private List<Long> ids = new ArrayList<Long>();

	@RequiredPermission("仓库列表")
	@InputConfig(methodName = "input")
	public String execute() throws Exception  {
		try {
			this.addContext("pageResult", this.depotService.query(qo));
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return LIST;
	}

	// 进入input.jsp
	@RequiredPermission("转到新增/修改仓库界面")
	public String input() throws Exception  {
		if (depot.getId() != null) {
			depot = depotService.get(depot.getId());
		}
		return INPUT;
	}

	//
	public void prepareSaveOrUpdate() throws Exception  {
		if (depot.getId() != null) {
			depot = this.depotService.get(depot.getId());
		}
	}

	// 这个方法最后跳到execute方法中
	@RequiredPermission("新增仓库")
	public String saveOrUpdate() throws Exception  {
		try {
			if (depot.getId() != null) {// 如果用户不为空，则是修改操作
				this.depotService.update(depot);
				addActionMessage("修改成功");
			} else {// 如果用户为空则是新增操作
				this.depotService.add(depot);
				addActionMessage("新增成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}

	@RequiredPermission("删除仓库")
	public String delete() throws Exception  {
		if (depot.getId() != null) {
			this.depotService.delete(depot.getId());
			putResponseText("删除成功");
		}
		return NONE;
	}
	@RequiredPermission("批量删除仓库")
	public String batchDelete() throws Exception {
		if (ids.size() > 0) {
			depotService.batchDelete(ids);
		}
		return NONE;
	}
}
