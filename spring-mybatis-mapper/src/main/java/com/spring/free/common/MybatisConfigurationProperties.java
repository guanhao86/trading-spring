package com.spring.free.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mybatis",locations = "classpath:config/mybatis.properties")
//@PropertySource(value = "classpath:config/mybatis.properties")
public class MybatisConfigurationProperties {
	private String typeAliasesPackage;
	private String mapperLocations;

	public String getTypeAliasesPackage() {
		return typeAliasesPackage;
	}

	public void setTypeAliasesPackage(String typeAliasesPackage) {
		this.typeAliasesPackage = typeAliasesPackage;
	}

	public String getMapperLocations() {
		return mapperLocations;
	}

	public void setMapperLocations(String mapperLocations) {
		this.mapperLocations = mapperLocations;
	}

}
