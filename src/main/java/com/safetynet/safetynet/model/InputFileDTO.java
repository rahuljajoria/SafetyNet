package com.safetynet.safetynet.model;

import java.util.List;

public class InputFileDTO {
    private List<PersonEntity> persons;
    private List<FirestationEntity> firestations;
    private List<MedicalRecordsEntity> medicalrecords;


    public List<PersonEntity> getPersons() {
        return persons;
    }

    public List<FirestationEntity> getFirestations(){
        return firestations;
    }

    public List<MedicalRecordsEntity> getMedicalrecords() {
        return medicalrecords;
    }
}
