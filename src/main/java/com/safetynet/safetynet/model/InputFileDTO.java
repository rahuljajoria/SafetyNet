package com.safetynet.safetynet.model;

import java.util.List;

public class InputFileDTO {
    private List<PersonEntity> persons;
    private List<FirestationEntity> firestations;
    private List<MedicalRecordEntity> medicalrecords;


    public List<PersonEntity> getPersons() {
        return persons;
    }

    public List<FirestationEntity> getFirestations(){
        return firestations;
    }

    public List<MedicalRecordEntity> getMedicalrecords() {
        return medicalrecords;
    }

    public void setPersons(List<PersonEntity> persons) {
        this.persons = persons;
    }

    public void setFirestations(List<FirestationEntity> firestations) {
        this.firestations = firestations;
    }

    public void setMedicalrecords(List<MedicalRecordEntity> medicalrecords) {
        this.medicalrecords = medicalrecords;
    }
}
