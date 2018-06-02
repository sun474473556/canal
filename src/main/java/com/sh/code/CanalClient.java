package com.sh.code;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CanalClient {

	public Logger logger = LoggerFactory.getLogger(CanalClient.class);
	private static boolean running = true;

	public void start(CanalConnector canalConnector, String destination) throws Exception {
		if (canalConnector == null) {
			throw new Exception("connector is null");
		}
		int batchSize = 1024;
		canalConnector.connect();
		canalConnector.rollback();
		canalConnector.subscribe();
		try {
			while (running) {
				Message message = canalConnector.getWithoutAck(batchSize);
				long bachId = message.getId();
				if (bachId == -1 || bachId == 0) {
					//推送rabbitmq
				} else {
					//展示binlog
				}
				canalConnector.ack(bachId);
			}
		} catch (Exception e) {
			logger.error("process error");
		} finally {
			canalConnector.disconnect();
		}

	}
}
