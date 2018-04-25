package com._520it.pss.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 到货入库单
 */
@Getter
@Setter
public class StockIncomeBill extends BaseDomain {
	public static final int NORMAL = 0;
	public static final int AUDIT = 1;
	private String sn;// 货品编号，可自动生成也可手动录入
	private Date vdate;// 业务时间
	private int status = 0;// 货品审核状态，缺省是未审核
	private Date inputTime;// 制单时间
	private Date auditTime;// 审核时间
	private BigDecimal totalNumber;// 库存总数量
	private BigDecimal totalAmount;// 库存总金额

	private Employee inputUser;// 制单人
	private Employee auditor;// 审核人
	private Depot depot;// 仓库
	private List<StockIncomeBillItem> items = new ArrayList<StockIncomeBillItem>();// 订单明细
}
