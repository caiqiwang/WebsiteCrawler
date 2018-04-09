package com.study.crawler.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author caiqibin
 * @date 2017年7月11日
 * @introduce:JdbcTemplate配置类，定义多数据源时读取application.properties中的配置信息
 */
@Configuration
public class DataSourceConfig {

	// 配置主数据源
	@Bean(name = "primaryDataSource")
	@Qualifier("primaryDataSource")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource primaryDataSource() {
		return DataSourceBuilder.create().build();
	}

	// 配置第二数据源
	/*@Bean(name = "secondDataSource")
	@Qualifier("secondDataSource")
	@Primary
	@ConfigurationProperties(prefix = "spring.second.datasource")
	public DataSource secondDataSource() {
		return DataSourceBuilder.create().build();
	}*/

	// 获取主数据源对应的jdbcTemplate 用jdbcTemplate操作数据库才需要这个配置
	/*	@Bean(name = "primaryJdbcTemplate")
		public JdbcTemplate primaryJdbcTemplate(@Qualifier("primaryDataSource") DataSource dataSource) {
			return new JdbcTemplate(dataSource);
		}
	
		// 获取第二数据源对应的jdbcTemplate
		@Bean(name = "secondJdbcTemplate")
		public JdbcTemplate secondJdbcTemplate(@Qualifier("secondDataSource") DataSource dataSource) {
			return new JdbcTemplate(dataSource);
		}*/
}
