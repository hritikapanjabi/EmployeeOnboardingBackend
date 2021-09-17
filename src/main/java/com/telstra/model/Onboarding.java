package com.telstra.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;


@Document(collection = "Onboarding")
public class Onboarding {
    private String toDoId;
    private String toDo;
    private String estDateOfCompletion;
    private String summary;
    private ArrayList<String> designation;
    private Boolean mandatory;


    public Onboarding(String toDoId, String toDo, String estDateOfCompletion, String summary,
                      ArrayList<String> designation,Boolean mandatory) {
        super();
        this.toDoId = toDoId;
        this.toDo = toDo;
        this.estDateOfCompletion = estDateOfCompletion;
        this.summary = summary;
        this.designation = designation;
        this.mandatory=mandatory;
    }
    public Onboarding(String toDoId, String toDo, String estDateOfCompletion, String summary,
                      ArrayList<String> designation) {
        super();
        this.toDoId = toDoId;
        this.toDo = toDo;
        this.estDateOfCompletion = estDateOfCompletion;
        this.summary = summary;
        this.designation = designation;

    }

    public Onboarding(String toDoId, String toDo, String estDateOfCompletion, String summary, Boolean mandatory) {

        super();
        this.toDoId = toDoId;
        this.toDo = toDo;
        this.estDateOfCompletion = estDateOfCompletion;
        this.summary = summary;
        this.mandatory=mandatory;
    }

    public String getToDoId() {
        return toDoId;
    }

    public void setToDoId(String toDoId) {
        this.toDoId = toDoId;
    }

    public String getToDo() {
        return toDo;
    }

    public void setToDo(String toDo) {
        this.toDo = toDo;
    }

    public String getEstDateOfCompletion() {
        System.out.println(estDateOfCompletion);
        return estDateOfCompletion;

    }

    public Boolean getMandatory() {
        return mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public void setEstDateOfCompletion(String estDateOfCompletion) {
        try {
            this.estDateOfCompletion = estDateOfCompletion;
            System.out.println(this.estDateOfCompletion);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("Exception in set date"+e);
            e.printStackTrace();
        }
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public ArrayList<String> getDesignation() {
        return designation;
    }

    public void setDesignation(ArrayList<String> designation) {
        this.designation = designation;
    }

    public Onboarding() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        return "Onboarding [toDoId=" + toDoId + ", toDo=" + toDo + ", estDateOfCompletion=" + estDateOfCompletion
                + ", summary=" + summary + ", designation=" + designation + ", mandatory=" + mandatory + "]" ;
    }

    public void addDesignationToTaskToComplete(String designation) {

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
            System.out.println("In addDesignationToTaskToComplete :"+e);
        }

    }


}
