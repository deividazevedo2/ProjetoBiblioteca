package br.edu.ifpb.mt.service;

import java.io.Serializable;

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
	AlunoDAO alunoDao;

	Validador validar = new Validador();

	@TransacionalCdi
	public void AddAluno(Aluno aluno) throws BibliotecaException {
		if (validar.validarAluno(aluno))
			this.alunoDao.salvar(aluno);
	}

	@TransacionalCdi
	public void ExcluirAluno(Aluno aluno) throws BibliotecaException {
		if (validar.validarAluno(aluno))
			this.alunoDao.deletar(aluno);

	}

	@TransacionalCdi
	public void EditarAluno(Aluno aluno) throws BibliotecaException {
		if (validar.validarAluno(aluno))
			this.alunoDao.alterar(aluno);
	}

	@TransacionalCdi
	public void BuscarAluno(Long matricula) throws BibliotecaException {
		if(validar.validarCampo(matricula))
		alunoDao.buscar(matricula);
	}

	public void AddLivro(Livro livro) {

	}

	public void ExcluirLivro(Livro livro) {

	}

	public void EditarLivro(Livro livro) {

	}

	public void BuscarLivro(Long isbn) {

	}

	public void AlugarLivro(Long isbn, Long matricula) {

	}

	public void DevolverLivro(Long isbn, Long matricula) {

	}

}
