package com.sha.fin.app.transaction.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sha.fin.app.transaction.external.error.CustomErrorDecoder;

import feign.codec.ErrorDecoder;

@Configuration
public class FeignConfig {

	@Bean
	ErrorDecoder errorDecoder() {
		return new CustomErrorDecoder();
	}

}
