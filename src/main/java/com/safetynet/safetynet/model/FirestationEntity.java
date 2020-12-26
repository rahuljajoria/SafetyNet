package com.safetynet.safetynet.model;

public class FirestationEntity {

    private String address;
    private String station;

    public FirestationEntity(String address, String station) {
        this.address = address;
        this.station = station;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
