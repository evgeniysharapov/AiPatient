/**
 * 
 */
package ai.patient.service;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.patient.data.PatientRepository;
import ai.patient.model.Patient;
import lombok.extern.slf4j.Slf4j;

/**
 * Complex operations related to the {@link Patient}. 
 * 
 * 
 * @author evgeniy.sharapov
 *
 */
@Service
@Slf4j
public class PatientService {
	@Autowired
	private PatientRepository patientRepo;

	/**
	 * Given a <code>source</code> and/or <code>medicalRecordNumber</code> returns a collection of {@link Patient} 
	 * with {@link Patient#getMemberRecords()} matching given criteria
	 * 
	 * @return {@link Collection} of {@link Patient} instances.
	 */
	public Collection<Patient> searchBySourceOrMedicalRecordNumber (String source, String medicalRecordNumber) {
		// each patients brings all of its PMRs
		Collection<Patient> bySource = patientRepo.findByMemberRecords_Source(source);
		Collection<Patient> byMRN = patientRepo.findByMemberRecords_MedicalRecordNumber(medicalRecordNumber);
		
		Map<String, List<Patient>> eidPatientsMap =
		Stream.concat(
				byMRN.stream().map(patient -> {
					// creating a new Patient object
					Patient p = new Patient();
					p.setEnterpriseId(patient.getEnterpriseId());
					// add only PMRs matching medicalRecordNumber
					p.setMemberRecords(patient.getMemberRecords().stream().filter( pmr -> pmr.getMedicalRecordNumber().equals(medicalRecordNumber)).collect(toList()));
					return p;
				}),
				bySource.stream().map(patient -> {
					// creating a new Patient object
					Patient p = new Patient();
					p.setEnterpriseId(patient.getEnterpriseId());
					// add only PMRs matching source
					p.setMemberRecords(patient.getMemberRecords().stream().filter(pmr -> pmr.getSource().equals(source)).collect(toList()));
					return p;
				}))
			.collect(Collectors.groupingBy(Patient::getEnterpriseId))
			;
		// now merge same enterpriseId patients' PMRs
		// go from List<List<Patient>> to List<Patient>
		List<Patient> l = eidPatientsMap.values().stream()
				// each value is a list of same patients, that may have different member records
				// List<Patient> -> Patient
				.map(patients -> patients.stream()
						.reduce((p1, p2) -> {
							Patient p = new Patient();
							// copy properties from either p1 and p2 (they should be the same)
							p.setEnterpriseId(p1.getEnterpriseId());
							// merge PMRs from p1 and p2, leave distinct ones and set it to p.
							p.setMemberRecords(
									Stream.concat(p1.getMemberRecords().stream(), p2.getMemberRecords().stream())
										.distinct()
										.collect(toList()));
							return p;
						})
						.get()
					)
				.collect(toList());
		return l;
	} 
}
