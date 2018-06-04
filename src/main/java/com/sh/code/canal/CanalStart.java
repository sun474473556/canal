package com.sh.code.canal;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

@Component
public class CanalStart implements InitializingBean {

	@Resource
	CanalExecute canalExecute;

	@Override
	public void afterPropertiesSet() throws Exception {
		canalExecute.init();
	}
}
