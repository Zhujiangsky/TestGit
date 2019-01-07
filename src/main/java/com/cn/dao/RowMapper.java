package com.cn.dao;
import java.util.Map;

public interface  RowMapper<T> {
	/**
	 * @author  admin
	 * @param map ����һ��MAP ���϶��󣨱�ʾһ����¼��
	 * @param <T>
	 * @return  ����һ�������ʵ�����
	 */
	public T mapper(Map<String, Object> map);
}
