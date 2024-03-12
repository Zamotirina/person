package telran.java51.person.dto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnknownPersonTypeException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1138666462501069598L;

}
