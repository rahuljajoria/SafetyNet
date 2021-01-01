package com.safetynet.safetynet.controller;

import com.safetynet.safetynet.dto.responses.MedicalRecordResponseDTO;
import com.safetynet.safetynet.model.FirestationEntity;
import com.safetynet.safetynet.model.MedicalRecordEntity;
import com.safetynet.safetynet.model.PersonEntity;
import com.safetynet.safetynet.service.FileDataLoadingService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
class MedicalRecordsControllerTest {
    @InjectMocks
    private MedicalRecordsController medicalRecordsController = new MedicalRecordsController();

    @Mock
    FileDataLoadingService fileDataLoadingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        List<PersonEntity> persons = new ArrayList<>();
        List <FirestationEntity> firestations = new ArrayList<>();
        List <MedicalRecordEntity> medicalRecords = new ArrayList<>();
        PersonEntity personEntity = new PersonEntity("John","Boyd","1509 Culver St",
                "Culver","97451","841-874-6512","jaboyd@email.com");
        persons.add(personEntity);
        personEntity = new PersonEntity("Jonanathan","Marrack","29 15th St","Culver"
                ,"97451","841-874-6513","drk@email.com");
        persons.add(personEntity);
        personEntity = new PersonEntity("Tenley","Boyd","1509 Culver St","Culver"
                ,"97451","841-874-6512","tenz@email.com");
        persons.add(personEntity);
        FirestationEntity firestationEntity = new FirestationEntity("1509 Culver St","3");
        firestations.add(firestationEntity);
        firestationEntity = new FirestationEntity("29 15th St","2");
        firestations.add(firestationEntity);
        MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity("John","Boyd",
                "03/06/1984", Arrays.asList("aznol:350mg", "hydrapermazol:100mg"),
                Arrays.asList("nillacilan"));
        medicalRecords.add(medicalRecordEntity);
        medicalRecordEntity = new MedicalRecordEntity("Jonanathan","Marrack",
                "01/03/1989",Arrays.asList(new String[]{}),
                Arrays.asList(new String[]{}));
        medicalRecords.add(medicalRecordEntity);
        medicalRecordEntity = new MedicalRecordEntity("Tenley","Boyd",
                "02/18/2012",Arrays.asList(new String[]{}),
                Arrays.asList("peanut"));
        medicalRecords.add(medicalRecordEntity);

        Mockito.when(fileDataLoadingService.getPersons()).thenReturn(persons);
        Mockito.when(fileDataLoadingService.getFirestations()).thenReturn(firestations);
        Mockito.when(fileDataLoadingService.getMedicalRecords()).thenReturn(medicalRecords);
    }

    @Test
    void deleteMedicalRecordTrue() {
        Mockito.when(fileDataLoadingService.updateDataFile(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(true);
        MedicalRecordResponseDTO medicalRecordResponseDTO = medicalRecordsController.
                deleteMedicalRecord("Tenley","Boyd");
        assertEquals("Delete successful",medicalRecordResponseDTO.getMessage());
        assertEquals(200,medicalRecordResponseDTO.getStatusCode());
        assertEquals("Tenley",medicalRecordResponseDTO.getFname());
    }

    @Test
    void deleteMedicalRecordFalse() {
        Mockito.when(fileDataLoadingService.updateDataFile(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(false);
        MedicalRecordResponseDTO medicalRecordResponseDTO = medicalRecordsController.
                deleteMedicalRecord("Tenley","Boyd");
        assertEquals("Record cannot be deleted",medicalRecordResponseDTO.getMessage());
        assertEquals(400,medicalRecordResponseDTO.getStatusCode());
        assertEquals("Tenley",medicalRecordResponseDTO.getFname());
    }

    @Test
    void updateMedicalRecordTrue() {
        MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity
                ("Tenley", "Boyd", "08/30/1979",
                        Arrays.asList("thradox:700mg"), Arrays.asList("illisoxian"));
        Mockito.when(fileDataLoadingService.updateDataFile(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(true);
        MedicalRecordResponseDTO medicalRecordResponseDTO =
                medicalRecordsController.updateMedicalRecord(medicalRecordEntity);
        assertEquals("Update successful",medicalRecordResponseDTO.getMessage());
        assertEquals(200,medicalRecordResponseDTO.getStatusCode());
        assertEquals("Tenley",medicalRecordResponseDTO.getFname());
    }

    @Test
    void updateMedicalRecordFalse() {
        MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity
                ("Tenley", "Boyd", "08/30/1979",
                        Arrays.asList("thradox:700mg"), Arrays.asList("illisoxian"));
        Mockito.when(fileDataLoadingService.updateDataFile(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(false);
        MedicalRecordResponseDTO medicalRecordResponseDTO =
                medicalRecordsController.updateMedicalRecord(medicalRecordEntity);
        assertEquals("Record cannot be updated",medicalRecordResponseDTO.getMessage());
        assertEquals(400,medicalRecordResponseDTO.getStatusCode());
        assertEquals("Tenley",medicalRecordResponseDTO.getFname());
    }

    @Test
    void updateMedicalRecordNull() {
        MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity
                (null, "Boyd", "08/30/1979",
                        Arrays.asList("thradox:700mg"), Arrays.asList("illisoxian"));
        Mockito.when(fileDataLoadingService.updateDataFile(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(true);
        MedicalRecordResponseDTO medicalRecordResponseDTO =
                medicalRecordsController.updateMedicalRecord(medicalRecordEntity);
        assertEquals("Either First name or Last name is null",medicalRecordResponseDTO.getMessage());
        assertEquals(400,medicalRecordResponseDTO.getStatusCode());
    }

    @Test
    void addMedicalRecordTrue() {
        MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity
                ("Reginold", "Walker", "08/30/1979",
                        Arrays.asList("thradox:700mg"), Arrays.asList("illisoxian"));
        Mockito.when(fileDataLoadingService.updateDataFile(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(true);
        MedicalRecordResponseDTO medicalRecordResponseDTO =
                medicalRecordsController.addMedicalRecord(medicalRecordEntity);
        assertEquals("add successful",medicalRecordResponseDTO.getMessage());
        assertEquals(200,medicalRecordResponseDTO.getStatusCode());
        assertEquals("Reginold",medicalRecordResponseDTO.getFname());
    }

    @Test
    void addMedicalRecordFalse() {
        MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity
                ("Reginold", "Walker", "08/30/1979",
                        Arrays.asList("thradox:700mg"), Arrays.asList("illisoxian"));
        Mockito.when(fileDataLoadingService.updateDataFile(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(false);
        MedicalRecordResponseDTO medicalRecordResponseDTO =
                medicalRecordsController.addMedicalRecord(medicalRecordEntity);
        assertEquals("Record cannot be added",medicalRecordResponseDTO.getMessage());
        assertEquals(400,medicalRecordResponseDTO.getStatusCode());
        assertEquals("Reginold",medicalRecordResponseDTO.getFname());
    }

    @Test
    void addMedicalRecordNull() {
        MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity
                ("Reginold", null, "08/30/1979",
                        Arrays.asList("thradox:700mg"), Arrays.asList("illisoxian"));
        Mockito.when(fileDataLoadingService.updateDataFile(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(true);
        MedicalRecordResponseDTO medicalRecordResponseDTO =
                medicalRecordsController.addMedicalRecord(medicalRecordEntity);
        assertEquals("Either First name or Last name is null",medicalRecordResponseDTO.getMessage());
        assertEquals(400,medicalRecordResponseDTO.getStatusCode());
    }
}