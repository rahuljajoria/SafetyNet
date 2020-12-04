package com.safetynet.safetynet.controller;
import com.safetynet.safetynet.model.PersonEntity;
import com.safetynet.safetynet.service.DataLoadingService;
import org.springframework.web.bind.annotation.*;


@RestController
public class PersonController {

    @DeleteMapping("/person")
    public void deletePerson(@RequestParam ("fName") String fName, @RequestParam("lName") String lName){
        for (int i = 0; i < DataLoadingService.persons.size(); i++) {
            if ((DataLoadingService.persons.get(i).getFirstName().equals(fName)) && (DataLoadingService.persons.get(i).getLastName().equals(lName))) {
                DataLoadingService.persons.remove(i);
                break;
            }
        }
    }

    @PutMapping("/person")
    public void updatePerson(@RequestBody PersonEntity personEntity){
//        if(personEntity.getFirstName() == null || personEntity.getLastName()){
//            throw new Exception();
//        }
        for (int i = 0; i < DataLoadingService.persons.size(); i++) {
            if ((DataLoadingService.persons.get(i).getFirstName().equals(personEntity.getFirstName())) &&
                    (DataLoadingService.persons.get(i).getLastName().equals(personEntity.getLastName()))) {
                DataLoadingService.persons.get(i).setAddress((personEntity.getAddress()));
                DataLoadingService.persons.get(i).setCity(personEntity.getCity());
                DataLoadingService.persons.get(i).setEmail(personEntity.getEmail());
                DataLoadingService.persons.get(i).setPhone(personEntity.getPhone());
                DataLoadingService.persons.get(i).setZip(personEntity.getZip());
                break;
            }
        }
    }

    @PostMapping("/person")
    public void addPerson(@RequestBody PersonEntity personEntity){
        DataLoadingService.persons.add(personEntity);
    }

}
