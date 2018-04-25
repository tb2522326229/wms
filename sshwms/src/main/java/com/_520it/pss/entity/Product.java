package com._520it.pss.entity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com._520it.pss.util.FileUploadUtil;
import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
/**
 * 商品
 */
public class Product extends BaseDomain {
	private String name;// 商品名称
	private String sn;// 商品编号
	private BigDecimal costPrice;// 商品成本价
	private BigDecimal salePrice;// 商品销售价格
	private String imagePath;// 商品图片路径
	private String intro;// 商品介绍
	private Brand brand;// 商品的品牌

	public String getSmailImagePath() {
		if (imagePath == null) {
			return null;
		}
		int index = imagePath.lastIndexOf(".");
		return imagePath.substring(0, index) + FileUploadUtil.suffix + imagePath.substring(index);
	}

	public String getProductJsonString() {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("id", id);
		jsonMap.put("name", name);
		jsonMap.put("costPrice", costPrice);
		jsonMap.put("salePrice", salePrice);
		jsonMap.put("brandName", this.brand != null ? this.brand.getName() : null);
		return JSON.toJSONString(jsonMap);
	}
}
