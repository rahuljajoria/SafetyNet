package com.safetynet.safetynet.controller;
import com.safetynet.safetynet.model.MedicalRecordEntity;
import com.safetynet.safetynet.service.DataLoadingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MedicalRecordsController {

    @DeleteMapping("/medicalRecord")
    public void deleteMedicalRecord(@RequestParam("fName") String fName, @RequestParam("lName") String lName){
        //LOGGER.info("Deleting the medical record for fname and last name fName", fName, lName);
        for (int i = 0; i < DataLoadingService.medicalRecords.size(); i++) {
            if ((DataLoadingService.medicalRecords.get(i).getFirstName().equals(fName)) &&
                    (DataLoadingService.medicalRecords.get(i).getLastName().equals(lName))) {
                DataLoadingService.medicalRecords.remove(i);
                break;
            }
        }
       // LOGGER.info("Successfully deleted the medical record for fname and last name fName", fName, lName);
    }

    @PutMapping("/medicalRecord")
    public void updateMedicalRecord(@RequestBody MedicalRecordEntity medicalRecordEntity){
//        if(personEntity.getFirstName() == null || personEntity.getLastName()){
//            throw new Exception();
//        }
        //@RequestBody
        for (int i = 0; i < DataLoadingService.medicalRecords.size(); i++) {
            if ((DataLoadingService.medicalRecords.get(i).getFirstName().equals(medicalRecordEntity.getFirstName())) &&
                    (DataLoadingService.medicalRecords.get(i).getLastName().equals(medicalRecordEntity.getLastName()))) {
                DataLoadingService.medicalRecords.get(i).setBirthDate(medicalRecordEntity.getBirthDate());
                DataLoadingService.medicalRecords.get(i).setAllergies(medicalRecordEntity.getAllergies());
                DataLoadingService.medicalRecords.get(i).setMedications(medicalRecordEntity.getMedications());
                break;
            }
        }
    }

    @PostMapping("/medicalRecord")
    public void addMedicalRecord(@RequestBody MedicalRecordEntity medicalRecordEntity){
        DataLoadingService.medicalRecords.add(medicalRecordEntity);
    }

}
