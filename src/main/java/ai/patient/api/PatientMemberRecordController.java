package ai.patient.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is a main API entrance to the application
 * @author evgeniy.sharapov
 *
 */
@RestController
public class PatientMemberRecordController {
	
	@GetMapping(path = "/api")
	public String index() {
		return "Running Patient API v 1.0.0";
	}

}
