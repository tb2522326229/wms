package com._520it.pss.web.action;

import java.lang.reflect.Method;
import java.util.Set;

import com._520it.pss.entity.Employee;
import com._520it.pss.util.PermissionUtil;
import com._520it.pss.util.RequiredPermission;
import com._520it.pss.util.UserContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class SecurityInceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Employee currentEmp = (Employee) UserContext.getEmployee();
		// 如果是超级管理员则直接放行
		if (currentEmp != null && currentEmp.isAdmin()) {
			return invocation.invoke();
		}
		// 获取当前执行的方法
		String methodName = invocation.getProxy().getMethod();
		// 获取访问action中的所有public方法
		Method actionMethod = invocation.getProxy().getAction().getClass().getDeclaredMethod(methodName);
		// 获取所有带RequiredPermission标签的方法
		RequiredPermission rp = actionMethod.getAnnotation(RequiredPermission.class);
		// 判断当前action的方法中是否有RequiredPermission标签，如果没有（不需要权限）直接放行
		if (rp == null) {
			return invocation.invoke();
		}
		// 为当前访问的action方法创建权限表达式
		String expression = PermissionUtil.createExpression(actionMethod);
		// 获取当前action的权限表达式
		Set<String> permission = (Set<String>) UserContext.getPremission();
		// 判断当前session中是否存在action方法对应的权限表达式
		if (permission.contains(expression)) {
			return invocation.invoke();
		}
		return BaseAction.NOPERMISSION;
	}

}
