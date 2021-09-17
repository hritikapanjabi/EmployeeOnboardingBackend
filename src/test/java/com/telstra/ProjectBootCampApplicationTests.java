package com.telstra;

import com.telstra.controller.EmployeeController;
import com.telstra.controller.OnboardingController;
import com.telstra.model.Course;
import com.telstra.model.Employee;
import com.telstra.model.Onboarding;
import com.telstra.repository.CourseRepository;
import com.telstra.repository.EmployeeRepository;
import com.telstra.repository.OnboardingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectBootCampApplicationTests {

	@Autowired
	EmployeeRepository eRepo;

	//Employees Test Cases

	@Test
	public void testGetAllEmployees () {
		List<Employee> list = eRepo.findAll();
		assertThat(list).size().isGreaterThan(0);
	}

	@Test
	public void postEmployee () {
		Employee e = new Employee();
		//e.setFirstName ("Samrudhi");
		//e.setLastName("Santaji");
		e.setDateOfJoining("2020/05/14");
		e.setEmailId("geet@gmal.com");
		e.setDesignation("Sales");
		//e.setAddress("Belgaum");
		eRepo.save(e);
		assertNotNull(eRepo.findById(e.getempId()).get());
	}

	@Test
	public void testEmpById () {
		Employee employee = eRepo.findById("6139c3d4544e726b9c295c28").get();
		assertEquals("radha@gmail.com", employee.getEmailID());
	}

	@Test
	public void updateEmployeeTest(){

		Optional<Employee> employeeData = eRepo.findByempId("6139c3d4544e726b9c295c28");

		if (employeeData.isPresent()) {
			Employee _employee = employeeData.get();
			_employee.setEmailId("radha@gmail.com");
			eRepo.save(_employee);
			assertEquals("radha@gmail.com", _employee.getEmailID());
		}

	}




	//Courses  Test Cases
	@Autowired
	CourseRepository courseRepository;

	@Test
	public void testGetAllCourses () {
		List<Course> list = courseRepository.findAll();
		assertThat(list).size().isGreaterThan(0);
	}

	@Test
	public void testCourseCreate () {
		Course c = new Course();
		c.setCourseId("80010");
		c.setCourseName("Jenkins");
		c.setCourseSummary("Jenkins summary");
		c.setcourseWeightage(70.0);
		c.setCourseDueDate("20/7/2021");
		courseRepository.save(c);
		assertNotNull(courseRepository.findBycourseId("80010").get());

	}

	@Test
	public void testCourseById () {
		Course course = courseRepository.findBycourseId("80010").get();
		assertEquals("Jenkins", course.getCourseName());
	}



	//Onboarding Task Test Cases

	@Autowired
	OnboardingRepository onboardingRepository;

	@Test
	public void testGetAllToDos () {
		List<Onboarding> list = onboardingRepository.findAll();
		assertThat(list).size().isGreaterThan(0);
	}


	@Test
	public void testToDoCreate () {
		Onboarding o = new Onboarding();
		o.setToDoId ("60001");
		o.setToDo("PAN information");
		//o.setEstDateOfCompletion (27-11-2021);
		o.setSummary("Please Complete the courses");
		ArrayList<String> ondesi = new ArrayList<>(List.of("Sales", "Support"));
		o.setDesignation(ondesi);
		o.setMandatory(false);
		onboardingRepository.save(o);

		assertNotNull(onboardingRepository.findByToDoId("60001").get());
	}

	@Test
	public void testTodoById () {

		Onboarding onboarding = onboardingRepository.findByToDoId("100").get();
		assertEquals("Upload Documents", onboarding.getToDo());
	}
	Integer count = 206;


	@Test
	public void testpostTodo(){
		Onboarding o = new Onboarding();
		o.setToDoId ((count).toString());
		o.setToDo("complete course ");
		o.setEstDateOfCompletion ("27-11-2021");
		o.setSummary("Please Complete the courses");
		ArrayList<String> ondesi = new ArrayList<>(List.of("NA"));
		o.setDesignation(ondesi);
		o.setMandatory(true);

		Onboarding onboarding= oc.createToDo(o);
//		assertEquals(onboarding,o);

	}

@Autowired
	OnboardingController oc;
	@Test
	public  void testgetToDo(){
		ResponseEntity<List<Onboarding>> re=oc.getAlltodos();
		assertEquals(re.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void testgetMandatoryToDo(){
		ResponseEntity<List<Onboarding>> re=oc.getMandatorytodos();
		assertEquals(re.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void testgetNotMandatoryToDo(){
		ResponseEntity<List<Onboarding>> re=oc.getNonMandatorytodos();
		assertEquals(re.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public  void  testgetToDoById(){
		Onboarding onboarding= onboardingRepository.findByToDoId("100").get();
		ResponseEntity<Onboarding> re = oc.getTodoById(onboarding);
		assertEquals(re.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public  void testputToDo(){
		Onboarding onboarding= onboardingRepository.findByToDoId(("103")).get();
		onboarding.setToDo(
				"Updated this toDo name"
		);
		Onboarding o=oc.updateToDo(onboarding);
		assertEquals("Updated this toDo name", o.getToDo());


	}

	@Test
	public  void  testDeleteToDobyId(){
		System.out.println(count);
		Onboarding onboarding= onboardingRepository.findByToDoId("800049").get();
		ResponseEntity<HttpStatus> re = oc.deleteToDo(onboarding);
		assertEquals(re.getStatusCode(), HttpStatus.NO_CONTENT);
	}


//	@Test
//	public  void  testUpdateTodoByDesignantion(){
//		ObjectTodo otd=new ObjectTodo();
//		otd.designantion="IT";
//		otd.toDoId=new ArrayList<>(List.of("104"));
//		String sm= oc.UpdateTodoByDesignantion(otd);
//		assertEquals(sm,"updated successfullly");
//	}

	@Test
	public void testgetTodosByDesignation(){
		Employee employee=eRepo.findByempId("6139c3d4544e726b9c295c28").get();
		List<Onboarding> list = oc.getTodosByDesignation(employee);
		assertThat(list.size()>0);

	}

}
