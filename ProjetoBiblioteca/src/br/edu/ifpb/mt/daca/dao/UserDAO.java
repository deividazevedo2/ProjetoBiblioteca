package br.edu.ifpb.mt.daca.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import br.edu.ifpb.mt.daca.entities.User;
import br.edu.ifpb.mt.daca.exception.BibliotecaException;

public class UserDAO extends DAO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7648980817529062236L;

	public void save(User user) throws BibliotecaException {
		EntityManager em = getEntityManager();
		try {
			em.persist(user);
		} catch (PersistenceException pe) {
			throw new BibliotecaException("Ocorreu algum problema ao tentar salvar o usuário.", pe);
		}
	}

	public User update(User user) throws BibliotecaException {
		EntityManager em = getEntityManager();
		User resultado = user;
		try {
			resultado = em.merge(user);
		} catch (PersistenceException pe) {
			throw new BibliotecaException("Ocorreu algum problema ao tentar atualizar o usuário.", pe);
		}

		return resultado;
	}

	public void delete(User user) throws BibliotecaException {
		EntityManager em = getEntityManager();
		try {
			user = em.merge(user);
			em.remove(user);
		} catch (PersistenceException pe) {
			throw new BibliotecaException("Ocorreu algum problema ao tentar remover o usuário.", pe);
		}
	}

	public User getByID(int userId) throws BibliotecaException {
		EntityManager em = getEntityManager();
		User resultado = null;
		try {
			resultado = em.find(User.class, userId);
		} catch (PersistenceException pe) {
			throw new BibliotecaException("Ocorreu algum problema ao tentar recuperar o usuário.", pe);
		}

		return resultado;
	}

	public List<User> getAll() throws BibliotecaException {
		EntityManager em = getEntityManager();
		List<User> resultado = null;
		try {
			TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
			resultado = query.getResultList();
		} catch (PersistenceException pe) {
			throw new BibliotecaException("Ocorreu algum problema ao tentar recuperar os usuários.", pe);
		}

		return resultado;
	}
	
	public List<User> findUserByFirstName(String firstName) throws BibliotecaException {
		EntityManager em = getEntityManager();
		List<User> resultado = null;
		if (firstName == null) {
			firstName = "";
		}
		try {
			TypedQuery<User> query = em.createQuery("select u from User u where u.firstName like :firstName",
					User.class);
			query.setParameter("firstName", "%" + firstName + "%");
			resultado = query.getResultList();
		} catch (PersistenceException pe) {
			throw new BibliotecaException("Ocorreu algum problema ao tentar recuperar os usuários com base no primeiro nome.", pe);
		}

		return resultado;
	}
}
