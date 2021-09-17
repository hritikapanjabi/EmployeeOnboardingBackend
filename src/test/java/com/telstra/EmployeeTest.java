package com.telstra;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.telstra.controller.EmployeeController;
import com.telstra.model.Employee;
import com.telstra.model.Onboarding;
import com.telstra.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Map;


public class EmployeeTest extends AbstractTest{

    @Autowired
    EmployeeRepository eRepo;



    @Override
    @Before
    public void setUp() {
        super.setUp();
    }
    @Test
    public void getEmployeesList() throws Exception {
        String uri = "http://localhost:8080/api/employees";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Employee[] employeelist = super.mapFromJson(content, Employee[].class);
        assertTrue(employeelist.length > 0);
    }
    @Test
    public void createEmployee() throws Exception {
        String uri = "http://localhost:8080/api/AddEmployee";
        Employee employee = new Employee();
        employee.setEmailId("shubhamrt@gmail.com");
        employee.setDesignation("Sales");
        employee.setDateOfJoining("2021/07/16");
        String inputJson = super.mapToJson(employee);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
       /*String content = mvcResult.getResponse().getContentAsString();
       assertEquals(content, eRepo.findById(employee.getempId()).get());*/
    }

    @Test
    public void createEmployeec() throws Exception {

            String uri = "http://localhost:8080/api/AddEmployee";
            Employee employee = new Employee();
            employee.setEmailId("shubhamrt@gmail.com");
            String inputJson = super.mapToJson(employee);
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(inputJson)).andReturn();

