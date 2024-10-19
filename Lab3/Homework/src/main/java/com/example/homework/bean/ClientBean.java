package com.example.homework.bean;

import com.example.homework.model.Client;
import com.example.homework.repository.ClientRepository;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;

import java.util.List;

@ManagedBean(name="clientBean")
@SessionScoped
public class ClientBean {
    private List<Client> clients ;
    private final ClientRepository clientRepository = new ClientRepository();
    private Client currentClient = new Client();
    public List<Client> getClients() {
        if (clients == null) {
            clients = clientRepository.findAll();
        }
        return clients;
    }

    public void prepareEdit(Client client){
        this.currentClient = client;
    }

    public void prepareNewClient() {
        this.currentClient = new Client();
    }

    public void saveClient() {
        if (currentClient.getId() == null) {
            clientRepository.addClient(currentClient);
        } else {
            clientRepository.updateClient(currentClient);
        }
        // refresh client list and reset current client
        clients = clientRepository.findAll();
        this.currentClient = new Client();
    }

    public Client getCurrentClient() {
        return currentClient;
    }

    public void setCurrentClient(Client currentClient) {
        this.currentClient = currentClient;
    }

}
