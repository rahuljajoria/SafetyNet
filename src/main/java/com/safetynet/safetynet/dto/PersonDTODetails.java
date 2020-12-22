package com.safetynet.safetynet.dto;


import com.safetynet.safetynet.dto.responses.ResponseDTO;

import java.util.List;

public class PersonDTODetails extends ResponseDTO {

    private List<PersonInfoDTO> personInfo;

    public static class PersonInfoDTO {

        private String firstName;
        private String lastName;
        private String address;
        private String age;
        private String email;
        private List <String> medications;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public List<String> getMedications() {
            return medications;
        }

        public void setMedications(List<String> medications) {
            this.medications = medications;
        }

        public List<String> getAllergies() {
            return allergies;
        }

        public void setAllergies(List<String> allergies) {
            this.allergies = allergies;
        }
    }


    public List<PersonInfoDTO> getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(List<PersonInfoDTO> personInfo) {
        this.personInfo = personInfo;
    }
}
