package br.edu.ifpb.mt.daca.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifpb.mt.daca.entities.Aluno;
import br.edu.ifpb.mt.daca.exception.BibliotecaException;
import br.edu.ifpb.mt.daca.service.AlunoService;

@Named
@ConversationScoped
public class DeletarAlunoBean extends ClasseAbstrata {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7500123259489897695L;

	private Aluno aluno;

	@Inject
	@RequestScoped
	private AlunoService alunoService;

	@Inject
	private Conversation conversation;

	@PostConstruct
	public void init() {
		if (conversation.isTransient()) {
			conversation.begin();
		}
	}

	public String deletar() {
		try {
			conversation.end();
			alunoService.excluirAluno(aluno);
			reportarMensagemDeSucesso("Aluno removido com sucesso!");
		} catch (BibliotecaException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
		return "index?faces-redirect=true";
	}

	public String cancel() {
		conversation.end();
		return "index?faces-redirect=true";
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

}
