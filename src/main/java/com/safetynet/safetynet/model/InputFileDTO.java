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


}
