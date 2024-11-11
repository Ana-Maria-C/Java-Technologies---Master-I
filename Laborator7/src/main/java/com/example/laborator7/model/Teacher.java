package com.example.laborator7.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@DiscriminatorValue("teacher")
public class Teacher extends User {
    @ManyToMany
    @JoinTable(
            name = "teacher_subject",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Subject> subjects = new ArrayList<>();
    public void addSubject(Subject subject) {
        subjects.add(subject);
        subject.getTeachers().add(this);
    }

}
