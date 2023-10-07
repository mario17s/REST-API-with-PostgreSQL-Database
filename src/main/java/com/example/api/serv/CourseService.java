package com.example.api.serv;

import com.example.api.entities.Course;
import com.example.api.repo.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository repo;

    public List<Course> getCourses()
    {
        return repo.findAll();
    }

    public Course saveCourse(Course c)
    {
        return repo.save(c);
    }

    public Course getCourseById(Long id)
    {
        return repo.findById(id).orElse(new Course());
    }

    public void removeCourse(Course c)
    {
        repo.delete(c);
    }
}
