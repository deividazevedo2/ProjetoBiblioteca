package br.edu.ifpb.mt.daca.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import br.edu.ifpb.mt.daca.entities.Aluno;
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

	public Aluno buscar(Integer matriculaAluno) throws BibliotecaException {
		EntityManager em = getEntityManager();
		Aluno resultado = null;
		try {
			resultado = em.find(Aluno.class, matriculaAluno);
		} catch (PersistenceException pe) {
			throw new BibliotecaException(
					"Ocorreu algum problema ao tentar recuperar o aluno.", pe);
		}

		return resultado;
	}

	public Aluno alterar(Aluno aluno) throws BibliotecaException {
		EntityManager em = getEntityManager();
		Aluno resultado = aluno;
		try {
			resultado = em.merge(aluno);
		} catch (PersistenceException pe) {
			throw new BibliotecaException(
					"Ocorreu algum problema ao tentar atualizar o aluno.", pe);
		}
		return resultado;
	}

	public List<Aluno> getAll(Long matriculaAluno, String nomeAluno)
			throws BibliotecaException {
		EntityManager em = getEntityManager();
		List<Aluno> resultado = null;

		String jpql = "select a from Aluno_JS a where 1=1";

		if (matriculaAluno != null && !matriculaAluno.equals("")) {
			jpql += " and a.matricula = :matricula";
		}

		if (nomeAluno != null && !nomeAluno.isEmpty()) {
			jpql += " and a.nome like :nome";
		}

		TypedQuery<Aluno> query = em.createQuery(jpql, Aluno.class);

		if (matriculaAluno != null && !matriculaAluno.equals("")) {
			query.setParameter("matricula", matriculaAluno);
		}

		if (nomeAluno != null && !nomeAluno.isEmpty()) {
			query.setParameter("nome", "%" + nomeAluno + "%");
		}

		try {
			resultado = query.getResultList();
		} catch (PersistenceException pe) {
			throw new BibliotecaException(
					"Ocorreu algum problema ao tentar recuperar os alunos com base no nome e/ou matricula.",
					pe);
		}
		return resultado;
	}

	public void deletar(Aluno aluno) throws BibliotecaException {
		EntityManager em = getEntityManager();
		try {
			aluno = em.merge(aluno);
			em.remove(aluno);
		} catch (PersistenceException pe) {
			throw new BibliotecaException("Erro ao deletar o aluno.", pe);
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
