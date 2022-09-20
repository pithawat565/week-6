package com.example.week6.controller;

import com.example.week6.pojo.Wizard;
import com.example.week6.repository.WizardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class WizardController {
    @Autowired
    private WizardService wizardService;

    @GetMapping (value = "/wizards")
    public ArrayList<Wizard> getWizards() {
        List<Wizard> wizards = wizardService.retrieveWizard();
        return (ArrayList<Wizard>) wizards;
    }

    @PostMapping (value = "/addWizard")
    public Wizard addWizard(@RequestBody MultiValueMap<String, String> body) {
        Map<String, String> data = body.toSingleValueMap();
        String sex = data.get("sex");
        String name = data.get("name");
        String school = data.get("school");
        String house = data.get("house");
        double money = Double.parseDouble(data.get("money"));
        String position = data.get("position");

        Wizard wizard = wizardService.addWizard(new Wizard(null, sex, name, school, house, money, position));

        return wizard;
    }

    @PostMapping(value = "/updateWizard")
    public ArrayList<Wizard> updateWizard(@RequestBody MultiValueMap<String, String> body){
        Map<String, String> data = body.toSingleValueMap();
        Wizard oldWizard = wizardService.retrieveWizardById(data.get("id"));
        String id = oldWizard.getId();
        String sex = data.get("sex");
        String name = data.get("name");
        String school = data.get("school");
        String house = data.get("house");
        double money = Double.parseDouble(data.get("money"));
        String position = data.get("position");

        Wizard wizard = wizardService.updateWizard(new Wizard(id, sex, name, school, house, money, position));
        List<Wizard> wizards = wizardService.retrieveWizard();

        return (ArrayList<Wizard>) wizards;
    }

    @PostMapping (value = "/deleteWizard")
    public void deleteWizard(@RequestBody MultiValueMap<String, String> body){
        Map<String, String> data = body.toSingleValueMap();
        String id = data.get("id");
        String sex = data.get("sex");
        String name = data.get("name");
        String school = data.get("school");
        String house = data.get("house");
        double money = Double.parseDouble(data.get("money"));
        String position = data.get("position");

        Wizard target = new Wizard(id, sex, name, school, house, money, position);
        wizardService.deleteWizard(target);
    }
}

