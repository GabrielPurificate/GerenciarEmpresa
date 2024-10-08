package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Filial {
	@Id
	@GeneratedValue
	private Long id;
	private String nome;
	private String cnpj;
	@OneToOne
	private Endereco endereco;

	public Filial() {
		this.endereco = new Endereco();
	}

	public Filial(String nome, String cnpj, Endereco endereco) {
		this.nome = nome;
		this.cnpj = cnpj;
		this.endereco = endereco;
		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
}
