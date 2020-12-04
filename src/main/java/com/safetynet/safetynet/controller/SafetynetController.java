package com.safetynet.safetynet.controller;

import com.safetynet.safetynet.dto.*;
import com.safetynet.safetynet.model.FirestationEntity;
import com.safetynet.safetynet.model.MedicalRecordEntity;
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
        FireStationDTODetails.FirestationDTO firestationDTO;
        FireStationDTODetails fireStationDTODetails = new FireStationDTODetails();
        int noOfAdults = 0;
        int noOfChildren = 0;
        int temp = 0;
        List <String> addressList = new ArrayList<>();
        List <FireStationDTODetails.FirestationDTO> personInfo = new ArrayList<>();
        for (int i = 0; i <DataLoadingService.firestations.size(); i++) {
            if (DataLoadingService.firestations.get(i).getStation().equals(stationNumber)){
                addressList.add(DataLoadingService.firestations.get(i).getAddress());
            }
        }
        for (int i = 0; i < addressList.size() ; i++) {
            for (int j = 0; j < DataLoadingService.persons.size(); j++) {
                if (addressList.get(i).equals(DataLoadingService.persons.get(j).getAddress())){
                    firestationDTO = new FireStationDTODetails.FirestationDTO();
                    firestationDTO.setFirstName(DataLoadingService.persons.get(j).getFirstName());
                    firestationDTO.setLastName(DataLoadingService.persons.get(j).getLastName());
                    firestationDTO.setAddress(DataLoadingService.persons.get(j).getAddress());
                    firestationDTO.setPhone(DataLoadingService.persons.get(j).getPhone());
                    personInfo.add(firestationDTO);
                }
            }
        }
        for (int i = 0; i < personInfo.size(); i++) {
            for (int j = 0; j < DataLoadingService.medicalRecords.size(); j++) {
                if (personInfo.get(i).getFirstName().equals(DataLoadingService.medicalRecords.get(j).getFirstName()) &&
                personInfo.get(i).getLastName().equals(DataLoadingService.medicalRecords.get(j).getLastName())) {
                   temp = Integer.parseInt(DataLoadingService.medicalRecords.get(j).getBirthDate().substring(6,10));
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
        FireDTODetails.FireDTO fireDTO;
        FireDTODetails fireDTODetails = new FireDTODetails();
        List <FireDTODetails.FireDTO> personDetails = new ArrayList<>();
        for (int i = 0; i < DataLoadingService.firestations.size(); i++) {
            if(DataLoadingService.firestations.get(i).getAddress().equals(address)) {
                fireDTODetails.setStation(DataLoadingService.firestations.get(i).getStation());
                break;
            }
        }
        for (int i = 0; i < DataLoadingService.persons.size(); i++) {
            if (DataLoadingService.persons.get(i).getAddress().equals(address)) {
                fireDTO = new FireDTODetails.FireDTO();
                fireDTO.setFirstName(DataLoadingService.persons.get(i).getFirstName());
                fireDTO.setLastName(DataLoadingService.persons.get(i).getLastName());
                fireDTO.setPhone(DataLoadingService.persons.get(i).getPhone());
                fireDTO.setAge(DataLoadingService.medicalRecords.get(i).getBirthDate().substring(6,10));
                fireDTO.setAge(String.valueOf((date.getYear()+1900)-Integer.parseInt(fireDTO.getAge())));
                fireDTO.setMedication(DataLoadingService.medicalRecords.get(i).getMedications());
                fireDTO.setAllergies(DataLoadingService.medicalRecords.get(i).getAllergies());
                personDetails.add(fireDTO);
            }
        }
        fireDTODetails.setPersonDetails(personDetails);
        return fireDTODetails;
    }

    @RequestMapping("/flood/stations") //http://localhost:8080/flood/stations?stations=<a list of station_numbers>
    FloodDTODetails getFloodInfo (@RequestParam("stations") String stations){
        FloodDTODetails.DetailDTO.FloodDTO floodDTO;
        FloodDTODetails.DetailDTO detailDTO ;
        FloodDTODetails floodDTODetails = new FloodDTODetails();
        List <FloodDTODetails.DetailDTO.FloodDTO> floodDTOList;
        List <FloodDTODetails.DetailDTO> detailDTOArrayList = new ArrayList<>();
        List <String> addressList = new ArrayList<>();
        for (int i = 0; i < DataLoadingService.firestations.size(); i++) {
            if (DataLoadingService.firestations.get(i).getStation().equals(stations)) {
                addressList.add(DataLoadingService.firestations.get(i).getAddress());
            }
        }
        for (int i = 0; i < addressList.size(); i++) {
            detailDTO = new FloodDTODetails.DetailDTO();
            floodDTOList = new ArrayList<>();
            for (int j = 0; j < DataLoadingService.persons.size(); j++) {
                if (addressList.get(i).equals(DataLoadingService.persons.get(j).getAddress())) {
                   floodDTO = new FloodDTODetails.DetailDTO.FloodDTO();
                   floodDTO.setFirstName(DataLoadingService.persons.get(j).getFirstName());
                    floodDTO.setLastName(DataLoadingService.persons.get(j).getLastName());
                    floodDTO.setPhone(DataLoadingService.persons.get(j).getPhone());
                    floodDTO.setAge(DataLoadingService.medicalRecords.get(i).getBirthDate().substring(6,10));
                    floodDTO.setAge(String.valueOf((date.getYear()+1900)-Integer.parseInt(floodDTO.getAge())));
                    floodDTO.setMedications(DataLoadingService.medicalRecords.get(j).getMedications());
                    floodDTO.setAllergies(DataLoadingService.medicalRecords.get(j).getAllergies());
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
        PhoneAlertDTO phoneAlertDTO = new PhoneAlertDTO();
        List <String> addressList = new ArrayList<>();
        List <String> phoneNumberList = new ArrayList<>();
        for (int i = 0; i < DataLoadingService.firestations.size() ; i++) {
            if (DataLoadingService.firestations.get(i).getStation().equals(firestation)){
                addressList.add(DataLoadingService.firestations.get(i).getAddress());
            }
        }
        for (int i = 0; i < addressList.size() ; i++) {
            for (int j = 0; j < DataLoadingService.persons.size() ; j++) {
                if (addressList.get(i).equals(DataLoadingService.persons.get(j).getAddress())){
                    phoneNumberList.add(DataLoadingService.persons.get(j).getPhone());
                }
            }
        }
        phoneAlertDTO.setPhone(phoneNumberList);
        return phoneAlertDTO;
    }

    @RequestMapping("/communityEmail") //http://localhost:8080/communityEmail?city=<city>
    public CommunityEmailDTO getEmailAddress(@RequestParam("city")String city){
        CommunityEmailDTO communityEmailDTO = new CommunityEmailDTO();
        List <String> emailList = new ArrayList<>();
        for (int i = 0; i < DataLoadingService.persons.size() ; i++) {
            if (DataLoadingService.persons.get(i).getCity().equals(city)){
                emailList.add(DataLoadingService.persons.get(i).getEmail());
            }
        }
        communityEmailDTO.setEmail(emailList);
        return communityEmailDTO;
    }

    @RequestMapping("/childAlert") //http://localhost:8080/childAlert?address=<address>
    public ChildAlertDTODetails getChildAlert (@RequestParam("address") String address){
        ChildAlertDTODetails.ChildAlertDTO childAlertDTO;
        ChildAlertDTODetails childAlertDTODetails = new ChildAlertDTODetails();
        List <ChildAlertDTODetails.ChildAlertDTO> childrenDetails = new ArrayList<>();
        List <String> adultDetails = new ArrayList<>();
        for (int i = 0; i < DataLoadingService.persons.size(); i++) {
            if (DataLoadingService.persons.get(i).getAddress().equals(address)) {
                childAlertDTO = new ChildAlertDTODetails.ChildAlertDTO();
                childAlertDTO.setFirstName(DataLoadingService.persons.get(i).getFirstName());
                childAlertDTO.setLastName(DataLoadingService.persons.get(i).getLastName());
                //medical record
                childAlertDTO.setAge(DataLoadingService.medicalRecords.get(i).getBirthDate().substring(6,10));
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
        PersonDTODetails personInfoList = new PersonDTODetails();
        PersonDTODetails.PersonInfoDTO personInfoDTO ;
        List <PersonDTODetails.PersonInfoDTO> personInfo = new ArrayList<>();
        for (int i = 0; i < DataLoadingService.medicalRecords.size() ; i++) {
            if ((DataLoadingService.medicalRecords.get(i).getFirstName().equals(firstName)) &&
                    (DataLoadingService.medicalRecords.get(i).getLastName().equals(lastName))){
                personInfoDTO = new PersonDTODetails.PersonInfoDTO();
                personInfoDTO.setFirstName(DataLoadingService.medicalRecords.get(i).getFirstName());
                personInfoDTO.setLastName(DataLoadingService.medicalRecords.get(i).getLastName());
                personInfoDTO.setAddress(DataLoadingService.persons.get(i).getAddress());
                personInfoDTO.setEmail(DataLoadingService.persons.get(i).getEmail());
                //medical record
                personInfoDTO.setAge(DataLoadingService.medicalRecords.get(i).getBirthDate().substring(6,10));
                personInfoDTO.setAge(String.valueOf((date.getYear()+1900)-Integer.parseInt(personInfoDTO.getAge())));
                personInfoDTO.setMedications(DataLoadingService.medicalRecords.get(i).getMedications());
                personInfoDTO.setAllergies(DataLoadingService.medicalRecords.get(i).getAllergies());
                personInfo.add(personInfoDTO);
            }
        }
        personInfoList.setPersonInfo(personInfo);
        return personInfoList;
    }

}
