package com._520it.pss.web.action;

import com._520it.pss.entity.Permission;
import com._520it.pss.query.ObjectQuery;
import com._520it.pss.service.PermissionService;
import com._520it.pss.util.RequiredPermission;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import lombok.Getter;
import lombok.Setter;

public class PermissionAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Setter
	@Getter
	private PermissionService permissionService;
	// 接受前台传过来的参数
	@Setter
	@Getter
	private ObjectQuery qo = new ObjectQuery();
	// 接收前台保存或修改的对象
	@Setter
	@Getter
	private Permission permission = new Permission();

	@RequiredPermission("权限列表")
	@InputConfig(methodName = "input")
	public String execute() {
		try {
			this.addContext("pageResult", this.permissionService.query(qo));
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return LIST;
	}

	@RequiredPermission("删除权限")
	public String delete() {
		if (permission.getId() != null) {
			this.permissionService.delete(permission.getId());
		}
		return SUCCESS;
	}
	
	@RequiredPermission("重新加载系统权限")
	public String reload(){
		this.permissionService.reload();
		return NONE;
	}

}
