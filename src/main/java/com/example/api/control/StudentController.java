package com.example.api.control;

import com.example.api.serv.StudentService;
import com.example.api.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@CrossOrigin(value = "http://localhost:8081")
@RequestMapping("/api")
public class StudentController {
    @Autowired
    public StudentService serv;

    @GetMapping("/students")
    public List<Student> getStudents()
    {
        return serv.getStudents();
    }

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student s)
    {
        return serv.saveStudent(s);
    }

    @GetMapping("/students/{i}")
    public Student getStudentById(@PathVariable Long i)
    {
        return serv.getById(i);
    }

    @PutMapping("/students/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student newStudent)
    {
        Student s = serv.getById(id);
        s.setFirstName(newStudent.getFirstName());
        s.setLastName(newStudent.getLastName());
        s.setAge(newStudent.getAge());
        s.setDob(newStudent.getDob());
        s.setEmail(newStudent.getEmail());
        return serv.saveStudent(s);
    }

    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable Long id)
    {
        Student s = serv.getById(id);
        serv.removeStudent(s);
    }
}
