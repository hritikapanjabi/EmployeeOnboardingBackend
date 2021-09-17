package com.telstra;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.telstra.model.Employee;
import lombok.Getter;
import lombok.Setter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.telstra.model.Course;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
class mixObj{
    ArrayList<String> courseId;
    String designation;
}

public class CourseDataAppTests extends AbstractTest {
    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void getCoursesList() throws Exception {
        String uri = "http://localhost:8080/api/courses";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Course [] courselist = super.mapFromJson(content, Course[].class);
        assertTrue(courselist.length > 0);
    }

    @Test
    public void getCoursesById() throws Exception {
        String uri = "http://localhost:8080/api/courseById";
        Course course=new Course();
        course.setCourseId("5045");
        String inputJson = super.mapToJson(course);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Course course_= super.mapFromJson(content, Course.class);
        System.out.println(course_);
        assertTrue(course_!=null);
    }


    @Test
    public void createCourse() throws Exception {
        String uri = "http://localhost:8080/api/AddCourses";
        Course course = new Course();
        course.setCourseId("93447");
        course.setCourseName("Boot");
        course.setCourseSummary("Imp Course");
        course.setcourseWeightage(20.5);
        course.setCourseDueDate("2021/10/10");



        String inputJson = super.mapToJson(course);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);


        String content = mvcResult.getResponse().getContentAsString();
        Course course_= super.mapFromJson(content, Course.class);
        System.out.println(course_);
        assertTrue(course_!=null);

    }
 /*   @Test
    public void createCoursec() throws Exception {
        String uri = "http://localhost:8080/api/AddCourses";
        Course course = new Course();
        course.setCourseId("93456");
        course.setCourseName("Boot");
        course.setCourseSummary("Imp Course");
        course.setcourseWeightage(20.5);
        course.setCourseDueDate("2021/10/10");



        String inputJson = super.mapToJson(course);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);


        String content = mvcResult.getResponse().getContentAsString();
        Course course_= super.mapFromJson(content, Course.class);
        System.out.println(course_);
        assertTrue(course_!=null);

    }*/

    @Test
    public void updateCourse() throws Exception {
        String uri = "http://localhost:8080/api/UpdateCourseById";
        Course course = new Course();
        course.setCourseId("6000");
        course.setCourseName("Docker");
        course.setCourseSummary("Imp Course");
        course.setcourseWeightage(20.5);
        course.setCourseDueDate("2021/10/10");
        course.setDesignation(null);



        String inputJson = super.mapToJson(course);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);


        String content = mvcResult.getResponse().getContentAsString();
        Course course_= super.mapFromJson(content, Course.class);
        System.out.println(course_);
        assertTrue(course_!=null);

    }

//    @Test
//    public void updateCoursesDesg() throws Exception {
//        String uri = "/UpdateCoursesByDesignantion";
//        ArrayList<Course> courses = new ArrayList<Course>();
//
//        Course course=new Course();
//        course.setCourseId("1000");
//        courses.add(course);
//
//        course.setCourseId("1001");
//        courses.add(course);
//
//        Employee employee=new Employee();
//        String designation= employee.getDesignation();
//
//        String inputJson = super.mapToJson(courses);
//        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(inputJson)).andReturn();
//
//        int status = mvcResult.getResponse().getStatus();
//        assertEquals(200, status);
//        String content = mvcResult.getResponse().getContentAsString();
//        assertEquals(content, "updated successfullly");
//    }
//

    @Test
    public void deleteCourse() throws Exception {
        String uri = "http://localhost:8080/api/DeleteCourseById";
        Course course=new Course();
        course.setCourseId("93456");
        String inputJson = super.mapToJson(course);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(204, status);

    }

    @Test
    public void deleteCourseC() throws Exception {
        String uri = "http://localhost:8080/api/DeleteCourseById";
        Course course=null;
        //course.setCourseId(null);
        String inputJson = super.mapToJson(course);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);

    }

    @Test
    public void updateCoursesDesg() throws Exception {
        String uri = "http://localhost:8080/api/UpdateCoursesByDesignantion";



        ArrayList<String> courses = new ArrayList<String>();


        courses.add("5014");


        courses.add("5045");



        mixObj mixObj=new mixObj();
        mixObj.setCourseId(courses);
        mixObj.setDesignation("Usage Cash and Billing");



        String inputJson = super.mapToJson(mixObj);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "updated successfullly");
    }

    @Test
    public void updateCoursesDesg2() throws Exception {
        String uri = "http://localhost:8080/api/UpdateCoursesByDesignantion";



        ArrayList<String> courses = new ArrayList<String>();


        courses.add("5014");


        courses.add("5045");



        mixObj mixObj=new mixObj();
        mixObj.setCourseId(courses);
        mixObj.setDesignation("Support");



        String inputJson = super.mapToJson(mixObj);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "updated successfullly");
    }





}