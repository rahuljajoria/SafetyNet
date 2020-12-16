package com.safetynet.safetynet.controller;
import com.safetynet.safetynet.model.PersonEntity;
import com.safetynet.safetynet.service.FileDataLoadingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
public class PersonController {
    private static final Logger logger = LogManager.getLogger(PersonController.class);

    @DeleteMapping("/person")//http://localhost:8080/person
    public void deletePerson(@RequestParam ("fName") String fName, @RequestParam("lName") String lName)
            throws IOException {
        logger.info("Request --"+"http://localhost:8080/person?firstName="+fName+"&lastName="+lName);
        List<PersonEntity> persons = FileDataLoadingService.persons;
        for (int i = 0; i < persons.size(); i++) {
            if ((persons.get(i).getFirstName().equals(fName)) && (persons.get(i).getLastName().equals(lName))) {
                persons.remove(i);
                logger.debug("removing the data from person list "+ fName+" "+ lName);
                break;
            }
            FileDataLoadingService fileDataLoadingService = new FileDataLoadingService();
            fileDataLoadingService.updateDataFile(persons,null,null);
            logger.info("Data is removed from the list");
        }
    }

    @PutMapping("/person")
    public void updatePerson(@RequestBody PersonEntity personEntity) throws IOException {
        logger.info("Request --"+"http://localhost:8080/person");
        List<PersonEntity> persons = FileDataLoadingService.persons;
        for (int i = 0; i < persons.size(); i++) {
            if ((persons.get(i).getFirstName().equals(personEntity.getFirstName())) &&
                    (persons.get(i).getLastName().equals(personEntity.getLastName()))) {
                persons.get(i).setAddress((personEntity.getAddress()));
                persons.get(i).setCity(personEntity.getCity());
                persons.get(i).setEmail(personEntity.getEmail());
                persons.get(i).setPhone(personEntity.getPhone());
                persons.get(i).setZip(personEntity.getZip());
                logger.debug("updating the data from person list");
                break;
            }
            FileDataLoadingService fileDataLoadingService = new FileDataLoadingService();
            fileDataLoadingService.updateDataFile(persons,null,null);
            logger.info("Data is successfully updated in the list");
        }
    }

    @PostMapping("/person")
    public void addPerson(@RequestBody PersonEntity personEntity) throws IOException {
        List<PersonEntity> persons = FileDataLoadingService.persons;
        logger.info("Request --"+"http://localhost:8080/person");
        persons.add(personEntity);
        logger.debug("Data added to the person list");
        FileDataLoadingService fileDataLoadingService = new FileDataLoadingService();
        fileDataLoadingService.updateDataFile(persons,null,null);
        logger.info("Data is successfully added in the list");
    }

}
