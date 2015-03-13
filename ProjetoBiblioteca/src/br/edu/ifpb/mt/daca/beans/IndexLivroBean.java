package br.edu.ifpb.mt.daca.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifpb.mt.daca.entities.Livro;
import br.edu.ifpb.mt.daca.exception.BibliotecaException;
import br.edu.ifpb.mt.daca.service.LivroService;

@Named
@RequestScoped
public class IndexLivroBean extends ClasseAbstrata {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6977557276403647255L;

	private List<Livro> livros;

	@Inject
	private LivroService livroService;

	private Long isbnLivro;
	private String nomeLivro;

	@PostConstruct
	public void init() {
		filtrar();
	}

	public List<Livro> getLivros() {
		return livros;
	}

	public void filtrar() {
		try {
			livros = livroService.getAll(isbnLivro, nomeLivro);
		} catch (BibliotecaException e) {
			reportarMensagemDeErro(e.getMessage());
		}
	}

	public void limpar() {
		nomeLivro = null;
		isbnLivro = null;
	}

}
