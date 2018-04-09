package com.study.crawler.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.crawler.dao.RecruitmentInfoDao;
import com.study.crawler.entity.RecruitmentInfo;
import com.study.crawler.manager.RecruitmentInfoManager;

@Service
public class RecruitmentInfoManagerImpl implements RecruitmentInfoManager {
	@Autowired
	private RecruitmentInfoDao recruitmentInfodao;

	public void insertRecruitmetInfo(RecruitmentInfo recruitmentInfo) {
		// TODO Auto-generated method stub
		recruitmentInfodao.insertRecruitmetInfo(recruitmentInfo);
	}

}
