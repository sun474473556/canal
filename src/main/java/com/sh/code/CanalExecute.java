package com.sh.code;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.sh.code.config.CanalConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class CanalExecute {

	private Logger logger = LoggerFactory.getLogger(CanalExecute.class);

	private ExecutorService executorService = Executors.newCachedThreadPool();

	private final String Cluster = "Cluster";

	private final String Single = "Single";

	@Resource
	CanalConfig canalConfig;

	public boolean init() throws Exception {
		String type = canalConfig.getType();
		if (type.equals(Cluster)) {

		} else if (type.equals(Single)) {

		} else {
			logger.error("canal配置文件错误");
		}
		return true;
	}

	public void start() {
		//executorService.submit()
	}
}
