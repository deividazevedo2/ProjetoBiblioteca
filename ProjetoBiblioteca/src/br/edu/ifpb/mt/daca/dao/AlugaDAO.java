package br.edu.ifpb.mt.daca.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import br.edu.ifpb.mt.daca.entities.Aluga;
import br.edu.ifpb.mt.service.GerenciadorEmprestimo;

public class AlugaDAO extends DAO {

	GerenciadorEmprestimo ge = new GerenciadorEmprestimo();

	public void alugar(Aluga aluga) {
		EntityManager em = getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		try {
			ge.fazerEmprestimo(aluga.getMatriculaAluno(), aluga.getIsbnLivro());
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
