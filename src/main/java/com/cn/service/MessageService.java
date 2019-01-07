package com.cn.service;

import com.cn.entity.Message;

import java.sql.SQLException;
import java.util.List;

public interface MessageService {
    public void add(Message message) throws SQLException, ClassNotFoundException;
    public List<Message> getMessageList(Integer currentPageNo, Integer pageSize) throws SQLException, ClassNotFoundException;
    public Integer count(String filename) throws SQLException, ClassNotFoundException;
}
