package com.safetynet.safetynet.model;

import java.util.List;

public class MedicalRecordsEntity {

    private String firstName;
    private String lastName;
    private String birthdate;
    private List <String> medications;
    private List <String> allergies;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthdate;
    }

    public void setBirthDate(String birthDate) {
        this.birthdate = birthDate;
    }

    public List<String> getMedications() {
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }
}
