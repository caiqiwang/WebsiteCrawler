package com.study.crawler.entity;

import java.text.MessageFormat;

public class TaoBaoCommodityInfo implements Comparable<TaoBaoCommodityInfo> {
	private String name;
	private String url;
	private String sales;
	private String allcount;
	private String brand;
	private String storeName;
	private String price;

	@Override
	public boolean equals(Object arg0) {
		TaoBaoCommodityInfo obj = (TaoBaoCommodityInfo) arg0;
		if (obj.getUrl().equals(this.getUrl())) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.url.hashCode();
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSales() {
		return sales;
	}

	public void setSales(String sales) {
		this.sales = sales;
	}

	public String getAllcount() {
		return allcount;
	}

	public void setAllcount(String allcount) {
		this.allcount = allcount;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	@Override
	public int compareTo(TaoBaoCommodityInfo o) {
		// TODO Auto-generated method stub
		if (this.getBrand().compareTo(o.getBrand()) > 0) {
			return 1;
		} else if (this.getBrand().compareTo(o.getBrand()) == 0) {
			if (this.getPrice().compareTo(o.getPrice()) > 0) {
				return 1;
			} else if (this.getPrice().compareTo(o.getPrice()) < 0) {
				return -1;
			} else {
				return 0;
			}
		}
		return -1;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return MessageFormat.format("{0};{1};{2};{3};{4};{5};{6};", this.brand, this.name, this.price, this.sales,
				this.allcount, this.storeName, this.url);
	}
}
