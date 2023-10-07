package com.example.api.control;

import com.example.api.entities.Course;
import com.example.api.entities.Registration;
import com.example.api.entities.Student;
import com.example.api.serv.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@ResponseBody
@CrossOrigin(value = "http://localhost:8081")
@RequestMapping("/api")
public class CourseController {
    @Autowired
    public CourseService serv;

    @GetMapping("/courses")
    public List<Course> getCourses(){return serv.getCourses();}

    @PostMapping("/courses")
    public Course addCourse(@RequestBody Course c){return serv.saveCourse(c);}

    @GetMapping("/courses/{id}")
    public Course getById(@PathVariable Long id){return serv.getCourseById(id);}

    @GetMapping("/courses/stud/{cid}")
    public List<Student> getStudentFromCourse(@PathVariable Long cid){
        List<Student> students = new ArrayList<>();
        Course c = serv.getCourseById(cid);
        for(Registration r: c.gtRegistrations())
        {
            students.add(r.getStudent());
        }
        return students;
    }

    @PutMapping("/courses/{id}")
    public Course updateCourse(@RequestBody Course c, @PathVariable Long id){
        Course course = serv.getCourseById(id);
        course.setTitle(c.getTitle());
        course.setDescription(c.getDescription());
        course.setCredits(c.getCredits());
        course.setProfessor(c.getProfessor());
        return serv.saveCourse(course);
    }

    @DeleteMapping("/courses/{id}")
    public void deleteCourse(@PathVariable Long id){
        Course c = serv.getCourseById(id);
        serv.removeCourse(c);
    }
}
