package com.study.crawler.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.study.crawler.factory.JDCrawlerFactory;

@SpringBootApplication
@ComponentScan(basePackages = { "com.study.crawler" }) // 用来自动扫描 该路径下的注解
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		JDCrawlerFactory factory = ApplicationContextSave.getBean(JDCrawlerFactory.class);
		factory.startCrawler();
		// System.exit(0);

		/*String str = "vv20({djdjskdjsdkj{{]}dddds})";
		// vv20([^)]+)
		String regex2 = "vv20({([^)]+)";
		String regex = "(^vv20[\\w]+ )";
		Pattern pattern = Pattern.compile(regex2);
		Matcher match = pattern.matcher(str);
		String s = "";
		if (match.find()) {
			s = match.group(1);
		}
		System.out.println(s);*/

	}
}
