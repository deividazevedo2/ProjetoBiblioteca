package br.edu.ifpb.mt.daca.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import br.edu.ifpb.mt.daca.entities.Emprestimo;
import br.edu.ifpb.mt.daca.exception.BibliotecaException;

public class EmprestimoDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4279820808145533639L;

	public void fazerEmprestimo(Emprestimo emprestimo)
			throws BibliotecaException {
		EntityManager em = getEntityManager();
		try {
			em.persist(emprestimo);
		} catch (PersistenceException e) {
			throw new BibliotecaException("Erro ao fazer o empréstimo", e);
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

	public List<Emprestimo> getAll(Long matriculaAluno, Long isbnLivro,
			Boolean expirados) throws BibliotecaException {
		EntityManager em = getEntityManager();
		List<Emprestimo> resultado = null;

		String jpql = "select e from Emprestimo e where 1=1";

		if (matriculaAluno != null && !matriculaAluno.equals("")) {
			jpql += " and e.matriculaAluno = :matriculaAluno";
		}

		if (isbnLivro != null && !isbnLivro.equals("")) {
			jpql += " and e.isbnLivro like :isbnLivro";
		}
		if (expirados == true) {
			jpql += " and e.dataDevolucao < curdate() ORDER BY e.dataDevolucao";
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
			multa(resultado);
		} catch (PersistenceException pe) {
			throw new BibliotecaException(
					"Ocorreu algum problema ao tentar recuperar os empréstimos com base na matrícula e/ou isbn do livro.",
					pe);
		}
		return resultado;
	}

	public Emprestimo alterarEmprestimo(Emprestimo emprestimo)
			throws BibliotecaException {
		EntityManager em = getEntityManager();
		Emprestimo resultado = emprestimo;
		try {
			resultado = em.merge(emprestimo);
		} catch (PersistenceException pe) {
			throw new BibliotecaException(
					"Ocorreu algum problema ao tentar atualizar o empréstimo.",
					pe);
		}
		return resultado;
	}

	public void deletar(Emprestimo emprestimo) throws BibliotecaException {
		EntityManager em = getEntityManager();
		try {
			emprestimo = em.merge(emprestimo);
			em.remove(emprestimo);
		} catch (PersistenceException pe) {
			throw new BibliotecaException("Erro ao deletar o empréstimo.", pe);
		}
	}

	private void multa(List<Emprestimo> emprestimos) {
		Date hoje = new Date();
		try {
			for (Emprestimo emprestimo : emprestimos) {
				String devolucao = emprestimo.getDataDevolucao().toString();
				Date ultima = new SimpleDateFormat("yyyy-MM-dd")
						.parse(devolucao);
				System.out.println(emprestimo);
				if (ultima.before(hoje)) {
					Long diferenca = hoje.getTime() - ultima.getTime();
					Long diferencaDeDias = diferenca / (24 * 60 * 60 * 1000);
					emprestimo.setMulta(diferencaDeDias * 0.50);

					alterarEmprestimo(emprestimo);
				} else {
					alterarEmprestimo(emprestimo);
				}
			}
		} catch (ParseException | BibliotecaException e) {
			e.printStackTrace();
		}

	}
}
