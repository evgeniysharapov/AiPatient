package ai.patient.api;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.patient.data.PatientRepository;
import ai.patient.model.Patient;
import ai.patient.service.PatientService;

/**
 * This is a main API entrance to the application
 * @author evgeniy.sharapov
 *
 * Rest API is 
 * 
 * /api/patients
 * /api/patients/{id}
 * /api/patients
 */
@RestController
@RequestMapping("/api/patients")
public class PatientController {
	
	@Autowired
	private PatientRepository repo;
	
	@Autowired
	private PatientService service;

	@GetMapping(path = "/")
	public Collection<Patient> list() {
		return repo.findAll();
	}
	
	@GetMapping(path = "/{id}")
	public Optional<Patient> getOne(@PathVariable("id") String id){
		return repo.findById(id);
	}
	
	@PostMapping(path = "/")
	public Patient create(@RequestBody Patient patient) {
		return repo.save(patient);
	}
	
	@DeleteMapping(path = "/{id}")
	public void delete(@PathVariable("id") String id) {
		repo.deleteById(id);
	}

	/**
	 * This is the same search as {@link PatientMemberRecordController#search(String, String)} but from the {@link Patient} perspective.
	 * Given a <code>source</code> and/or <code>medicalRecordNumber</code> returns a collection of {@link Patient} 
	 * with {@link Patient#getMemberRecords()} matching given criteria
	 * 
	 * @return {@link Collection} of {@link Patient} instances.
	 */
	@GetMapping("/search")
	public Collection<Patient> search(@RequestParam(name = "source", required = false) String source,
			@RequestParam( name = "medicalRecordNumber", required = false) String medicalRecordNumber) {
		return service.searchBySourceOrMedicalRecordNumber(source, medicalRecordNumber);
	}
}
