package com.cn.service;

import com.cn.entity.Message;
import com.cn.entitydao.MessageDaoImpl;

import java.sql.SQLException;
import java.util.List;

public class MessageServiceImpl implements MessageService {
    @Override
    public void add(Message message) throws SQLException, ClassNotFoundException {
        MessageDaoImpl messageDao=new MessageDaoImpl();
        messageDao.add(message);
    }

    @Override
    public List<Message> getMessageList(Integer currentPageNo, Integer pageSize) throws SQLException, ClassNotFoundException {
        MessageDaoImpl messageDao=new MessageDaoImpl();
            return messageDao.getMessageList(currentPageNo,pageSize);

    }

    @Override
    public Integer count(String filename) throws SQLException, ClassNotFoundException {
        MessageDaoImpl messageDao=new MessageDaoImpl();
        return messageDao.count("id");
    }
}
