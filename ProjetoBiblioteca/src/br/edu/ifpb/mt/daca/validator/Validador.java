package br.edu.ifpb.mt.daca.validator;

import br.edu.ifpb.mt.daca.dao.AlunoDAO;
import br.edu.ifpb.mt.daca.entities.Aluno;

public class Validador {

	AlunoDAO alunoDao = new AlunoDAO();

	public boolean validarAluno(Aluno aluno) {
		if (aluno.getCpf() == null || aluno.getCurso() == null
				|| aluno.getMatricula() == null || aluno.getNome() == null
				|| aluno.getTelefone() == null) {
			return false;
		}
		if (alunoDao.buscarAlunoPelaMatricula(aluno.getMatricula()) != null) {
			return false;
		}
		return true;
	}

	public boolean validarCampo(Long valor) {
		if (valor == null || valor.equals("")) {
			return false;

		}
		return true;
	}

	public boolean validarMatricula(Long matricula) {
		if (alunoDao.buscarAlunoPelaMatricula(matricula) != null)
			return false;

		return true;
	}

}
