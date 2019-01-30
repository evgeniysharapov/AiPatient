package ai.patient.api;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.patient.data.PatientMemberRecordRepository;
import ai.patient.model.PatientMemberRecord;

/**
 * Records REST API
 * @author evgeniy.sharapov
 *
 */
@RestController
@RequestMapping(path="/api/records")
public class PatientMemberRecordController extends BaseController<PatientMemberRecord, Long, PatientMemberRecordRepository>{

	/**
	 * Search by <code>source</code> or <code>medicalRecordNumber</code> 
	 * @return {@link Collection} of matching {@link PatientMemberRecord}
	 */
	@GetMapping(path="/search")
	public Collection<PatientMemberRecord> search(@RequestParam(name = "source", required = false) String source,
			@RequestParam(name = "medicalRecordNumber", required = false) String medicalRecordNumber) {
		return repo.findBySourceOrMedicalRecordNumber(source, medicalRecordNumber);
	}
}
