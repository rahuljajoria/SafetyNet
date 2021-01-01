package com.safetynet.safetynet.controller;

import com.safetynet.safetynet.dto.responses.MedicalRecordResponseDTO;
import com.safetynet.safetynet.dto.responses.PersonResponseDTO;
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
class PersonControllerTest {
    @InjectMocks
    private PersonController personController = new PersonController();

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
    void deletePersonTrue() {
        Mockito.when(fileDataLoadingService.updateDataFile(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(true);
        PersonResponseDTO personResponseDTO = personController.deletePerson("Tenley","Boyd");
        assertEquals("Delete Successful",personResponseDTO.getMessage());
        assertEquals(200,personResponseDTO.getStatusCode());
        assertEquals("Tenley",personResponseDTO.getfName());
    }

    @Test
    void deletePersonFalse() {
        Mockito.when(fileDataLoadingService.updateDataFile(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(false);
        PersonResponseDTO personResponseDTO = personController.deletePerson("Tenley","Boyd");
        assertEquals("Record cannot be deleted",personResponseDTO.getMessage());
        assertEquals(400,personResponseDTO.getStatusCode());
        assertEquals("Tenley",personResponseDTO.getfName());
    }

    @Test
    void updatePersonTrue() {
        PersonEntity personEntity = new PersonEntity("Tenley","Boyd","834 Binoc Ave",
                "Culver","97451","841-874-6512","tenz@email.com");
        Mockito.when(fileDataLoadingService.updateDataFile(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(true);
        PersonResponseDTO personResponseDTO = personController.updatePerson(personEntity);
        assertEquals("Update Successful",personResponseDTO.getMessage());
        assertEquals(200,personResponseDTO.getStatusCode());
        assertEquals("Tenley",personResponseDTO.getfName());
        assertEquals("Boyd",personResponseDTO.getlName());
    }

    @Test
    void updatePersonFalse() {
        PersonEntity personEntity = new PersonEntity("Tenley","Boyd","834 Binoc Ave",
                "Culver","97451","841-874-6512","tenz@email.com");
        Mockito.when(fileDataLoadingService.updateDataFile(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(false);
        PersonResponseDTO personResponseDTO = personController.updatePerson(personEntity);
        assertEquals("Record cannot be updated",personResponseDTO.getMessage());
        assertEquals(400,personResponseDTO.getStatusCode());
        assertEquals("Tenley",personResponseDTO.getfName());
        assertEquals("Boyd",personResponseDTO.getlName());
    }

    @Test
    void updatePersonNull() {
        PersonEntity personEntity = new PersonEntity(null,"Boyd","834 Binoc Ave",
                "Culver","97451","841-874-6512","tenz@email.com");
        Mockito.when(fileDataLoadingService.updateDataFile(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(true);
        PersonResponseDTO personResponseDTO = personController.updatePerson(personEntity);
        assertEquals("Either First name or Last name is null",personResponseDTO.getMessage());
        assertEquals(400,personResponseDTO.getStatusCode());
    }

    @Test
    void addPersonTrue() {
        PersonEntity personEntity = new PersonEntity("Tessa","Carman","834 Binoc Ave",
                "Culver","97451","841-874-6512","tenz@email.com");
        Mockito.when(fileDataLoadingService.updateDataFile(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(true);
        PersonResponseDTO personResponseDTO = personController.addPerson(personEntity);
        assertEquals("Add Successful",personResponseDTO.getMessage());
        assertEquals(200,personResponseDTO.getStatusCode());
        assertEquals("Tessa",personResponseDTO.getfName());
        assertEquals("Carman",personResponseDTO.getlName());
    }

    @Test
    void addPersonFalse() {
        PersonEntity personEntity = new PersonEntity("Tessa","Carman","834 Binoc Ave",
                "Culver","97451","841-874-6512","tenz@email.com");
        Mockito.when(fileDataLoadingService.updateDataFile(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(false);
        PersonResponseDTO personResponseDTO = personController.addPerson(personEntity);
        assertEquals("Record cannot be added",personResponseDTO.getMessage());
        assertEquals(400,personResponseDTO.getStatusCode());
        assertEquals("Tessa",personResponseDTO.getfName());
        assertEquals("Carman",personResponseDTO.getlName());
    }

    @Test
    void addPersonNull() {
        PersonEntity personEntity = new PersonEntity("Tessa",null,"834 Binoc Ave",
                "Culver","97451","841-874-6512","tenz@email.com");
        Mockito.when(fileDataLoadingService.updateDataFile(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(true);
        PersonResponseDTO personResponseDTO = personController.addPerson(personEntity);
        assertEquals("Either First name or Last name is null",personResponseDTO.getMessage());
        assertEquals(400,personResponseDTO.getStatusCode());
    }
}