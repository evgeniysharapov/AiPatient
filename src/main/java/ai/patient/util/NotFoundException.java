/**
 * 
 */
package ai.patient.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when object is not found.
 * 
 * @author evgeniy.sharapov
 *
 */
@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

	public NotFoundException(String message) {
		super(message);
	}
	
}
