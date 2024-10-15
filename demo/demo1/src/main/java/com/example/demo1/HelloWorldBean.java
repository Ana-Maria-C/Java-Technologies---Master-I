package com.example.demo1;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.view.ViewScoped;

@ManagedBean(name="helloWorldBean")
@SessionScoped
public class HelloWorldBean {
    private String message;

    public HelloWorldBean() {
        this.message = "Hello, World!";
    }

    public String getMessage() {
        return message;
    }
}
