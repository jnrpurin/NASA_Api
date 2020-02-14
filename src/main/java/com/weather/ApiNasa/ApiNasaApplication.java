package com.weather.ApiNasa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class ApiNasaApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ApiNasaApplication.class, args);
	}
}
