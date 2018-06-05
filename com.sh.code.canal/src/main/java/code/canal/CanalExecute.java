package code.canal;

import code.config.CanalConfig;
import code.canal.CanalClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Component
public class CanalExecute implements InitializingBean {

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
				destionations,canalConfig.getDbName(), canalConfig.getPassword(), canalConfig.getUsername());
			start();
		}
	}

	public void start() {
		executorService.submit(canalClient);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		init();
	}
}
