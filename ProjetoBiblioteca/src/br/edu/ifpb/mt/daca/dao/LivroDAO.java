package br.edu.ifpb.mt.daca.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import br.edu.ifpb.mt.daca.entities.Livro;
import br.edu.ifpb.mt.daca.exception.BibliotecaException;

public class LivroDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6387640448034553073L;

	public void salvar(Livro livro) throws BibliotecaException {
		EntityManager em = getEntityManager();
		try {
			em.persist(livro);
		} catch (PersistenceException pe) {
			throw new BibliotecaException("Erro ao salvar o livro "
					+ livro.getTitulo(), pe);
		}
	}

	public Livro buscar(Long isbnLivro) throws BibliotecaException {
		EntityManager em = getEntityManager();
		Livro resultado = null;
		try {
			resultado = em.find(Livro.class, isbnLivro);
		} catch (PersistenceException pe) {
			throw new BibliotecaException(
					"Ocorreu algum problema ao tentar recuperar o livro.", pe);
		}

		return resultado;
	}

	public Livro alterar(Livro livro) throws BibliotecaException {
		EntityManager em = getEntityManager();
		Livro resultado = livro;
		try {
			resultado = em.merge(livro);
		} catch (PersistenceException pe) {
			throw new BibliotecaException(
					"Ocorreu algum problema ao tentar atualizar o livro.", pe);
		}
		return resultado;
	}

	public void deletar(Livro livro) throws BibliotecaException {
		EntityManager em = getEntityManager();
		try {
			livro = em.merge(livro);
			em.remove(livro);
		} catch (PersistenceException pe) {
			throw new BibliotecaException("Erro ao deletar o livro.", pe);
		}
	}

	public List<Livro> getAll(Long isbnLivro, String tituloLivro)
			throws BibliotecaException {
		EntityManager em = getEntityManager();
		List<Livro> resultado = null;

		String jpql = "SELECT l FROM tb_livro l where 1=1";

		if (isbnLivro != null && !isbnLivro.equals("")) {
			jpql += " and l.isbn = :isbn";
		}

		if (tituloLivro != null && !tituloLivro.isEmpty()) {
			jpql += " and l.titulo like :titulo";
		}

		TypedQuery<Livro> query = em.createQuery(jpql, Livro.class);

		if (isbnLivro != null && !isbnLivro.equals("")) {
			query.setParameter("isbn", isbnLivro);
		}

		if (tituloLivro != null && !tituloLivro.isEmpty()) {
			query.setParameter("titulo", "%" + tituloLivro + "%");
		}

		try {
			resultado = query.getResultList();
		} catch (PersistenceException pe) {
			throw new BibliotecaException(
					"Ocorreu algum problema ao tentar recuperar os livros com base no ISBN e/ou matrícula.",
					pe);
		}
		return resultado;
	}

	public List<Livro> buscarLivroPeloNome(String tituloLivro)
			throws BibliotecaException {
		EntityManager em = getEntityManager();
		List<Livro> resultado = null;
		if (tituloLivro == null) {
			tituloLivro = "";
		}
		try {
			TypedQuery<Livro> query = em.createQuery(
					"select l from Livro l where l.titulo like :titulo",
					Livro.class);
			query.setParameter("titulo", "%" + tituloLivro + "%");
			resultado = query.getResultList();
		} catch (PersistenceException pe) {
			throw new BibliotecaException(
					"Erro ao buscar livro com o nome informado.", pe);
		}
		return resultado;
	}

}
