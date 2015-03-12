package br.edu.ifpb.mt.daca.validator;

import br.edu.ifpb.mt.daca.dao.AlunoDAO;
import br.edu.ifpb.mt.daca.entities.Aluno;
import br.edu.ifpb.mt.daca.exception.BibliotecaException;

public class Validador {

	AlunoDAO alunoDao;

	public boolean validarAluno(Aluno aluno) throws BibliotecaException {
		if (aluno.getCpf() == null || aluno.getCurso() == null
				|| aluno.getMatricula() == null || aluno.getNome() == null
				|| aluno.getTelefone() == null) {
			return false;
		}
//		if (validarMatricula(aluno.getMatricula())) {
//			return false;
//		}
		return true;
	}

	public boolean validarCampo(Long valor) {
		if (valor == null || valor.equals("")) {
			return false;

		}
		return true;
	}

	public boolean validarMatricula(Long matricula) throws BibliotecaException {
		if (alunoDao.buscarAlunoPelaMatricula(matricula) != null)
			return false;

		return true;
	}

}
