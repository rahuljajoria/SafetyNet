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

    /**
     * Delete the record searched with first and last name
     * @param fName
     * @param lName
     * @return First and last name from the record which is deleted
     */
    @DeleteMapping("/medicalRecord")//http://localhost:8080/medicalRecord
    public MedicalRecordResponseDTO deleteMedicalRecord
            (@RequestParam("fName") String fName, @RequestParam("lName") String lName) {
        MedicalRecordResponseDTO medicalRecordResponseDTO = new MedicalRecordResponseDTO();
        medicalRecordResponseDTO.setFname(fName);
        medicalRecordResponseDTO.setLname(lName);
        medicalRecordResponseDTO.setMessage("Could not find resource");
        medicalRecordResponseDTO.setStatusCode(404);
        logger.info("Request for deleting a person's record using first and last name" +fName+" "+lName);
        List<MedicalRecordEntity> medicalRecords = fileDataLoadingService.getMedicalRecords();
        for (int i = 0; i < medicalRecords.size(); i++) {
            if ((medicalRecords.get(i).getFirstName().equals(fName)) &&
                    (medicalRecords.get(i).getLastName().equals(lName))) {
                medicalRecords.remove(i);
                logger.debug("removing the data from medical records list "+ fName+" "+ lName);
                boolean isFileWritingSuccessful =  fileDataLoadingService.updateDataFile
                        (null,medicalRecords,null);
                if(isFileWritingSuccessful){
                    medicalRecordResponseDTO.setMessage("Delete successful");
                    medicalRecordResponseDTO.setStatusCode(200);
                    logger.info("Data is removed successfully from the list");
                    return medicalRecordResponseDTO;
                }
            }
        }
        medicalRecordResponseDTO.setMessage("Record cannot be deleted");
        logger.info("Could not delete record");
        return medicalRecordResponseDTO;
    }

    /**
     * Update the record searched with First and last name
     * @param medicalRecordEntity
     * @return updated object
     */
    @PutMapping("/medicalRecord")
    public MedicalRecordResponseDTO updateMedicalRecord(@RequestBody MedicalRecordEntity medicalRecordEntity) {
        MedicalRecordResponseDTO medicalRecordResponseDTO = new MedicalRecordResponseDTO();
        medicalRecordResponseDTO.setFname(medicalRecordEntity.getFirstName());
        medicalRecordResponseDTO.setLname(medicalRecordEntity.getLastName());
        medicalRecordResponseDTO.setMessage("Could not find resource");
        medicalRecordResponseDTO.setStatusCode(404);
        if (medicalRecordEntity.getFirstName().equals(null)||medicalRecordEntity.getLastName().equals(null)){
            medicalRecordResponseDTO.setMessage("Either First name or Last name is null");
            medicalRecordResponseDTO.setStatusCode(400);
            return medicalRecordResponseDTO;
        }
        logger.info("Request for updating medical record using First and last name" +
                medicalRecordEntity.getFirstName() +" "+ medicalRecordEntity.getLastName());
        List<MedicalRecordEntity> medicalRecords = fileDataLoadingService.getMedicalRecords();
        MedicalRecordEntity updatedMedicalRecord = null;
        for (int i = 0; i < medicalRecords.size(); i++) {
            if ((medicalRecords.get(i).getFirstName().equals(medicalRecordEntity.getFirstName())) &&
                    (medicalRecords.get(i).getLastName().equals(medicalRecordEntity.getLastName())))
            {
                updatedMedicalRecord = medicalRecords.get(i);
                updatedMedicalRecord.setBirthDate(medicalRecordEntity.getBirthDate());
                updatedMedicalRecord.setAllergies(medicalRecordEntity.getAllergies());
                updatedMedicalRecord.setMedications(medicalRecordEntity.getMedications());
                logger.debug("updating the data from person list");
                boolean isFileWritingSuccessful =  fileDataLoadingService.updateDataFile
                        (null,medicalRecords,null);
                if(isFileWritingSuccessful){
                    medicalRecordResponseDTO.setMessage("Update successful");
                    medicalRecordResponseDTO.setStatusCode(200);
                    logger.info("Data is updated successfully ");
                    return medicalRecordResponseDTO;
                }
            }
        }
        medicalRecordResponseDTO.setMessage("Record cannot be updated");
        logger.info("Could not update record");
        return medicalRecordResponseDTO;
    }

    /**
     * Add a new record
     * @param medicalRecordEntity
     * @return added object
     */
    @PostMapping("/medicalRecord")
    public MedicalRecordResponseDTO addMedicalRecord(@RequestBody MedicalRecordEntity medicalRecordEntity) {
        MedicalRecordResponseDTO medicalRecordResponseDTO = new MedicalRecordResponseDTO();
        medicalRecordResponseDTO.setFname(medicalRecordEntity.getFirstName());
        medicalRecordResponseDTO.setLname(medicalRecordEntity.getLastName());
        medicalRecordResponseDTO.setMessage("Could not add record");
        medicalRecordResponseDTO.setStatusCode(400);
        if (medicalRecordEntity.getFirstName().equals(null)||medicalRecordEntity.getLastName().equals(null)){
            return medicalRecordResponseDTO;
        }
        List<MedicalRecordEntity> medicalRecords = fileDataLoadingService.getMedicalRecords();
        logger.info("Request for adding new person details in the person record" + medicalRecordEntity.getFirstName()
                + " "+ medicalRecordEntity.getLastName());
        medicalRecords.add(medicalRecordEntity);
        logger.debug("removing the data from medical records list "+
                medicalRecordEntity.getFirstName()+" "+ medicalRecordEntity.getLastName());
        boolean isFileWritingSuccessful =  fileDataLoadingService.updateDataFile
                (null,medicalRecords,null);
        if(isFileWritingSuccessful){
            medicalRecordResponseDTO.setMessage("add successful");
            medicalRecordResponseDTO.setStatusCode(200);
            logger.info("Data is added successfully");
            return medicalRecordResponseDTO;
        }
        medicalRecordResponseDTO.setMessage("Record cannot be added");
                logger.info("Could not add record");
                return medicalRecordResponseDTO;
    }

}
