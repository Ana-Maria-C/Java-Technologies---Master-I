package com.example.homework;

import java.io.*;
import java.util.Date;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebFilter(urlPatterns = {"/*"})
public class LogFilter implements Filter {
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;

        // Find the IP of the request
        String ipAddress = request.getRemoteAddr();

        // Write address and time in the log
        System.out.println(
                "IP: " + ipAddress + ", Time: " + new Date().toString());

        chain.doFilter(req, res);
    }
}