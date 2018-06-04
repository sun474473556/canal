package com.sh.code.canal;

import com.sh.code.config.CanalConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Component
public class CanalExecute {

	private Logger logger = LoggerFactory.getLogger(CanalExecute.class);

	private ExecutorService executorService = Executors.newCachedThreadPool();

	@Resource
	CanalConfig canalConfig;

	@Resource
	CanalClient canalClient;

	public void init() throws Exception {
		String[] args = canalConfig.getDestination().split(";");
		for (String destionations : args) {
			canalClient.init(canalConfig.getAddress(), canalConfig.getPort(), canalConfig.getType(),
				destionations, canalConfig.getPassword(), canalConfig.getUsername());
			start();
		}
	}

	public void start() {
		executorService.submit(canalClient);
	}
}
