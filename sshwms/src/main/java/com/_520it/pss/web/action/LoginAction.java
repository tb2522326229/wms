package com._520it.pss.web.action;

import com._520it.pss.service.EmployeeService;

import lombok.Getter;
import lombok.Setter;

public class LoginAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Setter @Getter
	private String username;
	@Setter @Getter
	private String password;
	@Setter @Getter
	private EmployeeService employeeService;
	public String execute(){
		try {
			employeeService.login(username,password);
		} catch (RuntimeException e) {
			super.addActionError(e.getMessage());
			return "login";
		}
		return SUCCESS;
	}
}
