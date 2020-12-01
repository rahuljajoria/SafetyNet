package com.safetynet.safetynet.controller;

import com.safetynet.safetynet.dto.*;
import com.safetynet.safetynet.model.FirestationEntity;
import com.safetynet.safetynet.model.MedicalRecordsEntity;
import com.safetynet.safetynet.model.PersonEntity;
import com.safetynet.safetynet.service.DataLoadingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
public class SafetynetController {
    Date date = new Date();
    //loading the json file
   // @Value("classpath:data/data.json")
  //  Resource resourceFile;
    //  boolean checkIfFileExist = resourceFile.exists();
    //  System.out.println(checkIfFileExist);


    @RequestMapping("/firestation") //http://localhost:8080/firestation?stationNumber=<station_number>
    public FireStationDTODetails getPersonsFromFireStation(@RequestParam ("stationNumber") String stationNumber){
        List <PersonEntity> persons = DataLoadingService.persons;
        List <FirestationEntity> firestations = DataLoadingService.firestations;
        List <MedicalRecordsEntity> medicalRecords = DataLoadingService.medicalRecords;
        FireStationDTODetails.FirestationDTO firestationDTO;
        FireStationDTODetails fireStationDTODetails = new FireStationDTODetails();
        int noOfAdults = 0;
        int noOfChildren = 0;
        int temp = 0;
        List <String> addressList = new ArrayList<>();
        List <FireStationDTODetails.FirestationDTO> personInfo = new ArrayList<>();
        for (int i = 0; i <firestations.size(); i++) {
            if (firestations.get(i).getStation().equals(stationNumber)){
                addressList.add(firestations.get(i).getAddress());
            }
        }
        for (int i = 0; i < addressList.size() ; i++) {
            for (int j = 0; j < persons.size(); j++) {
                if (addressList.get(i).equals(persons.get(j).getAddress())){
                    firestationDTO = new FireStationDTODetails.FirestationDTO();
                    firestationDTO.setFirstName(persons.get(j).getFirstName());
                    firestationDTO.setLastName(persons.get(j).getLastName());
                    firestationDTO.setAddress(persons.get(j).getAddress());
                    firestationDTO.setPhone(persons.get(j).getPhone());
                    personInfo.add(firestationDTO);
                }
            }
        }
        for (int i = 0; i < personInfo.size(); i++) {
            for (int j = 0; j < medicalRecords.size(); j++) {
                if (personInfo.get(i).getFirstName().equals(medicalRecords.get(j).getFirstName()) &&
                personInfo.get(i).getLastName().equals(medicalRecords.get(j).getLastName())) {
                   temp = Integer.parseInt(medicalRecords.get(j).getBirthDate().substring(6,10));
                   temp = (date.getYear()+1900) - (temp);
                   if (temp > 18)
                   {
                       noOfAdults++;
                   }
                   else {
                       noOfChildren++;
                   }
                }
            }
        }
        fireStationDTODetails.setPersonsInfo(personInfo);
        fireStationDTODetails.setNoOfAdults(String.valueOf(noOfAdults));
        fireStationDTODetails.setNoOfChildren(String.valueOf(noOfChildren));
        return fireStationDTODetails;
    }

    @RequestMapping("/fire") //http://localhost:8080/fire?address=<address>
    public FireDTODetails getAddressInfo (@RequestParam("address") String address){
        List <PersonEntity> persons = DataLoadingService.persons;
        List <FirestationEntity> firestations = DataLoadingService.firestations;
        List <MedicalRecordsEntity> medicalRecords = DataLoadingService.medicalRecords;
        FireDTODetails.FireDTO fireDTO;
        FireDTODetails fireDTODetails = new FireDTODetails();
        List <FireDTODetails.FireDTO> personDetails = new ArrayList<>();
        for (int i = 0; i < firestations.size(); i++) {
            if(firestations.get(i).getAddress().equals(address)) {
                fireDTODetails.setStation(firestations.get(i).getStation());
                break;
            }
        }
        for (int i = 0; i < persons.size(); i++) {
            if (persons.get(i).getAddress().equals(address)) {
                fireDTO = new FireDTODetails.FireDTO();
                fireDTO.setFirstName(persons.get(i).getFirstName());
                fireDTO.setLastName(persons.get(i).getLastName());
                fireDTO.setPhone(persons.get(i).getPhone());
                // medical records
                fireDTO.setAge(medicalRecords.get(i).getBirthDate().substring(6,10));
                fireDTO.setAge(String.valueOf((date.getYear()+1900)-Integer.parseInt(fireDTO.getAge())));
                fireDTO.setMedication(medicalRecords.get(i).getMedications());
                fireDTO.setAllergies(medicalRecords.get(i).getAllergies());
                personDetails.add(fireDTO);
            }
        }
        fireDTODetails.setPersonDetails(personDetails);
        return fireDTODetails;
    }

