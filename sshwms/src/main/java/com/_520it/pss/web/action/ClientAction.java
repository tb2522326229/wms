package com._520it.pss.web.action;

import com._520it.pss.entity.Client;
import com._520it.pss.query.ClientObjectQuery;
import com._520it.pss.service.ClientService;

import java.util.ArrayList;
import java.util.List;

import com._520it.pss.util.RequiredPermission;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import lombok.Getter;
import lombok.Setter;

public class ClientAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Setter
	@Getter
	private ClientService clientService;
	// 接受前台传过来的参数
	@Setter
	@Getter
	private ClientObjectQuery qo = new ClientObjectQuery();
	// 接收前台保存或修改的对象
	@Setter
	@Getter
	private Client client = new Client();
	// 接收前台传入的id集合进行批量删除
	@Setter
	@Getter
	private List<Long> ids = new ArrayList<Long>();

	@RequiredPermission("客户列表")
	@InputConfig(methodName = "input")
	public String execute() throws Exception  {
		try {
			this.addContext("pageResult", this.clientService.query(qo));
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return LIST;
	}

	// 进入input.jsp
	@RequiredPermission("转到新增/修改客户界面")
	public String input() throws Exception  {
		if (client.getId() != null) {
			client = clientService.get(client.getId());
		}
		return INPUT;
	}

	//
	public void prepareSaveOrUpdate() throws Exception  {
		if (client.getId() != null) {
			client = this.clientService.get(client.getId());
		}
	}

	// 这个方法最后跳到execute方法中
	@RequiredPermission("新增客户")
	public String saveOrUpdate() throws Exception  {
		try {
			if (client.getId() != null) {// 如果用户不为空，则是修改操作
				this.clientService.update(client);
				addActionMessage("修改成功");
			} else {// 如果用户为空则是新增操作
				this.clientService.add(client);
				addActionMessage("新增成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}

	@RequiredPermission("删除客户")
	public String delete() throws Exception  {
		if (client.getId() != null) {
			this.clientService.delete(client.getId());
			putResponseText("删除成功");
		}
		return NONE;
	}
	@RequiredPermission("批量删除客户")
	public String batchDelete() throws Exception {
		if (ids.size() > 0) {
			clientService.batchDelete(ids);
		}
		return NONE;
	}
}
