package com.example.laborator8.bean;

import com.example.laborator8.interceptor.Logged;
import com.example.laborator8.model.Admin;
import com.example.laborator8.model.Student;
import com.example.laborator8.model.Teacher;
import com.example.laborator8.model.User;
import com.example.laborator8.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Named("userBean")
@SessionScoped
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBean implements Serializable{

    @Inject
    private UserRepository userRepository;

    private String email;
    private String name;
    private String role;
    private String password;
    private List<String> roles;
    private User loggedInUser;
    private List<Student> students;
    private List<Teacher> teachers;

    @PostConstruct
    public void init() {
        roles = Arrays.asList("admin", "teacher", "student");
    }

    // create User

    @Transactional
    public String createAccount() {
        if ("admin".equals(role)) {
            Admin admin = new Admin();
            admin.setEmail(email);
            admin.setName(name);
            admin.setPassword(password);
            userRepository.save(admin);
        } else if ("teacher".equals(role)) {
            Teacher teacher = new Teacher();
            teacher.setEmail(email);
            teacher.setName(name);
            teacher.setPassword(password);
            userRepository.save(teacher);
        } else if ("student".equals(role)) {
            Student student = new Student();
            student.setEmail(email);
            student.setName(name);
            student.setPassword(password);
            userRepository.save(student);
        }
        return "login.xhtml?faces-redirect=true";
    }

    //login User
    @Logged
    @Transactional
    public String authenticate() {
        try {
            User user = userRepository.findByEmail(email);
            if (user != null && user.getPassword().equals(password)) {
                this.loggedInUser = user;

                if (user instanceof Admin) {
                    return "adminPage.xhtml?faces-redirect=true";
                } else if (user instanceof Student) {
                    return "studentPage.xhtml?faces-redirect=true";
                } else if (user instanceof Teacher) {
                    return "teacherPage.xhtml?faces-redirect=true";
                } else {
                    return "login.xhtml?faces-redirect=true";
                }

            } else {
                return "login.xhtml?faces-redirect=true";
            }
        } catch (Exception e) {
            return "login.xhtml?faces-redirect=true";
        }
    }

    // logout User
    public String logout() {
        loggedInUser = null;
        return "login.xhtml?faces-redirect=true";
    }

    public void getAllStudents(){
        students = userRepository.getAllStudents();
    }

    public void getAllTeachers()
    {
        teachers = userRepository.getAllTeachers();
    }

    public String getUserById(Long userId){
        return userRepository.findById(userId).getName();
    }
}
