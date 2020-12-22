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
                personResponseDTO.setMessage("Delete Successful");
                personResponseDTO.setStatusCode(200);
                fileDataLoadingService.updateDataFile(persons,null,null);
                logger.info("Data is removed from the list");
                return personResponseDTO;
            }
        }
        logger.info("No record is found");
        return personResponseDTO;
    }

    @PutMapping("/person")
    public PersonResponseDTO updatePerson(@RequestBody PersonEntity personEntity) {
        PersonResponseDTO personResponseDTO = new PersonResponseDTO();
        personResponseDTO.setfName(personEntity.getFirstName());
        personResponseDTO.setlName(personEntity.getLastName());
        personResponseDTO.setMessage("Could not find resource");
        personResponseDTO.setStatusCode(404);
        if (personEntity.getFirstName().equals(null)||personEntity.getLastName().equals(null)){
            personResponseDTO.setMessage("Either First name or Last name is null");
            personResponseDTO.setStatusCode(500);
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
                personResponseDTO.setMessage("Update successful");
                personResponseDTO.setStatusCode(200);
                fileDataLoadingService.updateDataFile(persons,null,null);
                logger.info("Data is successfully updated in the list");
                return personResponseDTO;
            }
        }
        logger.info("No record is found");
        return personResponseDTO;
    }

    @PostMapping("/person")
    public PersonResponseDTO addPerson(@RequestBody PersonEntity personEntity) {
        PersonResponseDTO personResponseDTO = new PersonResponseDTO();
        personResponseDTO.setfName(personEntity.getFirstName());
        personResponseDTO.setlName(personEntity.getLastName());
        personResponseDTO.setMessage("Could not add resource");
        personResponseDTO.setStatusCode(500);
        if (personEntity.getFirstName().equals(null)||personEntity.getLastName().equals(null)){
            return personResponseDTO;
        }
        List<PersonEntity> persons = fileDataLoadingService.getPersons();
        logger.info("Request for adding new person details in the person record" + personEntity.getFirstName()
                + " "+ personEntity.getLastName());
        persons.add(personEntity);
        personResponseDTO.setMessage("Record created successfully");
        personResponseDTO.setStatusCode(200);
        logger.debug("Data added to the person list");
        fileDataLoadingService.updateDataFile(persons,null,null);
        logger.info("Data is successfully added in the list");
        return personResponseDTO;
    }

}
