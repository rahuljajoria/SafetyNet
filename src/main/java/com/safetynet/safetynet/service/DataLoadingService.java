package com.safetynet.safetynet.service;

import com.google.gson.Gson;
import com.safetynet.safetynet.model.*;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.util.List;

@Component
public class DataLoadingService {

    public static List<PersonEntity> persons;
    public static List<FirestationEntity> firestations;
    public static List<MedicalRecordEntity> medicalRecords;

    public InputFileDTO inputFileDTO;
    String dataUrl = "src/main/resources/data/data.json";
    @PostConstruct
    public void init() throws IOException {
       // Properties properties = new Properties();
       // properties.load(new FileInputStream("src/main/resources/data/data.json"));

        //File reading logic

        BufferedReader bufferedReader = new BufferedReader(new FileReader(dataUrl));
        //Json Parse the file inputs
        Gson gson = new Gson();
        InputFileDTO inputFileDTO = gson.fromJson(bufferedReader, InputFileDTO.class);

        //Populate DTOs as needed
        persons = inputFileDTO.getPersons();
        firestations = inputFileDTO.getFirestations();
        medicalRecords = inputFileDTO.getMedicalrecords();

    }

    public InputFileDTO getInputFileDTO() {
        return inputFileDTO;
    }

    public void setInputFileDTO(InputFileDTO inputFileDTO) {
        this.inputFileDTO = inputFileDTO;
    }

    @PreDestroy
    public void updateDataFile(List<PersonEntity> persons, List<MedicalRecordEntity> medicalRecords,
                               List<FirestationEntity> firestations) {
        InputFileDTO inputFileDTO = new InputFileDTO();
        if (persons != null){
            inputFileDTO.setPersons(persons);
        }
        if (medicalRecords != null){
            inputFileDTO.setMedicalrecords(medicalRecords);
        }
        if (firestations != null){
            inputFileDTO.setFirestations(firestations);
        }
        Gson gson = new Gson();
        String s = gson.toJson(inputFileDTO);
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(dataUrl));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bufferedWriter.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(s);
    }

}
