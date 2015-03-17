package br.edu.ifpb.mt.daca.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "Emprestimo")
@Table(name = "TB_Emprestimo")
public class Emprestimo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1816379125186122276L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private Long matriculaAluno;

	@Column
	private String nomeAluno;

	@Column
	private Long isbnLivro;

	@Column
	private String nomeLivro;

	@Column
	private Double multa;

	@Column
	@Temporal(TemporalType.DATE)
	private Date dataEmprestimo;

	@Column
	@Temporal(TemporalType.DATE)
	private Date dataDevolucao;

	public Emprestimo() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getMatriculaAluno() {
		return matriculaAluno;
	}

	public void setMatriculaAluno(Long matriculaAluno) {
		this.matriculaAluno = matriculaAluno;
	}

	public String getNomeAluno() {
		return nomeAluno;
	}

	public void setNomeAluno(String nomeAluno) {
		this.nomeAluno = nomeAluno;
	}

	public Long getIsbnLivro() {
		return isbnLivro;
	}

	public void setIsbnLivro(Long isbnLivro) {
		this.isbnLivro = isbnLivro;
	}

	public String getNomeLivro() {
		return nomeLivro;
	}

	public void setNomeLivro(String nomeLivro) {
		this.nomeLivro = nomeLivro;
	}

	public Date getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(Date dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public Double getMulta() {
		return multa;
	}

	public void setMulta(Double multa) {
		this.multa = multa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataDevolucao == null) ? 0 : dataDevolucao.hashCode());
		result = prime * result
				+ ((dataEmprestimo == null) ? 0 : dataEmprestimo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((isbnLivro == null) ? 0 : isbnLivro.hashCode());
		result = prime * result
				+ ((matriculaAluno == null) ? 0 : matriculaAluno.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Emprestimo other = (Emprestimo) obj;
		if (dataDevolucao == null) {
			if (other.dataDevolucao != null)
				return false;
		} else if (!dataDevolucao.equals(other.dataDevolucao))
			return false;
		if (dataEmprestimo == null) {
			if (other.dataEmprestimo != null)
				return false;
		} else if (!dataEmprestimo.equals(other.dataEmprestimo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isbnLivro == null) {
			if (other.isbnLivro != null)
				return false;
		} else if (!isbnLivro.equals(other.isbnLivro))
			return false;
		if (matriculaAluno == null) {
			if (other.matriculaAluno != null)
				return false;
		} else if (!matriculaAluno.equals(other.matriculaAluno))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Emprestimo [id=" + id + ", matriculaAluno=" + matriculaAluno
				+ ", nomeAluno=" + nomeAluno + ", isbnLivro=" + isbnLivro
				+ ", nomeLivro=" + nomeLivro + ", dataEmprestimo="
				+ dataEmprestimo + ", dataDevolucao=" + dataDevolucao + "]";
	}

}
