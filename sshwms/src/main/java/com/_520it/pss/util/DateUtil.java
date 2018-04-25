package com._520it.pss.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public DateUtil() {
	}

	/**
	 * 把传入时间设置为起始时间，把时分秒设置为0
	 * 
	 * @param current
	 *            传入的时间
	 * @return 设置后的时间
	 */
	public static Date getBeginDate(Date current) {
		Calendar c = Calendar.getInstance();
		c.setTime(current);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 0, 0, 0);
		return c.getTime();
	}

	/**
	 * 把传入时间设置为结束时间 1:把时分秒设置为0; 2:把天 + 1; 3:把秒 - 1
	 * 
	 * @param now
	 * @return
	 */
	public static Date getEndDate(Date current) {
		Calendar c = Calendar.getInstance();
		c.setTime(current);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 0, 0, 0);
		c.add(Calendar.DATE, 1);
		c.add(Calendar.SECOND, -1);
		return c.getTime();
	}
	public static void main(String[] args) {
		System.out.println(getBeginDate(new Date()).toLocaleString());
		System.out.println(getEndDate(new Date()).toLocaleString());
	}
}
