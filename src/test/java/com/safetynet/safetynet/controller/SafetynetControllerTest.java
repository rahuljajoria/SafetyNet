package com.safetynet.safetynet.controller;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;


class SafetynetControllerTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

//    @Test
//    void getPersonsFromFireStation() {
//
//        // Given
//        String name = "1";
//        HttpUriRequest request = new HttpGet( "https://api.github.com/users/" + name );
//        // When
//        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );
//
//        // Then
//        assertThat(
//                httpResponse.getStatusLine().getStatusCode(),
//                equalTo(HttpStatus.SC_NOT_FOUND));
//    }

    @Test
    void getAddressInfo() {

    }

    @Test
    void getFloodInfo() {
    }

    @Test
    void getPhonesNumbers() {
    }

    @Test
    void getEmailAddress() {
    }

    @Test
    void getChildAlert() {
    }

    @Test
    void getPersonInfo() {
    }

    @Test
    void testGetPersonsFromFireStation() {
    }

    @Test
    void testGetAddressInfo() {
    }

    @Test
    void testGetFloodInfo() {
    }

    @Test
    void testGetPhonesNumbers() {
    }

    @Test
    void testGetEmailAddress() {
    }

    @Test
    void testGetChildAlert() {
    }

    @Test
    void testGetPersonInfo() {
    }
}