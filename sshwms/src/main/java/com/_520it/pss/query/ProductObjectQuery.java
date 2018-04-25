package com._520it.pss.query;

import lombok.Getter;
import lombok.Setter;
@Setter @Getter
public class ProductObjectQuery extends ObjectQuery {
	private String keyword;
	private Long brand_id = -1L;
	public void custromQuery() {
		if(hasLength(keyword)){
			super.addQuery(" obj.name like ? or obj.sn like ?", "%" + keyword + "%","%" + keyword + "%");
		}
		if(brand_id > 0){
			super.addQuery(" obj.brand.id = ?",  brand_id);
		}
	}
}
