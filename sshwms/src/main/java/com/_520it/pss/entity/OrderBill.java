package com._520it.pss.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 采购订单
 */
@Setter
@Getter
public class OrderBill extends BaseDomain {
	private static final long serialVersionUID = 1L;
	public static final int NORMAL = 0;// 0：未审核状态
	public static final int AUDIT = 1;// 1：已审核
	private String sn; // 单据编号，可自动生成也可手动录入
	private Date vdate;// 业务时间
	private int status;// 单据审核状态，缺省是未审核
	private Date inputTime;// 制单时间
	private Date auditTime;// 审核时间
	private BigDecimal totalNumber;// 采购总数量
	private BigDecimal totalAmount;// 采购总金额

	private Employee inputUser;// 制单人
	private Employee auditor;// 审核人
	private Supplier supplier;// 供应商
	private List<OrderBillItem> items;// 订单明细

	public OrderBill() {
		status = OrderBill.NORMAL;
		totalNumber = BigDecimal.ZERO;
		totalAmount = BigDecimal.ZERO;
		items = new ArrayList<OrderBillItem>();
	}
}
