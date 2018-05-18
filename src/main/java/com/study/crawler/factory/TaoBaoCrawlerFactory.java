package com.study.crawler.factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.small.crawler.util.ExcelUtil;
import com.study.crawler.category.abstracts.impl.TaobaoCategory;
import com.study.crawler.details.abstracts.impl.TaobaoDetailImpl;
import com.study.crawler.entity.CommodityInfo;

@Service
public class TaoBaoCrawlerFactory {
	public void startCrawler() {
		List<String> list = new ArrayList<>();
		// Set<String> set = new HashSet<String>();
		List<CommodityInfo> commodityInfoList = new ArrayList<>();

		new TaobaoCategory(list).getWebsiteCategory();

		TaobaoDetailImpl detailImpl = new TaobaoDetailImpl(commodityInfoList);
		List<String> excelList = new ArrayList<String>();
		String info = "品牌;商品名称;价格;月销量;评价数;店铺名;链接;";
		excelList.add(info);

		for (String str : list) {
			detailImpl.getWebsiteDetail(str);
		}
		// 去重复
		Set<CommodityInfo> set = new HashSet<CommodityInfo>();
		set.addAll(commodityInfoList);
		List<CommodityInfo> commodityInfoLists = new ArrayList<>();
		commodityInfoLists.addAll(set);

		Collections.sort(commodityInfoLists);// 集合内容进行排序
		for (CommodityInfo commodityInfo : commodityInfoLists) {
			info = commodityInfo.getTaoBao();
			excelList.add(info);
		}
		ExcelUtil.exportExcel("E:\\excel\\TaoBao.xls", excelList, false);
	}
}
