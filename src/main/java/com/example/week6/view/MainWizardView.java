package com.example.week6.view;

import com.example.week6.pojo.Wizard;
import com.example.week6.pojo.Wizards;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;

@Route (value="/mainPage.it")
public class MainWizardView extends VerticalLayout {
    private TextField fullN;
    private RadioButtonGroup<String> gender;
    private ComboBox<String> position, school, house;
    private NumberField balance;
    private Button back, create, update, delete, next;
    private HorizontalLayout btnContainer;
    private Wizards wizards;
    private Wizard wizard;
    private int index = -1, maxLength;
    private ObjectMapper mapper;

    public MainWizardView() {
        fullN = new TextField();
        fullN.setPlaceholder("Fullname");

        gender = new RadioButtonGroup<>("Gender :");
        gender.setItems("Male", "Female");

        position = new ComboBox<>();
        position.setPlaceholder("Position");
        position.setItems("Student", "Teacher");

        balance = new NumberField("Dollars");
        balance.setPrefixComponent(new Span("$"));

        school = new ComboBox<>();
        school.setPlaceholder("School");
        school.setItems("Hogwarts", "Beauxbatons", "Durmstrang");

        house = new ComboBox<>();
        house.setPlaceholder("House");
        house.setItems("Gryffindor", "Ravenclaw", "Hufflepuff", "Slyther");

        btnContainer = new HorizontalLayout();
        back = new Button("<<");
        create = new Button("Create");
        update = new Button("Update");
        delete = new Button("Delete");
        next = new Button(">>");

        btnContainer.add(back, create, update, delete, next);

        this.add(fullN, gender, position, balance, school, house, btnContainer);

        ArrayList<Wizard> out = WebClient.create()
                .get()
                .uri("http://localhost:8080/wizards")
                .retrieve()
                .bodyToMono(ArrayList.class)
                .block();

        wizards = new Wizards(out);
        maxLength = wizards.getWizards().size() - 1;
        mapper = new ObjectMapper();

        update.addClickListener(event -> {
            if(index >= 0) {
                wizard = mapper.convertValue(wizards.getWizards().get(index), Wizard.class);
                MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
                formData.add("id", wizard.getId());
                formData.add("name", fullN.getValue());
                formData.add("sex", gender.getValue().equals("Male") ? "m" : "f");
                formData.add("school", school.getValue());
                formData.add("house", house.getValue());
                formData.add("money", balance.getValue() + "");
                formData.add("position", position.getValue());
                ArrayList<Wizard> output = WebClient.create()
                        .post()
                        .uri("http://localhost:8080/updateWizard")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .body(BodyInserters.fromFormData(formData))
                        .retrieve()
                        .bodyToMono(ArrayList.class)
                        .block();
                wizards.setWizards(output);
            }
        });

        delete.addClickListener(buttonClickEvent -> {
            if (index >= 0) {
                wizard = mapper.convertValue(wizards.getWizards().get(index), Wizard.class);
                MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
                formData.add("id", wizard.getId());
                formData.add("name", fullN.getValue());
                formData.add("sex", gender.getValue());
                formData.add("school", school.getValue());
                formData.add("house", house.getValue());
                formData.add("money", balance.getValue() + "");
                formData.add("position", position.getValue());

                WebClient.create()
                        .post()
                        .uri("http://localhost:8080/deleteWizard")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .body(BodyInserters.fromFormData(formData))
                        .retrieve()
                        .bodyToMono(ArrayList.class)
                        .block();
                wizards.deleteWizard(index);
                maxLength = wizards.getWizards().size() - 1;
                index = index > maxLength ? maxLength : index;
                pageRender(index);
            }
        });


        create.addClickListener(buttonClickEvent -> {
            if (!fullN.getValue().equals(null) && balance.getValue() != null && !gender.getValue().equals(null) && !position.getValue().equals(null)) {
                MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
                formData.add("name", fullN.getValue());
                formData.add("sex", gender.getValue().equals("Male") ? "m" : "f");
                formData.add("school", school.getValue());
                formData.add("house", house.getValue());
                formData.add("money", balance.getValue() + "");
                formData.add("position", position.getValue());

                Wizard output = WebClient.create()
                        .post()
                        .uri("http://localhost:8080/addWizard")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .body(BodyInserters.fromFormData(formData))
                        .retrieve()
                        .bodyToMono(Wizard.class)
                        .block();
                wizards.addWizard(output);
                maxLength = wizards.getWizards().size() - 1;
            }
        });

        next.addClickListener(buttonClickEvent -> {
            index = index < maxLength ? index+1 : maxLength;
            pageRender(index);
        });
        back.addClickListener(event -> {
            index = index >= 0 ? index-1 : -1;
            pageRender(index);
        });
    }
    public void pageRender(int index) {
        if(index == -1){
            fullN.setValue("");
            gender.setValue("");
            position.setValue("");
            balance.setValue(null);
            school.setValue("");
            house.setValue("");
        }
        else {
            wizard = mapper.convertValue(wizards.getWizards().get(index), Wizard.class);
            fullN.setValue(wizard.getName());
            gender.setValue(wizard.getSex().equals("m") ? "Male" : "Female");
            position.setValue(wizard.getPosition());
            balance.setValue(wizard.getMoney());
            school.setValue(wizard.getSchool());
            house.setValue(wizard.getHouse());
        }
    }
}