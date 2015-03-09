package br.edu.ifpb.mt.daca.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import br.edu.ifpb.mt.daca.entities.Aluno;

public class AlunoDAO extends DAO {

	public void save(Aluno aluno) {
		EntityManager em = getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		try {
			em.persist(aluno);
			transaction.commit();
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			transaction.rollback();
		} finally {
			em.close();
		}
	}

	// public List<Pessoa> getAll() {
	// EntityManager em = getEntityManager();
	// List<Pessoa> resultado = null;
	// try {
	// TypedQuery<Pessoa> query = em.createQuery("SELECT p FROM Pessoa_JS p",
	// Pessoa.class);
	// resultado = query.getResultList();
	// } catch (PersistenceException pe) {
	// pe.printStackTrace();
	// } finally {
	// em.close();
	// }
	// return resultado;
	// }
	//
	public void delete(Aluno aluno) {
		EntityManager em = getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		try {
			aluno = em.merge(aluno);
			em.remove(aluno);
			transaction.commit();
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			transaction.rollback();
		} finally {
			em.close();
		}
	}

}
