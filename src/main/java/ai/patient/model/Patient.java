package ai.patient.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Patient {
	@Id
	private String enterpriseId; // global identifier
	@OneToMany
	private List<PatientMemberRecord> memberRecords;  // individual Member records
}
