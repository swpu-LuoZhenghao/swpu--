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

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取用户输入的注册信息
        String userName = request.getParameter("userName");
        String userAddress = request.getParameter("userAddress");
        String userPhone = request.getParameter("userPhone");

        // 创建用户对象
        User user = new User();
        user.setName(userName);
        user.setAddress(userAddress);
        user.setPhone(userPhone);

        // 创建用户数据访问对象
        UserDao userDao = new UserDaoImpl();

        if (userDao.register(user)) {
            // 注册成功，设置欢迎消息，并转发到注册成功页面
            request.setAttribute("username", userName);
            request.getRequestDispatcher("/success.jsp").forward(request, response);
        } else {
            // 注册失败，重定向到首页
            response.sendRedirect("index.jsp");
        }
    }
}
