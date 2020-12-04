package com.safetynet.safetynet.controller;

import com.safetynet.safetynet.model.FirestationEntity;
import com.safetynet.safetynet.model.PersonEntity;
import com.safetynet.safetynet.service.DataLoadingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FirestationController {

    @DeleteMapping("/firestation")
    public void deletefirestation(@RequestParam("address") String address){
        System.out.println("size of list " +DataLoadingService.firestations.size());
        for (int i = 0; i < DataLoadingService.firestations.size(); i++) {
            if (DataLoadingService.firestations.get(i).getAddress().equals(address)) {
                DataLoadingService.firestations.remove(i);
                break;
            }
        }
    }

    @PutMapping("/firestation")
    public void updatefirestation(@RequestBody FirestationEntity firestationEntity){
//        if(personEntity.getFirstName() == null || personEntity.getLastName()){
//            throw new Exception();
//        }
        for (int i = 0; i < DataLoadingService.firestations.size(); i++) {
            if ((DataLoadingService.firestations.get(i).getAddress().equals(firestationEntity.getAddress()))) {
                DataLoadingService.firestations.get(i).setStation(firestationEntity.getStation());
                break;
            }
        }
    }

    @PostMapping("/firestation")
    public void addfirestation(@RequestBody FirestationEntity firestationEntity){
        DataLoadingService.firestations.add(firestationEntity);
    }
}
