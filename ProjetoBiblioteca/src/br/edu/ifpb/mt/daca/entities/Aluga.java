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

@Entity(name = "Aluga")
@Table(name = "TB_Aluga")
public class Aluga implements Serializable {

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
	private Long isbnLivro;

	@Column
	@Temporal(TemporalType.DATE)
	private Date dataEntrega;

	@Column
	@Temporal(TemporalType.DATE)
	private Date dataDevolucao;

	public Aluga() {
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

	public Long getIsbnLivro() {
		return isbnLivro;
	}

	public void setIsbnLivro(Long isbnLivro) {
		this.isbnLivro = isbnLivro;
	}

	public Date getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataDevolucao == null) ? 0 : dataDevolucao.hashCode());
		result = prime * result
				+ ((dataEntrega == null) ? 0 : dataEntrega.hashCode());
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
		Aluga other = (Aluga) obj;
		if (dataDevolucao == null) {
			if (other.dataDevolucao != null)
				return false;
		} else if (!dataDevolucao.equals(other.dataDevolucao))
			return false;
		if (dataEntrega == null) {
			if (other.dataEntrega != null)
				return false;
		} else if (!dataEntrega.equals(other.dataEntrega))
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

}
