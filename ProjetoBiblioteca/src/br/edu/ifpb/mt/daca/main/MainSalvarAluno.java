package br.edu.ifpb.mt.daca.main;

import br.edu.ifpb.mt.daca.dao.AlunoDAO;
import br.edu.ifpb.mt.daca.embedded.Endereco;
import br.edu.ifpb.mt.daca.entities.Aluno;

public class MainSalvarAluno {

	public static void main(String[] args) {
		AlunoDAO dao = new AlunoDAO();
		try {
			Aluno aluno = new Aluno();
			Endereco endereco = new Endereco();
			
			endereco.setBairro("Bairro");
			endereco.setCep(58500000);
			endereco.setCidade("Monteiro");
			endereco.setRua("Rua");
			endereco.setNumero(12);
			
			
			
			aluno.setCpf(12356);
			aluno.setNome("Maria");
			aluno.setTelefone("083-9999-9999");
			aluno.setEndereco(endereco);
			aluno.setMatricula(11233);
			aluno.setCurso("ADS");
			

			System.out.println(aluno.getNome());

			dao.save(aluno);

			System.out.println(aluno);
		} finally {
			dao.close();
		}
	}

	
}
