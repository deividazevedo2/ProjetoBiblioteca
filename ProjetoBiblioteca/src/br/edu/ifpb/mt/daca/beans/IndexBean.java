package br.edu.ifpb.mt.daca.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifpb.mt.daca.entities.Aluno;
import br.edu.ifpb.mt.daca.exception.BibliotecaException;
import br.edu.ifpb.mt.daca.service.AlunoService;

@Named
@RequestScoped
public class IndexBean extends ClasseAbstrata {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6977557276403647255L;

	private List<Aluno> alunos;

	@Inject
	private AlunoService alunoService;

	private Long matriculaAluno;
	private String nomeAluno;

	@PostConstruct
	public void init() {
		filtrar();
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public Long getMatriculaAluno() {
		return matriculaAluno;
	}

	public void setMatriculaAluno(Long matriculaAluno) {
		this.matriculaAluno = matriculaAluno;
	}

	public String getNomeAluno() {
		return nomeAluno;
	}

	public void setNomeAluno(String nomeAluno) {
		this.nomeAluno = nomeAluno;
	}

	public void filtrar() {
		try {
			alunos = alunoService.getAll(matriculaAluno, nomeAluno);
		} catch (BibliotecaException e) {
			reportarMensagemDeErro(e.getMessage());
		}
	}

	public void limpar() {
		nomeAluno = null;
		matriculaAluno = null;
	}

}
