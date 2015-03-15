package br.edu.ifpb.mt.daca.beans;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifpb.mt.daca.entities.Emprestimo;
import br.edu.ifpb.mt.daca.exception.BibliotecaException;
import br.edu.ifpb.mt.daca.service.EmprestimoService;

@Named
@ConversationScoped
public class EmprestimoBean extends ClasseAbstrata {

	private static final long serialVersionUID = -79727005056917194L;

	private Emprestimo emprestimo;

	@Inject
	private EmprestimoService emprestimoService;

	@Inject
	private Conversation conversation;

	public void preRenderView() {
		if (emprestimo == null) {
			emprestimo = new Emprestimo();
		}
		if (conversation.isTransient()) {
			conversation.begin();
		}
	}

	public String efetuarEmprestimo() {
		conversation.end();
		try {
			if (emprestimo.getIsbnLivro() != null
					&& emprestimo.getMatriculaAluno() != null) {
				emprestimoService.fazerEmprestimo(emprestimo);
				reportarMensagemDeSucesso("Empréstimo realizado com sucesso!");
			}
		} catch (BibliotecaException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}

		return "emprestimos?faces-redirect=true";
	}

	public String efetuarDevolucao() {
		conversation.end();
		try {
			if (emprestimo.getIsbnLivro() != null
					&& emprestimo.getMatriculaAluno() != null) {
				emprestimoService.fazerDevolucao(emprestimo);
				reportarMensagemDeSucesso("Devolução realizada com sucesso!");
			}
		} catch (BibliotecaException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}

		return "emprestimos?faces-redirect=true";
	}

	public Emprestimo getEmprestimo() {
		return emprestimo;
	}

	public void setEmprestimo(Emprestimo emprestimo) {
		this.emprestimo = emprestimo;
	}
}
