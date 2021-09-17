package com.telstra.model;

import lombok.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.jni.Address;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "Employee")
public class Employee {



	@Id
	private String empId;
    private String profile;
	private String firstName;
	private String lastName;
	private String dob;
	private String emailID;
	private String Address;
	private String designation;
	private String dateOfJoining;
	private Double completeWeightage;
	private Double onboardingProgress;
	private ArrayList<Map<String,Integer>> coursesToComplete;
	private ArrayList<Map<String,String>> taskToComplete;

	public Employee(String firstName, String lastName, String dob, String emailID, String address, String designation, String dateOfJoining,ArrayList<Map<String, Integer>> coursesToComplete, ArrayList<Map<String, String>> gettaskToComplete, Double onboardingProgress) {


		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.emailID = emailID;
		Address = address;
		this.designation = designation;
		this.dateOfJoining = dateOfJoining;
	}

	public Employee(String firstName, String lastName, String dob, String emailID, String address, String designation, String dateOfJoining, Double getcompleteWeightage, ArrayList<Map<String, Integer>> coursesToComplete, ArrayList<Map<String, String>> gettaskToComplete, Double onboardingProgress) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.emailID = emailID;
		Address = address;
		this.designation = designation;
		this.dateOfJoining = dateOfJoining;
	}

	public Employee(String emailID, String designation, String dateOfJoining) {
		this.emailID = emailID;
		//Address = address;
		this.designation = designation;
		this.dateOfJoining = dateOfJoining;
	}

	public String getempId() {
		return empId;
	}

	public void setempId(String empId) {
		this.empId= empId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailId(String emailID) {
		this.emailID = emailID;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(String dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public Double getcompleteWeightage() {
		return completeWeightage;
	}

	public void setcompleteWeightage(Double completeWeightage) {
		this.completeWeightage = completeWeightage;
	}

	public ArrayList<Map<String,Integer>> getCoursesToComplete() {
		return coursesToComplete;
	}

	public void setCoursesToComplete(ArrayList<Map<String, Integer>> coursesToComplete) {
		this.coursesToComplete = coursesToComplete;
	}

	public ArrayList<Map<String, String>> gettaskToComplete() {
		return taskToComplete;
	}

	public void settaskToComplete(ArrayList<Map<String, String>> taskToComplete) {
		this.taskToComplete = taskToComplete;
	}

	public Double getOnboardingProgress() {
		return onboardingProgress;
	}

	public void setOnboardingProgress(Double onboardingProgress) {
		this.onboardingProgress = onboardingProgress;
	}
	public Employee(String firstName, String lastName, String dob, String emailID, String address, String designation, String dateOfJoining  ) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.emailID = emailID;
		Address = address;
		this.designation = designation;
		this.dateOfJoining = dateOfJoining;

	}
	public Employee(String empId,String firstName, String lastName, String dob, String emailID, String address, String designation, String dateOfJoining  ) {
		super();
		this.empId= empId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.emailID = emailID;
		Address = address;
		this.designation = designation;
		this.dateOfJoining = dateOfJoining;

	}


	public void addEmployeeCourse(Map<String, Integer> course) {

		try{
			if(this.getCoursesToComplete()==null || this.getCoursesToComplete().isEmpty())
			{
				ArrayList<Map<String,Integer>> list=new ArrayList<Map<String,Integer>>();
				list.add(course);
				this.setCoursesToComplete(list);
			}
			else
				this.coursesToComplete.add(course);
				System.out.println(this.empId);
				System.out.println(course);
				System.out.println(this.coursesToComplete);
		}
		catch (Exception e)
		{
			System.out.println("&&&&&&&&&&&&&&&"+e);
		}

	}
	public void addEmployeeTask(Map<String,String> task) {
		try{
			if(this.gettaskToComplete()==null || this.gettaskToComplete().isEmpty())
			{
				ArrayList<Map<String,String>> list=new ArrayList<Map<String,String>>();
				list.add(task);

				this.settaskToComplete(list);

			}
			else
				this.taskToComplete.add(task);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}

	}
	public Employee(String empId,String firstName, String lastName, String dob, String emailID, String address, String designation, String dateOfJoining ,Double completeWeightage,ArrayList<Map<String, Integer>> coursesToComplete,ArrayList<Map<String,String>> taskToComplete,Double onboardingProgress ) {
		super();
		this.empId= empId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.emailID = emailID;
		Address = address;
		this.designation = designation;
		this.dateOfJoining = dateOfJoining;
		this.completeWeightage = completeWeightage;
		this.coursesToComplete = coursesToComplete;
		this.taskToComplete=taskToComplete;
		this.onboardingProgress=onboardingProgress;
	}
	public Employee() {
	}

	public void setCourseLevelProgress(ArrayList<Map<String, Integer>> coursesToComplete,String courseId, Integer completion){
		for(int i =0;i< coursesToComplete.size();i++){
			System.out.println(courseId);
			System.out.println(coursesToComplete.get(i).get("courseId").toString());

			if(coursesToComplete.get(i).get("courseId").toString().equals(courseId)){

				Integer b= coursesToComplete.get(i).replace("completion",completion);
				System.out.println(completion+" "+b);
				this.coursesToComplete = coursesToComplete;
				System.out.println(coursesToComplete);
				return;
			}
		}
	}
	public void setTodoProgress(ArrayList<Map<String, String>> taskToComplete,String toDoId, String todoCompletion){
		for(int i =0;i< taskToComplete.size();i++){
			System.out.println(toDoId);
			System.out.println(taskToComplete.get(i).get("toDoId").toString());

			if(taskToComplete.get(i).get("toDoId").toString().equals(toDoId)){

				String b= taskToComplete.get(i).replace("todoCompletion",todoCompletion);
				System.out.println(todoCompletion+" "+b);
				this.taskToComplete = taskToComplete;
				System.out.println(taskToComplete);
				return;
			}
		}
	}
	@Override
	public String toString() {
		return "Employee{" +
				"empId='" + empId + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", dob=" + dob +
				", emailId='" + emailID + '\'' +
				", Address='" + Address + '\'' +
				", designation='" + designation + '\'' +
				", dateOfJoining=" + dateOfJoining +
				", completeWeight=" + completeWeightage +
				", coursesToComplete=" + coursesToComplete +
				", taskToComplete=" + taskToComplete +
				",onboardingProgress=" + onboardingProgress +
				'}';
	}


}