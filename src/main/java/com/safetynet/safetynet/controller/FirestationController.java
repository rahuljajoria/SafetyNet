package com.safetynet.safetynet.controller;
import com.safetynet.safetynet.model.FirestationEntity;
import com.safetynet.safetynet.service.DataLoadingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
public class FirestationController {
    private static final Logger logger = LogManager.getLogger("FirestationController");

    @DeleteMapping("/firestation")
    public void deletefirestation(@RequestParam("address") String address) throws IOException {
        logger.info("Request --"+"http://localhost:8080/firestation?address="+address);
        List<FirestationEntity> firestations = DataLoadingService.firestations;
        for (int i = 0; i < firestations.size(); i++) {
            if (firestations.get(i).getAddress().equals(address)) {
                firestations.remove(i);
                logger.debug("removing the data from firestations "+address);
                break;
            }
        }
        DataLoadingService dataLoadingService = new DataLoadingService();
        dataLoadingService.updateDataFile(null,null,firestations);
        logger.info("Data is removed successfully from the list");
    }

    @PutMapping("/firestation")
    public void updatefirestation(@RequestBody FirestationEntity firestationEntity) throws IOException {
//        if(personEntity.getFirstName() == null || personEntity.getLastName()){
//            throw new Exception();
//        }
        List<FirestationEntity> firestations = DataLoadingService.firestations;
        for (int i = 0; i < firestations.size(); i++) {
            if ((firestations.get(i).getAddress().equals(firestationEntity.getAddress()))) {
                firestations.get(i).setStation(firestationEntity.getStation());
                logger.debug("updating the data from firestation");
                break;
            }
        }
        DataLoadingService dataLoadingService = new DataLoadingService();
        dataLoadingService.updateDataFile(null,null,firestations);
        logger.info("Successfully updated the firestation address "+ firestationEntity.getAddress());
    }

    @PostMapping("/firestation")
    public void addfirestation(@RequestBody FirestationEntity firestationEntity) throws IOException {
        logger.info("Request --"+"http://localhost:8080/firestation");
        List<FirestationEntity> firestations = DataLoadingService.firestations;
        firestations.add(firestationEntity);
        logger.debug("adding the data to firestation");
        DataLoadingService dataLoadingService = new DataLoadingService();
        dataLoadingService.updateDataFile(null,null,firestations);
        logger.info("Successfully added the firestation address and number");
    }
}
