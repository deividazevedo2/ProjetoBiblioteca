package br.edu.ifpb.mt.daca.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

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

	private List<Emprestimo> emprestimosEmAberto;
	private List<Emprestimo> emprestimosExpirados;
	private List<Emprestimo> historicoDeEmprestimos;

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

	public void setEmprestimosExpirados(List<Emprestimo> emprestimosExpirados) {
		this.emprestimosExpirados = emprestimosExpirados;
	}

	public List<Emprestimo> getEmprestimosEmAberto() {
		return emprestimosEmAberto;
	}

	public void setEmprestimosEmAberto(List<Emprestimo> emprestimosEmAberto) {
		this.emprestimosEmAberto = emprestimosEmAberto;
	}

	public List<Emprestimo> getHistoricoDeEmprestimos() {
		return historicoDeEmprestimos;
	}

	public void setHistoricoDeEmprestimos(
			List<Emprestimo> historicoDeEmprestimos) {
		this.historicoDeEmprestimos = historicoDeEmprestimos;
	}

	public void filtrar() {
		try {
			emprestimosEmAberto = emprestimoService.getAll(matriculaAluno,
					isbnLivro, false, true);
			emprestimosExpirados = emprestimoService.getAll(null, null, true,
					false);
			historicoDeEmprestimos = emprestimoService.getAll(matriculaAluno,
					isbnLivro, false, false);
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
