package com._520it.pss.service;

import java.util.List;

import com._520it.pss.entity.Brand;
import com._520it.pss.query.ObjectQuery;
import com._520it.pss.query.PageResult;


public interface BrandService {
	void add(Brand brand);// 新增

	void update(Brand brand);// 修改

	void delete(Long id);// 删除

	Brand get(Long id);// 通过id获取相应的结果

	List<Brand> list();// 获取相应实体类下的一切结果

	PageResult query(ObjectQuery qo);// 根据条件进行分页查询
}
