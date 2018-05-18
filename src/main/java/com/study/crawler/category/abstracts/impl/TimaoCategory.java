package com.study.crawler.category.abstracts.impl;

import java.util.List;

import com.study.crawler.category.abstracts.WebsiteCategoryAbstract;

public class TimaoCategory extends WebsiteCategoryAbstract {
	public TimaoCategory(List<String> list) {
		super(list);
	}

	@Override
	public void getWebsiteCategory() {
		// TODO Auto-generated method stub
		// phix
		String Phix = "https://list.tmall.com/search_product.htm?q=Phix&type=p&vmarket=&spm=875.7931836%2FB.a2227oh.d100&from=mallfp..pc_1_searchbutton"
				+ "!PHIX";
		// 山岚
		String laan = "https://list.tmall.com/search_product.htm?q=%C9%BD%E1%B0&sort=s&style=g&from=mallfp..pc_1_searchbutton&active=2&cat=50100092"
				+ "!山岚";

		// 超感觉
		String cgj = "https://list.tmall.com/search_product.htm?q=%B3%AC%B8%D0%BE%F5&sort=s&style=g&from=mallfp..pc_1_searchbutton&active=2&cat=50100092"
				+ "!超感觉";

		String nrx = "https://list.tmall.com/search_product.htm?q=NRX&sort=s&style=g&from=mallfp..pc_1_searchbutton&active=2&cat=50100092"
				+ "!NRX";
		String mt = "https://list.tmall.com/search_product.htm?q=MT&sort=s&style=g&from=mallfp..pc_1_searchbutton&active=2&cat=50100092"
				+ "!MT";
		list.add(Phix);
		list.add(laan);
		list.add(mt);
		list.add(cgj);
		list.add(nrx);
	}
}
