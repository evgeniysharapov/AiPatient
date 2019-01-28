package ai.patient.data;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.patient.model.PatientMemberRecord;

public interface PatientMemberRecordRepository extends JpaRepository<PatientMemberRecord, Long>{
	public Collection<PatientMemberRecord> findBySource(String source);
	public Collection<PatientMemberRecord> findByMedicalRecordNumber(String medicalRecordNumber);
	public Collection<PatientMemberRecord> findBySourceOrMedicalRecordNumber(String source, String medicalRecordNumber);	
}
