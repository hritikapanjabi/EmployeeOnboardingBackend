package com.telstra;


/*
import com.telstra.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class ProjectBootCampApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectBootCampApplication .class, args);
	}
}
*/

import com.telstra.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

//@EnableSwagger2
@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)

public class ProjectBootCampApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectBootCampApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/employees").allowedOrigins("http://localhost:3000");
				registry.addMapping("/courses").allowedOrigins("http://localhost:3000");
				registry.addMapping("/onboarding").allowedOrigins("http://localhost:3000");
				registry.addMapping("/employeeByemailid/{emailid}").allowedOrigins("http://localhost:3000");
				registry.addMapping("/NonMandatoryOnboarding").allowedOrigins("http://localhost:3000");

				registry.addMapping("/AddEmployee").allowedOrigins("http://localhost:3000");
				registry.addMapping("/AddCourses").allowedOrigins("http://localhost:3000");
				registry.addMapping("/UpdateCourseById").allowedOrigins("http://localhost:3000");
				registry.addMapping("/UpdateOnboarding").allowedOrigins("http://localhost:3000");
				registry.addMapping("/UpdateEmployeeById").allowedOrigins("http://localhost:3000");
				registry.addMapping("/UpdateCoursesByDesignation").allowedOrigins("http://localhost:3000");
				registry.addMapping("/UpdateTodoByDesignation").allowedOrigins("http://localhost:3000");
				registry.addMapping("/TodosDueDatePassed").allowedOrigins("http://localhost:3000");
				registry.addMapping("/TodosDueDateThisWeek/{empId}").allowedOrigins("http://localhost:3000");
				registry.addMapping("/DoneToDoByEmpId/{empId}").allowedOrigins("http://localhost:3000");
				registry.addMapping("/TodosDueDatePassedEmp/{empId}").allowedOrigins("http://localhost:3000");
				registry.addMapping("/PendingToDoByEmpId/{empId}").allowedOrigins("http://localhost:3000");
				registry.addMapping("/PendingCoursesByEmpId/{empId}").allowedOrigins("http://localhost:3000");
				registry.addMapping("/UpdateEmployeeCourses").allowedOrigins("http://localhost:3000");
				registry.addMapping("/UpdateEmployeeTodo").allowedOrigins("http://localhost:3000");


			}
		};
	}
}