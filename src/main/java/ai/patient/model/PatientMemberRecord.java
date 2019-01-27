package ai.patient.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

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
	@OneToMany
	private Address address;
}
