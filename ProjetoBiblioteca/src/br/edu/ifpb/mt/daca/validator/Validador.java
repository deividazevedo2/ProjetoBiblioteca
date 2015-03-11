package br.edu.ifpb.mt.daca.validator;

import br.edu.ifpb.mt.daca.entities.Aluno;

public class Validador {

	public void validarAluno(Aluno aluno) {
		if (aluno.getCpf() == null || aluno.getCurso() == null
				|| aluno.getMatricula() == null || aluno.getNome() == null) {

		}

	}
}
