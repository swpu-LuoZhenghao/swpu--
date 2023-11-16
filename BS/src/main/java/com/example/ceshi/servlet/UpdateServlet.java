package com.example.ceshi.servlet;

import com.example.ceshi.dao.UserDao;
import com.example.ceshi.dao.UserDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/UpdateServlet")
public class UpdateServlet extends HttpServlet {
    private UserDao userDao = new UserDaoImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 获取用户输入的更新信息
        String userName = request.getParameter("userName");
        String userAddress = request.getParameter("userAddress");
        String userPhone = request.getParameter("userPhone");

        if (userDao.update(userName, userAddress, userPhone)) {
            // 更新成功，设置成功消息，并转发到显示所有用户的页面
            request.setAttribute("message", "更新成功");
            request.getRequestDispatcher("/SearchServlet").forward(request, response);
        } else {
            // 更新失败，重定向到首页
            response.sendRedirect("index.jsp");
        }
    }
}
