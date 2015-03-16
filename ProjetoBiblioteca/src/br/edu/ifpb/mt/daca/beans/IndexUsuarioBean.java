package br.edu.ifpb.mt.daca.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifpb.mt.daca.entities.User;
import br.edu.ifpb.mt.daca.exception.BibliotecaException;
import br.edu.ifpb.mt.daca.service.UserService;

@Named
@RequestScoped
public class IndexUsuarioBean extends ClasseAbstrata {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8838451015814331503L;

	private List<User> users;

	@Inject
	private UserService userService;

	private String firstNameFiltro;

	public List<User> getUsers() {
		return users;
	}

	public String getFirstNameFiltro() {
		return firstNameFiltro;
	}

	public void setFirstNameFiltro(String firstNameFiltro) {
		this.firstNameFiltro = firstNameFiltro;
	}

	@PostConstruct
	public void init() {
		filtrar();
	}

	public void filtrar() {
		try {
			users = userService.findUserByFirstName(firstNameFiltro);
		} catch (BibliotecaException e) {
			reportarMensagemDeErro(e.getMessage());
		}
	}

	public void limpar() {
		firstNameFiltro = null;
	}

}
