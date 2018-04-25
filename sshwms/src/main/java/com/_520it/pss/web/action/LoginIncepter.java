package com._520it.pss.web.action;

import com._520it.pss.entity.Employee;
import com._520it.pss.util.UserContext;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginIncepter extends AbstractInterceptor {
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invoke) throws Exception {
		Employee emp = (Employee)UserContext.getEmployee();
		if(emp == null){
			return Action.LOGIN;
		}
		return invoke.invoke();
	}

}
