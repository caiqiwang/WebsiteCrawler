package com.study.crawler.category.abstracts.impl;

import java.util.List;

import com.study.crawler.category.abstracts.WebsiteCategoryAbstract;

public class JDCategory extends WebsiteCategoryAbstract {
	public JDCategory() {

	}

	public JDCategory(List<String> list) {
		super(list);
	}

	@Override
	public void getWebsiteCategory() {
		// phix
		String Phix = "https://search.jd.com/search?keyword=%E7%94%B5%E5%AD%90%E7%83%9F&enc=utf-8&qrst=1&stop=1&vt=2&ev=exbrand_PHIX%5E&uc=0#J_searchWrap"
				+ "!PHIX";
		// 山岚
		String laan = "https://search.jd.com/search?keyword=%E7%94%B5%E5%AD%90%E7%83%9F&enc=utf-8&qrst=1&stop=1&vt=2&ev=exbrand_%E5%B1%B1%E5%B2%9A%EF%BC%88Laan%EF%BC%89%5E&uc=0#J_searchWrap"
				+ "!山岚";
		// juul
		String juul = "https://search.jd.com/search?keyword=%E7%94%B5%E5%AD%90%E7%83%9F&enc=utf-8&qrst=1&stop=1&vt=2&ev=exbrand_JUUL%5E&uc=0#J_searchWrap"
				+ "!JUUL";
		// 超感觉
		String cgj = "https://search.jd.com/search?keyword=%E7%94%B5%E5%AD%90%E7%83%9F&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&ev=exbrand_%E8%B6%85%E6%84%9F%E8%A7%89%5E&uc=0#J_searchWrap"
				+ "!超感觉";
		// bopps
		String bopps = "https://search.jd.com/search?keyword=%E7%94%B5%E5%AD%90%E7%83%9F&enc=utf-8&qrst=1&stop=1&vt=2&ev=exbrand_%E5%8D%9A%E5%8D%87%EF%BC%88BOPPS%EF%BC%89%5E&uc=0#J_searchWrap"
				+ "!BOPPS";
		list.add(Phix);
		list.add(laan);
		list.add(juul);
		list.add(cgj);
		list.add(bopps);
	}

}
