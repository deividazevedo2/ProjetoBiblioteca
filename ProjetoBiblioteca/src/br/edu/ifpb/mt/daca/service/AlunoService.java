package br.edu.ifpb.mt.daca.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.edu.ifpb.mt.daca.dao.AlunoDAO;
import br.edu.ifpb.mt.daca.entities.Aluno;
import br.edu.ifpb.mt.daca.entities.Livro;
import br.edu.ifpb.mt.daca.exception.BibliotecaException;
import br.edu.ifpb.mt.daca.util.TransacionalCdi;
import br.edu.ifpb.mt.daca.validator.Validador;

public class AlunoService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5535337875332772958L;

	@Inject
	private AlunoDAO alunoDao;

	@TransacionalCdi
	public void addAluno(Aluno aluno) throws BibliotecaException {
		try {
			this.alunoDao.salvar(aluno);
		} catch (BibliotecaException e) {
			throw new BibliotecaException(e.getMessage());
		}
	}

	@TransacionalCdi
	public void excluirAluno(Aluno aluno) throws BibliotecaException {
		try {
			this.alunoDao.deletar(aluno);
		} catch (BibliotecaException e) {
			throw new BibliotecaException(e.getMessage());
		}

	}

	@TransacionalCdi
	public void editarAluno(Aluno aluno) throws BibliotecaException {
		// try {
		this.alunoDao.alterar(aluno);
		// } catch (BibliotecaException e) {
		// throw new BibliotecaException(e.getMessage());
		// }

	}

	public void buscarAluno(Long matricula) throws BibliotecaException {
		alunoDao.buscar(matricula);
	}

	// public List<Aluno> busca(Long matricula){
	// alunoDao.buscarAluno
	// }

	public void addLivro(Livro livro) {

	}

	public void excluirLivro(Livro livro) {

	}

	public void editarLivro(Livro livro) {

	}

	public void buscarLivro(Long isbn) {

	}

	public void alugarLivro(Long isbn, Long matricula) {

	}

	public void devolverLivro(Long isbn, Long matricula) {

	}

}
