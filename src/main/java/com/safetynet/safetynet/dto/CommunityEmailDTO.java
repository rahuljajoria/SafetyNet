package com.safetynet.safetynet.dto;

import com.safetynet.safetynet.dto.responses.ResponseDTO;

import java.util.List;

public class CommunityEmailDTO extends ResponseDTO{

    private List<String> emails;

    public List<String> getEmails() {

        return emails;
    }

    public void setEmails(List<String> emails) {

        this.emails = emails;
    }
}