package com.safetynet.safetynet.dto;

import java.util.List;

public class FloodDTODetails {
    List<DetailDTO> details;

    public static class DetailDTO{
        private String address;
        private List<FloodDTO> persons;

        public static class FloodDTO {

            private String firstName;
            private String lastName;
            private String age;
            private String phone;
            private List<String> medications;
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

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public List<FloodDTO> getPersons() {
            return persons;
        }

        public void setPersons(List<FloodDTO> persons) {
            this.persons = persons;
        }
    }

    public List<DetailDTO> getDetails() {
        return details;
    }

    public void setDetails(List<DetailDTO> details) {
        this.details = details;
    }
}