            int status = mvcResult.getResponse().getStatus();
            assertEquals(200, status);
       /*String content = mvcResult.getResponse().getContentAsString();
       assertEquals(content, eRepo.findById(employee.getempId()).get());*/



    }

    @Test
    public void getEmployeeById() throws Exception {
        String uri = "http://localhost:8080/api/employeeById";
        Employee employee=new Employee();
        employee.setempId("6139c3d4544e726b9c295c28");
        String inputJson = super.mapToJson(employee);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Employee employee_= super.mapFromJson(content, Employee.class);
        System.out.println(employee_);
        assertTrue(employee_!=null);
    }



    @Test
    public void updateEmployee() throws Exception {

        String uri = "http://localhost:8080/api/UpdateEmployeeById";
        Employee employee = new Employee();
        employee.setempId("6139c3d4544e726b9c295c28");
        employee.setFirstName("Manali");
        employee.setLastName("Santaji");
        employee.setAddress("pune");
        //employee.setDesignation(null);
        employee.setDob("2020/10/10");
        employee.setEmailId("abc@gmail.com");
        try{
            String inputJson = super.mapToJson(employee);
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(inputJson)).andReturn();

            int status = mvcResult.getResponse().getStatus();
            assertEquals(200, status);
        }
        catch(Exception e){
            System.out.println(e);

        }



       /*String content = mvcResult.getResponse().getContentAsString();
       assertEquals(content, mvcResult.getResponse().getContentAsString());*/
    }

    @Test
    public void updateEmployee2() throws Exception {

        String uri = "http://localhost:8080/api/UpdateEmployeeById";
        Employee employee = new Employee();
        employee.setempId("613e20a8fa6f5a0238071d91");
        employee.setFirstName("Samita");
        employee.setLastName("Rana");
        employee.setAddress("pune");
        //employee.setDesignation(null);
        employee.setDob("2020/10/10");
        employee.setEmailId("samita@gmail.com");
        try{
            String inputJson = super.mapToJson(employee);
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(inputJson)).andReturn();

            int status = mvcResult.getResponse().getStatus();
            assertEquals(200, status);
        }
        catch(Exception e){
            System.out.println(e);

        }



       /*String content = mvcResult.getResponse().getContentAsString();
       assertEquals(content, mvcResult.getResponse().getContentAsString());*/
    }

    @Test
    public void updateEmployee3() throws Exception {

        String uri = "http://localhost:8080/api/UpdateEmployeeById";
        Employee employee = new Employee();
        employee.setempId("614089841dedce2ae45fa6ec");
        employee.setFirstName("Samita");
        employee.setLastName("Rana");
        employee.setAddress("pune");
        //employee.setDesignation(null);
        employee.setDob("2020/10/10");
        employee.setEmailId("samita@gmail.com");
        try{
            String inputJson = super.mapToJson(employee);
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(inputJson)).andReturn();

            int status = mvcResult.getResponse().getStatus();
            assertEquals(200, status);
        }
        catch(Exception e){
            System.out.println(e);

        }



       /*String content = mvcResult.getResponse().getContentAsString();
       assertEquals(content, mvcResult.getResponse().getContentAsString());*/
    }

    @Test
    public void deleteEmployeeByid() throws Exception {
        String uri = "http://localhost:8080/api/DeleteEmployeeById";
        Employee employee=new Employee();
        employee.setempId("6139c3d4544e726b9c295c28");
        String inputJson = super.mapToJson(employee);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(204, status);
    }


    @Test
    public void getToDoProgress() throws Exception {
        String uri = "http://localhost:8080/api/getTodoProgress";
        Employee employee=new Employee();
        employee.setempId("6140a5a1baa23e44a0a37bd6");
        try{
            String inputJson = super.mapToJson(employee);
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(inputJson)).andReturn();


            String content = mvcResult.getResponse().getContentAsString();
            Employee employee_= super.mapFromJson(content, Employee.class);
            System.out.println(employee_);
            assertTrue(employee_!=null);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }


    }


    @Test
    public void updatedCoursesByDesg() throws Exception {
        String uri = "http://localhost:8080/api/updatedCoursesByDeignnation";
        Employee employee=new Employee();
        employee.setempId("613adc9a1d170f054ec7c07f");
        String inputJson = super.mapToJson(employee);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();


        String content = mvcResult.getResponse().getContentAsString();
        ArrayList<Map<String,Integer>> mp= super.mapFromJson(content, ArrayList.class);
        System.out.println(mp);
        assertTrue(mp!=null);
    }
    @Autowired
    EmployeeRepository ec;

    @Autowired
    EmployeeController employeeController;

    @Test
    public  void  testgetToDoProgress(){
        //Onboarding onboarding= onboardingRepository.findByToDoId("100").get();
        Employee employee= ec.findByempId("6139c3cd544e726b9c295c27").get();
        Double d= employeeController.getTodoProgress(employee);
        //Assertions.assertEquals(d.getStatusCode(), HttpStatus.OK);
        assertThat(!d.isNaN());
    }

    @Test
    public  void  testgetCourseProgress(){
        //Onboarding onboarding= onboardingRepository.findByToDoId("100").get();
        Employee employee= ec.findByempId("6139c3cd544e726b9c295c27").get();
        Double d= employeeController.getTodoProgress(employee);
        //Assertions.assertEquals(d.getStatusCode(), HttpStatus.OK);
        assertThat(!d.isNaN());
    }

/*    @Test
    public void getCoursesProgressById() throws Exception {
        String uri = "http://localhost:8080/api/getTodoProgress";
        Employee employee = new Employee();
        employee.setempId("6139c3cd544e726b9c295c27");
        String inputJson = super.mapToJson(employee);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Employee _employee= super.mapFromJson(content, Employee.class);
        System.out.println(_employee);
        assertTrue(_employee!=null);
    }*/


 /*   @Test
    public void todoDueDateThisWeek() throws Exception {
        String uri = "http://localhost:8080/api/TodosDueDateThisWeek/6140a5a1baa23e44a0a37bd6";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ArrayList<Map<String,String>>empTodoList= super.mapFromJson(content, ArrayList.class);
        System.out.println(empTodoList);
        assertTrue(empTodoList!=null);
    }*/
}

