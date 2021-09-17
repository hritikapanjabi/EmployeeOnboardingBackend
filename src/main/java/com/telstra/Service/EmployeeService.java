package com.telstra.Service;

import com.telstra.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface EmployeeService {

    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee);

   public ResponseEntity<Employee> EmployeeDetailsByEmpId(String empId);

    public ResponseEntity<List<Employee> >fetchEmployeeList();

    Employee updateEmployeeById(String empId, Employee employee);
    //public String generateEmpId(String emailID);
}
