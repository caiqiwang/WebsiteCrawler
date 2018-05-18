package com.study.crawler.category.abstracts.impl;

import java.util.List;

import com.study.crawler.category.abstracts.WebsiteCategoryAbstract;

public class TaobaoCategory extends WebsiteCategoryAbstract {
	public TaobaoCategory(List<String> list) {
		super(list);
	}

	@Override
	public void getWebsiteCategory() {
		String NRX = "https://s.taobao.com/search?initiative_id=tbindexz_20170306&ie=utf8&spm=a21bo.2017.201856-taobao-item.2&sourceId=tb.index&search_type=item&ssid=s5-e&commend=all&imgfile=&q=nrx%E7%94%B5%E5%AD%90%E7%83%9F&suggest=0_1&_input_charset=utf-8&wq=NRXdianz&suggest_query=NRXdianz&source=suggest!NRX!8";
		String Phix = "https://s.taobao.com/search?q=PHIX%E7%94%B5%E5%AD%90%E7%83%9F&imgfile=&commend=all&ssid=s5-e&search_type=item&sourceId=tb.index&spm=a21bo.2017.201856-taobao-item.1&ie=utf8&initiative_id=tbindexz_20170306!PHIX!46";
		// 山岚
		// String laan =
		// "https://shopsearch.taobao.com/search?app=shopsearch&q=%E5%B1%B1%E5%B2%9A&imgfile=&commend=all&ssid=s5-e&search_type=shop&sourceId=tb.index&spm=a21bo.2017.201856-taobao-item.1&ie=utf8&initiative_id=tbindexz_20170306!山岚";
		// String juul =
		// "https://shopsearch.taobao.com/search?app=shopsearch&q=Juul&js=1&initiative_id=staobaoz_20180411&ie=utf8!juul";
		String cgj = "https://s.taobao.com/search?ie=utf8&initiative_id=staobaoz_20180428&stats_click=search_radio_all%3A1&js=1&imgfile=&q=%E8%B6%85%E6%84%9F%E8%A7%89%E7%94%B5%E5%AD%90%E7%83%9F&suggest=history_1&_input_charset=utf-8&wq=%E8%B6%85%E6%84%9F%E8%A7%89&suggest_query=%E8%B6%85%E6%84%9F%E8%A7%89&source=suggest!超感觉!5";
		String Lumia = "https://s.taobao.com/search?q=lumia%E7%94%B5%E5%AD%90%E7%83%9F&imgfile=&commend=all&ssid=s5-e&search_type=item&sourceId=tb.index&spm=a21bo.2017.201856-taobao-item.1&ie=utf8&initiative_id=tbindexz_20170306"
				+ "!Lumia!3";
		String MT = "https://s.taobao.com/search?q=MT%E7%94%B5%E5%AD%90%E7%83%9F&imgfile=&commend=all&ssid=s5-e&search_type=item&sourceId=tb.index&spm=a21bo.2017.201856-taobao-item.1&ie=utf8&initiative_id=tbindexz_20170306"
				+ "!MT!53";
		String RELX = "https://s.taobao.com/search?q=RELX%E7%94%B5%E5%AD%90%E7%83%9F&imgfile=&commend=all&ssid=s5-e&search_type=item&sourceId=tb.index&spm=a21bo.2017.201856-taobao-item.1&ie=utf8&initiative_id=tbindexz_20170306"
				+ "!RELX!2";
		String epen = "https://s.taobao.com/search?q=z%26g++e-pen%E7%94%B5%E5%AD%90%E7%83%9F&imgfile=&js=1&stats_click=search_radio_all%3A1&initiative_id=staobaoz_20180428&ie=utf8"
				+ "!E-pen!1";

		list.add(MT);
		list.add(Phix);
		list.add(NRX);
		list.add(RELX);
		list.add(cgj);
		list.add(Lumia);
		list.add(epen);
	}
}
