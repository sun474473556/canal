package code.biz.impl;

import code.biz.BaseService;
import org.springframework.stereotype.Service;

@Service(value = "MysqlService")
public class MysqlService implements BaseService {
	@Override
	public int update() {
		return 0;
	}

	@Override
	public int insert() {
		return 0;
	}

	@Override
	public int delete() {
		return 0;
	}
}
