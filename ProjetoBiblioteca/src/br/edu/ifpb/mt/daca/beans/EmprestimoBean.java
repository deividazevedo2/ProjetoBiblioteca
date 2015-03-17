package br.edu.ifpb.mt.daca.beans;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.edu.ifpb.mt.daca.entities.Aluno;
import br.edu.ifpb.mt.daca.entities.Emprestimo;
import br.edu.ifpb.mt.daca.entities.Livro;
import br.edu.ifpb.mt.daca.exception.BibliotecaException;
import br.edu.ifpb.mt.daca.service.EmprestimoService;

@Named
// @ConversationScoped
@SessionScoped
public class EmprestimoBean extends ClasseAbstrata {

	private static final long serialVersionUID = -79727005056917194L;

	private Emprestimo emprestimo;
	private Aluno aluno;
	private Livro livro;

	@Inject
	private EmprestimoService emprestimoService;

	// @Inject
	// private Conversation conversation;

	public void preRenderView() {
		if (emprestimo == null) {
			emprestimo = new Emprestimo();
		}
		// if (conversation.isTransient()) {
		// conversation.begin();
		// }
	}

	public String efetuarEmprestimo() {
		// conversation.end();
		try {
			if (emprestimo.getIsbnLivro() != null
					&& emprestimo.getMatriculaAluno() != null) {
				emprestimoService.fazerEmprestimo(emprestimo);
				reportarMensagemDeSucesso("Empréstimo realizado com sucesso!");
			}
		} catch (BibliotecaException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}

		return EnderecoPaginas.PAGINA_PRINCIPAL_EMPRESTIMOS;
	}

	public String pagarAgora() {
		// conversation.end();
		try {
			emprestimoService.fazerDevolucao(emprestimo, false);
		} catch (BibliotecaException e) {
			reportarMensagemDeErro("Erro ao realizar o pagamento da multa!"
					+ e.getMessage());
		}

		return EnderecoPaginas.PAGINA_PRINCIPAL_EMPRESTIMOS;
	}

	public String pagarDepois() {
		// conversation.end();
		try {
			emprestimoService.fazerDevolucao(emprestimo, true);
		} catch (BibliotecaException e) {
			reportarMensagemDeErro("Erro ao realizar o pagamento da multa posteriormente!"
					+ e.getMessage());
		}
		return EnderecoPaginas.PAGINA_PRINCIPAL_EMPRESTIMOS;
	}

	public String confirmaSaldoDevedor(Emprestimo emp) {
		try {
			this.emprestimo = emprestimoService.capturaEmprestimo(emp);
			if (emprestimo.getId() != null
					&& ((emprestimo.getMulta() == null) || (emprestimo
							.getMulta() == 0))) {
				emprestimoService.fazerDevolucao(emprestimo, false);
			} else if (emprestimo.getId() != null && emprestimo.getMulta() > 0) {
				return EnderecoPaginas.PAGINA_CONFIRMACAO_DEVOLVER;

			}

		} catch (BibliotecaException e) {
			reportarMensagemDeErro("Erro ao realizar esta operação!"
					+ e.getMessage());
		}
		return EnderecoPaginas.PAGINA_PRINCIPAL_EMPRESTIMOS;
	}

	public String efetuarDevolucao() {
		String proxPagina = confirmaSaldoDevedor(emprestimo);
		if (proxPagina.equals(EnderecoPaginas.PAGINA_PRINCIPAL_EMPRESTIMOS)) {
			// conversation.end();
		}
		return proxPagina;

	}

	public Emprestimo getEmprestimo() {
		return emprestimo;
	}

	public void setEmprestimo(Emprestimo emprestimo) {
		this.emprestimo = emprestimo;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public void abrirDialogoAluno() {
		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("contentHeight", 470);

		RequestContext.getCurrentInstance().openDialog("listaAlunos", opcoes,
				null);

	}

	public void selecionarAluno(Aluno aluno) {
		RequestContext.getCurrentInstance().closeDialog(aluno);
	}

	public void alunoSelecionado(SelectEvent event) {
		Aluno aluno = (Aluno) event.getObject();
		emprestimo.setMatriculaAluno(aluno.getMatricula());
		setEmprestimo(emprestimo);
	}

	public void abrirDialogoLivro() {
		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("contentHeight", 470);

		RequestContext.getCurrentInstance().openDialog("listaLivros", opcoes,
				null);

	}

	public void selecionarLivro(Livro livro) {
		RequestContext.getCurrentInstance().closeDialog(livro);
	}

	public void livroSelecionado(SelectEvent event) {
		Livro livro = (Livro) event.getObject();
		emprestimo.setIsbnLivro(livro.getIsbn());
		setEmprestimo(emprestimo);
	}

}
