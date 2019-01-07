package com.cn.dao;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseDao {
    protected Connection connection;
    protected PreparedStatement pstm;
    protected ResultSet rs;
    static Logger logger=Logger.getLogger(BaseDao.class);
    public void createStatement(String sql, Object... objects) throws ClassNotFoundException, SQLException {
        connection = BDManager.openConnection();
        pstm = connection.prepareStatement(sql);
        if (objects != null) {
            for (int i = 0; i < objects.length; i++) {
                pstm.setObject((i + 1), objects[i]);
            }
        }
    }

    private  Connection getConnection(){
        try {
            connection=BDManager.openConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    public  int executeUpdate(String sql,Object... objects) throws SQLException, ClassNotFoundException {
        this.createStatement(sql, objects);
        int row = pstm.executeUpdate();
        BDManager.colse(connection,rs, pstm);
        return row;
    }
    public <T> List<T> executeQuery(String sql, RowMapper<T> rm, Object... objects)
            throws ClassNotFoundException, SQLException {
        this.createStatement(sql, objects);
        rs = pstm.executeQuery();
        List<T> list = new ArrayList<T>();
        ResultSetMetaData x = rs.getMetaData();
        while (rs.next()) {
            Map<String, Object> map = new HashMap<String, Object>();
            for (int i = 0; i < x.getColumnCount(); i++) {
                String y = x.getColumnName(i + 1);
                map.put(y, rs.getObject(y));
            }
            T t = rm.mapper(map);
            list.add(t);
        }
        BDManager.colse(connection,rs, pstm);
        return list;
    }
}
