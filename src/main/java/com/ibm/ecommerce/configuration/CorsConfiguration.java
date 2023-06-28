package com.ibm.ecommerce.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 
 *         The purpose of this configuration is to enable
 *         Cross-Origin Resource Sharing for all endpoints and allow
 *         requests with specified HTTP methods, headers, and origins. It allows
 *         requests to be made from any origin and includes support for
 *         credentials.
 * 
 *
 */
@Configuration
public class CorsConfiguration {

	private static final String GET = "GET";
	private static final String POST = "POST";
	private static final String PUT = "PUT";
	private static final String DELETE = "DELETE";

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods(GET, POST, PUT, DELETE).allowedHeaders("*")
						.allowedOriginPatterns("*").allowCredentials(true);
			}
		};
	}
}