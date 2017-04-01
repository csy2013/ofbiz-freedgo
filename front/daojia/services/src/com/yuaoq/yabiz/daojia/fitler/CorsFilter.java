package com.yuaoq.yabiz.daojia.fitler;


import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CorsFilter implements Filter {
    
    
    private String tokenHeader = "X-Auth-Token";
    
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        chain.doFilter(req, res);
    }
    
    public void init(FilterConfig filterConfig) {
    }
    
    public void destroy() {
    }
    
}
