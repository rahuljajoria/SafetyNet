package com.safetynet.safetynet.controller;
import com.safetynet.safetynet.dto.responses.MedicalRecordResponseDTO;
import com.safetynet.safetynet.model.MedicalRecordEntity;
import com.safetynet.safetynet.service.FileDataLoadingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class MedicalRecordsController {
    private static final Logger logger = LogManager.getLogger(MedicalRecordsController.class);

    @Autowired
    FileDataLoadingService fileDataLoadingService;

    @DeleteMapping("/medicalRecord")//http://localhost:8080/medicalRecord
    public MedicalRecordResponseDTO deleteMedicalRecord
            (@RequestParam("fName") String fName, @RequestParam("lName") String lName) {
        MedicalRecordResponseDTO medicalRecordResponseDTO =
                new MedicalRecordResponseDTO(fName,lName,"Could not find resource",404);
        logger.info("Request for deleting a person's record using first and last name" +fName+" "+lName);
        List<MedicalRecordEntity> medicalRecords = FileDataLoadingService.medicalRecords;
        for (int i = 0; i < medicalRecords.size(); i++) {
            if ((medicalRecords.get(i).getFirstName().equals(fName)) &&
                    (medicalRecords.get(i).getLastName().equals(lName))) {
                medicalRecords.remove(i);
                medicalRecordResponseDTO.setErrorMessage("Delete successful");
                medicalRecordResponseDTO.setStatusCode(200);
                logger.debug("removing the data from medical records list "+ fName+" "+ lName);
                break;
            }
        }
        fileDataLoadingService.updateDataFile(null,medicalRecords,null);
        logger.info("Data is removed successfully from the list");
        return medicalRecordResponseDTO;
    }

    @PutMapping("/medicalRecord")
    public MedicalRecordResponseDTO updateMedicalRecord(@RequestBody MedicalRecordEntity medicalRecordEntity) {
        MedicalRecordResponseDTO medicalRecordResponseDTO =
                new MedicalRecordResponseDTO(medicalRecordEntity.getFirstName(), medicalRecordEntity.getLastName(),
                        "Could not find resource",404);
        if (medicalRecordEntity.getFirstName().equals(null)||medicalRecordEntity.getLastName().equals(null)){
            medicalRecordResponseDTO.setErrorMessage("Either First name or Last name is null");
            medicalRecordResponseDTO.setStatusCode(500);
            return medicalRecordResponseDTO;
        }
        logger.info("Request for updating medical record using First and last name" +
                medicalRecordEntity.getFirstName() +" "+ medicalRecordEntity.getLastName());
        List<MedicalRecordEntity> medicalRecords = FileDataLoadingService.medicalRecords;
        MedicalRecordEntity updatedMedicalRecord = null;
        for (int i = 0; i < medicalRecords.size(); i++) {
            if ((medicalRecords.get(i).getFirstName().equals(medicalRecordEntity.getFirstName())) &&
                    (medicalRecords.get(i).getLastName().equals(medicalRecordEntity.getLastName())))
            {
                updatedMedicalRecord = medicalRecords.get(i);
                updatedMedicalRecord.setBirthDate(medicalRecordEntity.getBirthDate());
                updatedMedicalRecord.setAllergies(medicalRecordEntity.getAllergies());
                updatedMedicalRecord.setMedications(medicalRecordEntity.getMedications());
                logger.debug("updating the data from medicalRecords");
                medicalRecordResponseDTO.setErrorMessage("Update successful");
                medicalRecordResponseDTO.setStatusCode(200);
                break;
            }
        }
        fileDataLoadingService.updateDataFile(null,medicalRecords,null);
        logger.info("Data is successfully updated in the list");
        return medicalRecordResponseDTO;
    }

    @PostMapping("/medicalRecord")
    public MedicalRecordResponseDTO addMedicalRecord(@RequestBody MedicalRecordEntity medicalRecordEntity) {
        MedicalRecordResponseDTO medicalRecordResponseDTO =
                new MedicalRecordResponseDTO(medicalRecordEntity.getFirstName(), medicalRecordEntity.getLastName(),
                        "Could not add resource",500);
        if (medicalRecordEntity.getFirstName().equals(null)||medicalRecordEntity.getLastName().equals(null)){
            return medicalRecordResponseDTO;
        }
        List<MedicalRecordEntity> medicalRecords = FileDataLoadingService.medicalRecords;
        logger.info("Request for adding new person details in the person record" + medicalRecordEntity.getFirstName()
                + " "+ medicalRecordEntity.getLastName());
        medicalRecords.add(medicalRecordEntity);
        medicalRecordResponseDTO.setErrorMessage("Record created successfully");
        medicalRecordResponseDTO.setStatusCode(200);
        logger.debug("Data added to the medical record list");
        fileDataLoadingService.updateDataFile(null,medicalRecords,null);
        logger.info("Data is successfully added in the list");
        return medicalRecordResponseDTO;
    }

}
