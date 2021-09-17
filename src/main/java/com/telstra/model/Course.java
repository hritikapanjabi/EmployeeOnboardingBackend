package com.telstra.model;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Courses")
public class Course {

    //@Id
    private String courseId;

    private String courseName;
    private String courseSummary;
    private Double courseWeightage;
    private String courseDueDate;
    private ArrayList<String> designation;

    public Course() {

    }

    public Course(String courseId,String courseName, String courseSummary, Double courseWeightage, ArrayList<String> designation,String courseDueDate) {
        super();
        this.courseId=courseId;
        this.courseName = courseName;
        this.courseSummary = courseSummary;
        this.courseWeightage = courseWeightage;
        this.designation = designation;
        this.courseDueDate=courseDueDate;
    }

    public Course(String courseId, String courseName, String courseSummary, Double courseWeightage, String courseDueDate) {

        super();
        this.courseId=courseId;
        this.courseName = courseName;
        this.courseSummary = courseSummary;
        this.courseWeightage = courseWeightage;
        this.courseDueDate=courseDueDate;
    }

    public String getCourseDueDate() {
        return courseDueDate;
    }

    public void setCourseDueDate(String courseDueDate) {
        this.courseDueDate = courseDueDate;
    }

    public String getCourseId() {
        return courseId;
    }
    public void setCourseId(String courseId) {
        this.courseId=courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseSummary() {
        return courseSummary;
    }

    public Double getcourseWeightage() {
        return courseWeightage;
    }

    public ArrayList<String> getDesignation() {
        return designation;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseSummary(String courseSummary) {
        this.courseSummary = courseSummary;
    }

    public void setcourseWeightage(Double courseWeightage) {
        this.courseWeightage = courseWeightage;
    }

    public void setDesignation(ArrayList<String> designation) {
        this.designation = designation;
    }

    @Override
    public String toString() {
        return "Course [courseId=" + courseId + ", courseName=" + courseName + ", courseSummary=" + courseSummary
                + ", weightage=" + courseWeightage + ", designations=" + designation + ", courseDueDate=" + courseDueDate +"]";
    }

    public void addDesignationToCourse(String designation) {

        try{
            if(this.getDesignation()==null)
            {
                ArrayList<String> list =new ArrayList<String>();
                list.add(designation);
                this.setDesignation(list);
            }
            else if(this.getDesignation().contains("NA"))
            {
                this.designation.add(designation);
                this.designation.remove("NA");
            }
            else
            {
                this.designation.add(designation);
            }

        }
        catch (Exception e)
        {
            System.out.println("In addDesignationToCourse"+e);
        }

    }

}
