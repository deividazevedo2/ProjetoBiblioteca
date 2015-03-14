package br.edu.ifpb.mt.daca.service;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import br.edu.ifpb.mt.daca.dao.AlunoDAO;
import br.edu.ifpb.mt.daca.dao.LivroDAO;
import br.edu.ifpb.mt.daca.entities.Aluno;
import br.edu.ifpb.mt.daca.entities.Emprestimo;
import br.edu.ifpb.mt.daca.entities.Livro;
import br.edu.ifpb.mt.daca.exception.BibliotecaException;

public class GerenciadorEmprestimo {

	AlunoDAO alunoDao = new AlunoDAO();
	LivroDAO livroDao = new LivroDAO();

	public void fazerEmprestimo(Emprestimo emprestimo)
			throws BibliotecaException {
		Aluno aluno = alunoDao.buscarAlunoPelaMatricula(emprestimo
				.getMatriculaAluno());
		Livro livro = livroDao.buscar(emprestimo.getIsbnLivro());
		if (verificaParametrosNulos(aluno, livro)
				&& verificaQuantidadeExemplares(livro)) {
			List<Livro> livros = pegaListaDeLivros(aluno);
			Livro livro2 = livro;
			livro2.setExemplares(1);
			livros.add(livro2);
			aluno.setLivros(livros);
			livro.setExemplares(livro.getExemplares() - 1);
			alunoDao.alterar(aluno);
			livroDao.alterar(livro);
			atualizarDatas(emprestimo);
		}

	}

	public List<Livro> pegaListaDeLivros(Aluno aluno) {
		List<Livro> livros = aluno.getLivros();
		if (livros == null) {
			livros = new ArrayList<Livro>();
		}
		return livros;
	}

	public boolean verificaQuantidadeExemplares(Livro livro) {
		if (livro.getExemplares() > 1) {
			return true;
		}
		return false;

	}

	public boolean verificaParametrosNulos(Object obj1, Object obj2) {
		if (obj1 == null || obj2 == null) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("static-access")
	public void atualizarDatas(Emprestimo emprestimo) {
		GregorianCalendar gc = new GregorianCalendar();
		emprestimo.setDataEmprestimo(gc.getTime());
		gc.add(gc.DAY_OF_MONTH, 10);
		emprestimo.setDataDevolucao(gc.getTime());

	}

}
