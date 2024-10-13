package com.example.homework;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized (ServletContextEvent ce)
    {
        // get serverContext
        var context = ce.getServletContext();

        // read context parameters
        String prelude = context.getInitParameter("prelude");
        String coda = context.getInitParameter("coda");

        // store parameters in app scope

        if (prelude != null) {
            context.setAttribute("prelude", prelude);
        }
        if (coda != null) {
            context.setAttribute("coda", coda);
        }

        System.out.println("Prelude: "+ prelude);
        System.out.println("Coda "+ coda);
    }
}
