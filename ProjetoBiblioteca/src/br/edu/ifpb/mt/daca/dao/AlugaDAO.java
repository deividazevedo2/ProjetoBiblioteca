package br.edu.ifpb.mt.daca.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import br.edu.ifpb.mt.daca.entities.Aluga;
import br.edu.ifpb.mt.daca.exception.BibliotecaException;
import br.edu.ifpb.mt.daca.service.GerenciadorEmprestimo;

public class AlugaDAO extends DAO {

	GerenciadorEmprestimo ge = new GerenciadorEmprestimo();

	public void alugar(Aluga aluga) throws BibliotecaException {
		EntityManager em = getEntityManager();
		try {
			ge.fazerEmprestimo(aluga.getMatriculaAluno(), aluga.getIsbnLivro());
			em.persist(aluga);
		} catch (BibliotecaException e) {
			throw new BibliotecaException("Erro ao fazer o emprestimo", e);
		}
	}
}
