package com._520it.pss.web.action;

import java.util.ArrayList;
import java.util.List;

import com._520it.pss.entity.Department;
import com._520it.pss.query.EmployeeObjectQuery;
import com._520it.pss.service.DepartmentService;
import com._520it.pss.util.RequiredPermission;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import lombok.Getter;
import lombok.Setter;

public class DepartmentAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Setter
	@Getter
	private DepartmentService departmentService;
	@Getter
	private Department department = new Department();
	@Getter
	private EmployeeObjectQuery qo = new EmployeeObjectQuery();
	@Getter
	@Setter
	private List<Long> ids = new ArrayList<>();

	@RequiredPermission("获取部门列表")
	@InputConfig(methodName = "input")
	public String execute() throws Exception {
		try {
			ActionContext.getContext().put("pageResult", departmentService.query(qo));
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return BaseAction.LIST;
	}

	@RequiredPermission("进入新增/编辑部门页面")
	public String input() throws Exception {
		if (department.getId() != null) {
			department = departmentService.get(department.getId());
		}
		return BaseAction.INPUT;
	}

	@RequiredPermission("新增/修改部门")
	public String saveOrUpdate() throws Exception {
		try {
			if (department.getId() == null) {
				departmentService.add(department);
				addActionMessage("新增成功");
			} else {
				departmentService.update(department);
				addActionMessage("修改成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}

	@RequiredPermission("删除部门")
	public String delete() throws Exception {
		if(department.getId() != null){
			departmentService.delete(department.getId());
			putResponseText("删除成功");
		}
		return SUCCESS;
	}
	
	@RequiredPermission("批量删除部门")
	public String batchDelete() throws Exception{
		if(ids.size() > 0){
			departmentService.batchDelete(ids);
			putResponseText("删除成功");
		}
		return NONE;
	}
}
