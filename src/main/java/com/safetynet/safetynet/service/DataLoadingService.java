package com.safetynet.safetynet.service;

import com.google.gson.Gson;
import com.safetynet.safetynet.dto.CommunityEmailDTO;
import com.safetynet.safetynet.model.*;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Component
public class DataLoadingService {

    public static List<PersonEntity> persons;
    public static List<FirestationEntity> firestations;
    public static List<MedicalRecordsEntity> medicalRecords;

    @PostConstruct
    public void init() throws IOException {
       // Properties properties = new Properties();
       // properties.load(new FileInputStream("src/main/resources/data/data.json"));

        //File reading logic
        String dataUrl = "src/main/resources/data/data.json";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(dataUrl));
        //Json Parse the file inputs
        Gson gson = new Gson();
        InputFileDTO inputFileDTO = gson.fromJson(bufferedReader, InputFileDTO.class);

        //Populate DTOs as needed
        persons = inputFileDTO.getPersons();
        firestations = inputFileDTO.getFirestations();
        medicalRecords = inputFileDTO.getMedicalrecords();







    }


}
