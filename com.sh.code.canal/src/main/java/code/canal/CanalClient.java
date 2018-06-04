package code.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.Message;
import code.config.CanalConfig;
import code.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.InetSocketAddress;

@Component
public class CanalClient implements Runnable{

	public Logger logger = LoggerFactory.getLogger(CanalClient.class);

	private boolean running = true;

	private final String Cluster = "Cluster";

	private final String Single = "Single";

	private String destination;

	private String dbName;

	private String tableNamel;

	private CanalConnector connector;

	@Resource
	MessageUtil messageUtil;

	public void init(String address, int port, String type, String destinations, String database, String password, String username) throws Exception {
		String[] args= destinations.split(",");
		this.dbName = database;
		this.destination = args[0];
		this.tableNamel = args[0];
		if (type.equals(Cluster)) {
			connector = CanalConnectors.newClusterConnector(address, destination,
				username, password);
		} else if (type.equals(Single)) {
			connector = CanalConnectors.newSingleConnector(new InetSocketAddress(address, port),
				destination, username,
				password);
		} else {
			logger.error("canal配置文件错误");
		}
		if (connector == null) {
			logger.error("canall连接异常");
		}
	}

	public void run() {
		int batchSize = 1024;
		connector.connect();
		connector.rollback();//回到上次记录位置
		//filter一般应该为DBName.tableName
		connector.subscribe(dbName + "." + tableNamel);
		try {
			while (running) {
				Message message = connector.getWithoutAck(batchSize);
				long bachId = message.getId();
				if (bachId == -1 || bachId == 0) {

				} else {
					messageUtil.messageCovertJson(message);
				}
				connector.ack(bachId);
			}
		} catch (Exception e) {
			logger.error("process error");
		} finally {
			connector.disconnect();
		}
	}
}
