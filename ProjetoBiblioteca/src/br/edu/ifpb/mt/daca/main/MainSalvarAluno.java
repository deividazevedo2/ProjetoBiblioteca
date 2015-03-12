package br.edu.ifpb.mt.daca.main;

import br.edu.ifpb.mt.daca.embedded.Endereco;
import br.edu.ifpb.mt.daca.entities.Aluno;
import br.edu.ifpb.mt.daca.exception.BibliotecaException;
import br.edu.ifpb.mt.daca.service.AlunoService;

public class MainSalvarAluno {

	public static void main(String[] args) {
		AlunoService dao = new AlunoService();
		// LivroDAO livroDao = new LivroDAO();
		// AlugaDAO alugaDao = new AlugaDAO();
		// GerenciadorEmprestimo ge = new GerenciadorEmprestimo();
		// Aluga aluga = new Aluga();
		//
		// SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		// String dateInString = "7-Jun-2013";
		// Date date;
		// try {
		// date = formatter.parse(dateInString);
		// aluga.setDataDevolucao(date);
		// aluga.setDataEntrega(date);
		// aluga.setIsbnLivro(Long.valueOf("12345"));
		// aluga.setMatriculaAluno(Long.valueOf("11233"));
		//
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }
		// try {
		// alugaDao.alugar(aluga);
		// } catch (BibliotecaException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// Livro livro = new Livro();
		// livro.setIsbn(Long.valueOf(12345));
		// livro.setTitulo("Madagascar");
		// livro.setEditora("Abril");
		// livro.setDescricao("Entretenimento");
		// livro.setExemplares(5);
		// livroDao.salvar(livro);
		// System.out.println(livroDao.buscar(Long.valueOf(12345)));
		// // livroDao.deletar(livro);
		// System.out.println(livroDao.buscarLivroPeloNome("Madagascar"));

		try {
			Aluno aluno = new Aluno();
			Endereco endereco = new Endereco();

			endereco.setBairro("Bairro");
			endereco.setCep(58500000);
			endereco.setCidade("Monteiro");
			endereco.setRua("Rua");
			endereco.setNumero(12);

			aluno.setCpf("111");
			aluno.setNome("Braga");
			aluno.setTelefone("083-9999-9999");
			aluno.setEndereco(endereco);
			aluno.setMatricula(Long.valueOf(111));
			aluno.setCurso("ADS");

			System.out.println(aluno.getNome());

			dao.addAluno(aluno);

			System.out.println(aluno);
		} catch (BibliotecaException e) {
			e.printStackTrace();
		}
		// Aluno aluno = dao.buscarAlunoPelaMatricula(Long.valueOf(11233));
		// aluno.setNome("Indy Paula");
		// dao.alterar(aluno);

	}
}
