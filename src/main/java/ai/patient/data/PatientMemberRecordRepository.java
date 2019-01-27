package ai.patient.data;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.patient.model.PatientMemberRecord;

public interface PatientMemberRecordRepository extends JpaRepository<PatientMemberRecord, String>{

}
