package com.study.crawler.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.crawler.dao.CategoryDao;
import com.study.crawler.entity.CategoryInfo;
import com.study.crawler.manager.CategoryManager;

@Service
public class CategroyManagerImpl implements CategoryManager {
	@Autowired
	private CategoryDao categoryDao;

	public void insertCategoryInfo(CategoryInfo categroyInfo) {
		// TODO Auto-generated method stub
		categoryDao.insertCategoryInfo(categroyInfo);
	}

}
