package com.example.ceshi.servlet;

import com.example.ceshi.dao.UserDao;
import com.example.ceshi.dao.UserDaoImpl;
import com.example.ceshi.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 创建用户数据访问对象
        UserDao userDao = new UserDaoImpl();
        // 获取所有用户信息
        List<User> userList = userDao.getAllUsers();
        // 将用户信息设置到请求属性中
        request.setAttribute("userList", userList);
        try {
            // 转发到显示所有用户的页面
            request.getRequestDispatcher("/showall.jsp").forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
