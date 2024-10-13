package com.example.homework;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "fileUploadServlet", value = "/uploadServlet")
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

    private static final String SecretKey = "6LekPWAqAAAAACM0N9ybsxaFKI7jhqeKhMHtyA4t";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // capture reCaptcha response
        String reCaptchaResponse = request.getParameter("g-recaptcha-response");

        // verify reCaptcha response
        boolean isCaptchaValid = verifyCaptcha(reCaptchaResponse);

        if (!isCaptchaValid) {
            // if captcha is invalid, return an error
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Captcha invalid! Please try again!");
            return;
        }

        // if captcha is valid continue with file upload process
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

    private boolean verifyCaptcha(String reCaptchaResponse) throws IOException {

        String url = "https://www.google.com/recaptcha/api/siteverify";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        // add secretkey and response to request body

        String postParams = "secret= " + SecretKey + "&response= " + reCaptchaResponse;

        // sent request

        con.setDoOutput(true);
        try(OutputStream os = con.getOutputStream()){
            os.write(postParams.getBytes());
            os.flush();
        }

        // response from api
        InputStream is = con.getInputStream();
        try(Scanner scanner = new Scanner(is)){
            String jsonResponse = scanner.useDelimiter("\\A").next();
            return jsonResponse.contains("\"success\": true");
        }
    }
}