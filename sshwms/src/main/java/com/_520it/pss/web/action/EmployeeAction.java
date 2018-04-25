package com._520it.pss.web.action;

import java.util.ArrayList;
import java.util.List;

import com._520it.pss.entity.Department;
import com._520it.pss.entity.Employee;
import com._520it.pss.query.EmployeeObjectQuery;
import com._520it.pss.service.DepartmentService;
import com._520it.pss.service.EmployeeService;
import com._520it.pss.service.RoleService;
import com._520it.pss.util.RequiredPermission;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private EmployeeService employeeService;
	private DepartmentService departmentService;
	private Employee employee = new Employee();
	private Department dept = new Department();
	private RoleService roleService;
	private EmployeeObjectQuery qo = new EmployeeObjectQuery();
	private List<Long> ids = new ArrayList<Long>();

	@RequiredPermission("获取员工列表")
	@InputConfig(methodName = "input")
	public String execute() throws Exception {
		try {
			addContext("depts", departmentService.list());
			addContext("pageResult", employeeService.pageQuery(qo));

		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return BaseAction.LIST;
	}

	@RequiredPermission("进入新增/修改员工界面")
	public String input() throws Exception {
		addContext("depts", departmentService.list());
		addContext("roles", roleService.list());
		if (employee.getId() != null) {
			employee = employeeService.get(employee.getId());
		}
		return BaseAction.INPUT;
	}

	@RequiredPermission("删除员工")
	public String delete() throws Exception {
		if (employee.getId() != null) {
			employeeService.delete(employee.getId());
			putResponseText("删除成功");
		}
		return NONE;
	}

	@RequiredPermission("新增/修改员工")
	public String saveOrUpdate() throws Exception {
		try {
			// int i = 1 / 0;
			if (employee.getId() != null) {
				employeeService.update(employee);
				addActionMessage("修改成功");
			} else {
				employeeService.add(employee);
				addActionMessage("保存成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return BaseAction.SUCCESS;
	}

	@RequiredPermission("批量删除员工")
	public String batchDelete() throws Exception {
		if (ids.size() > 0) {
			employeeService.batchDelete(ids);
		}
		return NONE;
	}

	// 不这样做，修改操作的时候，密码属性会因为无法再前台接收到对应的值儿在数据库中被清空
	public void prepareSaveOrUpdate() throws Exception {
		if (employee.getId() != null) {
			employee = employeeService.get(employee.getId());
			employee.setDept(null);
			employee.getRoles().clear();
		}
	}
}
