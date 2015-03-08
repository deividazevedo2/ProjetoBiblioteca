package br.edu.ifpb.mt.daca.fachada;

import br.edu.ifpb.mt.daca.entities.Livro;
import br.edu.ifpb.mt.daca.entities.Pessoa;

public interface Fachada {

	public void AddPessoa(Pessoa p);

	public void ExcluirPessoa(Pessoa p);

	public void EditarPessoa(Pessoa p);

	public void BuscarPessoa(Pessoa p);

	public void AddLivro(Livro l);

	public void ExcluirLivro(Livro l);

	public void EditarLivro(Livro l);

	public void BuscarLivro(Livro l);

	public void AlugarLivro(Livro l, Pessoa pessoa);

	public void DevolverLivro(Livro l, Pessoa pessoa);

}
