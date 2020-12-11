package com.safetynet.safetynet.service;

import com.google.gson.Gson;
import com.safetynet.safetynet.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger("DataLoadingService");

    public InputFileDTO inputFileDTO;

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

//    public InputFileDTO getInputFileDTO() {
//        return inputFileDTO;
//    }
//
//    public void setInputFileDTO(InputFileDTO inputFileDTO) {
//        this.inputFileDTO = inputFileDTO;
//    }



    public void updateDataFile(List<PersonEntity> persons, List<MedicalRecordEntity> medicalRecords,
                               List<FirestationEntity> firestations) throws IOException {
        String dataUrl = "src/main/resources/data/data.json";
        InputFileDTO inputFileDTO = new InputFileDTO();

        if (persons != null){
            inputFileDTO.setPersons(persons);
            inputFileDTO.setFirestations(DataLoadingService.firestations);
            inputFileDTO.setMedicalrecords(DataLoadingService.medicalRecords);
        }
        else if (medicalRecords != null) {
            inputFileDTO.setMedicalrecords(medicalRecords);
            inputFileDTO.setPersons(DataLoadingService.persons);
            inputFileDTO.setFirestations(DataLoadingService.firestations);
        }
        else if (firestations != null){
            inputFileDTO.setFirestations(firestations);
            inputFileDTO.setPersons(DataLoadingService.persons);
            inputFileDTO.setMedicalrecords(DataLoadingService.medicalRecords);
        }
        Gson gson = new Gson();
        String outputFileData = gson.toJson(inputFileDTO);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dataUrl));
        bufferedWriter.write(outputFileData);
        bufferedWriter.close();
        logger.info("Data written to the file "+outputFileData);
    }

}
