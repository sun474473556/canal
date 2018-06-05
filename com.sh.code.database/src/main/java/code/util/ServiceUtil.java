package code.util;

import code.biz.BaseService;
import org.springframework.stereotype.Component;
import code.constants.ServiceConst;

@Component
public class ServiceUtil {

	public  BaseService getMysqlService() {
		return ContextUtils.getBean(ServiceConst.SERVICE_MYSQLSERVICE, BaseService.class);
	}

	public  BaseService getRedisService() {
		return ContextUtils.getBean(ServiceConst.SERVICE_REDISSERVICE, BaseService.class);
	}

	public  BaseService getRabbitMqService() {
		return ContextUtils.getBean(ServiceConst.SERVICE_RABBITMQSERVICE, BaseService.class);
	}
}
