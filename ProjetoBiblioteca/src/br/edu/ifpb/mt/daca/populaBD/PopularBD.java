package br.edu.ifpb.mt.daca.populaBD;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import br.edu.ifpb.mt.daca.dao.UserDAO;
import br.edu.ifpb.mt.daca.entities.Grupo;
import br.edu.ifpb.mt.daca.entities.User;
import br.edu.ifpb.mt.daca.exception.BibliotecaException;
import br.edu.ifpb.mt.daca.service.UserService;
import br.edu.ifpb.mt.daca.util.JPAUtil;

public class PopularBD {

	public static void main(String[] args) {

		JPAUtil jpaUtil = new JPAUtil();

		EntityManagerFactory emf = null;
		EntityManager em = null;
		EntityTransaction tx = null;
		UserService userService = new UserService();
		userService.setUserDAO(new UserDAO());

		try {
			emf = jpaUtil.criarEMF();
			em = emf.createEntityManager();

			userService.getUserDAO().setEntityManager(em);

			tx = em.getTransaction();
			tx.begin();

			List<User> usuarios = new ArrayList<User>();

			usuarios.add(getUsuarioAdmin());
			usuarios.add(getUsuarioVisitante());

			for (User user : usuarios) {
				userService.criptografarSenha(user);
				userService.save(user);
			}

			tx.commit();
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		} catch (BibliotecaException e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
			if (emf != null) {
				emf.close();
			}
		}

	}

	private static User getUsuarioVisitante() {
		User user = new User();

		user.setLogin("admin");
		user.setPassword("123456");
		user.setGrupo(Grupo.ADMIN);

		user.setFirstName("Fulano");
		user.setLastName("Tal");
		user.setBirthday(new Date());
		user.setEmail("admin@ggmail.com");

		return user;
	}

	private static User getUsuarioAdmin() {
		User user = new User();

		user.setLogin("visitante");
		user.setPassword("123456");
		user.setGrupo(Grupo.VISITANTE);

		user.setFirstName("Sicrano");
		user.setLastName("Tal");
		user.setBirthday(new Date());
		user.setEmail("visitante@ggmail.com");

		return user;
	}

}
