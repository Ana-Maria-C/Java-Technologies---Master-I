package com.example.homework;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/contextParams")
public class ServletContextParams extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        ServletContext c = this.getServletContext();
        PrintWriter writer = response.getWriter();
        writer.append("Prelude: ").append(c.getInitParameter("prelude")).append(", Coda: ")
                .append(c.getInitParameter("coda"));
    }
}
