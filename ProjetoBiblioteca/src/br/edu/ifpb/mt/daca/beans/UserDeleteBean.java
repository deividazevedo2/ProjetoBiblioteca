package br.edu.ifpb.mt.daca.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifpb.mt.daca.entities.User;
import br.edu.ifpb.mt.daca.exception.BibliotecaException;
import br.edu.ifpb.mt.daca.service.UserService;

@Named
@ConversationScoped
public class UserDeleteBean extends ClasseAbstrata {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2914307108338043685L;

	private User user;

	@Inject @RequestScoped
	private UserService userService;

	@Inject
	private Conversation conversation;
	
	@PostConstruct
	public void init() {
		if (conversation.isTransient()) {
			conversation.begin();
		}
	}
	
	public String delete() {
		try {
			conversation.end();
			userService.delete(user);
			reportarMensagemDeSucesso("Usuário removido com sucesso!");
		} catch (BibliotecaException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
		return EnderecoPaginas.PAGINA_PRINCIPAL_USUARIOS;
	}

	public String cancel() {
		conversation.end();
		return EnderecoPaginas.PAGINA_PRINCIPAL_USUARIOS;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
