package br.edu.ifpb.mt.daca.entities;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifpb.mt.daca.dao.LivroDAO;
import br.edu.ifpb.mt.daca.exception.BibliotecaException;

@Named
@RequestScoped
public class LivroConverter implements Converter {

	@Inject
	private LivroDAO livros;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String valor) {
		if (valor == null || valor.trim().isEmpty()) {
			return null;
		}
		Long isbn = Long.valueOf(valor);

		try {
			return livros.buscar(isbn);
		} catch (BibliotecaException e) {
			String msgErroStr = String
					.format("Erro de conversão! Não foi possível realizar a conversão da string '%s' para o tipo esperado.",
							valor);
			FacesMessage msgErro = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, msgErroStr, msgErroStr);
			throw new ConverterException(msgErro);
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object valor) {
		if (valor == null) {
			return null;
		}
		Long idLivro = ((Livro) valor).getId();

		return (idLivro != null) ? idLivro.toString() : null;
	}

}
