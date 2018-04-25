package com._520it.pss.query;

import java.util.Date;

import com._520it.pss.entity.OrderBill;
import com._520it.pss.util.DateUtil;

import lombok.Getter;
import lombok.Setter;

/**
 * 销售报表查询对象 查询主题：SaleAccount
 */
@Getter
@Setter
public class SaleChartObjectQuery extends ObjectQuery {

	private Date beginDate;// 业务开始时间
	private Date endDate;// 业务结束时间
	private String keyword;// 货品名称或编号
	private Long clientId = -1L;// 供应商
	private Long brandId = -1L;// 品牌
	private String groupType = "employee";// 分组类型

	public void custromQuery() {
		if (clientId > 0) {
			System.out.println("SaleChartObjectQuery中的clientId：" + clientId);
			super.addQuery(" obj.client.id = ? ", clientId);
		}
		if (brandId > 0) {
			System.out.println("SaleChartObjectQuery中的brandId：" + brandId);
			super.addQuery(" obj.product.brand.id = ? ", brandId);
		}
		if (beginDate != null) {
			super.addQuery("obj.vdate >= ?", DateUtil.getBeginDate(beginDate));
		}
		if (endDate != null) {
			super.addQuery("obj.vdate <= ?", DateUtil.getEndDate(endDate));
		}
		if (hasLength(keyword)) {
			super.addQuery(" obj.product.name like ? or obj.product.sn like ?", "%" + keyword + "%",
					"%" + keyword + "%");
		}
	}
}
