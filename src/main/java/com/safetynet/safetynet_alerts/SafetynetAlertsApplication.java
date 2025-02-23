package com.safetynet.safetynet_alerts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties.Web;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;

import com.safetynet.safetynet_alerts.model.FireStation;
import com.safetynet.safetynet_alerts.model.MedicalRecord;
import com.safetynet.safetynet_alerts.model.Person;
import com.safetynet.safetynet_alerts.repository.FireStationRepository;
import com.safetynet.safetynet_alerts.repository.MedicalRecordRepository;
import com.safetynet.safetynet_alerts.repository.PersonRepository;

@SpringBootApplication
public class SafetynetAlertsApplication implements CommandLineRunner {
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private FireStationRepository fireStationRepository;
	@Autowired
	private MedicalRecordRepository medicalRecordRepository;

	// @Value("${server.port}")
	// private int serverPort;

	public static void main(String[] args) {
		SpringApplication.run(SafetynetAlertsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		
		List<Person> persons = personRepository.getAllPersons() ;
		List<FireStation> fireStations = fireStationRepository.getAllFireStations();
		List<MedicalRecord> medicalRecords = medicalRecordRepository.getAllMedicalRecords();
		System.out.println(persons);
		System.out.println("\n" + fireStations);
		System.out.println("\n" + medicalRecords);
		// System.out.println("\n" + serverPort);
	}

}
