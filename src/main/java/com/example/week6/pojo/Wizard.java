package com.example.week6.pojo;

import com.vaadin.flow.component.template.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Wizard")
public class Wizard {
    @Id
    private String id;
    private String sex;
    private String name;
    private String school;
    private String house;
    private Double money;
    private String position;

    public Wizard() {
    }

    public Wizard(String id, String sex, String name, String school, String house, Double money, String position) {
        this.id = id;
        this.sex = sex;
        this.name = name;
        this.school = school;
        this.house = house;
        this.money = money;
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public String getSex() {
        return sex;
    }

    public String getName() {
        return name;
    }

    public String getSchool() {
        return school;
    }

    public String getHouse() {
        return house;
    }

    public Double getMoney() {
        return money;
    }

    public String getPosition() {
        return position;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
