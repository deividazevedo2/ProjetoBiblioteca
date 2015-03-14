package br.edu.ifpb.mt.daca.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.edu.ifpb.mt.daca.entities.Aluno;
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

	private List<Emprestimo> todosEmprestimos;
	private List<Emprestimo> emprestimosExpirados;

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

	public List<Emprestimo> getEmprestimosExpirados() {
		return emprestimosExpirados;
	}

	public List<Emprestimo> getTodosEmprestimos() {
		return todosEmprestimos;
	}

	public void setEmprestimosExpirados(List<Emprestimo> emprestimosExpirados) {
		this.emprestimosExpirados = emprestimosExpirados;
	}

	public void setTodosEmprestimos(List<Emprestimo> todosEmprestimos) {
		this.todosEmprestimos = todosEmprestimos;
	}

	public void filtrar() {
		try {
			todosEmprestimos = emprestimoService.getAll(isbnLivro,
					matriculaAluno, false);
			emprestimosExpirados = emprestimoService.getAll(null, null, true);
		} catch (BibliotecaException e) {
			reportarMensagemDeErro(e.getMessage());
		}
	}

	public void limpar() {
		matriculaAluno = null;
		isbnLivro = null;
	}

	public void abrirDialogo() {
		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("contentHeight", 470);

		RequestContext.getCurrentInstance().openDialog("emprestimosExpirados",
				opcoes, null);

	}

	public void fecharDialogo() {
		RequestContext.getCurrentInstance().closeDialog(null);
	}

}
