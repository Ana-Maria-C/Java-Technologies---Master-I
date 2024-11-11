package com.example.laborator7.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "evaluations")
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String registration_number;

    @Column(nullable = false)
    private Long student_id;

    @Column(nullable = false)
    private Long teacher_id;

    @Column(nullable = false)
    private Long subject_id;

    @Column(nullable = false)
    private String activity_type;

    @Column(nullable = false)
    private Integer grade;

    private String comment;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private LocalDateTime start_date;

    @Column(nullable = false)
    private LocalDateTime end_date;

}
