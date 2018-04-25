package com._520it.pss.web.action;

import com._520it.pss.query.ProductStockObjectQuery;
import com._520it.pss.service.BrandService;
import com._520it.pss.service.DepotService;
import com._520it.pss.service.ProductStockService;

import com._520it.pss.util.RequiredPermission;

import lombok.Getter;
import lombok.Setter;

public class ProductStockAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Setter
	@Getter
	private ProductStockService productStockService;
	@Setter
	private DepotService depotService;
	@Setter
	private BrandService brandService;
	// 接受前台传过来的参数
	@Setter
	@Getter
	private ProductStockObjectQuery qo = new ProductStockObjectQuery();

	@RequiredPermission("货品库存列表")
	public String execute() throws Exception {
		qo.setPageSize(25);
		this.addContext("depots", depotService.list());
		this.addContext("brands", brandService.list());
		this.addContext("pageResult", this.productStockService.query(qo));
		return LIST;
	}

}
