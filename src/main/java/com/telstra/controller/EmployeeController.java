package com.telstra.controller;


import com.telstra.Service.EmployeeService;
//import com.telstra.model.Counter;
import com.telstra.model.Course;
import com.telstra.model.Employee;
import com.telstra.model.Onboarding;
import com.telstra.repository.CourseRepository;
import com.telstra.repository.EmployeeRepository;
import com.telstra.repository.OnboardingRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;


@Getter
@Setter
class obj{
    String empId, courseName;
    Integer completion;
}
@Getter
@Setter
class objTODO{
    String empId,toDo ;
    String todoCompletion;

}
@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@EnableWebMvc
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    OnboardingController onboardingController;

    @Autowired
    OnboardingRepository onboardingRepository;

    public static boolean isDateInCurrentWeek(Date date) {
        Calendar currentCalendar = Calendar.getInstance();
        int week = currentCalendar.get(Calendar.WEEK_OF_YEAR);
        System.out.println(week);
        int year = currentCalendar.get(Calendar.YEAR);
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(date);
        int targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR);
        int targetYear = targetCalendar.get(Calendar.YEAR);
        return week == targetWeek && year == targetYear;
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllCoursesDetails(Model model) {
        ResponseEntity<List<Employee>> emp = employeeService.fetchEmployeeList();
        model.addAttribute("Employee", emp);
        return emp;
    }


    @GetMapping("/employeeById")
    public ResponseEntity<Employee> getEmployeeById(@RequestBody Employee employee) {


        String empId= employee.getempId();
        Optional<Employee> employeeData = employeeRepository.findByempId(empId);

        if (employeeData.isPresent()) {
            return new ResponseEntity<>(employeeData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/AddEmployee")
    public Employee createEmployee(@RequestBody Employee employee) {
        try {

            // List<Counter> counter=counterRepository.findAll();
            Employee _employee = employeeRepository.save(new Employee(employee.getEmailID(),
                    employee.getDesignation(),employee.getDateOfJoining()));



            _employee.setCoursesToComplete(addCourses(_employee));

            _employee.settaskToComplete(addTodos(employee));

            _employee.setcompleteWeightage(averageCompletion(employee.getempId()));

            _employee.setOnboardingProgress(OnBoardingProgress(employee.getempId()));

            return (employeeRepository.save(_employee));
        }
        catch (Exception e)
        {
            System.out.println(e);
            // return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            return new Employee();
        }
    }

    @Autowired
    CourseController courseController;

    @GetMapping("/employeeByemailid/{emailid}")
    public ResponseEntity<Employee> getByEmployeeEmailId(@PathVariable String emailid) {


        //String empId= employee.getempId();
//        String emailid= employee.getEmailID();
        Optional<Employee> employeeData = employeeRepository.findByemailID(emailid);

        if (employeeData.isPresent()) {
            return new ResponseEntity<>(employeeData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    @PutMapping("/UpdateEmployeeById")
    public Employee updateEmployee(@RequestBody Employee employee) {


        String empId= employee.getempId();
        Optional<Employee> employeeData = employeeRepository.findByempId(empId);

        if (employeeData.isPresent()) {
            Employee _employee = employeeData.get();

            if(employee.getFirstName()!=null)
            {
                _employee.setFirstName(employee.getFirstName());
            }
            if(employee.getLastName()!=null)
            {
                _employee.setLastName(employee.getLastName());
            }
            if(employee.getDateOfJoining()!=null)
            {
                _employee.setDateOfJoining(employee.getDateOfJoining());
            }
            if(employee.getAddress()!=null)
            {
                _employee.setAddress(employee.getAddress());
            }
            if(employee.getDob()!=null)
            {
                _employee.setDob(employee.getDob());
            }
           if(employee.getEmailID()!=null)
           {
               _employee.setEmailId(employee.getEmailID());
           }
           if( employee.getDesignation()!=null && !employee.getDesignation().equalsIgnoreCase(_employee.getDesignation()))
               {
                   _employee.setDesignation(employee.getDesignation());
                   _employee.settaskToComplete(UpdateTodoByDesignation(employee));
                   _employee.setCoursesToComplete(UpdateCourseByDesignation(employee));

           }

            employeeRepository.save(_employee);
            _employee.setcompleteWeightage(averageCompletion(empId));
            _employee.setOnboardingProgress(OnBoardingProgress(empId));
            employeeRepository.deleteByempId(empId);
            return (employeeRepository.save(_employee));
        } else {
            return new Employee();
        }
    }

    @GetMapping("/test")
    public ArrayList<Map<String,Integer>> addCourses(Employee employee)
    {

        System.out.println();
        try{
            ArrayList<Map<String,Integer>> l1=new ArrayList<Map<String,Integer>>();

            List<Course> courseList=courseController.getCoursesByDesignation(employee);

            for(int i=0;i<courseList.size();i++)
            {
                Map<String,Integer> m1=new HashMap<String,Integer>();
                m1.put("courseId", Integer.parseInt(courseList.get(i).getCourseId()));
                m1.put("completion", 0);
                l1.add(m1);
            }

            return  l1;
        }
        catch (Exception e)
        {
            System.out.println("exception: in addcourses" + e);
            return null;
        }


    }
    @GetMapping("/test1")
    public ArrayList<Map<String,String>> addTodos(Employee employee)
    {

        String designation= employee.getDesignation();
        try{
            ArrayList<Map<String,String>> l1=new ArrayList<Map<String,String>>();
            List<Onboarding> todoList= onboardingController.getTodosByDesignation(employee);

            for(int i=0;i<todoList.size();i++)
            {
                Map<String,String> m1=new HashMap<String,String>();
                m1.put("toDoId", todoList.get(i).getToDoId());
                m1.put("todoCompletion", "false");
                l1.add(m1);
            }

            System.out.println(l1);
            return  l1;
        }
        catch (Exception e)
        {
            System.out.println("exception: ########3 in add todos" + e);
            return null;
        }


    }

    @GetMapping("/AllcoursesForEmployee")
    public ArrayList<Map<String,Integer> >GetAllcoursesForEmployee(@RequestBody Employee emp)
    {


        try {
            String empId = emp.getempId();
            Optional<Employee> employeeData = employeeRepository.findByempId(empId);

            if (employeeData.isPresent()) {
                Employee employee = employeeData.get();

                ArrayList<Map<String, Integer>> pending = new ArrayList<Map<String,Integer>>();

                System.out.println(employee.getCoursesToComplete());

                for (int i = 0; i < employee.getCoursesToComplete().size(); i++) {


                        Map<String, Integer> m1 = new HashMap<String, Integer>();
                        m1.put("courseId", employee.getCoursesToComplete().get(i).get("courseId"));
                        m1.put("completion", employee.getCoursesToComplete().get(i).get("completion"));
                        pending.add(m1);


                }
                //System.out.println(pending);
                return pending;
            }
            else {
                return null;
            }
        }

        catch (Exception e)
        {
            System.out.println("In GetPendingCoursesByEmpId "+e);
            return null;
        }
    }

    @GetMapping("/PendingToDoByEmpId/{empId}")
    public ArrayList<Map<String,String>> GetToDoPendingByEmpId(@PathVariable String empId) {


        try {

            Optional<Employee> employeeData = employeeRepository.findByempId(empId);

            if (employeeData.isPresent()) {
                Employee employee = employeeData.get();

                ArrayList<Map<String, String>> pending = new ArrayList<Map<String, String>>();



                for (int i = 0; i < employee.gettaskToComplete().size(); i++) {
                    if (employee.gettaskToComplete().get(i).get("todoCompletion").equalsIgnoreCase("false")) {
                        Map<String, String> m1 = new HashMap<String, String>();
                        String toDoId = employee.gettaskToComplete().get(i).get("toDoId");
                        Optional<Onboarding> onboardingData
                                = onboardingRepository.findByToDoId(toDoId);
                        String toDo = onboardingData.get().getToDo();
                        m1.put("toDo", toDo);
                        m1.put("todoCompletion", employee.gettaskToComplete().get(i).get("todoCompletion"));
                        pending.add(m1);
                    }
                }


                return pending;
            }
            else {
                return null;
            }
        }

        catch (Exception e)
        {
            System.out.println(" $$$$$$$$$ "+e);
            return null;
        }
    }

    @GetMapping("/DoneToDoByEmpId/{empId}")
    public ArrayList<Onboarding> GetToDoDoneByEmpId(@PathVariable String empId) {


        try {

            Optional<Employee> employeeData = employeeRepository.findByempId(empId);

            if (employeeData.isPresent()) {
                Employee employee = employeeData.get();

                ArrayList<Onboarding> done = new ArrayList<Onboarding>();



                for (int i = 0; i < employee.gettaskToComplete().size(); i++) {
                    if (employee.gettaskToComplete().get(i).get("todoCompletion").equalsIgnoreCase("true")) {

                        String toDoId=employee.gettaskToComplete().get(i).get("toDoId");
                        Optional<Onboarding> onboardingData
                                = onboardingRepository.findByToDoId(toDoId);
                        done.add(onboardingData.get());
                    }
                }


                return done;
            }
            else {
                return null;
            }
        }

        catch (Exception e)
        {
            System.out.println(" $$$$$$$$$ "+e);
            return null;
        }
    }

    @GetMapping("/PendingCoursesByEmpId/{empId}")
    public ArrayList<Map<String,String> >GetPendingCoursesByEmpId(@PathVariable String empId) {


        try {

            Optional<Employee> employeeData = employeeRepository.findByempId(empId);

            if (employeeData.isPresent()) {
                Employee employee = employeeData.get();

                ArrayList<Map<String, String>> pending = new ArrayList<Map<String,String>>();

                System.out.println(employee.getCoursesToComplete());

                for (int i = 0; i < employee.getCoursesToComplete().size(); i++) {
                    if (employee.getCoursesToComplete().get(i).get("completion")<100) {

                        Map<String, String> m1 = new HashMap<String, String>();
                        String courseId=employee.getCoursesToComplete().get(i).get("courseId").toString();
                        System.out.println(courseId);
                        Optional<Course> courseData = courseRepository.findBycourseId(courseId);
                        String coursename = courseData.get().getCourseName();
                        System.out.println(coursename);
                        m1.put("courseName", coursename);
                        m1.put("completion", employee.getCoursesToComplete().get(i).get("completion").toString());
                        pending.add(m1);

                    }
                }
                //System.out.println(pending);
                return pending;
            }
            else {
                return null;
            }
        }

        catch (Exception e)
        {
            System.out.println("In GetPendingCoursesByEmpId "+e);
            return null;
        }
    }

    @DeleteMapping("/DeleteEmployeeById")
    public ResponseEntity<HttpStatus> deleteEmployee(@RequestBody Employee employee) {
        String id= employee.getempId();
        try {
            employeeRepository.deleteByempId(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/DeleteEmployees")
    public ResponseEntity<HttpStatus> deleteAllEmployees() {
        try {
            employeeRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PutMapping("/UpdateEmployeeCourses")
    public Employee updateEmployeeCourse(@RequestBody obj obj) {
        String empId=obj.getEmpId();
        String courseName=obj.getCourseName();
        Integer completion=obj.getCompletion();

        try
        {
            Optional<Employee> employeeData = employeeRepository.findByempId(empId);

            if (employeeData.isPresent()) {
                Employee _employee = employeeData.get();
                Optional<Course> c=courseRepository.findBycourseName(courseName);
                String courseId = c.get().getCourseId();
                _employee.setCourseLevelProgress(_employee.getCoursesToComplete(),courseId,completion);
                employeeRepository.save(_employee);
                _employee.setcompleteWeightage(averageCompletion(empId));
                return (employeeRepository.save(_employee));
            } else {
                return new Employee();
            }
        }
        catch (Exception e)
        {
            System.out.println("in updateEmployeeCourse :"+e);
            return new Employee();
        }
    }

    @PutMapping("/UpdateEmployeeTodo")
    public Employee updateEmployeeTODO(@RequestBody objTODO objTodo1) {
        String empId=objTodo1.getEmpId();
        String todo=objTodo1.getToDo();
        String TodoCompletion=objTodo1.getTodoCompletion();
        System.out.println(empId+" "+todo+" "+TodoCompletion);
        try
        {
            Optional<Employee> employeeData = employeeRepository.findByempId(empId);

            if (employeeData.isPresent()) {
                Employee _employee = employeeData.get();
                Optional<Onboarding> o=onboardingRepository.findByToDo(todo);
                String todoId= o.get().getToDoId();
                _employee.setTodoProgress(_employee.gettaskToComplete(),todoId,TodoCompletion);
                employeeRepository.save(_employee);
                _employee.setOnboardingProgress(OnBoardingProgress(empId));
                return (employeeRepository.save(_employee));
            } else {
                return new Employee();
            }
        }
        catch (Exception e)
        {
            System.out.println("In updateEmployeeTODO : "+e);
            return new Employee();
        }
    }
    @GetMapping("/employeesbydesignation")
    public ResponseEntity<List<Employee>> getEmployeeByDesignation(@RequestBody  Employee employee) {


        String designation= employee.getDesignation();
        System.out.println(designation);

        try {
            ArrayList<Employee> employees = new ArrayList<Employee>();

            employeeRepository.findBydesignation(designation).forEach(employees::add);

            if (employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }



   // @GetMapping("/employees/avg/{id}")
    //@GetMapping ("/getAvgCompletion/{id}")
    public double averageCompletion(@PathVariable("id") String empId){

        int total = 0;
        double avg=0;
        int sum=0;
        try{

            Optional<Employee> employeeData = employeeRepository.findByempId(empId);

            ArrayList<Map<String,Integer>> list= employeeData.get().getCoursesToComplete();
            //System.out.println("hii there :"+list);

            for(int i = 0; i<list.size(); i++){
                Map<String,Integer> maps=list.get(i);
                System.out.println("hii 2" + maps);
                String courseId= String.valueOf(maps.get("courseId"));
                //System.out.println("hii 3" + courseId);

                Optional<Course> courseData=courseRepository.findBycourseId(courseId);
                //System.out.println("hii 4" + courseData);

                Course _course = courseData.get();
               // System.out.println("hii 4" + _course);
                double courseWeightage= _course.getcourseWeightage();
                //System.out.println("hii 5" + courseWeightage);
                sum=sum+ (int)courseWeightage;
                total= total + maps.get("completion")* (int)courseWeightage;
            }
            avg =(double) (total /sum);
            DecimalFormat numberFormat = new DecimalFormat("#.00");
            avg=Double.parseDouble(numberFormat.format(avg));
            System.out.println(avg);
            return avg;
        }
        catch(Exception e){
            System.out.println("exception handled : "+e);
            return 0;
        }



    }
    @GetMapping("/OnBoardingProgressByEmpId")
    public double OnBoardingProgress(String empId)
    {
        double progress=0;
        try{


            Optional<Employee> employeeData = employeeRepository.findByempId(empId);
            ArrayList<Map<String,String>> todolist = employeeData.get().gettaskToComplete();
            int total=0;
            for(int i=0;i<todolist.size();i++)
            {
                Map<String,String> maps=todolist.get(i);
                if(maps.get("todoCompletion").equalsIgnoreCase("true"))
                {
                    total=total+1;
                }
            }

            progress=((double) total/todolist.size())*100;

            return progress;
        }
        catch (Exception e)
        {
            return 0;
        }

    }

    @GetMapping("/getTodoProgress")
    public double getTodoProgress(@RequestBody Employee employee)
    {
        Optional<Employee> employeeData = employeeRepository.findByempId(employee.getempId());
        Employee emp = employeeData.get();
        return emp.getOnboardingProgress();
    }

    @GetMapping("/getCourseProgress")
    public double getCourseProgress(@RequestBody Employee employee)
    {
        Optional<Employee> employeeData = employeeRepository.findByempId(employee.getempId());
        Employee emp = employeeData.get();
        return emp.getcompleteWeightage();
    }

@GetMapping("/updatedCoursesByDeignnation")
    public ArrayList<Map<String,Integer>> UpdateCourseByDesignation(@RequestBody Employee employee)
    {
        ArrayList<Map<String,Integer>> mp= new ArrayList<>();
        String designation = employee.getDesignation();
        Optional<Employee> employeeData = employeeRepository.findByempId(employee.getempId());
        Employee emp = employeeData.get();

        ArrayList<Course> courselist = new ArrayList<>();
        courselist = courseController.getCoursesByDesignation(employee);
        ArrayList<String> list1 = new ArrayList<>();
        System.out.println("courses to complete for degn2" + courselist);
        for(int i=0;i<courselist.size();i++)
        {
            list1.add(courselist.get(i).getCourseId());

        }
        System.out.println("courses to complete for degn2 list1 " + list1);

        ArrayList<Map<String,Integer>>  empscourses =  new ArrayList<>();
        empscourses=emp.getCoursesToComplete();
        System.out.println("courses to complete for degn1" + empscourses);

        ArrayList<String> list2 = new ArrayList<>();

        for(int i=0;i<empscourses.size();i++)
        {
            list2.add(String.valueOf(empscourses.get(i).get("courseId")));

        }
        System.out.println("courses to complete for degn1 list2 : " + list2);

        for(int i=0;i<list2.size();i++)
        {
            if(list1.contains(list2.get(i)))
            {
                mp.add(empscourses.get(i));
            }
            else{
                continue;
            }
        }
        for(int i=0;i<list1.size();i++)
        {
            if(list2.contains(list1.get(i)))
            {
                continue;
            }
            else{
                Map<String,Integer> m1=new HashMap<String,Integer>();
                m1.put("courseId", Integer.parseInt(courselist.get(i).getCourseId()));
                m1.put("completion", 0);
                mp.add(m1);
                System.out.println("het add this "+m1);

            }
        }
        System.out.println("FINAL courses to complete " + mp);
        //System.out.println(mp);
        return mp;

    }

    @GetMapping("/UpdateTodoByDesignation")
    public ArrayList<Map<String,String>> UpdateTodoByDesignation(@RequestBody Employee employee)
    {
        Optional<Employee> employeeData = employeeRepository.findByempId(employee.getempId());
        Employee _employee = employeeData.get();


        String designation = employee.getDesignation();
        System.out.println("hii there");

        ArrayList<Map<String,String>> mp= new ArrayList<Map<String,String>>();

        ArrayList<Onboarding> list = new ArrayList<>();
        list = onboardingController.getTodosByDesignation(employee);
        ArrayList<String> listids=new ArrayList<String>();
        System.out.println("todo for degn2"+list);


        for(int i=0;i<list.size();i++)
        {
            listids.add(list.get(i).getToDoId());
        }


        ArrayList<Map<String,String>>empTodoList = new ArrayList<Map<String,String>>();
        empTodoList =_employee.gettaskToComplete();
        System.out.println("todo for desgn 1"+empTodoList);

        for(int i=0;i<empTodoList.size();i++)
        {

            if(listids.contains(empTodoList.get(i).get("toDoId")))
            {
                mp.add(empTodoList.get(i));
            }
            else{
                continue;
            }

        }
        System.out.println("todolist after deletding old todo and before upadating" +mp);


        ArrayList<String> l1 =new ArrayList<String>();
        for(int i=0;i<mp.size();i++)
        {
            l1.add(mp.get(i).get("toDoId"));
        }

        for(int i=0;i<listids.size();i++)
        {

            if(l1.contains(listids.get(i)))
            {
                continue;
            }
            else{
                Map<String,String> m1=new HashMap<String,String>();
                m1.put("toDoId", listids.get(i));
                m1.put("todoCompletion", "false");
                mp.add(m1);

            }
        }
        System.out.println("final todo : "+mp);
        return  mp;
    }


    @GetMapping("/TodosDueDatePassed")
    public ArrayList<Map<String,ArrayList<Onboarding>>> getTodosDueDatePassedForAll(){
        ArrayList<Map<String,ArrayList<Onboarding>>> getTodosDueDatePassedForAll= new ArrayList<Map<String,ArrayList<Onboarding>>>();
        List<Employee> AllEmployees= employeeRepository.findAll();
        for(int i=0;i<AllEmployees.size();i++){
            String empId = AllEmployees.get(i).getempId();
            ArrayList<Onboarding> o= getTodosDueDatePassed(empId);
            Map<String,ArrayList<Onboarding>> m= new HashMap<String,ArrayList<Onboarding>>();
            m.put(empId,o);
            getTodosDueDatePassedForAll.add(m);
        }
        return getTodosDueDatePassedForAll;
    }
    @GetMapping("/TodosDueDatePassedEmp/{empId}")
    public ArrayList<Onboarding> getTodosDueDatePassedEmp(@PathVariable String empId){

        ArrayList<Onboarding> o= getTodosDueDatePassed(empId);
        return o;
    }

    public ArrayList<Onboarding> getTodosDueDatePassed(String empId){
        ArrayList<Onboarding> TodosDueDatePassed = new ArrayList<Onboarding>();
        Optional<Employee> employeeData = employeeRepository.findByempId(empId);
        Employee _employee = employeeData.get();
        ArrayList<Map<String,String>>empTodoList = new ArrayList<Map<String,String>>();
        empTodoList =_employee.gettaskToComplete();
        for(int i=0;i<empTodoList.size();i++)
        {
            Optional<Onboarding> onboardingData
                    = onboardingRepository.findByToDoId(empTodoList.get(i).get("toDoId"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date estdate = null;
            try {
                estdate = sdf.parse(onboardingData.get().getEstDateOfCompletion());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            TimeZone tz = TimeZone.getDefault();
            ZoneId zonedId = ZoneId.of( tz.getID());
            ZonedDateTime zdt = ZonedDateTime.now( zonedId );
            Date today = java.util.Date.from( zdt.toInstant() );
            if(estdate.compareTo(today)<0 && empTodoList.get(i).get("todoCompletion").equalsIgnoreCase("false"))
            {
                TodosDueDatePassed.add(onboardingData.get());
            }
            else{
                continue;
            }
        }
        return TodosDueDatePassed;

    }



    @GetMapping("/TodosDueDateThisWeek/{empId}")
    public ArrayList<Onboarding> getTodosDueDateThisWeek(@PathVariable String empId){
        System.out.println(empId);
        ArrayList<Onboarding> TodosDueDateThisWeek = new ArrayList<Onboarding>();
        Optional<Employee> employeeData = employeeRepository.findByempId(empId);
        Employee _employee = employeeData.get();
        ArrayList<Map<String,String>>empTodoList = new ArrayList<Map<String,String>>();
        empTodoList =_employee.gettaskToComplete();
        for(int i=0;i<empTodoList.size();i++)
        {
            Optional<Onboarding> onboardingData
                    = onboardingRepository.findByToDoId(empTodoList.get(i).get("toDoId"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date estdate = null;
            try {
                estdate = sdf.parse(onboardingData.get().getEstDateOfCompletion());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            TimeZone tz = TimeZone.getDefault();
            ZoneId zonedId = ZoneId.of( tz.getID());
            ZonedDateTime zdt = ZonedDateTime.now( zonedId );
            Date today = java.util.Date.from( zdt.toInstant() );

            if(estdate.compareTo(today)>0 && isDateInCurrentWeek(estdate) && empTodoList.get(i).get("todoCompletion").equalsIgnoreCase("false"))
            {

                TodosDueDateThisWeek.add(onboardingData.get());
            }
            else{
                continue;
            }
        }
        return TodosDueDateThisWeek;

    }

}

