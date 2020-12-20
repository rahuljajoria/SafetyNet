package com.safetynet.safetynet.dto.responses;

public class PersonResponseDTO extends ResponseDTO{
    private String fName;
    private String lName;

    public PersonResponseDTO(String fName, String lName, String error, int statusCode) {
        super();
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }
}