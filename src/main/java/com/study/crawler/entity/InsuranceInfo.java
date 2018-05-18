package com.study.crawler.entity;

import java.text.MessageFormat;

//保险信息单
public class InsuranceInfo {
	private String data;// 出生日期
	private String sex;// 性别
	private String baseMoney;// 基本保险金额
	private String way;// 交费方式
	private String timeLimit;// 交费期间
	private String eachMoney;// 价格

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBaseMoney() {
		return baseMoney;
	}

	public void setBaseMoney(String baseMoney) {
		this.baseMoney = baseMoney;
	}

	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}

	public String getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getEachMoney() {
		return eachMoney;
	}

	public void setEachMoney(String eachMoney) {
		this.eachMoney = eachMoney;
	}

	@Override
	public String toString() {
		return MessageFormat.format("{0};{1};{2};{3};{4};{5};", this.data, this.sex, this.baseMoney, this.way,
				this.timeLimit, this.eachMoney);
	}

}
