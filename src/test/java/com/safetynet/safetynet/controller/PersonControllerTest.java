package com.safetynet.safetynet.controller;

import com.safetynet.safetynet.service.FileDataLoadingService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class PersonControllerTest {
    @InjectMocks
    private PersonController personController = new PersonController();

    @Mock
    FileDataLoadingService fileDataLoadingService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void deletePerson() {
    }

    @Test
    void updatePerson() {
    }

    @Test
    void addPerson() {
    }
}