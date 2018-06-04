package com.sh.code.canal;

import com.sh.code.config.CanalConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
public class CanalExecute {

	private Logger logger = LoggerFactory.getLogger(CanalExecute.class);

	private ExecutorService executorService = Executors.newCachedThreadPool();

	@Resource
	CanalConfig canalConfig;

	public CanalExecute() throws Exception {
		String[] args = canalConfig.getDestination().split(";");
		for (String destionations : args) {
			CanalClient canalClient = new CanalClient(canalConfig.getAddress(), canalConfig.getPort(), canalConfig.getType(),
				destionations, canalConfig.getPassword(), canalConfig.getUsername());
			start(canalClient);
		}
	}

	public void start(CanalClient canalClient) {
		executorService.submit(canalClient);
	}
}
