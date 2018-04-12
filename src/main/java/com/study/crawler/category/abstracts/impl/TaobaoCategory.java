package com.study.crawler.category.abstracts.impl;

import java.util.List;

import com.study.crawler.category.abstracts.WebsiteCategoryAbstract;

public class TaobaoCategory extends WebsiteCategoryAbstract {
	public TaobaoCategory(List<String> list) {
		super(list);
	}

	@Override
	public void getWebsiteCategory() {
		String NHX = "https://shopsearch.taobao.com/search?app=shopsearch&q=NRX&imgfile=&commend=all&ssid=s5-e&search_type=shop&sourceId=tb.index&spm=a21bo.2017.201856-taobao-item.1&ie=utf8&initiative_id=tbindexz_20170306!NHX";
		String Phix = "https://shopsearch.taobao.com/search?app=shopsearch&q=Phix&imgfile=&commend=all&ssid=s5-e&search_type=shop&sourceId=tb.index&spm=a21bo.2017.201856-taobao-item.1&ie=utf8&initiative_id=tbindexz_20170306!Phix";
		// 山岚
		String laan = "https://shopsearch.taobao.com/search?app=shopsearch&q=%E5%B1%B1%E5%B2%9A&imgfile=&commend=all&ssid=s5-e&search_type=shop&sourceId=tb.index&spm=a21bo.2017.201856-taobao-item.1&ie=utf8&initiative_id=tbindexz_20170306!山岚";
		String juul = "https://shopsearch.taobao.com/search?app=shopsearch&q=Juul&js=1&initiative_id=staobaoz_20180411&ie=utf8!juul";
		String cgj = "https://shopsearch.taobao.com/search?app=shopsearch&q=%E8%B6%85%E6%84%9F%E8%A7%89&imgfile=&commend=all&ssid=s5-e&search_type=shop&sourceId=tb.index&spm=a21bo.2017.201856-taobao-item.1&ie=utf8&initiative_id=tbindexz_20170306!超感觉";
		list.add(NHX);
		list.add(Phix);
		list.add(laan);
		list.add(juul);
		list.add(cgj);
	}
}
