package com.telstra.controller;

import com.telstra.model.Course;
import com.telstra.model.Employee;
import com.telstra.model.Onboarding;
import com.telstra.repository.CourseRepository;
import com.telstra.repository.EmployeeRepository;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
@Getter
@Setter
class mixObj{
    ArrayList<String> courseId;
    String designation;
}

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api")

public class CourseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        try {
            List<Course> courses = new ArrayList<Course>();

            courses=courseRepository.findAll();//.forEach(courses::add);


            if (courses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/courseById")
    public ResponseEntity<Course> getCourseById(@RequestBody Course course) {
        String courseId = course.getCourseId();
        Optional<Course> courseData = courseRepository.findBycourseId(courseId);

        if (courseData.isPresent()) {
            return new ResponseEntity<>(courseData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @PostMapping("/AddCourses")
    public Course createCourse(@RequestBody Course course) {
        try {
            Course _course = courseRepository.save(new Course(course.getCourseId(), course.getCourseName(), course.getCourseSummary(), course.getcourseWeightage()/*,course.getDesignation()*/,course.getCourseDueDate()));
            String courseId = _course.getCourseId();
            String courseDueDate =_course.getCourseDueDate();
            ArrayList<String>list =new ArrayList<String>();
            list.add("NA");
            _course.setDesignation(list);
            System.out.println("trunali "+list);
            System.out.println("here are my designations ****"+_course.getDesignation());
            System.out.println("course is here"+_course);
            courseRepository.deleteBycourseId(course.getCourseId());
            return (courseRepository.save(_course));
        } catch (Exception e) {
            System.out.println("im posting course "+e);
            return new Course();
        }
    }

    @PutMapping("/UpdateCourseById")
    public Course updateTutorial(@RequestBody Course course) {

        try{
            String id = course.getCourseId();
            Optional<Course> courseData = courseRepository.findBycourseId(id);

            if (courseData.isPresent()) {
                Course _course = courseData.get();

                _course.setCourseId(course.getCourseId());
                if(course.getCourseName()!=null)
                {
                    _course.setCourseName(course.getCourseName());
                }
                if(course.getCourseSummary()!=null)
                {
                    _course.setCourseSummary(course.getCourseSummary());
                }
                if(course.getDesignation()!=null)
                {
                    _course.setDesignation(course.getDesignation());
                }
                if(course.getcourseWeightage()!=null)
                {
                    _course.setcourseWeightage(course.getcourseWeightage());
                }
                if(course.getCourseDueDate()!=null)
                {
                    _course.setCourseDueDate(course.getCourseDueDate());
                }

                courseRepository.deleteBycourseId(course.getCourseId());
                return (courseRepository.save(_course));

            } else {
                return new Course();
            }
        } catch (Exception e) {
            System.out.println("In updateTutorial :"+e);
            return new Course();
        }

    }

@GetMapping("/CoursesByDesignation")
    public ArrayList<Course> getCoursesByDesignation(Employee employee) {


        String designation= employee.getDesignation();

        try {
            ArrayList<Course> courses = new ArrayList<Course>();
           List<Course> courseArrayList = getAllCourses().getBody();
            System.out.println(courseArrayList);
            for(int i=0;i<courseArrayList.size();i++)
            {
                if(courseArrayList.get(i).getDesignation()==null)
                {
                    continue;
                }
                else if(courseArrayList.get(i).getDesignation().contains(designation))
                {
                    courses.add(courseArrayList.get(i));
                }
            }

            if (courses.isEmpty()) {
                return new ArrayList<>();
            }
            return courses;
        } catch (Exception e) {
            System.out.println("In getCoursesByDesignation"+e);
            return new ArrayList<>();
        }

    }





    @DeleteMapping("/DeleteCourseById")
    public ResponseEntity<HttpStatus> deleteCourse(@RequestBody Course course) {
            String courseId=course.getCourseId();
        try {

            courseRepository.deleteBycourseId(courseId);
            LOGGER.info("Course with courseID " + courseId + " is deleted successfully");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/DeleteCourses")
    public ResponseEntity<HttpStatus> deleteAllCourses() {
        try {
            courseRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Autowired
    EmployeeController employeeController;

    @PutMapping("/UpdateCoursesByDesignation")
    public String UpdateCoursesByDesignation(@RequestBody mixObj obj) {
        ArrayList<String> CourseIdList = obj.getCourseId();
        String designation = obj.getDesignation();
        try {
            for (int i = 0; i < CourseIdList.size(); i++) {

                Optional<Course> courseData = courseRepository.findBycourseId(CourseIdList.get(i));


                Course _course = courseData.get();

                ArrayList<String>list =new ArrayList<String>();
                list = _course.getDesignation();

                if (list.contains(designation)) {
                    System.out.println("Course is already assigned to designation");
                }
                else {

                        _course.addDesignationToCourse(designation);

                        List<Employee> l1= employeeRepository.findBydesignation(designation);

                        for (Employee employee : l1) {
                            ArrayList<Map<String,Integer>> m1=new ArrayList<Map<String,Integer>>();
                            Integer compeletePerc = 0;

                            Map<String, Integer> courseOfEmp = new HashMap<String, Integer>();

                            courseOfEmp.put("courseId", Integer.parseInt(CourseIdList.get(i)));
                            courseOfEmp.put("completion", compeletePerc);


                            Optional<Employee> employeeData = employeeRepository.findByempId(employee.getempId());

                            if (employeeData.isPresent()) {

                                Employee _employee = employeeData.get();

                                try{
                                    _employee.addEmployeeCourse(courseOfEmp);
                                    _employee.setcompleteWeightage(employeeController.averageCompletion(_employee.getempId()));
                                }
                                catch (Exception e)
                                {
                                    System.out.println(e);
                                }

                                employeeRepository.deleteByempId(_employee.getempId());
                                employeeRepository.save(_employee);
                            }
                            else {
                                System.out.println("employee not found");
                            }

                        }
                        courseRepository.deleteBycourseId(CourseIdList.get(i));
                        courseRepository.save(_course);

                    }

            }
            return "updated successfullly";

        } catch (Exception e) {
            System.out.println("In UpdateCoursesByDesignation : "+e);
            return "throw exception";
        }

    }

}
