package com.safetynet.safetynet.dto;

import com.safetynet.safetynet.dto.responses.ResponseDTO;

import java.util.List;

public class ChildAlertDTODetails extends ResponseDTO {

    private List <ChildAlertDTO> childrenDetails;
    private List <String> personDetails;

    public static class ChildAlertDTO {

        private String firstName;
        private String lastName;
        private String age;

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
    }

    public List<ChildAlertDTO> getChildrenDetails() { return childrenDetails; }

    public void setChildrenDetails(List<ChildAlertDTO> childrenDetails) {
        this.childrenDetails = childrenDetails;
    }

    public List<String> getPersonDetails() {
        return personDetails;
    }

    public void setPersonDetails(List<String> personDetails) {
        this.personDetails = personDetails;
    }



}
