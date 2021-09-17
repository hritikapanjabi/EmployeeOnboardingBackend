package com.telstra.repository;


import com.telstra.model.Onboarding;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OnboardingRepository extends MongoRepository<Onboarding, String> {
    Optional<Onboarding> findByToDoId(String toDoId);
    void deleteByToDoId(String toDoId);
    Optional<Onboarding> findByToDo(String toDo);

}