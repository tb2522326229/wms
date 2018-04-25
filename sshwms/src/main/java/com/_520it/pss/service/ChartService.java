package com._520it.pss.service;

import java.util.List;

import com._520it.pss.query.OrderChartObjectQuery;
import com._520it.pss.query.SaleChartObjectQuery;
import com._520it.pss.vo.OrderChartVO;
import com._520it.pss.vo.SaleChartVO;

public interface ChartService {
	/**
	 * 订单报表查询
	 * @param qo 查询条件
	 * @return
	 */
	public List<OrderChartVO> queryOrderChart(OrderChartObjectQuery qo);
	/**
	 * 销售报表查询
	 * @param qo 查询条件
	 * @return
	 */
	public List<SaleChartVO> querySaleChart(SaleChartObjectQuery qo);
}
