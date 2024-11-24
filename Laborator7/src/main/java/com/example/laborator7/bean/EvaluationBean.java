package com.example.laborator7.bean;

import com.example.laborator7.RegistrationNumberGenerator;
import com.example.laborator7.decorator.EvaluationService;
import com.example.laborator7.model.Evaluation;
import com.example.laborator7.model.Subject;
import com.example.laborator7.model.Teacher;
import com.example.laborator7.model.User;
import com.example.laborator7.observers.EvaluationCreatedEvent;
import com.example.laborator7.repository.EvaluationRepository;
import com.example.laborator7.repository.SubjectRepository;
import com.example.laborator7.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Named("evaluationBean")
@SessionScoped
@Getter
@Setter
public class EvaluationBean implements Serializable {

    @Inject
    private EvaluationRepository evaluationRepository;
    @Inject
    private SubjectRepository subjectRepository;
    @Inject
    private UserRepository userRepository;
    @Inject
    private UserBean userBean;

    @Inject
    private RegistrationNumberGenerator registrationNumberGenerator;

    @Inject
    EvaluationService evaluationService;

    @Inject
    private Event<EvaluationCreatedEvent> event;

    private Long subjectId;
    private Long teacherId;
    private Long studentId;
    private String activityType;
    private Integer grade;
    private String comment;
    private List<Teacher> teachers;
    private List<Subject> subjectsForTeacher;
    private Evaluation evaluation = new Evaluation();
    private List<Evaluation> evaluations;
    private List<Evaluation> evaluationsOfStudent;
    private List<Evaluation> evaluationsOfTeacher;
    private List<Integer> grades;
    private final LocalDateTime evaluationStartDate = LocalDateTime.of(2024, 11, 10, 8, 0);
    private final LocalDateTime evaluationEndDate = LocalDateTime.of(2024, 11, 20, 14, 0);

    @PostConstruct
    public void init() {
        grades = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
    }

    public void createEvaluation() {

            String registrationNumber = registrationNumberGenerator.generateRegistrationNumber();
            evaluation.setRegistration_number(registrationNumber);
            evaluation.setTimestamp(LocalDateTime.now());
            evaluation.setTeacher_id(teacherId);
            evaluation.setSubject_id(subjectId);
            evaluation.setStudent_id(userBean.getLoggedInUser().getId());
            evaluation.setStart_date(LocalDateTime.now());
            evaluation.setEnd_date(LocalDateTime.now().plusMonths(6));
            evaluation.setTimestamp(LocalDateTime.now());
            evaluation.setActivity_type(activityType);
            evaluation.setGrade(grade);
            evaluation.setComment(comment);
            evaluationService.createEvaluation(evaluation);
            event.fire(new EvaluationCreatedEvent(evaluation.getId()));
            evaluation = new Evaluation();

    }

    private boolean isWithinEvaluationPeriod() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(evaluationStartDate) && now.isBefore(evaluationEndDate);
    }

    public void getAllEvaluations() {
        evaluations = evaluationRepository.findAll();
    }

    public void loadEvaluationsOfLoggedStudent() {
        Long studentId = userBean.getLoggedInUser().getId();
        evaluationsOfStudent = evaluationRepository.findByStudentId(studentId);
    }

    public void loadEvaluationsOfLoggedTeacher() {
        Long teacherId = userBean.getLoggedInUser().getId();
        evaluationsOfTeacher = evaluationRepository.findByTeacherId(teacherId);
    }

    public void loadTeachers() {
       List<User> users = userRepository.findAll();
       List<Teacher> teacherList =  new ArrayList<>();
       for (User user: users){
           if(user instanceof Teacher)
           {
               teacherList.add((Teacher) user);
           }
       }
       teachers = teacherList;
    }

    public void loadSubjectsForTeacher() {
        if (teacherId != null) {
            subjectsForTeacher = subjectRepository.findSubjectsByTeacherId(teacherId);
        }
    }
}
