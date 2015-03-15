package br.edu.ifpb.mt.daca.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import br.edu.ifpb.mt.daca.entities.Aluno;
import br.edu.ifpb.mt.daca.entities.Emprestimo;
import br.edu.ifpb.mt.daca.entities.Livro;
import br.edu.ifpb.mt.daca.exception.BibliotecaException;
import br.edu.ifpb.mt.daca.service.GerenciadorEmprestimo;

public class EmprestimoDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4279820808145533639L;

	private GerenciadorEmprestimo ge = new GerenciadorEmprestimo();
	private EntityManager em;

	public void fazerEmprestimo(Emprestimo emprestimo)
			throws BibliotecaException {
		EntityManager em = getEntityManager();
		this.em = em;
		try {
			verificaEmprestimo(emprestimo);
			em.persist(emprestimo);
		} catch (BibliotecaException e) {
			throw new BibliotecaException("Erro ao fazer o empréstimo", e);
		}
	}

	public void fazerDevolucao(Emprestimo emprestimo)
			throws BibliotecaException {
		EntityManager em = getEntityManager();
		this.em = em;
		try {
			fazerDevolucao(emprestimo.getMatriculaAluno(),
					emprestimo.getIsbnLivro());
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
		} catch (PersistenceException pe) {
			throw new BibliotecaException(
					"Ocorreu algum problema ao tentar recuperar os empréstimos com base na matrícula e/ou isbn do livro.",
					pe);
		}
		return resultado;
	}

	public void verificaEmprestimo(Emprestimo emprestimo)
			throws BibliotecaException {
		try {
			Aluno aluno = alunoDoEmprestimo(emprestimo.getMatriculaAluno());
			Livro livro = livroDoEmprestimo(emprestimo.getIsbnLivro());
			Integer quantidade = livro.getExemplares();
			if (ge.verificaParametrosNulos(aluno, livro)
					&& ge.verificaQuantidadeExemplares(livro)) {
				List<Livro> livros = aluno.getLivros();
				Livro livro2 = livro;
				livro2.setExemplares(1);
				livros.add(livro2);
				aluno.setLivros(livros);
				livro.setExemplares(quantidade - 1);
				emprestimo.setNomeAluno(aluno.getNome());
				emprestimo.setNomeLivro(livro2.getTitulo());
				ge.atualizarDatas(emprestimo);
				alterarLivro(livro);
				alterarAluno(aluno);
			}
		} catch (BibliotecaException e) {
			throw new BibliotecaException("Erro ao realizar este empréstimo.",
					e);
		}

	}

	private Aluno alunoDoEmprestimo(Long matriculaAluno)
			throws BibliotecaException {
		Aluno resultado = null;
		if (matriculaAluno == null) {
			matriculaAluno = Long.valueOf("");
		}
		try {
			TypedQuery<Aluno> query = em
					.createQuery(
							"select a from Aluno_JS a where a.matricula like :matricula",
							Aluno.class);
			query.setParameter("matricula", matriculaAluno);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			throw new BibliotecaException(
					"Erro ao recuperar aluno por matricula.", pe);
		}
		return resultado;
	}

	private Livro livroDoEmprestimo(Long isbnLivro) throws BibliotecaException {
		Livro resultado = null;
		if (isbnLivro == null) {
			isbnLivro = Long.valueOf("");
		}
		try {
			TypedQuery<Livro> query = em.createQuery(
					"select l from Livro l where l.isbn like :isbn",
					Livro.class);
			query.setParameter("isbn", isbnLivro);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			throw new BibliotecaException("Erro ao recuperar livro por ISBN.",
					pe);
		}
		return resultado;
	}

	public Aluno alterarAluno(Aluno aluno) throws BibliotecaException {
		Aluno resultado = aluno;
		try {
			resultado = em.merge(aluno);
		} catch (PersistenceException pe) {
			throw new BibliotecaException(
					"Ocorreu algum problema ao tentar atualizar o aluno.", pe);
		}
		return resultado;
	}

	public Livro alterarLivro(Livro livro) throws BibliotecaException {
		Livro resultado = livro;
		try {
			resultado = em.merge(livro);
		} catch (PersistenceException pe) {
			throw new BibliotecaException(
					"Ocorreu algum problema ao tentar atualizar o livro.", pe);
		}
		return resultado;
	}

	public void fazerDevolucao(Long matriculaAluno, Long isbnLivro)
			throws BibliotecaException {
		Aluno aluno = alunoDoEmprestimo(matriculaAluno);
		Livro livro = livroDoEmprestimo(isbnLivro);
		if (ge.verificaParametrosNulos(aluno, livro)) {
			List<Livro> livros = ge.pegaListaDeLivros(aluno);
			devolver(livro, aluno, livros);
		}
	}

	private void devolver(Livro livro, Aluno aluno, List<Livro> livros)
			throws BibliotecaException {
		if (livros.contains(livro)) {
			livros.remove(livro);
			livro.setExemplares(livro.getExemplares() + 1);
			aluno.setLivros(livros);
			alterarLivro(livro);
			alterarAluno(aluno);
			remover(aluno.getMatricula(), livro.getIsbn(), false);
		}
	}

	private void remover(Long matriculaAluno, Long isbnLivro, Boolean condicao)
			throws BibliotecaException {
		List<Emprestimo> e = getAll(matriculaAluno, isbnLivro, condicao);
		for (Emprestimo emprestimo : e) {
			em.remove(emprestimo);
		}

	}

}
