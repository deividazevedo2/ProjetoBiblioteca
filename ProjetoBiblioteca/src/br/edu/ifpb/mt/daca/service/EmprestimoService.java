package br.edu.ifpb.mt.daca.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import br.edu.ifpb.mt.daca.dao.AlunoDAO;
import br.edu.ifpb.mt.daca.dao.EmprestimoDAO;
import br.edu.ifpb.mt.daca.dao.LivroDAO;
import br.edu.ifpb.mt.daca.entities.Aluno;
import br.edu.ifpb.mt.daca.entities.Emprestimo;
import br.edu.ifpb.mt.daca.entities.Livro;
import br.edu.ifpb.mt.daca.exception.BibliotecaException;
import br.edu.ifpb.mt.daca.util.TransacionalCdi;

public class EmprestimoService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5535337875332772958L;

	@Inject
	private EmprestimoDAO emprestimoDao;

	@Inject
	private AlunoDAO alunoDao;

	@Inject
	private LivroDAO livroDao;

	private GerenciadorEmprestimo ge = new GerenciadorEmprestimo();

	@TransacionalCdi
	public void fazerEmprestimo(Emprestimo emprestimo)
			throws BibliotecaException {
		try {
			verificaEmprestimo(emprestimo);
			emprestimoDao.fazerEmprestimo(emprestimo);
		} catch (BibliotecaException e) {
			throw new BibliotecaException(e.getMessage());
		}
	}

	@TransacionalCdi
	public void fazerDevolucao(Emprestimo emprestimo)
			throws BibliotecaException {
		try {
			verificaDevolucao(emprestimo);
		} catch (BibliotecaException e) {
			throw new BibliotecaException(e.getMessage());
		}
	}

	public void verificaDevolucao(Emprestimo emprestimo)
			throws BibliotecaException {
		Aluno aluno = alunoDao.buscarAlunoPelaMatricula(emprestimo
				.getMatriculaAluno());
		Livro livro = livroDao.buscarLivroPeloIsbn(emprestimo.getIsbnLivro());
		if (ge.verificaParametrosNulos(aluno, livro)) {
			List<Livro> livros = ge.pegaListaDeLivros(aluno);
			devolver(livro, aluno, livros, emprestimo);
		}
	}

	private void devolver(Livro livro, Aluno aluno, List<Livro> livros,
			Emprestimo emprestimo) throws BibliotecaException {

		if (livros.contains(livro)) {
			livros.remove(livro);
			livro.setExemplares(livro.getExemplares() + 1);
			aluno.setLivros(livros);
			livroDao.alterar(livro);
			alunoDao.alterar(aluno);
			emprestimoDao.deletar(emprestimo);

		}
	}

	public Emprestimo getById(Integer idEmprestimo) throws BibliotecaException {
		try {
			return this.emprestimoDao.buscar(idEmprestimo);
		} catch (PersistenceException e) {
			throw new BibliotecaException(e.getMessage(), e);
		}

	}

	@TransacionalCdi
	public List<Emprestimo> getAll(Long matriculaAluno, Long isbnLivro,
			Boolean expirados) throws BibliotecaException {
		try {
			return this.emprestimoDao.getAll(matriculaAluno, isbnLivro,
					expirados);
		} catch (PersistenceException e) {
			throw new BibliotecaException(e.getMessage(), e);
		}
	}

	public void verificaEmprestimo(Emprestimo emprestimo)
			throws BibliotecaException {
		try {
			Aluno aluno = alunoDao.buscarAlunoPelaMatricula(emprestimo
					.getMatriculaAluno());
			Livro livro = livroDao.buscarLivroPeloIsbn(emprestimo
					.getIsbnLivro());
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
				livroDao.alterar(livro);
				alunoDao.alterar(aluno);
			}
		} catch (BibliotecaException e) {
			throw new BibliotecaException("Erro ao realizar este empréstimo.",
					e);
		}

	}

}
