package com.telstra.Service;

import com.telstra.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CourseServiceImpl implements CourseService{
    @Autowired
    CourseRepository courseRepository;

}
