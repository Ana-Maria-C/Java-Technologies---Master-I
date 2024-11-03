package com.example.compulsory5.bean;
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

}