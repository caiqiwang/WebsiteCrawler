package com.study.crawler.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * @author cqw
 * @Introduce mybatis信息配置 自动读取mapping配置文件
 * @Param
 * @Return
 * @Time 2018年3月31日
 */
@Configuration
@MapperScan(basePackages = "com.study.crawler.dao", sqlSessionTemplateRef = "spiderSqlSessionTemplate")
public class MybatisConfigTest {
	@Autowired
	private DataSource dataSource;

	@Bean(name = "spiderSqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		// 显式指定数据源
		bean.setDataSource(dataSource);
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		// 指定xml文件位置
		bean.setMapperLocations(resolver.getResources("classpath:/sqlMap/*.xml"));
		return bean.getObject();
	}

	@Bean(name = "spiderSqlSessionTemplate")
	public SqlSessionTemplate testSqlSessionTemplate(
			@Qualifier("spiderSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
