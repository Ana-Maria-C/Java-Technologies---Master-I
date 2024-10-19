package com.example.homework.bean;

import jakarta.annotation.PostConstruct;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.context.FacesContext;

import java.io.Serializable;
import java.util.Locale;
@ManagedBean
@SessionScoped
public class LocaleBean implements Serializable {
    private String currentLocale = "en";
    @PostConstruct
    public void init() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(currentLocale));
    }

    public String getCurrentLocale() {
        return currentLocale;
    }

    public void setCurrentLocale(String currentLocale) {
        this.currentLocale = currentLocale;
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(currentLocale));
    }

    public Locale getLocale() {
        return new Locale(currentLocale);
    }

}
