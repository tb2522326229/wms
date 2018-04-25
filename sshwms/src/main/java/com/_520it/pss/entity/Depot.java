package com._520it.pss.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Depot extends BaseDomain {
	private String name;// 仓库名称
	private String address;// 仓库地址
}
