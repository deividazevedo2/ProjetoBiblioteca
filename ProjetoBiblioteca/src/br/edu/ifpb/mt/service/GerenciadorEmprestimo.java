package br.edu.ifpb.mt.service;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.mt.daca.dao.AlunoDAO;
import br.edu.ifpb.mt.daca.dao.LivroDAO;
import br.edu.ifpb.mt.daca.entities.Aluno;
import br.edu.ifpb.mt.daca.entities.Livro;

public class GerenciadorEmprestimo {

	AlunoDAO alunoDao = new AlunoDAO();
	LivroDAO livroDao = new LivroDAO();

	public void fazerEmprestimo(Long matricula, Long isbnLivro) {
		Aluno aluno = alunoDao.buscarAlunoPelaMatricula(matricula);
		Livro livro = livroDao.buscar(isbnLivro);
		if (verificaParametrosNulos(aluno, livro)
				&& verificaQuantidadeExemplares(livro)) {
			List<Livro> livros = pegaListaDeLivros(aluno);
			livros.add(livro);
			aluno.setLivros(livros);
			livro.setExemplares(livro.getExemplares() - 1);
			alunoDao.alterar(aluno);
			livroDao.alterar(livro);
		}

	}

	private List<Livro> pegaListaDeLivros(Aluno aluno) {
		List<Livro> livros = aluno.getLivros();
		if (livros == null) {
			livros = new ArrayList<Livro>();
		}
		return livros;
	}

	private boolean verificaQuantidadeExemplares(Livro livro) {
		if (livro.getExemplares() > 1) {
			return true;
		}
		return false;

	}

	private boolean verificaParametrosNulos(Object obj1, Object obj2) {
		if (obj1 == null || obj2 == null) {
			return false;
		}
		return true;
	}

}
