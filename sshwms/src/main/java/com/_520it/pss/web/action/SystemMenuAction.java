package com._520it.pss.web.action;

import java.util.ArrayList;
import java.util.List;

import com._520it.pss.entity.SystemMenu;
import com._520it.pss.query.SystemMenuObjectQuery;
import com._520it.pss.service.SystemMenuService;
import com._520it.pss.util.RequiredPermission;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import lombok.Getter;
import lombok.Setter;

public class SystemMenuAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Setter
	@Getter
	private SystemMenuService systemMenuService;
	// 接受前台传过来的参数
	@Setter
	@Getter
	private SystemMenuObjectQuery qo = new SystemMenuObjectQuery();
	// 接收前台保存或修改的对象
	@Setter
	@Getter
	private SystemMenu systemMenu = new SystemMenu();
	@Setter
	@Getter
	private List<Long> ids = new ArrayList<Long>();

	@RequiredPermission("系统菜单列表")
	@InputConfig(methodName = "input")
	public String execute() {
		try {
			if (qo.getParentId() > 0) {
				this.addContext("menus", this.systemMenuService.queryMenusByParentId(qo.getParentId()));
			}
			this.addContext("pageResult", this.systemMenuService.query(qo));
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return LIST;
	}

	// 进入input.jsp
	@RequiredPermission("转到新增/修改系统菜单界面")
	public String input() {
		if (qo.getParentId() > 0) {
			addContext("parentsName", systemMenuService.get(qo.getParentId()).getName());
		} else if (qo.getParentId() < 0) {
			addContext("parentsName", "根菜单");
		}
		if (systemMenu.getId() != null) {
			systemMenu = systemMenuService.get(systemMenu.getId());
		}
		return INPUT;
	}

	//
	public void prepareSaveOrUpdate() {
		if (systemMenu.getId() != null) {
			systemMenu = this.systemMenuService.get(systemMenu.getId());
		}
	}

	// 这个方法最后跳到execute方法中
	@RequiredPermission("新增系统菜单")
	public String saveOrUpdate() {
		try {
			if (systemMenu.getId() != null) {// 如果用户不为空，则是修改操作
				this.systemMenuService.update(systemMenu);
				addActionMessage("修改成功");
			} else {// 如果用户为空则是新增操作
				// 新增子菜单的时候，接收前台传来的父id并设置
				if (qo.getParentId() > 0) {
					SystemMenu parentMenu = systemMenuService.get(qo.getParentId());
					// 设置父级菜单
					systemMenu.setParent(parentMenu);
				}
				this.systemMenuService.add(systemMenu);
				addActionMessage("新增成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}

	@RequiredPermission("删除系统菜单")
	public String delete() throws Exception {
		try {
			if (systemMenu.getId() != null) {
				this.systemMenuService.delete(systemMenu.getId());
				putResponseText("删除成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			putResponseText(e.getMessage());
		}
		return NONE;
	}

	/**
	 * 接收前台ztree传递过来的sn=qo.parentSn
	 * @return 根据sn查询出来的系统菜单
	 * @throws Exception
	 */
	public String loadMenuByParentSn() throws Exception {
		List<SystemMenu> menus = systemMenuService.loadMenuByParentSn(qo.getParentSn());
		List<Object> jsonList = new ArrayList<>();// 封装将systemMenu转换完json格式后的对象
		for (SystemMenu menu : menus) {
			jsonList.add(menu.toJson());
		}
		// 将拼接完成后的json格式的子菜单加载回前台页面（main.jsp）对应的一级菜单下
		putJson(jsonList);
		return NONE;
	}

	@RequiredPermission("批量删除菜单")
	public String batchDelete() throws Exception {
		if (ids.size() > 0) {
			systemMenuService.batchDelete(ids);
		}
		return NONE;
	}

}
