package DemandasDm.api.rest;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.mapping.Constraint;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.support.incrementer.PostgreSQLSequenceMaxValueIncrementer;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
@ControllerAdvice
class ControllerDeExcecoes extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler({Exception.class, RuntimeException.class , Throwable.class})
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String msg = "";
		if (ex instanceof MethodArgumentNotValidException) {
			List<ObjectError> ListErros = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
			for (ObjectError objectError : ListErros) {
				msg += objectError.getDefaultMessage() +"\n";
			}
			
		}else {
			msg = ex.getMessage();
		}
		
		
		ObjetoError objError = new ObjetoError();
		objError.setError(msg);
		objError.setCodigo(status.value()+"==>" +status.getReasonPhrase());
		
		
		return new ResponseEntity<Object>(objError,headers,status);
	}
	
	@ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class})
	protected ResponseEntity<Object> handleExceptionData(Exception ex){
		
		String msg = "";
		
		if (ex instanceof DataIntegrityViolationException) {
			msg = ((DataIntegrityViolationException) ex).getCause().getCause().getMessage();
			
		}else {
			msg = ex.getMessage();
		}
		
		ObjetoError objE = new ObjetoError();
		objE.setError(msg);
		objE.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR+ "==>" +HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		
		
		return new ResponseEntity<>(objE,HttpStatus.INTERNAL_SERVER_ERROR	);
		
	}
}
