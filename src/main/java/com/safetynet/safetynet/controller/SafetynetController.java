package com.safetynet.safetynet.controller;

import com.google.gson.Gson;
import com.safetynet.safetynet.dto.*;
import com.safetynet.safetynet.model.FirestationEntity;
import com.safetynet.safetynet.model.MedicalRecordEntity;
import com.safetynet.safetynet.model.PersonEntity;
import com.safetynet.safetynet.service.FileDataLoadingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.safetynet.safetynet.utils.AgeCalculator.calculateAge;


@RestController
public class SafetynetController {
    private static final Logger logger = LogManager.getLogger(SafetynetController.class);
    Gson gson = new Gson();

    @Autowired
    FileDataLoadingService fileDataLoadingService;

    /**
     * Search on the basis of firestation number
     * @param stationNumber
     * @return a list of people serviced by the corresponding firestation
     */
    @RequestMapping("/firestation") //http://localhost:8080/firestation?stationNumber=<station_number>
    public FireStationDTODetails getPersonsFromFireStation(@RequestParam("stationNumber") String stationNumber) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate currDate = LocalDate.now();
        logger.info("Request for a list of people serviced by the corresponding firestation " + stationNumber);
        FireStationDTODetails.FirestationDTO firestationDTO;
        FireStationDTODetails fireStationDTODetails = new FireStationDTODetails();
        fireStationDTODetails.setMessage("No data found");
        fireStationDTODetails.setStatusCode(404);
        List<PersonEntity> persons = fileDataLoadingService.getPersons();
        List<MedicalRecordEntity> medicalRecords = fileDataLoadingService.getMedicalRecords();
        List<FirestationEntity> firestations = fileDataLoadingService.getFirestations();
        int noOfAdults = 0;
        int noOfChildren = 0;
        int age;
        List<String> addressList = new ArrayList<>();
        List<FireStationDTODetails.FirestationDTO> personInfo = new ArrayList<>();
        for (int i = 0; i < firestations.size(); i++) {
            if (firestations.get(i).getStation().equals(stationNumber)) {
                addressList.add(firestations.get(i).getAddress());
            }
        }
        logger.debug("Data from the address list " + addressList);
        for (int i = 0; i < addressList.size(); i++) {
            for (int j = 0; j < persons.size(); j++) {
                if (addressList.get(i).equals(persons.get(j).getAddress())) {
                    firestationDTO = new FireStationDTODetails.FirestationDTO();
                    firestationDTO.setFirstName(persons.get(j).getFirstName());
                    firestationDTO.setLastName(persons.get(j).getLastName());
                    firestationDTO.setAddress(persons.get(j).getAddress());
                    firestationDTO.setPhone(persons.get(j).getPhone());
                    personInfo.add(firestationDTO);
                }
            }
        }
        logger.debug("Data from the person list " + gson.toJson(personInfo));
        for (int i = 0; i < personInfo.size(); i++) {
            for (int j = 0; j < medicalRecords.size(); j++) {
                if (personInfo.get(i).getFirstName().equals(medicalRecords.get(j).getFirstName()) &&
                        personInfo.get(i).getLastName().equals(medicalRecords.get(j).getLastName())) {
                    String date = medicalRecords.get(j).getBirthDate();
                    LocalDate localDate = LocalDate.parse(date, format);
                    age = calculateAge(localDate, currDate);
                    if (age > 18) {
                        noOfAdults++;
                    } else {
                        noOfChildren++;
                    }
                }
            }
        }
        logger.debug("Calculated no of adults " + noOfAdults);
        logger.debug("Calculated no of children " + noOfChildren);
        fireStationDTODetails.setPersonsInfo(personInfo);
        fireStationDTODetails.setNoOfAdults(String.valueOf(noOfAdults));
        fireStationDTODetails.setNoOfChildren(String.valueOf(noOfChildren));
        if (!(fireStationDTODetails.getPersonsInfo().isEmpty())) {
            fireStationDTODetails.setMessage("Success");
            fireStationDTODetails.setStatusCode(200);
        }
        logger.info("Response --" + gson.toJson(fireStationDTODetails));
        return fireStationDTODetails;
    }

    /**
     * Search on the basis of address
     * @param address
     * @return fire station number that services the provided address as well as a list of
     * all of the people living at the address
     */
    @RequestMapping("/fire") //http://localhost:8080/fire?address=<address>
    public FireDTODetails getAddressInfo(@RequestParam("address") String address) {
        logger.info("Request is returning fire station number that services the provided address " +
                "as well as a list of all of the people living at the address " + address);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate currDate = LocalDate.now();
        FireDTODetails.FireDTO fireDTO;
        FireDTODetails fireDTODetails = new FireDTODetails();
        fireDTODetails.setMessage("No data found");
        fireDTODetails.setStatusCode(404);
        List<PersonEntity> persons = fileDataLoadingService.getPersons();
        List<MedicalRecordEntity> medicalRecords = fileDataLoadingService.getMedicalRecords();
        List<FirestationEntity> firestations = fileDataLoadingService.getFirestations();
        List<FireDTODetails.FireDTO> personDetails = new ArrayList<>();
        for (int i = 0; i < firestations.size(); i++) {
            if (firestations.get(i).getAddress().equals(address)) {
                fireDTODetails.setStation(firestations.get(i).getStation());
                break;
            }
        }
        logger.debug("Station number " + fireDTODetails.getStation());
        for (int i = 0; i < persons.size(); i++) {
            if (persons.get(i).getAddress().equals(address)) {
                fireDTO = new FireDTODetails.FireDTO();
                fireDTO.setFirstName(persons.get(i).getFirstName());
                fireDTO.setLastName(persons.get(i).getLastName());
                fireDTO.setPhone(persons.get(i).getPhone());
                String date = medicalRecords.get(i).getBirthDate();
                LocalDate localDate = LocalDate.parse(date, format);
                fireDTO.setAge(String.valueOf(calculateAge(localDate, currDate)));
                fireDTO.setMedication(medicalRecords.get(i).getMedications());
                fireDTO.setAllergies(medicalRecords.get(i).getAllergies());
                personDetails.add(fireDTO);
            }
        }
        fireDTODetails.setPersonDetails(personDetails);
        if (!(fireDTODetails.getPersonDetails().isEmpty())) {
            fireDTODetails.setMessage("Success");
            fireDTODetails.setStatusCode(200);
        }
        logger.info("Response --" + gson.toJson(fireDTODetails));
        return fireDTODetails;
    }

    /**
     * Search on the basis of the list of station numbers
     * @param stations
     * @return a list of all the households in each fire station’s jurisdiction group by address
     */
    @RequestMapping("/flood/stations") //http://localhost:8080/flood/stations?stations=<a list of station_numbers>
    public FloodDTODetails getFloodInfo(@RequestParam("stations") List<String> stations) {
        logger.info("Request return a list of all the households in each fire station’s jurisdiction "
                + stations.toString());
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate currDate = LocalDate.now();
        FloodDTODetails.DetailDTO.FloodDTO floodDTO;
        FloodDTODetails.DetailDTO detailDTO;
        FloodDTODetails floodDTODetails = new FloodDTODetails();
        floodDTODetails.setMessage("No data found");
        floodDTODetails.setStatusCode(404);
        List<PersonEntity> persons = fileDataLoadingService.getPersons();
        List<MedicalRecordEntity> medicalRecords = fileDataLoadingService.getMedicalRecords();
        List<FirestationEntity> firestations = fileDataLoadingService.getFirestations();
        List<FloodDTODetails.DetailDTO.FloodDTO> floodDTOList;
        List<FloodDTODetails.DetailDTO> detailDTOArrayList = new ArrayList<>();
        List<String> addressList = new ArrayList<>();
        for (int i = 0; i < firestations.size(); i++) {
            for (int j = 0; j < stations.size(); j++) {
                if (firestations.get(i).getStation().equals(stations.get(j))) {
                    addressList.add(firestations.get(i).getAddress());
                }
            }
        }
        logger.debug("Data from the address list " + addressList);
        for (int i = 0; i < addressList.size(); i++) {
            detailDTO = new FloodDTODetails.DetailDTO();
            floodDTOList = new ArrayList<>();
            for (int j = 0; j < persons.size(); j++) {
                if (addressList.get(i).equals(persons.get(j).getAddress())) {
                    floodDTO = new FloodDTODetails.DetailDTO.FloodDTO();
                    floodDTO.setFirstName(persons.get(j).getFirstName());
                    floodDTO.setLastName(persons.get(j).getLastName());
                    floodDTO.setPhone(persons.get(j).getPhone());
                    String date = medicalRecords.get(i).getBirthDate();
                    LocalDate localDate = LocalDate.parse(date, format);
                    floodDTO.setAge(String.valueOf(calculateAge(localDate, currDate)));
                    floodDTO.setMedications(medicalRecords.get(j).getMedications());
                    floodDTO.setAllergies(medicalRecords.get(j).getAllergies());
                    floodDTOList.add(floodDTO);
                }
            }
            logger.debug("Data from the list " + floodDTOList);
            detailDTO.setAddress(addressList.get(i));
            detailDTO.setPersons(floodDTOList);
            detailDTOArrayList.add(detailDTO);
            logger.debug("Data from the list with grouped by addresses " + gson.toJson(detailDTOArrayList));
        }
        floodDTODetails.setDetails(detailDTOArrayList);
        if (!(floodDTODetails.getDetails().isEmpty())) {
            floodDTODetails.setMessage("Success");
            floodDTODetails.setStatusCode(200);
        }
        logger.info("Response --" + gson.toJson(floodDTODetails));
        return floodDTODetails;
    }

    /**
     * Search on the basis of firestation number
     * @param firestation
     * @return a list of phone numbers of each person within the fire station’s jurisdiction
     */
    @RequestMapping("/phoneAlert") //http://localhost:8080/phoneAlert?firestation=<firestation_number>
    public PhoneAlertDTO getPhonesNumbers(@RequestParam("firestation") String firestation) {
        logger.info("Request return a list of phone numbers of each person within " +
                "the fire station’s jurisdiction " + firestation);
        PhoneAlertDTO phoneAlertDTO = new PhoneAlertDTO();
        phoneAlertDTO.setMessage("No data found");
        phoneAlertDTO.setStatusCode(404);
        List<PersonEntity> persons = fileDataLoadingService.getPersons();
        List<FirestationEntity> firestations = fileDataLoadingService.getFirestations();
        List<String> addressList = new ArrayList<>();
        List<String> phoneNumberList = new ArrayList<>();
        for (int i = 0; i < firestations.size(); i++) {
            if (firestations.get(i).getStation().equals(firestation)) {
                addressList.add(firestations.get(i).getAddress());
            }
        }
        logger.debug("Data from the address list " + addressList);
        for (int i = 0; i < addressList.size(); i++) {
            for (int j = 0; j < persons.size(); j++) {
                if (addressList.get(i).equals(persons.get(j).getAddress())) {
                    phoneNumberList.add(persons.get(j).getPhone());
                }
            }
        }
        logger.debug("Data from phone no list " + phoneNumberList);
        phoneAlertDTO.setPhone(phoneNumberList);
        if (!(phoneAlertDTO.getPhone().isEmpty())) {
            phoneAlertDTO.setMessage("Success");
            phoneAlertDTO.setStatusCode(200);
        }
        logger.info("Response --" + gson.toJson(phoneAlertDTO));
        return phoneAlertDTO;
    }

    /**
     * Search on the basis of City name
     * @param city
     * @return the email addresses of all of the people in the city
     */
    @RequestMapping("/communityEmail") //http://localhost:8080/communityEmail?city=<city>
    public CommunityEmailDTO getEmailAddress(@RequestParam("city") String city) {
        logger.info("Request return the email addresses of all of the people in the city " + city);
        CommunityEmailDTO communityEmailDTO = new CommunityEmailDTO();
        communityEmailDTO.setMessage("No data found");
        communityEmailDTO.setStatusCode(404);
        List<PersonEntity> persons = fileDataLoadingService.getPersons();
        List<String> emailList = new ArrayList<>();
        for (int i = 0; i < persons.size(); i++) {
            if (persons.get(i).getCity().equals(city)) {
                emailList.add(persons.get(i).getEmail());
            }
        }
        logger.debug("data from the email list " + emailList);
        communityEmailDTO.setEmails(emailList);
        if (!(communityEmailDTO.getEmails().isEmpty())) {
            communityEmailDTO.setMessage("Success");
            communityEmailDTO.setStatusCode(200);
        }
        logger.info("Response --" + gson.toJson(communityEmailDTO));
        return communityEmailDTO;
    }

    /**
     * search on the basis of address
     * @param address
     * @return a list of children (anyone under the age of 18) at living that address
     */
    @RequestMapping("/childAlert") //http://localhost:8080/childAlert?address=<address>
    public ChildAlertDTODetails getChildAlert(@RequestParam("address") String address) {
        logger.info("Request return a list of children (anyone under the age of 18) at that address " + address);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate currDate = LocalDate.now();
        ChildAlertDTODetails.ChildAlertDTO childAlertDTO;
        ChildAlertDTODetails childAlertDTODetails = new ChildAlertDTODetails();
        childAlertDTODetails.setMessage("No data found");
        childAlertDTODetails.setStatusCode(404);
        List<PersonEntity> persons = fileDataLoadingService.getPersons();
        List<MedicalRecordEntity> medicalRecords = fileDataLoadingService.getMedicalRecords();
        List<ChildAlertDTODetails.ChildAlertDTO> childrenDetails = new ArrayList<>();
        List<String> adultDetails = new ArrayList<>();
        for (int i = 0; i < persons.size(); i++) {
            if (persons.get(i).getAddress().equals(address)) {
                childAlertDTO = new ChildAlertDTODetails.ChildAlertDTO();
                childAlertDTO.setFirstName(persons.get(i).getFirstName());
                childAlertDTO.setLastName(persons.get(i).getLastName());
                //medical record
                String date = medicalRecords.get(i).getBirthDate();
                LocalDate localDate = LocalDate.parse(date, format);
                childAlertDTO.setAge(String.valueOf(calculateAge(localDate, currDate)));
                if (Integer.parseInt(childAlertDTO.getAge()) < 18) {
                    childrenDetails.add(childAlertDTO);
                } else {
                    String adultDetail = childAlertDTO.getFirstName() + " " + childAlertDTO.getLastName();
                    adultDetails.add(adultDetail);
                }
            }
        }
        logger.debug("Data from children list " + gson.toJson(childrenDetails));
        logger.debug("Data from adult list " + adultDetails);
        for (int i = 0; i < childrenDetails.size(); i++) {
            if (Integer.parseInt(childrenDetails.get(i).getAge()) >= 18) {
                String adultDetail = childrenDetails.get(i).getFirstName() + " " + childrenDetails.get(i).getLastName();
                adultDetails.add(adultDetail);
            }
        }
        if (childrenDetails.size() == 0) {
            adultDetails.clear();
        }
        childAlertDTODetails.setChildrenDetails(childrenDetails);
        childAlertDTODetails.setPersonDetails(adultDetails);
        if (!(childAlertDTODetails.getChildrenDetails().isEmpty())) {
            childAlertDTODetails.setMessage("Success");
            childAlertDTODetails.setStatusCode(200);
        }
        logger.info("Response --" + gson.toJson(childAlertDTODetails));
        return childAlertDTODetails;
    }

    /**
     * Search on the basis of person’s name
     * @param firstName
     * @param lastName
     * @return person’s name, address, age, email with a list of medications with dosages and allergies
     */
    @RequestMapping("/personInfo") //http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
    public PersonDTODetails getPersonInfo(@RequestParam("firstName") String firstName,
                                          @RequestParam("lastName") String lastName) {
        logger.info("Request return the person’s name, address, age, email, " +
                "list of medications with dosages and allergies " + firstName + " " + lastName);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate currDate = LocalDate.now();
        PersonDTODetails personInfoList = new PersonDTODetails();
        personInfoList.setMessage("No data found");
        personInfoList.setStatusCode(404);
        PersonDTODetails.PersonInfoDTO personInfoDTO;
        List<PersonEntity> persons = fileDataLoadingService.getPersons();
        List<MedicalRecordEntity> medicalRecords = fileDataLoadingService.getMedicalRecords();
        List<PersonDTODetails.PersonInfoDTO> personInfo = new ArrayList<>();
        for (int i = 0; i < medicalRecords.size(); i++) {
            if ((medicalRecords.get(i).getFirstName().equals(firstName)) &&
                    (medicalRecords.get(i).getLastName().equals(lastName))) {
                personInfoDTO = new PersonDTODetails.PersonInfoDTO();
                personInfoDTO.setFirstName(medicalRecords.get(i).getFirstName());
                personInfoDTO.setLastName(medicalRecords.get(i).getLastName());
                personInfoDTO.setAddress(persons.get(i).getAddress());
                personInfoDTO.setEmail(persons.get(i).getEmail());
                //medical record
                String date = medicalRecords.get(i).getBirthDate();
                LocalDate localDate = LocalDate.parse(date, format);
                personInfoDTO.setAge(String.valueOf(calculateAge(localDate, currDate)));
                personInfoDTO.setMedications(medicalRecords.get(i).getMedications());
                personInfoDTO.setAllergies(medicalRecords.get(i).getAllergies());
                personInfo.add(personInfoDTO);
            }
        }
        logger.debug("Data from the person list " + gson.toJson(personInfo));
        personInfoList.setPersonInfo(personInfo);
        if (!(personInfoList.getPersonInfo().isEmpty())) {
            personInfoList.setMessage("Success");
            personInfoList.setStatusCode(200);
        }
        logger.info("Response --" + gson.toJson(personInfoList));
        return personInfoList;
    }

}
