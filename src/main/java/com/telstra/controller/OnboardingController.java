package com.telstra.controller;

import com.telstra.model.Course;
import com.telstra.model.Employee;
import com.telstra.model.Onboarding;
import com.telstra.repository.EmployeeRepository;
import com.telstra.repository.OnboardingRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Getter
@Setter
class ObjectTodo{
    ArrayList<String> toDoId;
    String designation;
}

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api")
public class OnboardingController {


    @Autowired
    OnboardingRepository onboardingRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/onboarding")
    public ResponseEntity<List<Onboarding>> getAlltodos() {
        try {
            List<Onboarding> onboarding = new ArrayList<Onboarding>();

            onboardingRepository.findAll().forEach(onboarding::add);


            if (onboarding.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(onboarding, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Exception in getAll "+e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/MandatoryOnboarding")
    public ResponseEntity<List<Onboarding>> getMandatorytodos() {
        try {

            List<Onboarding> list= onboardingRepository.findAll();
            List<Onboarding> mandatoryList = new ArrayList<Onboarding>();
            for(int i=0;i< list.size();i++)
            {
                if(list.get(i).getMandatory())
                {
                    mandatoryList.add(list.get(i));
                }
            }

            if (mandatoryList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(mandatoryList, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("getMandatorytodos : "+e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/NonMandatoryOnboarding")
    public ResponseEntity<List<Onboarding>> getNonMandatorytodos() {
        try {

            List<Onboarding> list= onboardingRepository.findAll();
            List<Onboarding> NonmandatoryList = new ArrayList<Onboarding>();
            for(int i=0;i< list.size();i++)
            {
                if(!list.get(i).getMandatory())
                {
                    NonmandatoryList.add(list.get(i));
                }
            }

            if (NonmandatoryList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(NonmandatoryList, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("In getNonMandatorytodos "+e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/onboardingbyId")
    public ResponseEntity<Onboarding> getTodoById(@RequestBody Onboarding onboarding) {
        String toDoId = onboarding.getToDoId();
        Optional<Onboarding> onboardingData = onboardingRepository.findByToDoId(toDoId);

        if (onboardingData.isPresent()) {
            return new ResponseEntity<>(onboardingData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/onboarding")
    public Onboarding createToDo(@RequestBody Onboarding onboarding) {
        try {
            Onboarding _onboarding = onboardingRepository.save(new Onboarding(onboarding.getToDoId(),onboarding.getToDo(),onboarding.getEstDateOfCompletion(),onboarding.getSummary()/*,onboarding.getDesignation()*/,onboarding.getMandatory()));
            String toDoId = _onboarding.getToDoId();
            if(onboarding.getMandatory())
            {
                ArrayList<String>list =new ArrayList<String>();
                list.add("NA");
                _onboarding.setDesignation(list);
                List<Employee> employees = employeeRepository.findAll();
                for (Employee employee : employees) {

                    String completedStatus="false";

                    Map<String,String > taskOfEmp = new HashMap<String, String>();
                    taskOfEmp.put("toDoId",toDoId);
                    taskOfEmp.put("todoCompletion", completedStatus);


                    Optional<Employee> employeeData = employeeRepository.findByempId(employee.getempId());
                    if (employeeData.isPresent()) {

                        Employee _employee = employeeData.get();

                        _employee.addEmployeeTask(taskOfEmp);
                        employeeRepository.save(_employee);

                    }

                }

            }
            else{
                ArrayList<String>list =new ArrayList<String>();
                list.add("NA");
                _onboarding.setDesignation(list);
              /*  System.out.println("trunali "+list);
                System.out.println("here are my designations ****"+_onboarding.getDesignation());

                System.out.println("course is here"+_onboarding);*/
                onboardingRepository.deleteByToDoId(onboarding.getToDoId());
                return (onboardingRepository.save(_onboarding));

            }

            onboardingRepository.deleteByToDoId(onboarding.getToDoId());
            return (onboardingRepository.save(_onboarding));
        } catch (Exception e) {
            System.out.println("In createToDo: "+ e);
            return new Onboarding();
        }
    }


    @PutMapping("/UpdateOnboarding")
    public Onboarding updateToDo(@RequestBody Onboarding onboarding) {

        try{
        String toDoId = onboarding.getToDoId();
        Optional<Onboarding> onboardingData = onboardingRepository.findByToDoId(toDoId);

        if (onboardingData.isPresent()) {

            Onboarding _onboarding = onboardingData.get();
            if(onboarding.getToDo()!=null)
            {
                _onboarding.setToDo(onboarding.getToDo());
            }
            if(onboarding.getEstDateOfCompletion()!=null)
            {
                _onboarding.setEstDateOfCompletion(onboarding.getEstDateOfCompletion());
            }
            if(onboarding.getSummary()!=null)
            {
                _onboarding.setSummary(onboarding.getSummary());
            }
                _onboarding.setMandatory(_onboarding.getMandatory());



            if (_onboarding.getMandatory()) {
                 System.out.println("its mandatory todo");
            } else {
                if (onboarding.getDesignation().size() > _onboarding.getDesignation().size()) {

                    ArrayList<String> list = new ArrayList<String>();
                    list = onboarding.getDesignation();

                    for (int i = 0; i < onboarding.getDesignation().size(); i++) {
                        if (!_onboarding.getDesignation().contains(onboarding.getDesignation().get(i))) {

                            String designation = onboarding.getDesignation().get(i);

                            List<Employee> employees = new ArrayList<Employee>();
                            employeeRepository.findBydesignation(designation).forEach(employees::add);


                            for (Employee employee : employees) {

                                String completedStatus = "false";

                                Map<String, String> taskOfEmp = new HashMap<String, String>();
                                taskOfEmp.put("toDoId", onboarding.getToDoId());
                                taskOfEmp.put("todoCompletion", completedStatus);


                                Optional<Employee> employeeData = employeeRepository.findByempId(employee.getempId());
                                if (employeeData.isPresent()) {

                                    Employee _employee = employeeData.get();

                                    _employee.addEmployeeTask(taskOfEmp);
                                    employeeRepository.save(_employee);
                                }
                            }


                        }
                    }
                    _onboarding.setDesignation(onboarding.getDesignation());
                }

            }
            onboardingRepository.deleteByToDoId(toDoId);
            return onboardingRepository.save(_onboarding);
        } else {
            Onboarding _onboarding = onboardingRepository.save(new Onboarding(onboarding.getToDoId(), onboarding.getToDo(), onboarding.getEstDateOfCompletion(), onboarding.getSummary(), onboarding.getDesignation()));
            return _onboarding;
        }

    }
        catch (Exception e)
        {
            System.out.println("exception updateToDo "+e);
            return new Onboarding();
        }
    }

    @DeleteMapping("/DeleteOnboardingById")
    public ResponseEntity<HttpStatus> deleteToDo( @RequestBody Onboarding onboarding) {

        String toDoId = onboarding.getToDoId();
        try {
            onboardingRepository.deleteByToDoId(toDoId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //
    @DeleteMapping("/DeleteOnboardings")
    public ResponseEntity<HttpStatus> deleteAllToDos() {
        try {
            onboardingRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Autowired
    EmployeeController employeeController;
    @PutMapping("/UpdateTodoByDesignation")
    public String UpdateTodoByDesignation(@RequestBody ObjectTodo objectTodo) {

        ArrayList<String> TodoIdList = objectTodo.getToDoId();
        String designation = objectTodo.getDesignation();

        try {
            for (int i = 0; i < TodoIdList.size(); i++) {
                Optional<Onboarding> onboardingData = onboardingRepository.findByToDoId(TodoIdList.get(i));

                Onboarding _onboarding = onboardingData.get();

                ArrayList<String>list =new ArrayList<String>();
                list = _onboarding.getDesignation();


                if (list.contains(designation)) {
                    System.out.println("Course is already assigned to designation");
                } else {
                    _onboarding.addDesignationToTaskToComplete(designation);
                    List<Employee> l1= employeeRepository.findBydesignation(designation);

                    for (Employee employee : l1) {
                        ArrayList<Map<String,String>> m1=new ArrayList<Map<String,String>>();

                        Map<String, String> TodoOfEmp = new HashMap<String, String>();

                        TodoOfEmp.put("toDoId", TodoIdList.get(i));
                        TodoOfEmp.put("todoCompletion", "false");


                        Optional<Employee> employeeData = employeeRepository.findByempId(employee.getempId());

                        if (employeeData.isPresent()) {

                            Employee _employee = employeeData.get();

                            try{
                                _employee.addEmployeeTask(TodoOfEmp);
                                _employee.setcompleteWeightage(employeeController.averageCompletion(_employee.getempId()));
                            }
                            catch (Exception e)
                            {
                                System.out.println("In UpdateTodoByDesignation : "+e);
                            }

                            employeeRepository.deleteByempId(_employee.getempId());
                            employeeRepository.save(_employee);
                        }
                        else {
                            System.out.println("employee not found");
                        }

                    }

                    onboardingRepository.deleteByToDoId((TodoIdList.get(i)));
                    onboardingRepository.save(_onboarding);

                }


            }
            return "updated successfullly";

        } catch (Exception e) {
            System.out.println("In UpdateTodoByDesignation : "+e);
            return "throw exception";
        }

    }

    @GetMapping("/TodosByDesignation")
    public ArrayList<Onboarding> getTodosByDesignation(@RequestBody Employee employee) {


        String designation= employee.getDesignation();
        System.out.println(designation);

        try {

            ArrayList<Onboarding> onboardings = new ArrayList<Onboarding>();

            List<Onboarding> todoArrayList = getAlltodos().getBody();

            for(int i=0;i<todoArrayList.size();i++)
            {
                if(todoArrayList.get(i).getMandatory())
                {
                    //continue;
                    onboardings.add(todoArrayList.get(i));
                }
                else if(todoArrayList.get(i).getDesignation().contains(designation))
                {
                    onboardings.add(todoArrayList.get(i));

                }
            }

            if (onboardings.isEmpty()) {
                return new ArrayList<>();
            }
            return (onboardings);
        } catch (Exception e) {
            System.out.println("In getTodosByDesignation: "+e);
            return new ArrayList<>();
        }

    }

}