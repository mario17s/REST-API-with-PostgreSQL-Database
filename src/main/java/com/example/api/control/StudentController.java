package com.example.api.control;

import com.example.api.serv.StudentService;
import com.example.api.entities.Student;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.apache.tomcat.util.security.ConcurrentMessageDigest.digest;

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
    public Student addStudent(@RequestBody Student s) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String hashed = BCrypt.hashpw(s.getPassword(), BCrypt.gensalt());
        s.setPassword(hashed);
        return serv.saveStudent(s);
    }

    @GetMapping("/students/{i}")
    public Student getStudentById(@PathVariable Long i)
    {
        return serv.getById(i);
    }

    @GetMapping("/students/{uname}/{password}")
    public Student loginStudent(@PathVariable String uname, @PathVariable String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        List<Student> students = serv.getStudents();
        for(Student s: students) {

            if (s.getEmail().equals(uname) && BCrypt.checkpw(password, s.getPassword()))
                return s;
        }
        throw new RuntimeException("error");
    }

    @PutMapping("/students/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student newStudent) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Student s = serv.getById(id);
        s.setFirstName(newStudent.getFirstName());
        s.setLastName(newStudent.getLastName());
        s.setAge(newStudent.getAge());
        s.setDob(newStudent.getDob());
        s.setEmail(newStudent.getEmail());
        String hashed = BCrypt.hashpw(newStudent.getPassword(), BCrypt.gensalt());
        s.setPassword(hashed);
        return serv.saveStudent(s);
    }

    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable Long id)
    {
        Student s = serv.getById(id);
        serv.removeStudent(s);
    }
}
