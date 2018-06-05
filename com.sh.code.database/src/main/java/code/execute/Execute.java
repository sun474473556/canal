package code.execute;

import code.canal.CanalExecute;
import code.handler.CanalHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
public class Execute implements InitializingBean {

	@Resource
	CanalExecute canalExecute;

	@Resource
	CanalHandler canalHandler;

	private Executor executor = Executors.newFixedThreadPool(6);

	@Override
	public void afterPropertiesSet() throws Exception {
		canalHandler.setLinkedBlockingDeque(canalExecute.getLinkedBlockingDeque());
		//6个线程执行一个对象，参考生产者消费者模型
		for (int i = 0; i < 6; i++) {
			executor.execute(canalHandler);
		}
		canalExecute.init();
	}
}
