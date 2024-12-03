package com.example.laborator8.bean;

import com.example.laborator8.model.Subject;
import com.example.laborator8.model.Teacher;
import com.example.laborator8.repository.SubjectRepository;
import com.example.laborator8.repository.UserRepository;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("subjectBean")
@SessionScoped
@Getter
@Setter
public class SubjectBean implements Serializable {

    @Inject
    private SubjectRepository subjectRepository;
    @Inject
    UserRepository userRepository;
    private List<Subject> subjects;
    private List<Subject> subjectsByTeacher;
    private String name;
    private List<Long> teacherIds;

    public void getAllSubjects() {
        subjects = subjectRepository.findAll();
    }

    public void getSubjectsByTeacherId(Long id) {
        subjectsByTeacher = subjectRepository.findSubjectsByTeacherId(id);
    }
    public String getSubjectById(Long subjectId){
        return subjectRepository.findById(subjectId).getName();
    }

    public void createSubject()
    {
        Subject subject = new Subject();
        subject.setName(name);
        List<Teacher> teachersList = new ArrayList<>();

        if (teacherIds != null && !teacherIds.isEmpty()) {
            for (Long teacherId : teacherIds) {
                Teacher teacher = (Teacher) userRepository.findById(teacherId);
                if (teacher != null) {
                    teachersList.add(teacher);
                    teacher.addSubject(subject);
                }
            }
        }
        subject.setTeachers(teachersList);
        subjectRepository.save(subject);

        getAllSubjects();
        name = null;
        teacherIds = new ArrayList<>();
    }
}
