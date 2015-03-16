package br.edu.ifpb.mt.daca.beans;

import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifpb.mt.daca.entities.Grupo;
import br.edu.ifpb.mt.daca.entities.User;
import br.edu.ifpb.mt.daca.exception.BibliotecaException;
import br.edu.ifpb.mt.daca.service.UserService;

@Named
@ConversationScoped
public class UserEditBean extends ClasseAbstrata {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2985423005613270820L;

	private User user;

	private String oldPassword;

	@Inject
	private UserService userService;

	@Inject
	private Conversation conversation;

	private List<Grupo> grupos;

	public void preRenderView() {
		if (user == null) {
			user = new User();
		} else {
			oldPassword = user.getPassword();
		}
		if (grupos == null) {
			grupos = Arrays.asList(Grupo.values());
		}
		if (conversation.isTransient()) {
			conversation.begin();
		}
	}

	public String saveUser() {
		conversation.end();
		try {
			if (user.getId() != null) {
				if (!user.getPassword().equals(oldPassword)) {
					userService.criptografarSenha(user);
				}
				userService.update(user);
				reportarMensagemDeSucesso("Usuário atualizado com sucesso!");
			} else {
				userService.criptografarSenha(user);
				userService.save(user);
				reportarMensagemDeSucesso("Usuário criado com sucesso!");
			}
		} catch (BibliotecaException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}

		return EnderecoPaginas.PAGINA_PRINCIPAL_USUARIOS;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}
	
}
