package br.com.desafiodevsuperior.service.exceptions;

/**
 * Quando se herda de Exception, obrigatoriamente ela precisa ser tratada, o compilador não permite que vc não trate.
 * RuntimeException -> excessão mais flexivel, pode ser ou não tratada
 * @author wepbi
 *
 */
public class ResourceNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	
	
	
	public ResourceNotFoundException(String msg) {
		super(msg);
	}

}
