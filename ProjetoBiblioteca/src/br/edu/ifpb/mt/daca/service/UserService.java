package br.edu.ifpb.mt.daca.service;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.inject.Inject;

import br.edu.ifpb.mt.daca.dao.UserDAO;
import br.edu.ifpb.mt.daca.entities.User;
import br.edu.ifpb.mt.daca.exception.BibliotecaException;
import br.edu.ifpb.mt.daca.util.TransacionalCdi;

public class UserService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8086449267296650848L;

	@Inject
	private UserDAO userDAO;

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@TransacionalCdi
	public void save(User user) throws BibliotecaException {
		try {
			this.userDAO.save(user);
		} catch (BibliotecaException e) {
			throw new BibliotecaException(e.getMessage(), e);
		}
	}

	@TransacionalCdi
	public User update(User user) throws BibliotecaException {
		try {
			return this.userDAO.update(user);
		} catch (BibliotecaException e) {
			throw new BibliotecaException(e.getMessage(), e);
		}
	}

	@TransacionalCdi
	public void delete(User user) throws BibliotecaException {
		try {
			this.userDAO.delete(user);
		} catch (BibliotecaException e) {
			throw new BibliotecaException(e.getMessage(), e);
		}
	}

	public User getByID(int userId) throws BibliotecaException {
		try {
			return this.userDAO.getByID(userId);
		} catch (BibliotecaException e) {
			throw new BibliotecaException(e.getMessage(), e);
		}
	}

	public List<User> getAll() throws BibliotecaException {
		try {
			return this.userDAO.getAll();
		} catch (BibliotecaException e) {
			throw new BibliotecaException(e.getMessage(), e);
		}
	}

	public List<User> findUserByFirstName(String firstName) throws BibliotecaException {
		try {
			return this.userDAO.findUserByFirstName(firstName);
		} catch (BibliotecaException e) {
			throw new BibliotecaException(e.getMessage(), e);
		}
	}

	public void criptografarSenha(User user) throws BibliotecaException {
		user.setPassword(criptografarSenha(user.getPassword()));
	}

	private static String criptografarSenha(String password) throws BibliotecaException {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes("UTF-8")); // Change this to "UTF-16" if needed
			byte[] digest = md.digest();
			BigInteger bigInt = new BigInteger(1, digest);
			String output = bigInt.toString(16);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new BibliotecaException("Não foi possível criptografar a senha!");
		} catch (UnsupportedEncodingException e) {
			throw new BibliotecaException("Não foi possível criptografar a senha!");
		}
	}

}
