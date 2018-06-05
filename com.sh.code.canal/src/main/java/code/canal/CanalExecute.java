package code.canal;

import code.config.CanalConfig;
import code.dto.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;


@Component
public class CanalExecute {

	private Logger logger = LoggerFactory.getLogger(CanalExecute.class);

	private ExecutorService executorService = Executors.newCachedThreadPool();

	@Resource
	CanalConfig canalConfig;

	@Resource
	CanalClient canalClient;

	private LinkedBlockingDeque<List<Field>> linkedBlockingDeque = new LinkedBlockingDeque<List<Field>>();

	public void init() throws Exception {
		String[] args = canalConfig.getDestination().split(";");
		for (String destionations : args) {
			canalClient.init(canalConfig.getAddress(), canalConfig.getPort(), canalConfig.getType(),
				destionations,canalConfig.getDbName(), canalConfig.getPassword(), canalConfig.getUsername(), linkedBlockingDeque);
			start();
		}
	}

	public void start() {
		executorService.submit(canalClient);
	}

	public LinkedBlockingDeque<List<Field>> getLinkedBlockingDeque() {
		return linkedBlockingDeque;
	}

	public void setLinkedBlockingDeque(LinkedBlockingDeque<List<Field>> linkedBlockingDeque) {
		this.linkedBlockingDeque = linkedBlockingDeque;
	}
}
