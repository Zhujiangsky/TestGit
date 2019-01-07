package com.cn.entitydao;

import com.cn.dao.BDManager;
import com.cn.dao.BaseDao;
import com.cn.dao.Pager;
import com.cn.dao.RowMapper;
import com.cn.entity.Message;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MessageDaoImpl  extends BaseDao implements MessageDao {
    RowMapper<Message> rm=new RowMapper<Message>() {
        @Override
        public Message mapper(Map<String, Object> map) {
            Message ms=new Message();
            Iterator<String> iter = map.keySet().iterator();
            while (iter.hasNext()) {
                String x = iter.next();
                Object ob = map.get(x);
                if (x.equals("id")) {
                    ms.setId(Integer.parseInt(String.valueOf(ob)));
                } else if (x.equals("message")) {
                    ms.setMessage(String.valueOf(ob));
                } else if (x.equals("author")) {
                    ms.setAuthor(String.valueOf(ob));
                } else if (x.equals("postTime")) {
                    ms.setDate(java.sql.Date.valueOf(String.valueOf(ob).substring(0, 10)));
                }
            }
            return ms;
        }
    };


    @Override
    public void add(Message message) throws SQLException, ClassNotFoundException {
        String sql="insert into tbl_message(message,author,postTime) values(?,?,now())";
        this.executeUpdate(sql,message.getMessage(),message.getAuthor());
    }

    @Override
    public int update(Message message) {
        return 0;
    }

    @Override
    public List<Message> getMessageList(Integer currentPageNo, Integer pageSize) throws SQLException, ClassNotFoundException {
        StringBuffer sql=new StringBuffer("select message,author,postTime from tbl_message where 1=1");
        int total=this.count("id");
        Pager pager=new Pager(total,pageSize,currentPageNo);
        sql.append(" limit "+(pager.getCurrentPage()-1)* pager.getRowPerPage() +","+pager.getRowPerPage());
        return this.executeQuery(sql.toString(),rm);
    }



    public Integer count(String filename) throws SQLException, ClassNotFoundException {
        List<Object> paramsList=new ArrayList<Object>();
        StringBuffer sql=new StringBuffer(" select count(1) as "+filename+" from tbl_message where 1=1 ");
        Integer count=0;
        this.createStatement(sql.toString());
        ResultSet rs= this.pstm.executeQuery();
        try {
            if (rs.next()) {
                count=rs.getInt(filename);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BDManager.colse(this.connection,this.rs,this.pstm);
        return count;
    }

}
