package com.bksoftwarevn.itstudent.filter;

import com.bksoftwarevn.itstudent.model.MyConnection;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "ApiFilter", urlPatterns = {"/api/*"})
public class ApiFilter implements Filter {
    private MyConnection myConnection = new MyConnection();

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");

        try {
            myConnection.connectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
