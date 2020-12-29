package com.safetynet.safetynet.controller;
import com.safetynet.safetynet.dto.responses.PersonResponseDTO;
import com.safetynet.safetynet.model.PersonEntity;
import com.safetynet.safetynet.service.FileDataLoadingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class PersonController {
    private static final Logger logger = LogManager.getLogger(PersonController.class);

    @Autowired
    FileDataLoadingService fileDataLoadingService;

    /**
     * Delete the record searched with first and last name
     * @param fName
     * @param lName
     * @return First and last name from the record which is deleted
     */
    @DeleteMapping("/person")//http://localhost:8080/person
    public PersonResponseDTO deletePerson(@RequestParam ("fName") String fName, @RequestParam("lName") String lName) {
        PersonResponseDTO personResponseDTO = new PersonResponseDTO();
        personResponseDTO.setfName(fName);
        personResponseDTO.setlName(lName);
        personResponseDTO.setMessage("Could not find resource");
        personResponseDTO.setStatusCode(404);
        logger.info("Request for deleting person's record using first name and last name "+ fName + " " + lName);
        List<PersonEntity> persons = fileDataLoadingService.getPersons();
        for (int i = 0; i < persons.size(); i++) {
            if ((persons.get(i).getFirstName().equals(fName)) && (persons.get(i).getLastName().equals(lName))) {
                persons.remove(i);
                logger.debug("removing the data from person list "+ fName+" "+ lName);
                boolean isFileWritingSuccessful = fileDataLoadingService.updateDataFile
                        (persons,null,null);
                if(isFileWritingSuccessful){
                    personResponseDTO.setMessage("Delete Successful");
                    personResponseDTO.setStatusCode(200);
                    logger.info("Data is removed from the list");
                    return personResponseDTO;
                }
            }
        }
        personResponseDTO.setMessage("Record cannot be deleted");
        logger.info("No record is found");
        return personResponseDTO;
    }

    /**
     * Update the record searched with First and last name
     * @param personEntity
     * @return updated object
     */
    @PutMapping("/person")
    public PersonResponseDTO updatePerson(@RequestBody PersonEntity personEntity) {
        PersonResponseDTO personResponseDTO = new PersonResponseDTO();
        personResponseDTO.setfName(personEntity.getFirstName());
        personResponseDTO.setlName(personEntity.getLastName());
        personResponseDTO.setMessage("Could not find resource");
        personResponseDTO.setStatusCode(404);
        if (personEntity.getFirstName().equals(null)||personEntity.getLastName().equals(null)){
            personResponseDTO.setMessage("Either First name or Last name is null");
            personResponseDTO.setStatusCode(400);
            return personResponseDTO;
        }
        logger.info("Request for updating person record using First and last name "
                +personEntity.getFirstName() + " "+ personEntity.getLastName());
        List<PersonEntity> persons = fileDataLoadingService.getPersons();
        PersonEntity updatedPerson = null;
        for (int i = 0; i < persons.size(); i++) {
            if ((persons.get(i).getFirstName().equals(personEntity.getFirstName())) &&
                    (persons.get(i).getLastName().equals(personEntity.getLastName()))) {
                updatedPerson = persons.get(i);
                updatedPerson.setAddress((personEntity.getAddress()));
                updatedPerson.setCity(personEntity.getCity());
                updatedPerson.setEmail(personEntity.getEmail());
                updatedPerson.setPhone(personEntity.getPhone());
                updatedPerson.setZip(personEntity.getZip());
                logger.debug("updating the data from person list");
                boolean isFileWritingSuccessful = fileDataLoadingService.updateDataFile
                        (persons,null,null);
                if(isFileWritingSuccessful){
                    personResponseDTO.setMessage("Update Successful");
                    personResponseDTO.setStatusCode(200);
                    logger.info("Data is updated successfully ");
                    return personResponseDTO;
                }
            }
        }
        personResponseDTO.setMessage("Record cannot be updated");
        logger.info("Cannot find record");
        return personResponseDTO;
    }

    /**
     * Add a new record
     * @param personEntity
     * @return added object
     */
    @PostMapping("/person")
    public PersonResponseDTO addPerson(@RequestBody PersonEntity personEntity) {
        PersonResponseDTO personResponseDTO = new PersonResponseDTO();
        personResponseDTO.setfName(personEntity.getFirstName());
        personResponseDTO.setlName(personEntity.getLastName());
        personResponseDTO.setMessage("Could not add resource");
        personResponseDTO.setStatusCode(400);
        if ((personEntity.getFirstName().equals(null)) || (personEntity.getLastName().equals(null))) {
            return personResponseDTO;
        }
        List<PersonEntity> persons = fileDataLoadingService.getPersons();
        logger.info("Request for adding new person details in the person record" + personEntity.getFirstName()
                + " " + personEntity.getLastName());
        persons.add(personEntity);
        boolean isFileWritingSuccessful = fileDataLoadingService.updateDataFile
                (persons, null, null);
        if (isFileWritingSuccessful) {
            personResponseDTO.setMessage("Add Successful");
            personResponseDTO.setStatusCode(200);
            logger.info("Data is added successfully ");
            return personResponseDTO;
        }
        personResponseDTO.setMessage("Record cannot be added");
        logger.info("Could not add record");
        return personResponseDTO;
    }

}
