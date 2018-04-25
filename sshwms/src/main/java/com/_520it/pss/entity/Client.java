package com._520it.pss.entity;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Client extends BaseDomain {
private String name;// 客户名称
private String sn;// 客户编码
private String phone;// 客户电话
}
