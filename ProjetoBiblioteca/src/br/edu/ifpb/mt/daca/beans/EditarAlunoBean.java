package br.edu.ifpb.mt.daca.beans;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifpb.mt.daca.entities.Aluno;
import br.edu.ifpb.mt.daca.exception.BibliotecaException;
import br.edu.ifpb.mt.daca.service.AlunoService;

@Named
@ConversationScoped
public class EditarAlunoBean extends ClasseAbstrata {

	/**
	 * 
	 */
	private static final long serialVersionUID = -79727005056917194L;

	private Aluno aluno;

	@Inject
	private AlunoService alunoService;

	@Inject
	private Conversation conversation;

	public void preRenderView() {
		if (aluno == null) {
			aluno = new Aluno();
		}
		if (conversation.isTransient()) {
			conversation.begin();
		}
	}

	public String salvarAluno() {
		conversation.end();
		try {
			if (aluno.getCpf() != null) {
				alunoService.editarAluno(aluno);
				reportarMensagemDeSucesso("Aluno " + aluno.getNome()
						+ " atualizado com sucesso!");
			} else {
				alunoService.addAluno(aluno);
				reportarMensagemDeSucesso("Aluno " + aluno.getNome()
						+ " salvo com sucesso!");
			}
		} catch (BibliotecaException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
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
