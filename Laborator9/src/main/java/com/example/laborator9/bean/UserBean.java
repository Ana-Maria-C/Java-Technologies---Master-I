package com.example.laborator9.bean;

import com.example.laborator9.model.User;
import com.example.laborator9.model.UserRoles;
import com.example.laborator9.repository.UserRepository;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ManagedBean
@SessionScoped
public class UserBean implements Serializable {
    @NotNull
    @Size(min = 1, max = 50)
    private String email;
    @NotNull
    @Size(min = 1, max = 50)
    private String name;
    @Size(min = 3, max=25, message = "The name should between 3 and 25 characters.")
    private String password;

    @Inject UserRepository userRepository;
    private boolean isAdmin;
    private List<User> users;
    private static final String REST_URL = "http://localhost:8080/Laborator9-1.0-SNAPSHOT/api/users";

    public void setAdmin(boolean admin) {
        this.isAdmin = admin;
    }

    public void loadUsers() {
        users = getUsersFromAPI();
    }

    public List<User> getUsersFromAPI() {
        Client client = ClientBuilder.newClient();
        try {
            Response response = client.target(REST_URL)
                    .request(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                return response.readEntity(new GenericType<List<User>>(){});
            } else {
                addErrorMessage(new Exception("Failed to retrieve users: " + response.getStatus()));
                return new ArrayList<>();
            }
        } catch (Exception e) {
            addErrorMessage(e);
            return new ArrayList<>();
        }
    }

    private void addErrorMessage(Exception e) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Exception occurred: " + e.getMessage()));
    }


    public List<User> getUsers() {
        loadUsers();
        return users;
    }

    public void redirectBasedOnRole() throws IOException {

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        if (request.isUserInRole("admin")) {
            externalContext.redirect(externalContext.getRequestContextPath() + "/admin/adminpage.xhtml");
        } else {
            externalContext.redirect(externalContext.getRequestContextPath() + "/userpage.xhtml");
        }
    }


    public void redirectIfLoggedIn() throws IOException {
        if (isLoggedIn()) {
            redirectBasedOnRole();
        }
        else {
            redirectToPage("login.xhtml");
        }

    }

    public boolean isLoggedIn() {
        return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal() != null;
    }

    public void logout() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
        try {
            request.logout();
            redirectToPage("/login.xhtml");
        } catch (ServletException | IOException e) {
            context.addMessage(null, new FacesMessage("Logout failed."));
            e.printStackTrace();
        }
    }

    private void redirectToPage(String page) throws IOException {
       String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/" + page;
       FacesContext.getCurrentInstance().getExternalContext().redirect(path);
    }

    public void register() throws IOException {
        User user = new User();
        user.setPassword(password);
        user.setEmail(email);
        user.setName(name);

        UserRoles role = new UserRoles();
        role.setUser(user);
        if (isAdmin){
            role.setRole_name("admin");
        }
        else{
            role.setRole_name("user");
        }
        user.setRoles(List.of(role));

        userRepository.save(user);

        redirectIfLoggedIn();
    }

}
