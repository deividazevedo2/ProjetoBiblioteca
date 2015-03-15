package br.edu.ifpb.mt.daca.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifpb.mt.daca.entities.Livro;
import br.edu.ifpb.mt.daca.exception.BibliotecaException;
import br.edu.ifpb.mt.daca.service.LivroService;

@Named
@ConversationScoped
public class DeletarLivroBean extends ClasseAbstrata {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7274557542385371855L;

	private Livro livro;

	@Inject
	private LivroService livroService;

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
			livroService.excluirLivro(livro);
			reportarMensagemDeSucesso("Livro removido com sucesso!");
		} catch (BibliotecaException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
		return "livros?faces-redirect=true";
	}

	public String cancel() {
		conversation.end();
		return "livros?faces-redirect=true";
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

}
