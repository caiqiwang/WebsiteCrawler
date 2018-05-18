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
		String Lumia = "https://search.jd.com/search?keyword=%E7%94%B5%E5%AD%90%E7%83%9F&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&ev=exbrand_Lumia%5E&uc=0#J_searchWrap"
				+ "!Lumia";
		String RELX = "https://search.jd.com/search?keyword=%E7%94%B5%E5%AD%90%E7%83%9F&enc=utf-8&qrst=1&stop=1&vt=2&ev=exbrand_RELX%5E&uc=0#J_searchWrap"
				+ "!RELX";
		// 关键字搜索的url
		String MT = "https://search.jd.com/Search?keyword=mt&enc=utf-8&wq=mt&pvid=5de25dbedb1b47ff8f4879a70767cee5"
				+ "!MT";
		String NRX = "https://search.jd.com/Search?keyword=nrx&enc=utf-8&wq=nr&pvid=08181cf39ec84d78a7f5c2b3a0a8042d"
				+ "!NRX";
		String sPhix = "https://search.jd.com/Search?keyword=PHIX&enc=utf-8&wq=nr&pvid=08181cf39ec84d78a7f5c2b3a0a8042d"
				+ "!phix";
		String sCgj = "https://search.jd.com/Search?keyword=%E8%B6%85%E6%84%9F%E8%A7%89&enc=utf-8&wq=%E8%B6%85%E6%84%9F%E8%A7%89&pvid=7a867599dee740da8f3e23ea05df5199"
				+ "!超感觉";
		String sLumai = "https://search.jd.com/Search?keyword=Lumia&enc=utf-8&wq=Lumia&pvid=43d10f2aa27c41ec9d705f41277afce8"
				+ "!Lumia";
		// https://search.jd.com/Search?keyword=nrx&enc=utf-8&wq=nr&pvid=08181cf39ec84d78a7f5c2b3a0a8042d
		list.add(Phix);
		// list.add(laan);
		// list.add(juul);
		list.add(cgj);
		// list.add(bopps);
		list.add(Lumia);
		list.add(RELX);
		list.add(NRX);
		list.add(MT);
		list.add(sPhix);
		list.add(sCgj);
		list.add(sLumai);

	}

}
