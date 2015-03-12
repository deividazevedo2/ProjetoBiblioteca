package br.edu.ifpb.mt.daca.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.edu.ifpb.mt.daca.dao.AlugaDAO;
import br.edu.ifpb.mt.daca.dao.AlunoDAO;
import br.edu.ifpb.mt.daca.dao.LivroDAO;
import br.edu.ifpb.mt.daca.embedded.Endereco;
import br.edu.ifpb.mt.daca.entities.Aluga;
import br.edu.ifpb.mt.daca.entities.Aluno;
import br.edu.ifpb.mt.daca.entities.Livro;
import br.edu.ifpb.mt.service.GerenciadorEmprestimo;

public class MainSalvarAluno {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AlunoDAO dao = new AlunoDAO();
		LivroDAO livroDao = new LivroDAO();
		AlugaDAO alugaDao = new AlugaDAO();
		GerenciadorEmprestimo ge = new GerenciadorEmprestimo();
		Aluga aluga = new Aluga();

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		String dateInString = "7-Jun-2013";
		Date date;
		try {
			date = formatter.parse(dateInString);
			aluga.setDataDevolucao(date);
			aluga.setDataEntrega(date);
			aluga.setIsbnLivro(Long.valueOf("12345"));
			aluga.setMatriculaAluno(Long.valueOf("11233"));

		} catch (ParseException e) {
			e.printStackTrace();
		}
		alugaDao.alugar(aluga);

		
//		System.out.println(dao.buscarAlunoPeloNome("Maria"));
//		Livro livro = new Livro();
//		livro.setIsbn(Long.valueOf(12345));
//		livro.setTitulo("Madagascar");
//		livro.setEditora("Abril");
//		livro.setDescricao("Entretenimento");
//		livro.setExemplares(5);
//		livroDao.salvar(livro);
//		System.out.println(livroDao.buscar(Long.valueOf(12345)));
//		// livroDao.deletar(livro);
//		System.out.println(livroDao.buscarLivroPeloNome("Madagascar"));
//
//		try {
//			Aluno aluno = new Aluno();
//			Endereco endereco = new Endereco();
//
//			endereco.setBairro("Bairro");
//			endereco.setCep(58500000);
//			endereco.setCidade("Monteiro");
//			endereco.setRua("Rua");
//			endereco.setNumero(12);
//
//			aluno.setCpf("12356");
//			aluno.setNome("Maria");
//			aluno.setTelefone("083-9999-9999");
//			aluno.setEndereco(endereco);
//			aluno.setMatricula(Long.valueOf(11233));
//			aluno.setCurso("ADS");
//
//			System.out.println(aluno.getNome());
//
//			dao.salvar(aluno);
//
//			System.out.println(aluno);
//		} finally {
//			dao.close();
//		}
		// Aluno aluno = dao.buscarAlunoPelaMatricula(Long.valueOf(11233));
		// aluno.setNome("Indy Paula");
		// dao.alterar(aluno);

	}
}
