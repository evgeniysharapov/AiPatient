/**
 * 
 */
package ai.patient.util;

import java.util.Optional;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * When we return Optional objects that are empty, return 404 HTTP code
 * 
 * @author evgeniy.sharapov
 *
 */
@RestControllerAdvice("ai.patient.api")
@SuppressWarnings("rawtypes")
public class OptionalEmptyIsNotFound implements ResponseBodyAdvice<Optional<?> > {

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return returnType.getParameterType().equals(Optional.class);
	}

	@Override
	public Optional<?> beforeBodyWrite(Optional<?> body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		if (body != null && body.isPresent()) {
			return body;
		} else {
			throw new NotFoundException("Object was not found: " + request.getURI().toString());
		}
	}

	 
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Message requestHandlingNoHandlerFound(Exception e) {
		return new Message(e.getLocalizedMessage());
	}
}
