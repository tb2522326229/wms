package com._520it.pss.web.action;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class BaseAction extends ActionSupport implements Preparable {
	private static final long serialVersionUID = 1L;
	// 定义基本的视图
	public static final String LIST = "list";
	public static final String NOPERMISSION = "nopermission";// 没有权限
	public static final String PAGERESULT = "result";

	// 把获取到的值放入context中
	public void addContext(String key, Object value) {
		ActionContext.getContext().put(key, value);
	}

	// 把获取到的值放入session中
	public void addSession(String key, Object value) {
		// 代码类似于HttpServletRequest.request.getSession().setAttribute("key", value);
		ActionContext.getContext().getSession().put(key, value);
	}

	/**
	 * 给客户端做响应数据
	 * @param data 
	 * @throws Exception
	 */
	public void putResponseText(String data) throws Exception{
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(data);
	}
	/**
	 * 给客户端输出json格式的字符串
	 * @param data 被转化的对象
	 * @throws Exception
	 */
	public void putJson(Object data) throws Exception{
		ServletActionContext.getResponse().setContentType("html/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(JSON.toJSONString(data));
	}
	@Override
	public void prepare() throws Exception {

	}

}
