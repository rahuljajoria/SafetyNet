package com.safetynet.safetynet.dto;

import com.safetynet.safetynet.dto.responses.ResponseDTO;

import java.util.List;

public class PhoneAlertDTO extends ResponseDTO {

    private List <String> phone;

    public List<String> getPhone() {
        return phone;
    }

    public void setPhone(List<String> phone) {
        this.phone = phone;
    }
}
