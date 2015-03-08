package br.edu.ifpb.mt.gerenciadores;

import br.edu.ifpb.mt.daca.dao.AlunoDAO;
import br.edu.ifpb.mt.daca.entities.Aluno;
import br.edu.ifpb.mt.daca.entities.Livro;
import br.edu.ifpb.mt.daca.entities.Pessoa;
import br.edu.ifpb.mt.daca.fachada.Fachada;

public class GerenciadorGeral implements Fachada {

	AlunoDAO alunoDao = new AlunoDAO();
	Aluno aluno = new Aluno();

	@Override
	public void AddPessoa(Pessoa p) {

		alunoDao.save(aluno);

	}

	@Override
	public void ExcluirPessoa(Pessoa p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void EditarPessoa(Pessoa p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void BuscarPessoa(Pessoa p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void AddLivro(Livro l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ExcluirLivro(Livro l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void EditarLivro(Livro l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void BuscarLivro(Livro l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void AlugarLivro(Livro l, Pessoa pessoa) {
		// TODO Auto-generated method stub

	}

	@Override
	public void DevolverLivro(Livro l, Pessoa pessoa) {
		// TODO Auto-generated method stub

	}

}
