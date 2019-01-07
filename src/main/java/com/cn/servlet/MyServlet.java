package com.cn.servlet;

import com.alibaba.fastjson.JSON;
import com.cn.dao.Pager;
import com.cn.entity.Message;
import com.cn.service.MessageService;
import com.cn.service.MessageServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "MyServlet", urlPatterns = {"/MyServlet"})
public class MyServlet extends HttpServlet {
    MessageService messageService;
    Logger logger = Logger.getLogger(MyServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        if (request.getParameter("op").equals("fb")) {
            try {
                this.messageFb(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (request.getParameter("op").equals("show")) {
            try {
                Pager p = this.showMessages(request, response);
                String s = JSON.toJSONString(p);
                System.out.println(s);
                response.getWriter().print(s);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


    protected void messageFb(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ClassNotFoundException {
        Message message = new Message();
        message.setAuthor(request.getParameter("name"));
        message.setMessage(request.getParameter("message"));
        messageService = new MessageServiceImpl();
        messageService.count("id");
        Pager pager = this.getUsersPage(messageService, request);
        try {
            pager.setDataList(messageService.getMessageList(1, 5));
            request.setAttribute("list", pager);
            messageService.add(message);
            request.setAttribute("pg", this.getUsersPage(messageService, request));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    private Pager getUsersPage(MessageService userBiz, HttpServletRequest request) throws SQLException, ClassNotFoundException {
        String pageIndex = request.getParameter("pageIndex");
        System.out.println(pageIndex);
        //获得当前页
        if (pageIndex == null || (pageIndex = pageIndex.trim()).length() == 0) {
            pageIndex = "1";
        }
        int count = userBiz.count("id");
        int currPageNo = Integer.parseInt(pageIndex);
        if (currPageNo > count) {
            currPageNo = count;
        }
        Pager pageObj = new Pager();
        pageObj.setCurrentPage(currPageNo);//设置当前页码
        pageObj.setRowPerPage(5);//设置每页显示条数
        pageObj.setRowCount(count);
        logger.debug("currPageNo:" + currPageNo);
        return pageObj;
    }


    private Pager showMessages(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ClassNotFoundException {
        messageService = new MessageServiceImpl();
        Pager pager = this.getUsersPage(messageService, request);
        List<Message> messageslist = messageService.getMessageList(pager.getCurrentPage(), pager.getRowPerPage());
        pager.setDataList(messageslist);
        return pager;
    }
}
