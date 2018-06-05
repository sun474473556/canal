package code.handler;

import code.biz.BaseService;
import code.dto.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

@Component
abstract class AbstractHandler implements Runnable {

	Logger logger = LoggerFactory.getLogger(AbstractHandler.class);

	@Resource(name = "BaseService")
	BaseService service;

	protected LinkedBlockingDeque<List<Field>> linkedBlockingDeque;

	public LinkedBlockingDeque<List<Field>> getLinkedBlockingDeque() {
		return linkedBlockingDeque;
	}

	public void setLinkedBlockingDeque(LinkedBlockingDeque<List<Field>> linkedBlockingDeque) {
		this.linkedBlockingDeque = linkedBlockingDeque;
	}

	abstract void handler();

	public void handlerType(Field field){

		String eventType = field.getEventType();

		if (eventType.equals("UPDATE")) {
			service.update();
		} else if (eventType.equals("DELETE")) {
			service.insert();
		} else if (eventType.equals("INSERT")) {
			service.insert();
		} else {
			logger.info("其他类型：" + eventType);
		}
	}

	@Override
	public void run() {
		handler();
	}
}
