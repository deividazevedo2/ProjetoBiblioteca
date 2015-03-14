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
			if (aluno.getId() != null) {
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

		return "alunos.xhtml?faces-redirect=true";
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

//	public void abrirDialogo() {
//		Map<String, Object> opcoes = new HashMap<>();
//		opcoes.put("modal", true);
//		opcoes.put("resizable", false);
//		opcoes.put("contentHeight", 470);
//
//		RequestContext.getCurrentInstance().openDialog("listaAlunos", opcoes,
//				null);
//
//	}
//
//	public void selecionar(Aluno aluno) {
//		RequestContext.getCurrentInstance().closeDialog(aluno);
//	}
//
//	public void alunoSelecionado(SelectEvent event) {
//		Aluno aluno = (Aluno) event.getObject();
//		setAluno(aluno);
//	}
}
