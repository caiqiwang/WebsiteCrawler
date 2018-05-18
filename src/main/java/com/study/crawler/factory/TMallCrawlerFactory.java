package com.study.crawler.factory;

import org.springframework.stereotype.Service;

@Service
public class TMallCrawlerFactory {
	public void startCrawler() {
		/*List<String> list = new ArrayList<>();
		Set<String> set = new HashSet<String>();
		List<CommodityInfo> commodityInfoList = new ArrayList<>();
		new TimaoCategory(list).getWebsiteCategory();
		
		TimaoListImpl listImpl = new TimaoListImpl(set);
		for (String str : list) {
			listImpl.getWebsiteList(str);
		}
		System.out.println("列表信息获取完毕 共有url=" + set.size());
		for (String str : set) {
			System.out.println(str);
		}
		TianMaoDetail detailImpl = new TianMaoDetail(commodityInfoList);
		List<String> excelList = new ArrayList<String>();
		String info = "商品名称;商品链接;月销量;评价数;差评数;差评内容;电子烟品牌;店铺名称;来源网站;";
		excelList.add(info);
		for (String str : set) {
			System.out.println("进入detail的url :" + str);
			detailImpl.getWebsiteDetail(str);
		}
		Collections.sort(commodityInfoList);// 集合内容进行排序
		for (CommodityInfo commodityInfo : commodityInfoList) {
			info = commodityInfo.toString();
			excelList.add(info);
		}
		
		ExcelUtil.exportExcel("E:\\excel\\TMall.xls", excelList, false);*/
	}
}
