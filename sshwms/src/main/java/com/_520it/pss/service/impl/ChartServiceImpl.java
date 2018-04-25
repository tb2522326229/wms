package com._520it.pss.service.impl;

import java.util.List;

import com._520it.pss.dao.ChartDao;
import com._520it.pss.query.OrderChartObjectQuery;
import com._520it.pss.query.SaleChartObjectQuery;
import com._520it.pss.service.ChartService;
import com._520it.pss.vo.OrderChartVO;
import com._520it.pss.vo.SaleChartVO;

import lombok.Setter;

public class ChartServiceImpl implements ChartService {
	@Setter
	private ChartDao chartDao;

	@Override
	public List<OrderChartVO> queryOrderChart(OrderChartObjectQuery qo) {
		return chartDao.queryOrderChart(qo);
	}

	@Override
	public List<SaleChartVO> querySaleChart(SaleChartObjectQuery qo) {
		return chartDao.querySaleChart(qo);
	}

}
