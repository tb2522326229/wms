package com._520it.pss.web.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com._520it.pss.query.OrderChartObjectQuery;
import com._520it.pss.query.SaleChartObjectQuery;
import com._520it.pss.service.BrandService;
import com._520it.pss.service.ChartService;
import com._520it.pss.service.ClientService;
import com._520it.pss.service.SupplierService;
import com._520it.pss.util.RequiredPermission;
import com._520it.pss.vo.OrderGroupByType;
import com._520it.pss.vo.SaleChartVO;
import com._520it.pss.vo.SaleGroupByType;
import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

public class ChartAction extends BaseAction {
	@Setter
	@Getter
	private ChartService chartService;
	@Setter
	@Getter
	private BrandService brandService;
	@Setter
	@Getter
	private SupplierService supplierService;
	@Setter
	@Getter
	private ClientService clientService;
	@Setter
	@Getter
	private OrderChartObjectQuery oqo = new OrderChartObjectQuery();
	@Setter
	@Getter
	private SaleChartObjectQuery sqo = new SaleChartObjectQuery();

	@RequiredPermission("订货报表")
	public String orderChart() throws Exception {
		super.addContext("brands", brandService.list());
		super.addContext("suppliers", supplierService.list());
		super.addContext("orderGroupByType", OrderGroupByType.values());
		super.addContext("orderCharts", chartService.queryOrderChart(oqo));
		return "orderChart";
	}

	@RequiredPermission("销售报表")
	public String saleChart() throws Exception {
		super.addContext("brands", brandService.list());
		super.addContext("clients", clientService.list());
		super.addContext("saleGroupByType", SaleGroupByType.values());
		super.addContext("saleCharts", chartService.querySaleChart(sqo));
		return "saleChart";
	}

	// 折现图
	public String saleChartByLine() throws Exception {
		List<SaleChartVO> saleChartVO = chartService.querySaleChart(sqo);
		List<String> groupByType = new ArrayList<String>();
		List<BigDecimal> totalAmount = new ArrayList<BigDecimal>();
		for (SaleChartVO saleVO : saleChartVO) {
			groupByType.add(saleVO.getGroupType());
			totalAmount.add(saleVO.getTotalAmounnt());
		}
		super.addContext("groupByType", JSON.toJSONString(groupByType));
		super.addContext("groupBy", SaleGroupByType.valueOf(sqo.getGroupType().toUpperCase()).getGroupType());
		super.addContext("totalAmount", JSON.toJSONString(totalAmount));
		return "saleChartByLine";
	}

	// 饼状图
	public String saleChartByPie() throws Exception {
		List<SaleChartVO> saleChartVO = chartService.querySaleChart(sqo);
		List<Object[]> datas = new ArrayList<Object[]>();
		for(SaleChartVO saleVO:saleChartVO){
			datas.add(new Object[]{saleVO.getGroupType(),saleVO.getTotalAmounnt()});
		}
		super.addContext("datas", JSON.toJSONString(datas));
		super.addContext("groupBy", SaleGroupByType.valueOf(sqo.getGroupType().toUpperCase()).getGroupType());
		return "saleChartByPie";
	}

}
