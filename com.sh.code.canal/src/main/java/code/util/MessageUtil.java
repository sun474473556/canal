package code.util;

import code.dto.Field;
import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

@Component
public class MessageUtil {

	private Logger logger = LoggerFactory.getLogger(MessageUtil.class);

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public List<Field> messageCovertJson(Message message) {
		List<Field> rowList = new ArrayList<Field>();
		//循环每行binlog
		for (Entry entry: message.getEntries()) {
			//binlog时间
			String time = entryTime(entry);
			//不解析transaction，仅解析rowdata
			if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
				continue;
			}

			RowChange rowChange = null;
			try {
				rowChange = RowChange.parseFrom(entry.getStoreValue());
			}catch (Exception e) {
				throw new RuntimeException();
			}

			//sql类型（insert、update）
			EventType eventType = rowChange.getEventType();

			//binlog基础信息
			String header_str = "{\"binlog\":\"" + entry.getHeader().getLogfileName()+ ":" + entry.getHeader().getLogfileOffset() + "\"," +
				"\"dbName\":\"" + entry.getHeader().getSchemaName() + "\"," +
				"\"table\":\"" + entry.getHeader().getTableName() + "\",";


			//受影响 数据行
			for (RowData rowData : rowChange.getRowDatasList()) {
				String row_str = "\"eventType\":\"" + eventType +"\",";
				String before = "\"\"";
				String after = "\"\"";

				//获取字段值
				if (eventType == EventType.DELETE) {
					after = printColumn(rowData.getBeforeColumnsList());
				} else if (eventType == EventType.INSERT) {
					after = printColumn(rowData.getAfterColumnsList());
				} else if (eventType == EventType.UPDATE) {
					before = printColumn(rowData.getBeforeColumnsList());
					after = printColumn(rowData.getAfterColumnsList());
				} else {
					logger.error("未知格式类型"+eventType);
				}

				String row_data = header_str + row_str + "\"before\":" +before + ",\"after\":" + after + ",\"time\":\"" + time +"\"}";
				Field field = JSON.parseObject(row_data, Field.class);
				rowList.add(field);
			}
		}
		return rowList;
	}

	private String printColumn(List<CanalEntry.Column> columns) {
		Map<String, String> column_map = new HashMap<String, String>();
		for (Column column : columns) {
			String column_name = column.getName();
			String column_value = column.getValue();
			column_map.put(column_name, column_value);
		}
		return JSON.toJSONString(column_map);
	}
	//时间显示
	private String entryTime(Entry entry){
		long time = entry.getHeader().getExecuteTime();
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		String timeStr = format.format(time);
		return timeStr;
	}
}
