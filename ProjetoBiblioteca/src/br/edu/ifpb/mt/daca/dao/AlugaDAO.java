package br.edu.ifpb.mt.daca.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import br.edu.ifpb.mt.daca.entities.Aluga;

public class AlugaDAO extends DAO{

	public void alugar(Aluga aluga) {
		EntityManager em = getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		try {
			em.persist(aluga);
			transaction.commit();
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			transaction.rollback();
		} finally {
			em.close();
		}
	}
	
}
