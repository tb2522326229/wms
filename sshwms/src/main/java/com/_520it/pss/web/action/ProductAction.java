package com._520it.pss.web.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com._520it.pss.entity.Product;
import com._520it.pss.query.ProductObjectQuery;
import com._520it.pss.service.BrandService;
import com._520it.pss.service.ProductService;
import com._520it.pss.util.FileUploadUtil;
import com._520it.pss.util.RequiredPermission;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import lombok.Getter;
import lombok.Setter;

public class ProductAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Setter
	@Getter
	private ProductService productService;
	// 接受前台传过来的参数
	@Setter
	@Getter
	private ProductObjectQuery qo = new ProductObjectQuery();
	// 获取商品的品牌
	@Setter
	@Getter
	private BrandService brandService;
	// 接收前台保存或修改的对象
	@Setter
	@Getter
	private Product product = new Product();
	@Setter
	@Getter
	private File pic;
	@Setter
	@Getter
	private String picFileName;
	@Setter
	@Getter
	private List<Long> ids = new ArrayList<Long>();

	@RequiredPermission("商品列表")
	@InputConfig(methodName = "input")
	public String execute() throws Exception {
		try {
			this.addContext("brands", brandService.list());
			this.addContext("pageResult", this.productService.query(qo));
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return LIST;
	}

	// 进入input.jsp
	@RequiredPermission("转到新增/修改商品界面")
	public String input() throws Exception {
		addContext("brands", brandService.list());
		if (product.getId() != null) {
			product = productService.get(product.getId());
			product.setBrand(null);
		}
		return INPUT;
	}

	//
	public void prepareSaveOrUpdate() throws Exception {
		if (product.getId() != null) {
			product = this.productService.get(product.getId());

		}
	}

	// 这个方法最后跳到execute方法中
	@RequiredPermission("新增商品")
	public String saveOrUpdate() throws Exception {
		try {
			if (product.getId() != null && pic != null && product.getSmailImagePath() != null) {// 修改操作
				FileUploadUtil.deleteFile(product.getImagePath());
			}
			if (pic != null) {
				String savePath = FileUploadUtil.uploadFile(pic, picFileName);
				product.setImagePath(savePath);
			}
			if (product.getId() != null) {// 如果用户不为空，则是修改操作
				this.productService.update(product);
				addActionMessage("修改成功");
			} else {// 如果用户为空则是新增操作
				this.productService.add(product);
				addActionMessage("新增成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}

	@RequiredPermission("删除商品")
	public String delete() throws Exception {
		if (product.getId() != null) {
			product = productService.get(product.getId());
			if (product.getImagePath() != null) {
				FileUploadUtil.deleteFile(product.getImagePath());
			}
			this.productService.delete(product.getId());
			putResponseText("删除成功");
		}
		return NONE;
	}

	@RequiredPermission("批量删除产品")
	public String batchDelete() throws Exception {
		if (ids.size() > 0) {
			this.productService.batchDelete(ids);
		}
		return NONE;
	}

	@RequiredPermission("商品选择列表")
	public String selectProductList() throws Exception {
		// 固定每页只显示10条
		qo.setPageSize(10);
		this.addContext("brands", brandService.list());
		this.addContext("pageResult", this.productService.query(qo));
		return "selectProductList";
	}
}
