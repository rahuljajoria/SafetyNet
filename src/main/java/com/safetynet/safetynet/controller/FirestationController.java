package com.safetynet.safetynet.controller;
import com.safetynet.safetynet.dto.responses.FirestationResponseDTO;
import com.safetynet.safetynet.model.FirestationEntity;
import com.safetynet.safetynet.service.FileDataLoadingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class FirestationController {
    private static final Logger logger = LogManager.getLogger(FirestationController.class);

    @Autowired
    FileDataLoadingService fileDataLoadingService;

    @DeleteMapping("/firestation")
    public FirestationResponseDTO deletefirestation(@RequestParam("address") String address) {
        FirestationResponseDTO firestationResponseDTO = new FirestationResponseDTO();
        firestationResponseDTO.setAddress(address);
        firestationResponseDTO.setMessage("Could not find resource");
        firestationResponseDTO.setStatusCode(404);
        logger.info("Request for deleting firestation record "+address);
        List<FirestationEntity> firestations = fileDataLoadingService.getFirestations();
        for (int i = 0; i < firestations.size(); i++) {
            if (firestations.get(i).getAddress().equals(address)) {
                firestations.remove(i);
                firestationResponseDTO.setMessage("Delete successful");
                firestationResponseDTO.setStatusCode(200);
                logger.debug("removing the data from firestations "+address);
                fileDataLoadingService.updateDataFile(null,null,firestations);
                logger.info("Data is removed successfully from the list");
                return firestationResponseDTO;
            }
        }
        logger.info("No record is found");
        return firestationResponseDTO;
    }

    @PutMapping("/firestation")
    public FirestationResponseDTO updatefirestation(@RequestBody FirestationEntity firestationEntity) {
        FirestationResponseDTO firestationResponseDTO = new FirestationResponseDTO();
        firestationResponseDTO.setAddress(firestationEntity.getAddress());
        firestationResponseDTO.setMessage("Could not find resource");
        firestationResponseDTO.setStatusCode(404);
        if ((firestationEntity.getAddress().equals(null))){
            firestationResponseDTO.setMessage("Address is null");
            firestationResponseDTO.setStatusCode(500);
            return firestationResponseDTO;
        }
        logger.info("Request for updating firestation record "+ firestationEntity.getAddress());
        List<FirestationEntity> firestations = fileDataLoadingService.getFirestations();
        FirestationEntity updateFirestationEntity = null;
        for (int i = 0; i < firestations.size(); i++) {
            if ((firestations.get(i).getAddress().equals(firestationEntity.getAddress()))) {
                updateFirestationEntity = firestations.get(i);
                updateFirestationEntity.setStation(firestationEntity.getStation());
                firestationResponseDTO.setMessage("Update successful");
                firestationResponseDTO.setStatusCode(200);
                logger.debug("updating the data from firestation");
                fileDataLoadingService.updateDataFile(null,null,firestations);
                logger.info("Successfully updated the firestation address "+ firestationEntity.getAddress());
                return firestationResponseDTO;
            }
        }
        logger.info("Could not find record");
        return firestationResponseDTO;
    }

    @PostMapping("/firestation")
    public FirestationResponseDTO addfirestation(@RequestBody FirestationEntity firestationEntity) {
        FirestationResponseDTO firestationResponseDTO = new FirestationResponseDTO();
        firestationResponseDTO.setAddress(firestationEntity.getAddress());
        firestationResponseDTO.setMessage("Could not find resource");
        firestationResponseDTO.setStatusCode(404);
        if ((firestationEntity.getAddress().equals(null))){
            firestationResponseDTO.setMessage("Address is null");
            firestationResponseDTO.setStatusCode(500);
            return firestationResponseDTO;
        }
        logger.info("Request for adding new firestation details in the firestation record "
                + firestationEntity.getAddress());
        List<FirestationEntity> firestations = fileDataLoadingService.getFirestations();
        firestations.add(firestationEntity);
        firestationResponseDTO.setMessage("Record created successfully");
        firestationResponseDTO.setStatusCode(200);
        logger.debug("adding the data to firestation");
        fileDataLoadingService.updateDataFile(null,null,firestations);
        logger.info("Successfully added the firestation address and number");
        return firestationResponseDTO;
    }
}