    @RequestMapping("/flood/stations") //http://localhost:8080/flood/stations?stations=<a list of station_numbers>
    FloodDTODetails getFloodInfo (@RequestParam("stations") String stations){
        List <PersonEntity> persons = DataLoadingService.persons;
        List <FirestationEntity> firestations = DataLoadingService.firestations;
        List <MedicalRecordsEntity> medicalRecords = DataLoadingService.medicalRecords;
        FloodDTODetails.DetailDTO.FloodDTO floodDTO;
        FloodDTODetails.DetailDTO detailDTO ;
        FloodDTODetails floodDTODetails = new FloodDTODetails();
        List <FloodDTODetails.DetailDTO.FloodDTO> floodDTOList;
        List <FloodDTODetails.DetailDTO> detailDTOArrayList = new ArrayList<>();
        List <String> addressList = new ArrayList<>();
        for (int i = 0; i < firestations.size(); i++) {
            if (firestations.get(i).getStation().equals(stations)) {
                addressList.add(firestations.get(i).getAddress());
            }
        }
        for (int i = 0; i < addressList.size(); i++) {
            detailDTO = new FloodDTODetails.DetailDTO();
            floodDTOList = new ArrayList<>();
            for (int j = 0; j < persons.size(); j++) {
                if (addressList.get(i).equals(persons.get(j).getAddress())) {
                   floodDTO = new FloodDTODetails.DetailDTO.FloodDTO();
                   floodDTO.setFirstName(persons.get(j).getFirstName());
                    floodDTO.setLastName(persons.get(j).getLastName());
                    floodDTO.setPhone(persons.get(j).getPhone());
                    //medical record
                    floodDTO.setAge(medicalRecords.get(i).getBirthDate().substring(6,10));
                    floodDTO.setAge(String.valueOf((date.getYear()+1900)-Integer.parseInt(floodDTO.getAge())));
                    floodDTO.setMedications(medicalRecords.get(j).getMedications());
                    floodDTO.setAllergies(medicalRecords.get(j).getAllergies());
                    floodDTOList.add(floodDTO);
                }
            }
            detailDTO.setAddress(addressList.get(i));
            detailDTO.setPersons(floodDTOList);
            detailDTOArrayList.add(detailDTO);
        }
        floodDTODetails.setDetails(detailDTOArrayList);
        return floodDTODetails;
    }

    @RequestMapping("/phoneAlert") //http://localhost:8080/phoneAlert?firestation=<firestation_number>
    public PhoneAlertDTO getPhonesNumbers(@RequestParam("firestation")String firestation){
        List<PersonEntity> persons = DataLoadingService.persons;
        List <FirestationEntity> firestations = DataLoadingService.firestations;
        PhoneAlertDTO phoneAlertDTO = new PhoneAlertDTO();
        List <String> addressList = new ArrayList<>();
        List <String> phoneNumberList = new ArrayList<>();
        for (int i = 0; i < firestations.size() ; i++) {
            if (firestations.get(i).getStation().equals(firestation)){
                addressList.add(firestations.get(i).getAddress());
            }
        }
        for (int i = 0; i < addressList.size() ; i++) {
            for (int j = 0; j < persons.size() ; j++) {
                if (addressList.get(i).equals(persons.get(j).getAddress())){
                    phoneNumberList.add(persons.get(j).getPhone());
                }
            }
        }
        phoneAlertDTO.setPhone(phoneNumberList);
        return phoneAlertDTO;
    }

