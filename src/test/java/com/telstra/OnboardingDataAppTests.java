package com.telstra;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.telstra.model.Employee;
import com.telstra.model.Onboarding;
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




public class OnboardingDataAppTests extends AbstractTest {
    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void getOnboardingList() throws Exception {
        String uri = "http://localhost:8080/api/onboarding";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Onboarding[] onboardinglist = super.mapFromJson(content, Onboarding[].class);
        assertTrue(onboardinglist.length > 0);
    }

    @Test
    public void getCoursesById() throws Exception {
        String uri = "http://localhost:8080/api/onboardingbyId";
        Onboarding onboarding=new Onboarding();
        onboarding.setToDoId("100");
        String inputJson = super.mapToJson(onboarding);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Onboarding onboarding1= super.mapFromJson(content, Onboarding.class);
        System.out.println(onboarding1);
        assertTrue(onboarding1!=null);
    }


    @Test
    public void createTodo() throws Exception {
        String uri = "http://localhost:8080/api//onboarding";
        Onboarding todo = new Onboarding();
        todo.setToDoId("125");
        todo.setToDo("Complete your form");
        todo.setMandatory(false);
        todo.setEstDateOfCompletion("2021/12/12");
        todo.setDesignation(null);

        String inputJson = super.mapToJson(todo);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);


        String content = mvcResult.getResponse().getContentAsString();
        Onboarding onboarding= super.mapFromJson(content, Onboarding.class);
        System.out.println(onboarding);
        assertTrue(onboarding!=null);

    }



    @Test
    public void updateOnboarding() throws Exception {
        String uri = "http://localhost:8080/api/UpdateOnboarding";
        Onboarding onboarding=new Onboarding();
        onboarding.setToDoId("121");
        onboarding.setToDo("Upload Docs");
        onboarding.setEstDateOfCompletion("2021/01/01");
        onboarding.setMandatory(false);
        ArrayList<String> a= new ArrayList<String>();
        a.add("Assurance");
        a.add("Orchestration");
        a.add("Digital");
        a.add("Customer Marketing and Sales");
        onboarding.setDesignation(a);

        String inputJson = super.mapToJson(onboarding);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);


        String content = mvcResult.getResponse().getContentAsString();
        Onboarding onboarding1= super.mapFromJson(content, Onboarding.class);
        System.out.println(onboarding);
        assertTrue(onboarding1!=null);

    }

    @Test
    public void updateOnboarding1() throws Exception {
        String uri = "http://localhost:8080/api/UpdateOnboarding";
        Onboarding onboarding=new Onboarding();
        onboarding.setToDoId("121");
        onboarding.setToDo("Upload Docs");
        onboarding.setEstDateOfCompletion("2021/01/01");
        onboarding.setMandatory(false);
        //onboarding.setDesignation(a);




        String inputJson = super.mapToJson(onboarding);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);


        String content = mvcResult.getResponse().getContentAsString();
        Onboarding onboarding1= super.mapFromJson(content, Onboarding.class);
        System.out.println(onboarding);
        assertTrue(onboarding1!=null);

    }


    @Test
    public void deleteToDo() throws Exception {
        String uri = "http://localhost:8080/api/DeleteOnboardingById";
        Onboarding onboarding=new Onboarding();
        onboarding.setToDoId("20007");
        String inputJson = super.mapToJson(onboarding);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(204, status);

    }

    @Getter
    @Setter
    class ObjectTodo{
        ArrayList<String> toDoId;
        String designantion;
    }

    @Test
    public void updateOnboardingDesg() throws Exception {
        String uri = "http://localhost:8080/api/UpdateTodoByDesignantion";

        ArrayList<String> todo = new ArrayList<String>();

        todo.add("001");
        todo.add("002");

       ObjectTodo objectTodo=new ObjectTodo();
       objectTodo.setToDoId(todo);
       objectTodo.setDesignantion("Intern");

        String inputJson = super.mapToJson(objectTodo);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content,"updated successfullly");
    }

    @Test
    public void updateOnboardingDesg1() throws Exception {
        String uri = "http://localhost:8080/api/UpdateTodoByDesignantion";

        ArrayList<String> todo = new ArrayList<String>();

        todo.add("206");
        todo.add("90001");

        ObjectTodo objectTodo=new ObjectTodo();
        objectTodo.setToDoId(todo);
        objectTodo.setDesignantion("Intern");

        String inputJson = super.mapToJson(objectTodo);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content,"updated successfullly");
    }

}