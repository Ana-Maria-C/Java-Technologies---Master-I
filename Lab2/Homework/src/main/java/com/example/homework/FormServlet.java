package com.example.homework;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@WebServlet(name="Test Captcha" ,value="/form")
public class FormServlet extends HttpServlet {

    private static final String SecretKey="6LekPWAqAAAAACM0N9ybsxaFKI7jhqeKhMHtyA4t";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.sendRedirect("form.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // capture reCaptcha response

        String reCaptchaResponse = request.getParameter("g-recaptcha-response");

        // verify reCaptcha response

        boolean isCaptchaValid = verifyCaptcha(reCaptchaResponse);

        if(isCaptchaValid){
            PrintWriter out = response.getWriter();
            out.write("Form submitted successfully");
        }
        else{
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Captcha invalid! Please try again!");
        }
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
