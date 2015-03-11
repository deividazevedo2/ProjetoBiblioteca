package br.edu.ifpb.mt.daca.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import br.edu.ifpb.mt.daca.entities.Livro;

public class LivroDAO extends DAO {

	public void salvar(Livro livro) {
		EntityManager em = getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		try {
			em.persist(livro);
			transaction.commit();
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			transaction.rollback();
		} finally {
			em.close();
		}
	}

	public Livro alterar(Livro livro) {
		EntityManager em = getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		Livro resultado = livro;
		try {
			resultado = em.merge(livro);
			transaction.commit();
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			transaction.rollback();
		} finally {
			em.close();
		}
		return resultado;
	}

	public void deletar(Livro livro) {
		EntityManager em = getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		try {
			livro = em.merge(livro);
			em.remove(livro);
			transaction.commit();
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			transaction.rollback();
		} finally {
			em.close();
		}
	}

	public Livro buscar(Long livroId) {
		EntityManager em = getEntityManager();
		Livro resultado = null;
		try {
			resultado = em.find(Livro.class, livroId);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		} finally {
			em.close();
		}

		return resultado;
	}

	public List<Livro> getAll() {
		EntityManager em = getEntityManager();
		List<Livro> resultado = null;
		try {
			TypedQuery<Livro> query = em.createQuery(
					"SELECT l FROM tb_livro l", Livro.class);
			resultado = query.getResultList();
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		} finally {
			em.close();
		}
		return resultado;
	}

	public List<Livro> buscarLivroPeloNome(String tituloLivro) {
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
			pe.printStackTrace();
		} finally {
			em.close();
		}
		return resultado;
	}

}
