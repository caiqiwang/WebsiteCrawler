package com.study.crawler.factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.small.crawler.util.ExcelUtil;
import com.study.crawler.category.abstracts.impl.JDCategory;
import com.study.crawler.details.abstracts.impl.JDDetailImpl;
import com.study.crawler.entity.CommodityInfo;
import com.study.crawler.lists.abstracts.impl.JDListImpl;

@Service
public class JDCrawlerFactory {
	public void startCrawler() {
		System.out.println("start");
		List<String> list = new ArrayList<>();
		Set<String> set = new HashSet<String>();
		List<CommodityInfo> commodityInfoList = new ArrayList<>();
		new JDCategory(list).getWebsiteCategory();

		JDListImpl listImpl = new JDListImpl(set);
		for (String str : list) {
			listImpl.getWebsiteList(str);
		}
		System.out.println("列表信息获取完毕 共有url=" + set.size());

		JDDetailImpl detailImpl = new JDDetailImpl(commodityInfoList);
		List<String> excelList = new ArrayList<String>();
		String info = "商品名称;商品链接;月销量;评价数;差评数;差评内容;电子烟品牌;店铺名称;来源网站;";
		excelList.add(info);
		for (String str : set) {
			detailImpl.getWebsiteDetail(str);
		}
		Collections.sort(commodityInfoList);// 集合内容进行排序
		for (CommodityInfo commodityInfo : commodityInfoList) {
			info = commodityInfo.toString();
			excelList.add(info);
		}

		ExcelUtil.exportExcel("E:\\excel\\JD.xls", excelList, false);
	}
}
