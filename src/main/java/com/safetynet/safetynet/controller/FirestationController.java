package com.safetynet.safetynet.controller;
import com.safetynet.safetynet.dto.responses.FirestationResponseDTO;
import com.safetynet.safetynet.dto.responses.MedicalRecordResponseDTO;
import com.safetynet.safetynet.model.FirestationEntity;
import com.safetynet.safetynet.model.MedicalRecordEntity;
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
        FirestationResponseDTO firestationResponseDTO =
                new FirestationResponseDTO(address,"Could not find resource",404);
        logger.info("Request for deleting firestation record "+address);
        List<FirestationEntity> firestations = FileDataLoadingService.firestations;
        for (int i = 0; i < firestations.size(); i++) {
            if (firestations.get(i).getAddress().equals(address)) {
                firestations.remove(i);
                firestationResponseDTO.setErrorMessage("Delete successful");
                firestationResponseDTO.setStatusCode(200);
                logger.debug("removing the data from firestations "+address);
                break;
            }
        }
        fileDataLoadingService.updateDataFile(null,null,firestations);
        logger.info("Data is removed successfully from the list");
        return firestationResponseDTO;
    }

    @PutMapping("/firestation")
    public FirestationResponseDTO updatefirestation(@RequestBody FirestationEntity firestationEntity) {
        FirestationResponseDTO firestationResponseDTO =
                new FirestationResponseDTO(firestationEntity.getAddress(),
                        "Could not find resource",404);
        if ((firestationEntity.getAddress().equals(null))){
            firestationResponseDTO.setErrorMessage("Address is null");
            firestationResponseDTO.setStatusCode(500);
            return firestationResponseDTO;
        }
        logger.info("Request for updating firestation record "+ firestationEntity.getAddress());
        List<FirestationEntity> firestations = FileDataLoadingService.firestations;
        FirestationEntity updateFirestationEntity = null;
        for (int i = 0; i < firestations.size(); i++) {
            if ((firestations.get(i).getAddress().equals(firestationEntity.getAddress()))) {
                updateFirestationEntity = firestations.get(i);
                updateFirestationEntity.setStation(firestationEntity.getStation());
                firestationResponseDTO.setErrorMessage("Update successful");
                firestationResponseDTO.setStatusCode(200);
                logger.debug("updating the data from firestation");
                break;
            }
        }
        fileDataLoadingService.updateDataFile(null,null,firestations);
        logger.info("Successfully updated the firestation address "+ firestationEntity.getAddress());
        return firestationResponseDTO;
    }

    @PostMapping("/firestation")
    public FirestationResponseDTO addfirestation(@RequestBody FirestationEntity firestationEntity) {
        FirestationResponseDTO firestationResponseDTO =
                new FirestationResponseDTO(firestationEntity.getAddress(),
                        "Could not find resource",404);
        if ((firestationEntity.getAddress().equals(null))){
            firestationResponseDTO.setErrorMessage("Address is null");
            firestationResponseDTO.setStatusCode(500);
            return firestationResponseDTO;
        }
        logger.info("Request for adding new firestation details in the firestation record "
                + firestationEntity.getAddress());
        List<FirestationEntity> firestations = FileDataLoadingService.firestations;
        firestations.add(firestationEntity);
        firestationResponseDTO.setErrorMessage("Record created successfully");
        firestationResponseDTO.setStatusCode(200);
        logger.debug("adding the data to firestation");
        fileDataLoadingService.updateDataFile(null,null,firestations);
        logger.info("Successfully added the firestation address and number");
        return firestationResponseDTO;
    }
}
