package br.com.desafiodevsuperior.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.desafiodevsuperior.service.exceptions.DatabaseException;
import br.com.desafiodevsuperior.service.exceptions.ResourceNotFoundException;



/**
 * Classe que intercepta qualquer excessão que acorra na camada de Resource(Controlador REST)
 * @author wepbi
 *
 */
@ControllerAdvice // Controlador de excessão
public class ResourceExceptionHandler {
	
	
	/**
	 * Esse metodo intercepta o Resource(Controller)
	 * Esse metodo e uma resposta de requisição onde o conteudo da resposta vai ser o objeto StandardError(Resposta Customizada de acordo com POSTMAN)
	 * HttpServletRequest -> Objeto que tem as informações da requisição
	 */
	@ExceptionHandler(ResourceNotFoundException.class) // Avisa qual e o tipo de excessão que sera interceptada
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value()); // NOT_FOUND -> e do Tipo Enum e o value() pega o numero inteiro dele
		err.setError("Resource not found");
		err.setMessage(e.getMessage()); // pegando a mensagem que foi definida no service
		err.setPath(request.getRequestURI()); // Pega o caminho da requisição feita no momento do erro
		
		return ResponseEntity.status(status).body(err); // No corpo(body) da resposta vai o objeto err
	}

	
	
	
	
	@ExceptionHandler(DatabaseException.class) // Avisa qual e o tipo de excessão que sera interceptada
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request){
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value()); // BAD_REQUEST -> e do Tipo Enum e o value() pega o numero inteiro dele
		err.setError("Database exception");
		err.setMessage(e.getMessage()); // pegando a mensagem que foi definida no service
		err.setPath(request.getRequestURI()); // Pega o caminho da requisição feita no momento do erro
		
		return ResponseEntity.status(status).body(err); // No corpo(body) da resposta vai o objeto err
	}
	
	
}
