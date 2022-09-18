package com.example.week6.repository;

import com.example.week6.pojo.Wizard;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WizardRepository extends MongoRepository<Wizard, String> {

}
