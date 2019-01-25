package ai.patient.data;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.patient.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, String> {

}
