package br.edu.ifpb.mt.daca.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.edu.ifpb.mt.daca.entities.Aluno;
import br.edu.ifpb.mt.daca.exception.BibliotecaException;
import br.edu.ifpb.mt.service.AlunoService;

@ViewScoped
@ManagedBean
public class EditarAlunoBean {

	private Aluno aluno;

	private AlunoService alunoService = new AlunoService();

	public void preRenderView() {
		if (aluno == null) {
			aluno = new Aluno();
		}
	}

	public String salvarAluno() throws BibliotecaException {
		if (aluno.getCpf() != null) {
			alunoService.EditarAluno(aluno);
		} else {
			alunoService.AddAluno(aluno);
		}

		return "index.xhtml?faces-redirect=true";
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
}
