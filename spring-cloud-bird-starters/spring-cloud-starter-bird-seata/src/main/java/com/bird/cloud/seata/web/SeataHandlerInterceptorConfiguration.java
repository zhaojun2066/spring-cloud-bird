

package com.bird.cloud.seata.web;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@ConditionalOnWebApplication
public class SeataHandlerInterceptorConfiguration implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SeataHandlerInterceptor()).addPathPatterns("/**");
	}

}
