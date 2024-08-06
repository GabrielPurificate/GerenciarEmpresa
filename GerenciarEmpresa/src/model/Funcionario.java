package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Funcionario {
	@Id
	@GeneratedValue
	private Long id;
	private String nome;
	private Integer idade;
	private String cpf;
	private double salario;

	@ManyToOne
	private Filial filial;

	@OneToOne
	private Endereco endereco;

	public Funcionario(Long id, String nome, Integer idade, String cpf, double salario, Filial filial,
			Endereco endereco) {
		this.id = id;
		this.nome = nome;
		this.idade = idade;
		this.cpf = cpf;
		this.salario = salario;
		this.filial = filial;
		this.endereco = endereco;
	}

	public Funcionario() {
	}

	// Getters e Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
}