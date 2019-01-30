package ai.patient.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
public class PatientMemberRecord implements Serializable {
	// XXX: do we need artificial key ??
	@Id  
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String source;
	private String medicalRecordNumber;
	private String firstName;
	private String lastName;
	private String socialSecurityNumber;
	@OneToOne
	private Address address;
}
