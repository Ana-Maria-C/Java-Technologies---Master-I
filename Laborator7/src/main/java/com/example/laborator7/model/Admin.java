package com.example.laborator7.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("admin")
public class Admin extends User {

}
