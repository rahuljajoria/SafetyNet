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

    /**
     * Delete the record searched with address
     * @param address
     * @return address from the record which is deleted
     */
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
                logger.debug("removing the data from firestations "+address);
                boolean isFileWritingSuccessful = fileDataLoadingService.updateDataFile
                        (null,null,firestations);
                if (isFileWritingSuccessful){
                    firestationResponseDTO.setMessage("Delete successful");
                    firestationResponseDTO.setStatusCode(200);
                    logger.info("Data is removed successfully from the list");
                    return firestationResponseDTO;
                }
            }
        }
        firestationResponseDTO.setMessage("Record cannot be deleted");
        logger.info("could not delete record");
        return firestationResponseDTO;
    }

    /**
     * Delete the record searched with address
     * @param firestationEntity
     * @return updated object
     */
    @PutMapping("/firestation")
    public FirestationResponseDTO updatefirestation(@RequestBody FirestationEntity firestationEntity) {
        FirestationResponseDTO firestationResponseDTO = new FirestationResponseDTO();
        firestationResponseDTO.setAddress(firestationEntity.getAddress());
        firestationResponseDTO.setMessage("Could not find resource");
        firestationResponseDTO.setStatusCode(404);
        if ((firestationEntity.getAddress().equals(null))){
            firestationResponseDTO.setMessage("Address is null");
            firestationResponseDTO.setStatusCode(400);
            return firestationResponseDTO;
        }
        logger.info("Request for updating firestation record "+ firestationEntity.getAddress());
        List<FirestationEntity> firestations = fileDataLoadingService.getFirestations();
        FirestationEntity updateFirestationEntity = null;
        for (int i = 0; i < firestations.size(); i++) {
            if ((firestations.get(i).getAddress().equals(firestationEntity.getAddress()))) {
                updateFirestationEntity = firestations.get(i);
                updateFirestationEntity.setStation(firestationEntity.getStation());
                logger.debug("updating the data from firestations "+ firestationEntity.getAddress());
                boolean isFileWritingSuccessful = fileDataLoadingService.updateDataFile
                        (null,null,firestations);
                if (isFileWritingSuccessful){
                    firestationResponseDTO.setMessage("Update successful");
                    firestationResponseDTO.setStatusCode(200);
                    logger.info("Successfully updated the firestation address "+ firestationEntity.getAddress());
                    return firestationResponseDTO;
                }
            }
        }
        firestationResponseDTO.setMessage("Record cannot be updated");
        logger.info("Could not update record");
        return firestationResponseDTO;
    }

    /**
     * Add a new record
     * @param firestationEntity
     * @return added object
     */
    @PostMapping("/firestation")
    public FirestationResponseDTO addfirestation(@RequestBody FirestationEntity firestationEntity) {
        FirestationResponseDTO firestationResponseDTO = new FirestationResponseDTO();
        firestationResponseDTO.setAddress(firestationEntity.getAddress());
        firestationResponseDTO.setMessage("Could not find resource");
        firestationResponseDTO.setStatusCode(404);
        if ((firestationEntity.getAddress().equals(null))){
            firestationResponseDTO.setMessage("Address is null");
            firestationResponseDTO.setStatusCode(400);
            return firestationResponseDTO;
        }
        logger.info("Request for adding new firestation details in the firestation record "
                + firestationEntity.getAddress());
        List<FirestationEntity> firestations = fileDataLoadingService.getFirestations();
        firestations.add(firestationEntity);
        logger.debug("adding the data to firestation");
        boolean isFileWritingSuccessful = fileDataLoadingService.updateDataFile
                (null,null,firestations);
        if (isFileWritingSuccessful){
            firestationResponseDTO.setMessage("Record created successfully");
            firestationResponseDTO.setStatusCode(200);
            logger.info("Successfully added the firestation address and number"+ firestationEntity.getAddress());
            return firestationResponseDTO;
        }
        firestationResponseDTO.setMessage("Record cannot be added");
        logger.info("Record cannot be added");
        return firestationResponseDTO;
    }

}
