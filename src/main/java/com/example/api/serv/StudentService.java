package com.example.api.serv;

import com.example.api.repo.StudentRepository;
import com.example.api.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repo;

    public List<Student> getStudents()
    {
        return repo.findAll();
    }

    public Student saveStudent(Student s)
    {
        return repo.save(s);
    }

    public Student getById(Long id)
    {
        return repo.findById(id).orElse(new Student());
    }

    public void removeStudent(Student s)
    {
        repo.delete(s);
    }
}
