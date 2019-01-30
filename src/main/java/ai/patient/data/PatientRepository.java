package ai.patient.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.patient.model.Patient;
import ai.patient.model.PatientMemberRecord;

public interface PatientRepository extends JpaRepository<Patient, String> {
	public List<Patient> findByMemberRecords_Source(String source);
	public List<Patient> findByMemberRecords_MedicalRecordNumber(String medicalRecordNumber);
}
