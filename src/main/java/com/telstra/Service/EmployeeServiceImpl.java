package com.telstra.Service;

import com.telstra.model.Employee;
import com.telstra.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;
    public EmployeeServiceImpl(EmployeeRepository employeeRepository)
    {
        super();
        this.employeeRepository=employeeRepository;
    }

    @Override
    public ResponseEntity<Employee> saveEmployee( Employee employee)
    {
        try {
            System.out.println(employee);

            Employee _employee = employeeRepository.save(new Employee(employee.getempId(),employee.getFirstName(),employee.getLastName(),employee.getDob(),employee.getEmailID(),
                    employee.getAddress(), employee.getDesignation(),employee.getDateOfJoining()));

            return new ResponseEntity<>(_employee, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Employee> EmployeeDetailsByEmpId(String empId)
    {
        Optional<Employee> employeeData = employeeRepository.findByempId(empId);

        if (employeeData.isPresent()) {
            return new ResponseEntity<>(employeeData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<Employee> >fetchEmployeeList() {
        try {
            List<Employee> employees = new ArrayList<Employee>();

            employeeRepository.findAll().forEach(employees::add);


            if (employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Employee updateEmployeeById(String empId,Employee employee) {

        Optional<Employee> employeeData = employeeRepository.findByempId(empId);

        if (employeeData.isPresent()) {
            Employee _employee = employeeData.get();

            _employee.setFirstName(employee.getFirstName());
            _employee.setLastName(employee.getLastName());
            _employee.setDateOfJoining(employee.getDateOfJoining());
            _employee.setAddress(employee.getAddress());
            _employee.setDesignation(employee.getDesignation());
            _employee.setDob(employee.getDob());
            _employee.setEmailId(employee.getEmailID());
            _employee.setCoursesToComplete(employee.getCoursesToComplete());
            _employee.settaskToComplete(employee.gettaskToComplete());
            employeeRepository.deleteByempId(empId);
            return (employeeRepository.save(_employee));
        } else {
            return new Employee();
        }
    }

   /* public String generateEmpId(String emailID) {

        Employee counter = mongoOperations.findAndModify(query(where("emailID").is(emailID)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                Employee.class);
        return !Objects.isNull(counter) ? counter.getempId() : 1;
    }*/


}
