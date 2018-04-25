package com._520it.pss.web.action;

import com._520it.pss.entity.Dog;
import com._520it.pss.query.DogObjectQuery;
import com._520it.pss.service.DogService;

import java.util.ArrayList;
import java.util.List;

import com._520it.pss.util.RequiredPermission;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import lombok.Getter;
import lombok.Setter;

public class DogAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Setter
	@Getter
	private DogService dogService;
	// 接受前台传过来的参数
	@Setter
	@Getter
	private DogObjectQuery qo = new DogObjectQuery();
	// 接收前台保存或修改的对象
	@Setter
	@Getter
	private Dog dog = new Dog();
	// 接收前台传入的id集合进行批量删除
	@Setter
	@Getter
	private List<Long> ids = new ArrayList<Long>();

	@RequiredPermission("Dog列表")
	@InputConfig(methodName = "input")
	public String execute() throws Exception  {
		try {
			this.addContext("pageResult", this.dogService.query(qo));
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return LIST;
	}

	// 进入input.jsp
	@RequiredPermission("转到新增/修改Dog界面")
	public String input() throws Exception  {
		if (dog.getId() != null) {
			dog = dogService.get(dog.getId());
		}
		return INPUT;
	}

	//
	public void prepareSaveOrUpdate() throws Exception  {
		if (dog.getId() != null) {
			dog = this.dogService.get(dog.getId());
		}
	}

	// 这个方法最后跳到execute方法中
	@RequiredPermission("新增Dog")
	public String saveOrUpdate() throws Exception  {
		try {
			if (dog.getId() != null) {// 如果用户不为空，则是修改操作
				this.dogService.update(dog);
				addActionMessage("修改成功");
			} else {// 如果用户为空则是新增操作
				this.dogService.add(dog);
				addActionMessage("新增成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}

	@RequiredPermission("删除Dog")
	public String delete() throws Exception  {
		if (dog.getId() != null) {
			this.dogService.delete(dog.getId());
			putResponseText("删除成功");
		}
		return NONE;
	}
	@RequiredPermission("批量删除Dog")
	public String batchDelete() throws Exception {
		if (ids.size() > 0) {
			dogService.batchDelete(ids);
		}
		return NONE;
	}
}
