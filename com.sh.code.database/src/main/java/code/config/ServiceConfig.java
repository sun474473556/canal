package code.config;

import code.biz.BaseService;
import code.constants.ServiceConst;
import code.util.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.Resource;

@Configuration
public class ServiceConfig {
	private Logger logger = LoggerFactory.getLogger(ServiceConfig.class);

	@Value("${service.type}")
	private String type;

	@Resource
	ServiceUtil serviceUtil;

	@Bean(value = "BaseService")
	@DependsOn(value = "setApplicationContext")
	public BaseService baseService() {
		if (type.equals(ServiceConst.TYPE_MYSQL)) {
			return serviceUtil.getMysqlService();
		} else if (type.equals(ServiceConst.TYPE_REDIS)) {
			return serviceUtil.getRedisService();
		} else if (type.equals(ServiceConst.TYPE_RABBITMQ)) {
			return serviceUtil.getRabbitMqService();
		} else {
			logger.error("type参数错误");
		}
		return null;
	}

}
