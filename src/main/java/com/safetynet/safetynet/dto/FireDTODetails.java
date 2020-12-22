package com.safetynet.safetynet.dto;

import com.safetynet.safetynet.dto.responses.ResponseDTO;

import java.util.List;

public class FireDTODetails extends ResponseDTO {

    private String station;
    private List <FireDTO> personDetails;

    public static class FireDTO {

        private String firstName;
        private String lastName;
        private String phone;
        private String age;
        private List <String> medication;
        private List <String> allergies;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public List<String> getMedication() {
            return medication;
        }

        public void setMedication(List<String> medication) {
            this.medication = medication;
        }

        public List<String> getAllergies() {
            return allergies;
        }

        public void setAllergies(List<String> allergies) {
            this.allergies = allergies;
        }
    }

    public List<FireDTO> getPersonDetails() {
        return personDetails;
    }

    public void setPersonDetails(List<FireDTO> personDetails) {
        this.personDetails = personDetails;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
