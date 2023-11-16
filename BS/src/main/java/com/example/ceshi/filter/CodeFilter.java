package com.example.ceshi.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * 编码过滤器
 */
public class CodeFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("初始化过滤器");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("结束");
    }
}
