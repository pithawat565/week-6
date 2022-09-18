package com.example.week6.repository;

import com.example.week6.pojo.Wizard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WizardService {
    @Autowired
    private WizardRepository repository;


    public WizardService(WizardRepository repository){
        this.repository = repository;
    }

    public List<Wizard> retrieveWizard(){
        return repository.findAll();
    }

    public Wizard addWizard(Wizard wizard){
        return repository.save(wizard);
    }

    public Wizard retrieveWizardById (String id){
        return repository.findById(id).get();
    }

    public Wizard updateWizard(Wizard wizard){
        return repository.save(wizard);
    }

    public boolean deleteWizard(Wizard wizard){
        repository.delete(wizard);
        return true;
    }
}
