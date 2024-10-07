package ro.uaic.info.hello.helloworld;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "HelloWorldServlet", urlPatterns = {"/characterlist"})
public class HelloWorldServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String inputString = "Hello World";

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html><head><title>Character List</title></head><body>");
            out.println("<h1>Character List:</h1>");
            out.println("<ol>");

            for (char c : inputString.toCharArray()) {
                out.println("<li>" + c + "</li>");
            }

            out.println("</ol>"); // End ordered list
            out.println("</body></html>");

        }
    }

    public void destroy() {
    }
}