package com.example.homework.bean;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.ViewScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class RedirectBean {

    public void redirectToViewDataPage() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        try {
            externalContext.redirect("dataView.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void redirectToEditDataPage() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        try {
            externalContext.redirect("dataEdit.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}