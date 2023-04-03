package com.MyApp.DbConfiguration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
@ConfigurationProperties("spring.datasource")
public class DbConfigure {
	private String driverClassName;
	private String url;
	private String username;
	private String password;
	
	Logger logger = Logger.getLogger(DbConfigure.class.getName());

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Profile("dev")
	@Bean
	public String devDatabaseConnection() {
		logger.log(Level.INFO, "DB connection for DEV mode - HSQL db");
		logger.log(Level.INFO, driverClassName);
		logger.log(Level.INFO, url);
		return "DB connection for DEV - H2";
	}

	@Profile("test")
	@Bean
	public String testDatabaseConnection() {
		logger.log(Level.INFO, "DB connection for TEST mode - H2 db");
		logger.log(Level.INFO, driverClassName);
		logger.log(Level.INFO, url);
		return "DB Connection to TEST";
	}

	@Profile("prod")
	@Bean
	public String prodDatabaseConnection() {
		logger.log(Level.INFO, "DB connection for PROD mode - Mysql Db");
		logger.log(Level.INFO, driverClassName);
		logger.log(Level.INFO, url);
		return "DB Connection to PROD";
	} 
}
