package br.edu.ifpb.mt.gerenciadores;

import br.edu.ifpb.mt.daca.dao.AlugaDAO;
import br.edu.ifpb.mt.daca.dao.AlunoDAO;
import br.edu.ifpb.mt.daca.entities.Aluno;
import br.edu.ifpb.mt.daca.entities.Livro;
import br.edu.ifpb.mt.daca.entities.Pessoa;
import br.edu.ifpb.mt.daca.fachada.Fachada;

public class GerenciadorGeral implements Fachada {

	AlunoDAO alunoDao = new AlunoDAO();
	AlugaDAO alugaDAO = new AlugaDAO();

	@Override
	public void AddAluno(Aluno p) {
		alunoDao.salvar(p);

	}

	@Override
	public void ExcluirAluno(Aluno aluno) {
		alunoDao.deletar(aluno);

	}

	@Override
	public void EditarAluno(Aluno p) {

	}

	@Override
	public void BuscarAluno(Aluno p) {

	}

	@Override
	public void AddLivro(Livro l) {

	}

	@Override
	public void ExcluirLivro(Livro l) {

	}

	@Override
	public void EditarLivro(Livro l) {

	}

	@Override
	public void BuscarLivro(Livro l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void AlugarLivro(Livro l, Pessoa pessoa) {

	}

	@Override
	public void DevolverLivro(Livro l, Pessoa pessoa) {
		// TODO Auto-generated method stub

	}

}
