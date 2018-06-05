package code.biz;

import code.dto.Field;

public interface BaseService {
	void update(Field field);

	void insert(Field field);

	void delete(Field field);
}
