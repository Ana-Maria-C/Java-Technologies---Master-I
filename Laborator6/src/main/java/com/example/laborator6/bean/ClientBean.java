package com.example.laborator6.bean;

import com.example.laborator6.model.Client;
import com.example.laborator6.repository.ClientRepository;
import com.example.laborator6.repository.OrderRepository;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;


import java.io.Serializable;
import java.util.List;

@Named("clientBean")
@SessionScoped

public class ClientBean implements Serializable {
    @EJB
    private ClientRepository clientRepository;
    @Inject
    private OrderBean orderBean;

    private String email;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String findClientByEmail() {
        Client client = clientRepository.findByEmail(email);
        if (client != null) {
            orderBean.startNewOrder(client);
            return "myOrder?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email not found!", "Email not found!"));
            return null;
        }
    }

    public List<Client> getAll(){
        return clientRepository.findAll();
    }
}
