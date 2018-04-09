package com.study.crawler.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.crawler.dao.CompanyDao;
import com.study.crawler.entity.CompanyInfo;
import com.study.crawler.manager.CompanyManager;

@Service
public class CompanyManagerImpl implements CompanyManager {
	@Autowired
	private CompanyDao companyDao;

	public void insertCompanyInfo(CompanyInfo companyInfo) {
		// TODO Auto-generated method stub
		companyDao.insertCompanyInfo(companyInfo);
	}

}
