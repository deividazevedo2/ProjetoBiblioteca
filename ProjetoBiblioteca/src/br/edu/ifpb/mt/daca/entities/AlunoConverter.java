package br.edu.ifpb.mt.daca.entities;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifpb.mt.daca.dao.AlunoDAO;
import br.edu.ifpb.mt.daca.exception.BibliotecaException;

@Named
@RequestScoped
public class AlunoConverter implements Converter {

	@Inject
	private AlunoDAO alunos;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String valor) {
		if (valor == null || valor.trim().isEmpty()) {
			return null;
		}
		Integer matricula = Integer.valueOf(valor);

		try {
			return alunos.buscar(matricula);
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
		String cpfAluno = ((Aluno) valor).getCpf();

		return (cpfAluno != null) ? cpfAluno.toString() : null;
	}

}
