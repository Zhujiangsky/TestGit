package com.cn.entitydao;

import com.cn.entity.Message;

import java.sql.SQLException;
import java.util.List;

public interface MessageDao {
    void add(Message message) throws SQLException, ClassNotFoundException;

    int update(Message message);

    public List<Message> getMessageList(Integer currentPageNo, Integer pageSize) throws SQLException, ClassNotFoundException;

    public Integer count(String filename) throws SQLException, ClassNotFoundException;
}
