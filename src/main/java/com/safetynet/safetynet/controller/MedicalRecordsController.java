package com.safetynet.safetynet.controller;
import com.safetynet.safetynet.model.MedicalRecordEntity;
import com.safetynet.safetynet.service.DataLoadingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
public class MedicalRecordsController {
    private static final Logger logger = LogManager.getLogger("MedicalRecordsController");

    @DeleteMapping("/medicalRecord")//http://localhost:8080/medicalRecord
    public void deleteMedicalRecord(@RequestParam("fName") String fName, @RequestParam("lName") String lName)
            throws IOException {
        logger.info("Request --"+"http://localhost:8080/medicalRecord?firstName="+fName+"&lastName="+lName);
        List<MedicalRecordEntity> medicalRecords = DataLoadingService.medicalRecords;
        for (int i = 0; i < medicalRecords.size(); i++) {
            if ((medicalRecords.get(i).getFirstName().equals(fName)) &&
                    (medicalRecords.get(i).getLastName().equals(lName))) {
                medicalRecords.remove(i);
                logger.debug("removing the data from medical records list "+ fName+" "+ lName);
                break;
            }
        }
        DataLoadingService dataLoadingService = new DataLoadingService();
        dataLoadingService.updateDataFile(null,medicalRecords,null);
        logger.info("Data is removed successfully from the list");
    }

    @PutMapping("/medicalRecord")
    public void updateMedicalRecord(@RequestBody MedicalRecordEntity medicalRecordEntity) throws IOException {
        List<MedicalRecordEntity> medicalRecords = DataLoadingService.medicalRecords;
        for (int i = 0; i < medicalRecords.size(); i++) {
            if ((medicalRecords.get(i).getFirstName().equals(medicalRecordEntity.getFirstName())) &&
                    (medicalRecords.get(i).getLastName().equals(medicalRecordEntity.getLastName())))
            {
                medicalRecords.get(i).setBirthDate(medicalRecordEntity.getBirthDate());
                medicalRecords.get(i).setAllergies(medicalRecordEntity.getAllergies());
                medicalRecords.get(i).setMedications(medicalRecordEntity.getMedications());
                logger.debug("updating the data from medicalRecords");
                break;
            }
        }
        DataLoadingService dataLoadingService = new DataLoadingService();
        dataLoadingService.updateDataFile(null,medicalRecords,null);
        logger.info("Data is successfully updated in the list");
    }

    @PostMapping("/medicalRecord")
    public void addMedicalRecord(@RequestBody MedicalRecordEntity medicalRecordEntity) throws IOException {
        logger.info("Request --"+"http://localhost:8080/medicalRecord");
        List<MedicalRecordEntity> medicalRecords = DataLoadingService.medicalRecords;
        medicalRecords.add(medicalRecordEntity);
        logger.debug("Data added to the medical record list");
        DataLoadingService dataLoadingService = new DataLoadingService();
        dataLoadingService.updateDataFile(null,medicalRecords,null);
        logger.info("Data is successfully added in the list");
    }

}
