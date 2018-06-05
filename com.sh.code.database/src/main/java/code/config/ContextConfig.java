package code.config;

import code.util.ContextUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class ContextConfig {

	@Resource
	ContextUtils contextUtils;

	@Bean
	public String setApplicationContext(ApplicationContext context) {
		contextUtils.setApplicationContext(context);
		return "init success";
	}

}
