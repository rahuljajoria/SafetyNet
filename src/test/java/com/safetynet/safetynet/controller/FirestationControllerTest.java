package com.safetynet.safetynet.controller;

import com.safetynet.safetynet.dto.responses.FirestationResponseDTO;
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
class FirestationControllerTest {
    @InjectMocks
    private FirestationController firestationController = new FirestationController();

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
    void deletefirestationTestTrue() {
        Mockito.when(fileDataLoadingService.updateDataFile(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(true);
        FirestationResponseDTO firestationResponseDTO = firestationController.deletefirestation
                ("1509 Culver St");
        assertEquals( "Delete successful",firestationResponseDTO.getMessage());
        assertEquals(200,firestationResponseDTO.getStatusCode());
        assertEquals(true,firestationResponseDTO.getAddress().equals("1509 Culver St"));
    }

    @Test
    void deletefirestationTestFalse() {
        Mockito.when(fileDataLoadingService.updateDataFile(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(false);
        FirestationResponseDTO firestationResponseDTO = firestationController.deletefirestation
                ("1509 Culver St");
        assertEquals( "Record cannot be deleted",firestationResponseDTO.getMessage());
        assertEquals(400,firestationResponseDTO.getStatusCode());
        assertEquals(true,firestationResponseDTO.getAddress().equals("1509 Culver St"));
    }

    @Test
    void updatefirestationTrue() {
        Mockito.when(fileDataLoadingService.updateDataFile(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(true);
        FirestationEntity firestationEntity = new FirestationEntity("1509 Culver St","3");
        FirestationResponseDTO firestationResponseDTO = firestationController.updatefirestation(firestationEntity);
        assertEquals("Update successful",firestationResponseDTO.getMessage());
        assertEquals(200,firestationResponseDTO.getStatusCode());
        assertEquals(true,firestationResponseDTO.getAddress().equals("1509 Culver St"));
    }

    @Test
    void updatefirestationFalse() {
        Mockito.when(fileDataLoadingService.updateDataFile(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(false);
        FirestationEntity firestationEntity = new FirestationEntity("1509 Culver St","3");
        FirestationResponseDTO firestationResponseDTO = firestationController.updatefirestation(firestationEntity);
        assertEquals("Record cannot be updated",firestationResponseDTO.getMessage());
        assertEquals(400,firestationResponseDTO.getStatusCode());
        assertEquals(true,firestationResponseDTO.getAddress().equals("1509 Culver St"));
    }

    @Test
    void updatefirestationNull() {
        Mockito.when(fileDataLoadingService.updateDataFile(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(true);
        FirestationEntity firestationEntity = new FirestationEntity(null,"3");
        FirestationResponseDTO firestationResponseDTO = firestationController.updatefirestation(firestationEntity);
        assertEquals("Address is null",firestationResponseDTO.getMessage());
        assertEquals(400,firestationResponseDTO.getStatusCode());
    }

    @Test
    void addfirestationTrue() {
        Mockito.when(fileDataLoadingService.updateDataFile(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(true);
        FirestationEntity firestationEntity = new FirestationEntity("908 73rd St","1");
        FirestationResponseDTO firestationResponseDTO = firestationController.addfirestation(firestationEntity);
        assertEquals("Record created successfully",firestationResponseDTO.getMessage());
        assertEquals(200,firestationResponseDTO.getStatusCode());
        assertEquals(true,firestationResponseDTO.getAddress().equals("908 73rd St"));
    }

    @Test
    void addfirestationFalse() {
        Mockito.when(fileDataLoadingService.updateDataFile(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(false);
        FirestationEntity firestationEntity = new FirestationEntity("908 73rd St","1");
        FirestationResponseDTO firestationResponseDTO = firestationController.addfirestation(firestationEntity);
        assertEquals("Record cannot be added",firestationResponseDTO.getMessage());
        assertEquals(400,firestationResponseDTO.getStatusCode());
        assertEquals(true,firestationResponseDTO.getAddress().equals("908 73rd St"));
    }
    @Test
    void addfirestationNull() {
        Mockito.when(fileDataLoadingService.updateDataFile(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(true);
        FirestationEntity firestationEntity = new FirestationEntity(null,"1");
        FirestationResponseDTO firestationResponseDTO = firestationController.addfirestation(firestationEntity);
        assertEquals("Address is null",firestationResponseDTO.getMessage());
        assertEquals(400,firestationResponseDTO.getStatusCode());
    }
}