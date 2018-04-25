package com._520it.pss.web.action;

import com._520it.pss.entity.Brand;
import com._520it.pss.query.BrandObjectQuery;
import com._520it.pss.service.BrandService;
import com._520it.pss.util.RequiredPermission;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import lombok.Getter;
import lombok.Setter;

public class BrandAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Setter
	@Getter
	private BrandService brandService;
	// 接受前台传过来的参数
	@Setter
	@Getter
	private BrandObjectQuery qo = new BrandObjectQuery();
	// 接收前台保存或修改的对象
	@Setter
	@Getter
	private Brand brand = new Brand();

	@RequiredPermission("Brand列表")
	@InputConfig(methodName = "input")
	public String execute() throws Exception  {
		try {
			this.addContext("pageResult", this.brandService.query(qo));
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return LIST;
	}

	// 进入input.jsp
	@RequiredPermission("转到新增/修改Brand界面")
	public String input() throws Exception  {
		if (brand.getId() != null) {
			brand = brandService.get(brand.getId());
		}
		return INPUT;
	}

	//
	public void prepareSaveOrUpdate() throws Exception  {
		if (brand.getId() != null) {
			brand = this.brandService.get(brand.getId());
		}
	}

	// 这个方法最后跳到execute方法中
	@RequiredPermission("新增Brand")
	public String saveOrUpdate() throws Exception  {
		try {
			if (brand.getId() != null) {// 如果用户不为空，则是修改操作
				this.brandService.update(brand);
				addActionMessage("修改成功");
			} else {// 如果用户为空则是新增操作
				this.brandService.add(brand);
				addActionMessage("新增成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}

	@RequiredPermission("删除Brand")
	public String delete() throws Exception  {
		if (brand.getId() != null) {
			this.brandService.delete(brand.getId());
			putResponseText("删除成功");
		}
		return NONE;
	}

}
