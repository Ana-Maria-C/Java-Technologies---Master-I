package com.example.laborator7.interceptor;

import com.example.laborator7.bean.UserBean;
import com.example.laborator7.model.User;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.inject.Inject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

@Logged
@Interceptor
public class LoggedInterceptor implements Serializable {
    private static final String LOG_FILE_PATH = "C:\\Users\\Ana-Maria\\Java-Technologies---Master-I\\Laborator7\\src\\main\\java\\com\\example\\laborator7\\users.txt";

    @AroundInvoke
    public Object logMethodEntry(InvocationContext invocationContext) throws Exception {
        System.out.println("Interceptor invoked for method: " + invocationContext.getMethod().getName());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.write("New user was logged in at ");
            writer.write("Login Time: " + new Date());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return invocationContext.proceed();
    }

}