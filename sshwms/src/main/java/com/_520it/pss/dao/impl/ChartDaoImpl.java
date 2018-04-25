package com._520it.pss.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com._520it.pss.dao.ChartDao;
import com._520it.pss.query.ObjectQuery;
import com._520it.pss.query.OrderChartObjectQuery;
import com._520it.pss.query.SaleChartObjectQuery;
import com._520it.pss.vo.OrderChartVO;
import com._520it.pss.vo.OrderGroupByType;
import com._520it.pss.vo.SaleChartVO;
import com._520it.pss.vo.SaleGroupByType;

import lombok.Setter;

public class ChartDaoImpl implements ChartDao {
	@Setter
	private SessionFactory sessionFactory;

	@Override
	public List<OrderChartVO> queryOrderChart(OrderChartObjectQuery qo) {
		Session session = sessionFactory.getCurrentSession();
		OrderGroupByType groupByType = OrderGroupByType.valueOf(qo.getGroupType().toUpperCase());
		StringBuilder hql = new StringBuilder(100);
		hql.append("select new OrderChartVO( ");
		hql.append(groupByType.getGroupValue());
		hql.append(" ,sum(obj.number) , sum(obj.amount)) from OrderBillItem obj ");
		hql.append(qo.getQuery());
		hql.append("group by ");
		hql.append(groupByType.getGroupBy());
		Query query = session.createQuery(hql.toString());
		setPlaceParameter(qo, query);
		return query.list();
	}

	@Override
	public List<SaleChartVO> querySaleChart(SaleChartObjectQuery qo) {
		Session session = sessionFactory.getCurrentSession();
		SaleGroupByType groupByType = SaleGroupByType.valueOf(qo.getGroupType().toUpperCase());
		StringBuilder hql = new StringBuilder(100);
		hql.append("select new SaleChartVO(");
		hql.append(groupByType.getGroupValue());
		hql.append(" ,sum(obj.number),sum(obj.saleAmount),sum(obj.saleAmount - obj.costAmount)) from SaleAccount obj ");
		hql.append(qo.getQuery());
		hql.append("group by ");
		hql.append(groupByType.getGroupBy());
		Query query = session.createQuery(hql.toString());
		setPlaceParameter(qo, query);
		return query.list();
	}
	
	/**
	 * 设置占位符参数的方法
	 * 
	 * @param qo
	 *            查询条件
	 * @param query
	 *            Query对象
	 */
	public void setPlaceParameter(ObjectQuery qo, Query query) {
		for (int i = 0; i < qo.getParameters().size(); i++) {
			query.setParameter(i, qo.getParameters().get(i));
		}
	}
}