    @RequestMapping("/communityEmail") //http://localhost:8080/communityEmail?city=<city>
    public CommunityEmailDTO getEmailAddress(@RequestParam("city")String city){
        List<PersonEntity> persons = DataLoadingService.persons;
        CommunityEmailDTO communityEmailDTO = new CommunityEmailDTO();
        List <String> emailList = new ArrayList<>();
        for (int i = 0; i < persons.size() ; i++) {
            if (persons.get(i).getCity().equals(city)){
                emailList.add(persons.get(i).getEmail());
            }
        }
        communityEmailDTO.setEmail(emailList);
        return communityEmailDTO;

    }

    @RequestMapping("/childAlert") //http://localhost:8080/childAlert?address=<address>
    public ChildAlertDTODetails getChildAlert (@RequestParam("address") String address){
        List<PersonEntity> persons = DataLoadingService.persons;
        List <MedicalRecordsEntity> medicalRecords = DataLoadingService.medicalRecords;
        ChildAlertDTODetails.ChildAlertDTO childAlertDTO;
        ChildAlertDTODetails childAlertDTODetails = new ChildAlertDTODetails();
        List <ChildAlertDTODetails.ChildAlertDTO> childrenDetails = new ArrayList<>();
        List <String> adultDetails = new ArrayList<>();
        for (int i = 0; i < persons.size(); i++) {
            if (persons.get(i).getAddress().equals(address)) {
                childAlertDTO = new ChildAlertDTODetails.ChildAlertDTO();
                childAlertDTO.setFirstName(persons.get(i).getFirstName());
                childAlertDTO.setLastName(persons.get(i).getLastName());
                //medical record
                childAlertDTO.setAge(medicalRecords.get(i).getBirthDate().substring(6,10));
                childAlertDTO.setAge(String.valueOf((date.getYear()+1900)-Integer.parseInt(childAlertDTO.getAge())));
                if (Integer.parseInt(childAlertDTO.getAge())<18) {
                    childrenDetails.add(childAlertDTO);
                }
                else {
                    String adultDetail = childAlertDTO.getFirstName()+" "+childAlertDTO.getLastName();
                    adultDetails.add(adultDetail);
                }
            }
        }
        for (int i = 0; i < childrenDetails.size(); i++) {
            if (Integer.parseInt(childrenDetails.get(i).getAge()) >= 18) {
                String adultDetail = childrenDetails.get(i).getFirstName()+" "+childrenDetails.get(i).getLastName();
                adultDetails.add(adultDetail);
            }
        }
        if (childrenDetails.size() == 0){
            adultDetails.clear();
        }
        childAlertDTODetails.setChildrenDetails(childrenDetails);
        childAlertDTODetails.setPersonDetails(adultDetails);
        return childAlertDTODetails;
    }

    @RequestMapping("/personInfo") //http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
    public PersonDTODetails getPersonInfo(@RequestParam("firstName")String firstName,
                                          @RequestParam("lastName")String lastName){
        List<PersonEntity> persons = DataLoadingService.persons;
        List <MedicalRecordsEntity> medicalRecords = DataLoadingService.medicalRecords;
        PersonDTODetails personInfoList = new PersonDTODetails();
        PersonDTODetails.PersonInfoDTO personInfoDTO ;
        List <PersonDTODetails.PersonInfoDTO> personInfo = new ArrayList<>();
        for (int i = 0; i < medicalRecords.size() ; i++) {
            if ((medicalRecords.get(i).getFirstName().equals(firstName)) &&
                    (medicalRecords.get(i).getLastName().equals(lastName))){
                personInfoDTO = new PersonDTODetails.PersonInfoDTO();
                personInfoDTO.setFirstName(medicalRecords.get(i).getFirstName());
                personInfoDTO.setLastName(medicalRecords.get(i).getLastName());
                personInfoDTO.setAddress(persons.get(i).getAddress());
                personInfoDTO.setEmail(persons.get(i).getEmail());
                //medical record
                personInfoDTO.setAge(medicalRecords.get(i).getBirthDate().substring(6,10));
                personInfoDTO.setAge(String.valueOf((date.getYear()+1900)-Integer.parseInt(personInfoDTO.getAge())));
                personInfoDTO.setMedications(medicalRecords.get(i).getMedications());
                personInfoDTO.setAllergies(medicalRecords.get(i).getAllergies());
                personInfo.add(personInfoDTO);
            }
        }
        personInfoList.setPersonInfo(personInfo);
        return personInfoList;
    }

}
