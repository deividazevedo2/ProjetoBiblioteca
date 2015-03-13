package br.edu.ifpb.mt.daca.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import br.edu.ifpb.mt.daca.entities.Emprestimo;
import br.edu.ifpb.mt.daca.exception.BibliotecaException;
import br.edu.ifpb.mt.daca.service.GerenciadorEmprestimo;

public class EmprestimoDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4279820808145533639L;

	private GerenciadorEmprestimo ge = new GerenciadorEmprestimo();

	public void fazerEmprestimo(Emprestimo emprestimo)
			throws BibliotecaException {
		EntityManager em = getEntityManager();
		try {
			ge.fazerEmprestimo(emprestimo);
			em.persist(emprestimo);
		} catch (BibliotecaException e) {
			throw new BibliotecaException("Erro ao fazer o empréstimo", e);
		}
	}

	public void fazerDevolucao(Emprestimo emprestimo)
			throws BibliotecaException {
		EntityManager em = getEntityManager();
		try {
			ge.fazerDevolucao(emprestimo.getMatriculaAluno(),
					emprestimo.getIsbnLivro());
			em.remove(emprestimo);
		} catch (BibliotecaException e) {
			throw new BibliotecaException("Erro ao fazer a devolução", e);
		}
	}

	public Emprestimo buscar(Integer idEmprestimo) throws BibliotecaException {
		EntityManager em = getEntityManager();
		Emprestimo resultado = null;
		try {
			resultado = em.find(Emprestimo.class, idEmprestimo);
		} catch (PersistenceException pe) {
			throw new BibliotecaException(
					"Ocorreu algum problema ao tentar recuperar o Empréstimo.",
					pe);
		}

		return resultado;
	}

	public List<Emprestimo> getAll(Long matriculaAluno, Long isbnLivro)
			throws BibliotecaException {
		EntityManager em = getEntityManager();
		List<Emprestimo> resultado = null;

		String jpql = "select e from Emprestimo e where 1=1";

		if (matriculaAluno != null && !matriculaAluno.equals("")) {
			jpql += " and e.matriculaAluno = :matriculaAluno";
		}

		if (isbnLivro != null && !isbnLivro.equals("")) {
			jpql += " and e.isbnLivro like :isbnLivro";
		}

		TypedQuery<Emprestimo> query = em.createQuery(jpql, Emprestimo.class);

		if (matriculaAluno != null && !matriculaAluno.equals("")) {
			query.setParameter("matriculaAluno", matriculaAluno);
		}

		if (isbnLivro != null && !isbnLivro.equals("")) {
			query.setParameter("isbnLivro", isbnLivro);
		}

		try {
			resultado = query.getResultList();
		} catch (PersistenceException pe) {
			throw new BibliotecaException(
					"Ocorreu algum problema ao tentar recuperar os empréstimos com base na matrícula e/ou isbn do livro.",
					pe);
		}
		return resultado;
	}

}
