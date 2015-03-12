package br.edu.ifpb.mt.daca.exception;

public class BibliotecaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5426566422361229699L;

	public BibliotecaException(String mensagem) {
		super(mensagem);
	}

	public BibliotecaException(String mensagem, Throwable throwable) {
		super(mensagem, throwable);
	}
}
