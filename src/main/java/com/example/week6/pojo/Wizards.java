package com.example.week6.pojo;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Wizards {
    private ArrayList<Wizard> wizards, tempWizards;
    private ObjectMapper mapper;

    public Wizards(ArrayList<Wizard> wizards) {
        this.wizards = wizards;
        mapper = new ObjectMapper();
    }

    public void setWizards(ArrayList<Wizard> wizards) {
        this.wizards = wizards;
    }

    public ArrayList<Wizard> getWizards() {
        return wizards;
    }

    public void addWizard(Wizard wizard){
        this.wizards.add(wizard);
    }

    public ArrayList<Wizard> deleteWizard(Wizard wizard){
        this.wizards.remove(wizard);
        return wizards;
    }

}
