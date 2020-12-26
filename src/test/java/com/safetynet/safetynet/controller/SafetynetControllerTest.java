package com.safetynet.safetynet.controller;

import com.safetynet.safetynet.dto.*;
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

import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(SpringRunner.class)
class SafetynetControllerTest {
    @InjectMocks
    private SafetynetController safetynetController = new SafetynetController();

    @Mock
    FileDataLoadingService fileDataLoadingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        List <PersonEntity> persons = new ArrayList<>();
        List <FirestationEntity> firestations = new ArrayList<>();
        List <MedicalRecordEntity> medicalRecords = new ArrayList<>();
        List <String> medications;
        List <String> allergies;
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
                "03/06/1984",Arrays.asList("aznol:350mg", "hydrapermazol:100mg"),
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

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetPersonsFromFireStation() {
        FireStationDTODetails fireStationDTODetails = safetynetController.getPersonsFromFireStation("3");
        assertEquals(fireStationDTODetails.getMessage(), "Success");
        assertEquals(fireStationDTODetails.getStatusCode(),200);
        assertEquals(fireStationDTODetails.getPersonsInfo().get(0).getAddress(),"1509 Culver St");
    }

    @Test
    void testGetAddressInfo() {
        FireDTODetails fireDTODetails = safetynetController.getAddressInfo("1509 Culver St");
        assertEquals(fireDTODetails.getMessage(), "Success");
        assertEquals(fireDTODetails.getStatusCode(),200);
        assertEquals(fireDTODetails.getPersonDetails().get(0).getFirstName(),"John");
        assertEquals(fireDTODetails.getPersonDetails().get(0).getLastName(),"Boyd");
    }

    @Test
    void testGetFloodInfo() {
        FloodDTODetails floodDTODetails = safetynetController.getFloodInfo(Arrays.asList(new String[]{"2", "3"}));
        assertEquals(floodDTODetails.getMessage(), "Success");
        assertEquals(floodDTODetails.getStatusCode(),200);
        assertEquals(floodDTODetails.getDetails().get(0).getAddress().contains("1509 Culver St"),true);
    }

    @Test
    void testGetPhonesNumbers() {
        PhoneAlertDTO phoneAlertDTO = safetynetController.getPhonesNumbers("3");
        assertEquals(phoneAlertDTO.getMessage(), "Success");
        assertEquals(phoneAlertDTO.getStatusCode(),200);
        assertEquals(phoneAlertDTO.getPhone().contains("841-874-6512"),true);
    }

    @Test
    void testGetEmailAddress() {
        CommunityEmailDTO communityEmailDTO= safetynetController.getEmailAddress("Culver");
        assertEquals(communityEmailDTO.getMessage(), "Success");
        assertEquals(communityEmailDTO.getStatusCode(),200);
        assertEquals(communityEmailDTO.getEmails().contains("jaboyd@email.com"),true);
    }

    @Test
    void testGetChildAlert() {
        ChildAlertDTODetails childAlertDTODetails = safetynetController.getChildAlert("1509 Culver St");
        assertEquals(childAlertDTODetails.getMessage(), "Success");
        assertEquals(childAlertDTODetails.getStatusCode(),200);
        assertEquals(childAlertDTODetails.getChildrenDetails().get(0).getFirstName(),"Tenley");
        assertEquals(childAlertDTODetails.getChildrenDetails().get(0).getLastName(),"Boyd");
    }

    @Test
    void testGetPersonInfo() {
        PersonDTODetails personDTODetails = safetynetController.getPersonInfo("John","Boyd");
        assertEquals(personDTODetails.getMessage(), "Success");
        assertEquals(personDTODetails.getStatusCode(),200);
        assertEquals(personDTODetails.getPersonInfo().get(0).getFirstName(),"John");
        assertEquals(personDTODetails.getPersonInfo().get(0).getLastName(),"Boyd");
    }
}