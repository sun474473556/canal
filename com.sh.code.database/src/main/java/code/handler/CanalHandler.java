package code.handler;

import code.dto.Field;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CanalHandler extends AbstractHandler {

	@Override
	void handler() {
		try {
			List<Field> listField= getLinkedBlockingDeque().take();
			for (Field field : listField) {
				handlerType(field);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
