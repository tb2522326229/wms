package com._520it.pss.query;

import java.util.Date;

import com._520it.pss.util.DateUtil;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockIncomeBillObjectQuery extends ObjectQuery {
	private Long depotId = -1L;// 供应商id
	private Date beginDate;// 业务开始时间
	private Date endDate;// 业务结束时间
	private int status = -1;// 审核状态，默认为所有状态

	public void custromQuery() {
		if (depotId > 0) {
			super.addQuery(" obj.depot.id = ? ", depotId);
		}
		if (status >= 0) {
			super.addQuery(" obj.status = ?", status);
		}
		if (beginDate != null) {
			super.addQuery("obj.vdate >= ?", DateUtil.getBeginDate(beginDate));
		}
		if (endDate != null) {
			super.addQuery("obj.vdate <= ?", DateUtil.getEndDate(endDate));
		}
	}
}
