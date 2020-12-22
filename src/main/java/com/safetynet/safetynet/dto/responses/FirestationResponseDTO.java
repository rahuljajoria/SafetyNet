package com.safetynet.safetynet.dto.responses;

public class FirestationResponseDTO extends ResponseDTO{
    private String address;
    private String station;

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
