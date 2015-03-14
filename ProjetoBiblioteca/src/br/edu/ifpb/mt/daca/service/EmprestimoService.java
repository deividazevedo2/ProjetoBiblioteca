package br.edu.ifpb.mt.daca.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import br.edu.ifpb.mt.daca.dao.EmprestimoDAO;
import br.edu.ifpb.mt.daca.entities.Emprestimo;
import br.edu.ifpb.mt.daca.exception.BibliotecaException;
import br.edu.ifpb.mt.daca.util.TransacionalCdi;

public class EmprestimoService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5535337875332772958L;

	@Inject
	private EmprestimoDAO emprestimoDao;

	@TransacionalCdi
	public void fazerEmprestimo(Emprestimo emprestimo)
			throws BibliotecaException {
		try {
			this.emprestimoDao.fazerEmprestimo(emprestimo);
		} catch (BibliotecaException e) {
			throw new BibliotecaException(e.getMessage());
		}
	}

	@TransacionalCdi
	public void fazerDevolucao(Emprestimo emprestimo)
			throws BibliotecaException {
		try {
			this.emprestimoDao.fazerDevolucao(emprestimo);
		} catch (BibliotecaException e) {
			throw new BibliotecaException(e.getMessage());
		}
	}

	public Emprestimo getById(Integer idEmprestimo) throws BibliotecaException {
		try {
			return this.emprestimoDao.buscar(idEmprestimo);
		} catch (PersistenceException e) {
			throw new BibliotecaException(e.getMessage(), e);
		}

	}

	public List<Emprestimo> getAll(Long matriculaAluno, Long isbnLivro, Boolean expirados)
			throws BibliotecaException {
		try {
			return this.emprestimoDao.getAll(matriculaAluno, isbnLivro, expirados);
		} catch (PersistenceException e) {
			throw new BibliotecaException(e.getMessage(), e);
		}
	}
	
}
