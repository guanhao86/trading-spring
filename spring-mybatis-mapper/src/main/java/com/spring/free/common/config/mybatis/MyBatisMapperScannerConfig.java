package com.spring.free.common.config.mybatis;

import com.spring.free.common.MyBatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

/**
 * MyBatis配置类
 * @author Memory
 * @date 2016/10/20
 * @version 1.0
 * @Inc 恒谱科技
 */
@Configuration
// TODO 注意，由于MapperScannerConfigurer执行的比较早，所以必须有下面的注解
@AutoConfigureAfter(MyBatisAutoConfiguration.class)
public class MyBatisMapperScannerConfig {

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		mapperScannerConfigurer.setBasePackage("com.spring.free.mapper");
		Properties properties = new Properties();
		properties.setProperty("mapper", "com.spring.free.common.mybatis.conf.TradingMapper");
		properties.setProperty("notEmpty", "false");
		properties.setProperty("IDENTITY", "MYSQL");
		mapperScannerConfigurer.setProperties(properties);
		return mapperScannerConfigurer;
	}
}
