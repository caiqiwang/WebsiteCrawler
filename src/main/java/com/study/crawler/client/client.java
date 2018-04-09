package com.study.crawler.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.study.crawler.factory.CrawlerFactory;

@SpringBootApplication
@ComponentScan(basePackages = { "com.study.crawler" }) // 用来自动扫描 该路径下的注解
public class client {
	public static void main(String[] args) {
		SpringApplication.run(client.class, args);
		CrawlerFactory factory = ApplicationContextSave.getBean(CrawlerFactory.class);
		factory.startCrawler();
		System.exit(0);
	}
}
