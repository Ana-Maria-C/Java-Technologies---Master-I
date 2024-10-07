package com.example.homework;

import java.io.*;
import java.util.Random;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "GenerateGraph", value = "/generateGraph")
public class HomeworkServlet extends HttpServlet {
   @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


       logRequestInfo(request);

       // get the parameters from input form

       int numVertices =Integer.parseInt(request.getParameter("numVertices"));
       int numEdges = Integer.parseInt(request.getParameter("numEdges"));

       // create matrix

       int[][] adjacencyMatrix = new int[numVertices][numVertices];
       generateRandomGraph(adjacencyMatrix, numVertices, numEdges);

       response.setContentType("text/html;charset=UTF-8");
       try(PrintWriter out = response.getWriter()){

           out.println("<html><head><title>Adjacency Matrix</title></head><body>");
           out.println("<h1>Adjacency Matrix</h1>");
           out.println("<table border='1'>");
           out.println("<tr><th></th>");
           for (int i = 0; i < numVertices; i++)
           {
               out.println("<th>" + i + "</th>");
           }
           out.println("</tr>");

           for (int i = 0; i < numVertices; i++) {
               out.println("<tr><th>" + i + "</th>");
               for (int j = 0; j < numVertices; j++) {
                   out.println("<td>" + adjacencyMatrix[i][j] + "</td>");
               }
               out.println("</tr>");
           }
           out.println("</table>");
           out.println("</body></html>");
       }

    }

    public void generateRandomGraph(int[][] adjacencyMatrix, int numVertices, int numEdges)
    {
        Random random = new Random();
        while(numEdges>0)
        {
            int v1 = random.nextInt(numVertices);
            int v2 = random.nextInt(numVertices);

            if(v1!=v2 && adjacencyMatrix[v1][v2] == 0)
            {
                adjacencyMatrix[v1][v2] = 1;
                adjacencyMatrix[v2][v1] = 1;

                numEdges--;
            }
        }
    }

    private void logRequestInfo(HttpServletRequest request){

       String httpMethod = request.getMethod();
       String IPAddress = request.getLocalAddr();
       String userAgent = request.getHeader("User-Agent");
       String clientLanguage = request.getHeader("Accept-Language");
       StringBuilder parameters = new StringBuilder("Parameters: ");

       // get parameters

        request.getParameterMap().forEach((key, value)->
        {
            parameters.append(key).append("=").append(String.join(", ", value)).append(";");
        });

        System.out.println("HTTP Method: " + httpMethod);
        System.out.println("Client IP: " + IPAddress);
        System.out.println("User-Agent: " + userAgent);
        System.out.println("Accept-Language: " + clientLanguage);
        System.out.println(parameters);
    }

    public void destroy() {
    }
}