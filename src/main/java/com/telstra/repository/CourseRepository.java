package com.telstra.repository;

import com.telstra.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CourseRepository extends MongoRepository<Course, String> {
    Optional<Course> findBycourseId(String courseId);
    Optional<Course> findBycourseName(String courseName);

    void deleteByCourseId(String empId);

    void deleteBycourseId(String courseId);

    Optional<Course> findBydesignation(String designation);
}
