package br.edu.ifpb.mt.daca.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import br.edu.ifpb.mt.daca.dao.LivroDAO;
import br.edu.ifpb.mt.daca.entities.Livro;
import br.edu.ifpb.mt.daca.exception.BibliotecaException;
import br.edu.ifpb.mt.daca.util.TransacionalCdi;

public class LivroService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5535337875332772958L;

	@Inject
	private LivroDAO livroDao;

	@TransacionalCdi
	public void addLivro(Livro livro) throws BibliotecaException {
		try {
			this.livroDao.salvar(livro);
		} catch (BibliotecaException e) {
			throw new BibliotecaException(e.getMessage());
		}
	}

	@TransacionalCdi
	public Livro editarLivro(Livro livro) throws BibliotecaException {
		try {
			return this.livroDao.alterar(livro);
		} catch (BibliotecaException e) {
			throw new BibliotecaException(e.getMessage());
		}

	}

	@TransacionalCdi
	public void excluirLivro(Livro livro) throws BibliotecaException {
		try {
			this.livroDao.deletar(livro);
		} catch (BibliotecaException e) {
			throw new BibliotecaException(e.getMessage());
		}

	}

	public Livro getByIsbn(Long isbn) throws BibliotecaException {
		try {
			return this.livroDao.buscar(isbn);
		} catch (PersistenceException e) {
			throw new BibliotecaException(e.getMessage(), e);
		}

	}

	public List<Livro> getAll(Long isbnLivro, String tituloLivro)
			throws BibliotecaException {
		try {
			return this.livroDao.getAll(isbnLivro, tituloLivro);
		} catch (PersistenceException e) {
			throw new BibliotecaException(e.getMessage(), e);
		}
	}

	public void alugarLivro(Long isbn, Long matricula) {

	}

	public void devolverLivro(Long isbn, Long matricula) {

	}

}
