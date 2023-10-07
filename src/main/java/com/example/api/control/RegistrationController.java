package com.example.api.control;

import com.example.api.entities.Course;
import com.example.api.entities.Registration;
import com.example.api.entities.Student;
import com.example.api.repo.RegistrationRepository;
import com.example.api.serv.CourseService;
import com.example.api.serv.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@ResponseBody
@CrossOrigin(value = "http://localhost:8081")
@RequestMapping("/api")
public class RegistrationController {
    @Autowired
    public StudentService ss;

    @Autowired
    public CourseService cs;

    @Autowired
    public RegistrationRepository repo;

    @GetMapping("/registrations")
    public List<Registration> getRegistrations()
    {
        return repo.findAll();
    }

    @PostMapping("/registrations/{sid}/{cid}")
    public String addRegistration(@PathVariable Long sid, @PathVariable Long cid){
        Student s = ss.getById(sid);
        String error = "";
        int sum = 0;
        for (Registration r : s.gtRegistrations()) {
            Course currentCourse = r.getCourse();
            if (currentCourse.getId() == cid)
                return "Duplicate!";
            sum += currentCourse.getCredits();
        }
        if (sum >= 50)
            return "Over the limit!";
        Course c = cs.getCourseById(cid);
        Registration r = repo.save(new Registration(s, c));
        s.addRegistration(r);
        c.addRegistration(r);
        return error;
    }

    @GetMapping("/registrations/{sid}")
    public int getCredits(@PathVariable Long sid){
        Student s = ss.getById(sid);
        int sum = 0;
        for(Registration r: s.gtRegistrations())
        {
            Course currentCourse = r.getCourse();
            sum += currentCourse.getCredits();
        }
        return sum;
    }

    @DeleteMapping("/registrations/{sid}/{cid}")
    public void deleteRegistration(@PathVariable Long sid, @PathVariable Long cid){
        Student s = ss.getById(sid);
        Course c = cs.getCourseById(cid);
        Registration r = new Registration();
        for(Registration re: s.gtRegistrations())
        {
            if(re.getStudent().getId() == sid && re.getCourse().getId() == cid)
                r = re;
        }
        repo.delete(r);
        s.removeRegistration(r);
        c.removeRegistration(r);
    }

}
