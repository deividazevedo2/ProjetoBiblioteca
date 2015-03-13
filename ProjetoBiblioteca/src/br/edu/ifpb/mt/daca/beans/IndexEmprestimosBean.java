package br.edu.ifpb.mt.daca.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifpb.mt.daca.entities.Emprestimo;
import br.edu.ifpb.mt.daca.exception.BibliotecaException;
import br.edu.ifpb.mt.daca.service.EmprestimoService;

@Named
@RequestScoped
public class IndexEmprestimosBean extends ClasseAbstrata {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6977557276403647255L;

	private List<Emprestimo> emprestimos;

	@Inject
	private EmprestimoService emprestimoService;

	private Long isbnLivro;
	private Long matriculaAluno;

	@PostConstruct
	public void init() {
		filtrar();
	}

	public Long getIsbnLivro() {
		return isbnLivro;
	}

	public Long getMatriculaAluno() {
		return matriculaAluno;
	}

	public void setIsbnLivro(Long isbnLivro) {
		this.isbnLivro = isbnLivro;
	}

	public void setMatriculaAluno(Long matriculaAluno) {
		this.matriculaAluno = matriculaAluno;
	}

	public List<Emprestimo> getEmprestimos() {
		return emprestimos;
	}

	public void filtrar() {
		try {
			emprestimos = emprestimoService.getAll(isbnLivro, matriculaAluno);
		} catch (BibliotecaException e) {
			reportarMensagemDeErro(e.getMessage());
		}
	}

	public void limpar() {
		matriculaAluno = null;
		isbnLivro = null;
	}

}
