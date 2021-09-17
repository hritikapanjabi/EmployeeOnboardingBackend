package com.telstra.repository;

import com.telstra.model.Employee;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

    Optional<Employee> findByempId(String empId);

    Optional<Employee> findByemailID(String emailID);
    void deleteByempId(String empId);

    //<S extends Employee> Optional<S> findOne(Example<S> example);

    ArrayList<Employee> findBydesignation(String designation);
    //public String generateEmpId(String emailID);

}
