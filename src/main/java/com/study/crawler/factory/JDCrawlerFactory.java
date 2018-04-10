package com.study.crawler.factory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.study.crawler.category.abstracts.impl.JDCategory;
import com.study.crawler.details.abstracts.impl.JDDetailImpl;
import com.study.crawler.lists.abstracts.impl.JDListImpl;

@Service
public class JDCrawlerFactory {
	public void startCrawler() {
		List<String> list = new ArrayList<>();
		Set<String> set = new HashSet<String>();
		new JDCategory(list);
		JDListImpl listImpl = new JDListImpl(set);
		for (String str : list) {
			listImpl.getWebsiteList(str);
		}
		JDDetailImpl detailImpl = new JDDetailImpl();
		for (String str : set) {
			detailImpl.getWebsiteDetail(str);
		}
	}
}
