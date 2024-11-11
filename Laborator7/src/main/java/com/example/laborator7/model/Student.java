package com.example.laborator7.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("student")
public class Student extends User {

}
