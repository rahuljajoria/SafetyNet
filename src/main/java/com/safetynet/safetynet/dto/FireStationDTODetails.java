package com.safetynet.safetynet.dto;

import java.util.List;

public class FireStationDTODetails {
    private List<FirestationDTO> personsInfo;
    private String noOfAdults;
    private String noOfChildren;

    public static class FirestationDTO {

        private String firstName;
        private String lastName;
        private String address;
        private String phone;

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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    public List<FirestationDTO> getPersonsInfo() {
        return personsInfo;
    }

    public void setPersonsInfo(List<FirestationDTO> personsInfo) {
        this.personsInfo = personsInfo;
    }

    public String getNoOfAdults() {
        return noOfAdults;
    }

    public void setNoOfAdults(String noOfAdults) {
        this.noOfAdults = noOfAdults;
    }

    public String getNoOfChildren() {
        return noOfChildren;
    }

    public void setNoOfChildren(String noOfChildren) {
        this.noOfChildren = noOfChildren;
    }
}