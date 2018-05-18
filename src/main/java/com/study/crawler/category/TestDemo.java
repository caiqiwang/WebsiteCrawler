package com.study.crawler.category;

import java.io.Serializable;

public class TestDemo implements Comparable<TestDemo>, Serializable {
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String count;
	private String view;

	public TestDemo(String name, String count, String view) {
		this.name = name;
		this.count = count;
		this.view = view;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	@Override
	public int compareTo(TestDemo o) {
		// TODO Auto-generated method stub
		/*if (this.name.compareTo(o.getName()) > 0) {
		
		}*/
		return 0;
	}

	public static void main(String[] args) {
		TestDemo TestDemo = new TestDemo("zs", "3", "d");
		// byte[] bytes=SerializableU
	}
}
