package com.example.laborator8.model;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    @JsonbTransient
    private LocalDateTime timestamp;

    @Column(nullable = false)
    @JsonbTransient
    private LocalDateTime start_date;

    @Column(nullable = false)
    @JsonbTransient
    private LocalDateTime end_date;

}
