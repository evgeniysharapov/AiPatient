package ai.patient.model;

import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
public class PatientMemberRecord {
	// XXX: do we need artificial key ?? 
	private String source;
	private String medicalRecordNumber;
	private String firstName;
	private String lastName;
	private String socialSecurityNumber;
	private Address address;
}
