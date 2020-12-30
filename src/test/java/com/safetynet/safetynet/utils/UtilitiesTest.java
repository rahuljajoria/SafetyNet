package com.safetynet.safetynet.utils;

import com.safetynet.safetynet.controller.PersonController;
import com.safetynet.safetynet.service.FileDataLoadingService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UtilitiesTest {


    @BeforeEach
    void setUp() {
    Utilities utilities = new Utilities();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void calculateAgeTest() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate curr = LocalDate.now();
        LocalDate birthdate = LocalDate.parse("10/12/1991",format);
        assertEquals(29,Utilities.calculateAge(birthdate,curr));
    }


}
