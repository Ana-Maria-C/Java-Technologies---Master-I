package com.example.compulsory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "fileUploadServlet", value = "/uploadServlet")
@MultipartConfig
public class FileUploadServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // extract file from request

        Part filePart = request.getPart("file");
        InputStream fileContent = filePart.getInputStream();

        // read file

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(fileContent))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        // save lines to current session

        HttpSession session = request.getSession();
        session.setAttribute("fileLines", lines);

        // forward to result.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("result.jsp");
        dispatcher.forward(request, response);
    }
}