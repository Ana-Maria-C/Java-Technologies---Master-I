package com.example.homework;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@WebFilter("/*")
public class DecorationFilter implements Filter {
    private String prelude;
    private String coda;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException{
        ServletContext context = filterConfig.getServletContext();

        // extract prelude and coda from app scope

        prelude = (String) context.getAttribute("prelude");
        coda = (String) context.getAttribute("coda");

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{

        // create wrapper for response
        var responseWrapper = new DecorateResponseWrapper((HttpServletResponse) response);

        chain.doFilter(request, responseWrapper);

        // decorate response

        PrintWriter out = response.getWriter();
        out.write(prelude + responseWrapper.toString() + coda);
    }

    private static class DecorateResponseWrapper extends HttpServletResponseWrapper{

        private final StringWriter output;

        public DecorateResponseWrapper(HttpServletResponse response) {
            super(response);
            output = new StringWriter();
        }

        @Override
        public PrintWriter getWriter() {
            return new PrintWriter(output);
        }

        public String toString() {
            return output.toString();
        }
    }

}
