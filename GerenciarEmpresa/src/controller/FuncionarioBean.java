package controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import model.Endereco;
import model.Filial;
import model.Funcionario;
import service.EnderecoService;
import service.FilialService;
import service.FuncionarioService;

@ViewScoped
@ManagedBean
public class FuncionarioBean {
	@EJB
	private FuncionarioService funcionarioService;
	@EJB
	private EnderecoService enderecoService;
	@EJB
	private FilialService filialService;

	private Funcionario funcionario = new Funcionario();
	private Endereco endereco = new Endereco();
	private Filial filial;
	private Boolean edicao = false;

	private List<Filial> listaFilial = new ArrayList<Filial>();
	private List<Funcionario> listaFuncionario = new ArrayList<Funcionario>();

	@PostConstruct
	public void inicializar() {
		atualizarLista();
		listaFilial = filialService.listar();
	}

	private void atualizarLista() {
		listaFuncionario = funcionarioService.listar();
	}

	public void gravarFuncionario() {
		enderecoService.criar(endereco);
		funcionario.setEndereco(endereco);
		funcionario.setFilial(filial);
		funcionarioService.criar(funcionario);

		funcionario = new Funcionario();
		endereco = new Endereco();
		filial = null;

		FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Funcionario criado com Sucesso!"));
		atualizarLista();
	}

	public void editarFuncionario() {
		enderecoService.editar(endereco);
		funcionario.setEndereco(endereco);
		funcionario.setFilial(filial);
		funcionarioService.editar(funcionario);

		funcionario = new Funcionario();
		endereco = new Endereco();
		filial = null;

		FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Funcionário Editado com Sucesso!"));
		atualizarLista();
	}

	public void carregarFuncionario(Funcionario f) {
		funcionario = f;
		endereco = f.getEndereco();
		filial = f.getFilial();
		edicao = true;
	}

	public FuncionarioService getFuncionarioService() {
		return funcionarioService;
	}

	public void setFuncionarioService(FuncionarioService funcionarioService) {
		this.funcionarioService = funcionarioService;
	}

	public EnderecoService getEnderecoService() {
		return enderecoService;
	}

	public void setEnderecoService(EnderecoService enderecoService) {
		this.enderecoService = enderecoService;
	}

	public FilialService getFilialService() {
		return filialService;
	}

	public void setFilialService(FilialService filialService) {
		this.filialService = filialService;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	public Boolean getEdicao() {
		return edicao;
	}

	public void setEdicao(Boolean edicao) {
		this.edicao = edicao;
	}

	public List<Filial> getListaFilial() {
		return listaFilial;
	}

	public void setListaFilial(List<Filial> listaFilial) {
		this.listaFilial = listaFilial;
	}

	public List<Funcionario> getListaFuncionario() {
		return listaFuncionario;
	}

	public void setListaFuncionario(List<Funcionario> listaFuncionario) {
		this.listaFuncionario = listaFuncionario;
	}

}
