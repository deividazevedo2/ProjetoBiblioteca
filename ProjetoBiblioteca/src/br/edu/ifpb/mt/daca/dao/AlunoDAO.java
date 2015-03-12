package br.edu.ifpb.mt.daca.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import br.edu.ifpb.mt.daca.entities.Aluno;
import br.edu.ifpb.mt.daca.entities.Pessoa;
import br.edu.ifpb.mt.daca.exception.BibliotecaException;

public class AlunoDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2082479246855523828L;

	public void salvar(Aluno aluno) throws BibliotecaException {
		EntityManager em = getEntityManager();
		try {
			em.persist(aluno);
		} catch (PersistenceException pe) {
			throw new BibliotecaException("Erro ao salvar o aluno "
					+ aluno.getNome(), pe);
		}
	}

	public Aluno buscar(Long matriculaAluno) throws BibliotecaException {
		EntityManager em = getEntityManager();
		Aluno resultado = null;
		try {
			resultado = em.find(Aluno.class, matriculaAluno);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		} finally {
			em.close();
		}

		return resultado;
	}

	public Aluno alterar(Aluno aluno) {
		EntityManager em = getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		Aluno resultado = aluno;
		try {
			resultado = em.merge(aluno);
			transaction.commit();
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			transaction.rollback();
		} finally {
			em.close();
		}
		return resultado;
	}

	public List<Pessoa> getAll() {
		EntityManager em = getEntityManager();
		List<Pessoa> resultado = null;
		try {
			TypedQuery<Pessoa> query = em.createQuery(
					"SELECT p FROM Pessoa_JS p", Pessoa.class);
			resultado = query.getResultList();
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		} finally {
			em.close();
		}
		return resultado;
	}

	public void deletar(Aluno aluno) throws BibliotecaException {
		EntityManager em = getEntityManager();
		try {
			aluno = em.merge(aluno);
			em.remove(aluno);
		} catch (PersistenceException pe) {
			throw new BibliotecaException("Erro ao deletar o aluno", pe);
		}
	}

	public List<Aluno> buscarAlunoPeloNome(String nomeAluno)
			throws BibliotecaException {
		EntityManager em = getEntityManager();
		List<Aluno> resultado = null;
		if (nomeAluno == null) {
			nomeAluno = "";
		}
		try {
			TypedQuery<Aluno> query = em.createQuery(
					"select a from Aluno_JS a where a.nome like :nome",
					Aluno.class);
			query.setParameter("nome", "%" + nomeAluno + "%");
			resultado = query.getResultList();
		} catch (PersistenceException pe) {
			throw new BibliotecaException("Erro ao recuperar aluno.", pe);
		}
		return resultado;
	}

	public Aluno buscarAlunoPelaMatricula(Long matriculaAluno)
			throws BibliotecaException {
		EntityManager em = getEntityManager();
		Aluno resultado = null;
		if (matriculaAluno == null) {
			matriculaAluno = Long.valueOf("");
		}
		try {
			TypedQuery<Aluno> query = em
					.createQuery(
							"select a from Aluno_JS a left join fetch a.livros where a.matricula like :matricula",
							Aluno.class);
			query.setParameter("matricula", matriculaAluno);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			throw new BibliotecaException(
					"Erro ao recuperar aluno por matricula.", pe);
		}
		return resultado;
	}

}
