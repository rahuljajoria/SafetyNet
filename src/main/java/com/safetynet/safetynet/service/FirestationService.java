package com.safetynet.safetynet.service;

import com.safetynet.safetynet.model.FirestationEntity;

import java.util.ArrayList;
import java.util.List;

public class FirestationService {

    public static List<String> getAddressList(String stationNo, List<FirestationEntity> firestationAddressList) {
        List<String> addressList = new ArrayList<>();
        for (int i = 0; i < firestationAddressList.size(); i++) {
            if (firestationAddressList.get(i).getStation().equals(stationNo)) {
                addressList.add(firestationAddressList.get(i).getAddress());
            }
        }
        return addressList;
    }

}
