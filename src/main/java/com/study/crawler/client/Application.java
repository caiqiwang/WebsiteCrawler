package com.study.crawler.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.study.crawler" }) // 用来自动扫描 该路径下的注解
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		/*SpringApplication.run(Application.class, args);
		JDCrawlerFactory factory = ApplicationContextSave.getBean(JDCrawlerFactory.class);
		factory.startCrawler();
		System.exit(0);*/
		/*SpringApplication.run(Application.class, args);
		TaoBaoCrawlerFactory factory = ApplicationContextSave.getBean(TaoBaoCrawlerFactory.class);
		factory.startCrawler();
		System.exit(0);*/
		/*SpringApplication.run(Application.class, args);
		MaFWCrawlerFactory factory = ApplicationContextSave.getBean(MaFWCrawlerFactory.class);
		factory.startCrawler();
		System.exit(0);*/
	}
}
