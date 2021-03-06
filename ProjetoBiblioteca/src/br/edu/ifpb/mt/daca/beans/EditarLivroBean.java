package br.edu.ifpb.mt.daca.beans;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.edu.ifpb.mt.daca.entities.Livro;
import br.edu.ifpb.mt.daca.exception.BibliotecaException;
import br.edu.ifpb.mt.daca.service.LivroService;

@Named
@ConversationScoped
public class EditarLivroBean extends ClasseAbstrata {

	private static final long serialVersionUID = 2951780582455065780L;

	private Livro livro;

	@Inject
	private LivroService livroService;

	@Inject
	private Conversation conversation;

	public void preRenderView() {
		if (livro == null) {
			livro = new Livro();
		}
		if (conversation.isTransient()) {
			conversation.begin();
		}
	}

	public String salvarLivro() {
		conversation.end();
		try {
			if (livro.getId() != null) {
				livroService.editarLivro(livro);
				reportarMensagemDeSucesso("Livro " + livro.getTitulo()
						+ " atualizado com sucesso!");
			} else {
				livroService.addLivro(livro);
				reportarMensagemDeSucesso("Livro " + livro.getTitulo()
						+ " salvo com sucesso!");
			}
		} catch (BibliotecaException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}

		return EnderecoPaginas.PAGINA_PRINCIPAL_LIVROS;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public void abrirDialogo() {
		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("contentHeight", 470);

		RequestContext.getCurrentInstance().openDialog("listaLivros", opcoes,
				null);

	}

	public void selecionar(Livro livro) {
		RequestContext.getCurrentInstance().closeDialog(livro);
	}

	public void livroSelecionado(SelectEvent event) {
		Livro livro = (Livro) event.getObject();
		setLivro(livro);
	}
}
