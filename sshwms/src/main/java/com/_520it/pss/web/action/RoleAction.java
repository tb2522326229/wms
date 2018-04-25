package com._520it.pss.web.action;

import com._520it.pss.entity.Role;
import com._520it.pss.query.ObjectQuery;
import com._520it.pss.service.PermissionService;
import com._520it.pss.service.RoleService;
import com._520it.pss.service.SystemMenuService;
import com._520it.pss.util.RequiredPermission;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import lombok.Getter;
import lombok.Setter;

public class RoleAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Setter
	@Getter
	private RoleService roleService;
	@Setter
	@Getter
	private PermissionService permissionService;
	@Setter
	@Getter
	private SystemMenuService systemMenuService;
	// 接受前台传过来的参数
	@Setter
	@Getter
	private ObjectQuery qo = new ObjectQuery();
	// 接收前台保存或修改的对象
	@Setter
	@Getter
	private Role role = new Role();

	@RequiredPermission("角色列表")
	@InputConfig(methodName = "input")
	public String execute() throws Exception  {
		try {
			this.addContext("pageResult", this.roleService.query(qo));
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return LIST;
	}

	// 进入input.jsp
	@RequiredPermission("转到新增/修改角色界面")
	public String input() throws Exception  {
		this.addContext("permissions", this.permissionService.list());
		this.addContext("menus", this.systemMenuService.queryChildrenMenus());
		if (role.getId() != null) {
			role = roleService.get(role.getId());
		}
		return INPUT;
	}

	//
	public void prepareSaveOrUpdate() throws Exception  {
		if (role.getId() != null) {
			role = this.roleService.get(role.getId());
		}
		//添加到数据库的值会重复，是因为struts的配置文件中用的拦截器会生成两次参数，所以要在这里先清空，在插入
		role.getPermissions().clear();
		role.getMenus().clear();
	}

	// 这个方法最后跳到execute方法中
	@RequiredPermission("新增角色")
	public String saveOrUpdate() throws Exception  {
		try {
			if (role.getId() != null) {// 如果用户不为空，则是修改操作
				this.roleService.update(role);
				addActionMessage("修改成功");
			} else {// 如果用户为空则是新增操作
				this.roleService.add(role);
				addActionMessage("新增成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}

	@RequiredPermission("删除角色")
	public String delete() throws Exception  {
		if (role.getId() != null) {
			this.roleService.delete(role.getId());
			putResponseText("删除成功");
		}
		return NONE;
	}

}
