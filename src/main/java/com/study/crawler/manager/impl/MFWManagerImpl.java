package com.study.crawler.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.crawler.dao.MFWInfoDao;
import com.study.crawler.entity.MFWTravelNotesInfo;
import com.study.crawler.manager.MFWManager;

@Service
public class MFWManagerImpl implements MFWManager {
	@Autowired
	private MFWInfoDao mfwInfoDao;

	@Override
	public void insertMFWInfo(MFWTravelNotesInfo MmFWTravelNotesInfo) {
		// TODO Auto-generated method stub
		mfwInfoDao.insertMFWInfo(MmFWTravelNotesInfo);
	}

}
