package com.study.crawler.client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.study.crawler" }) // 用来自动扫描 该路径下的注解
public class Application {
	public static void main(String[] args) {
		/*SpringApplication.run(Application.class, args);
		LagouCrawlerFactory factory = ApplicationContextSave.getBean(LagouCrawlerFactory.class);
		factory.startCrawler();
		System.exit(0);*/
		String str = "卖        房    ";
		str = str.replaceAll(" ", "");
		System.out.println(str);
	}
}
