package com.study.crawler.category;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Test {
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>(Arrays.asList("a1", "a2", "a3", "b1", "b2", "b3"));
		for (Iterator<String> iter = list.iterator(); iter.hasNext();) {
			String str = iter.next();
			if (str.contains("b")) {
				iter.remove();
			}
		}
		/*for (String str : list) {
			if (str.contains("b")) {
				list.remove(str);
			}
		}*/
		for (String str : list) {
			System.out.println(str);
		}
	}

	// 将对象序列化存到本地文件
	public static void SerializableTestDemo() throws FileNotFoundException, IOException {
		TestDemo TestDemo = new TestDemo("zs", "3", "d");
		String path = "E//excel/serializabeTestDemo.txt";
		ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(new File(path)));
		oo.writeObject(TestDemo);
		System.out.println("Customer对象序列化成功！");
		oo.close();
	}

	// 反序列化文件内容为对象
	private static TestDemo DeserializeCustomer() throws Exception, IOException {
		ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream(new File("E//excel/serializabeTestDemo.txt")));
		TestDemo customer = (TestDemo) ois.readObject();
		System.out.println("Customer对象反序列化成功！");
		return customer;
	}
}
