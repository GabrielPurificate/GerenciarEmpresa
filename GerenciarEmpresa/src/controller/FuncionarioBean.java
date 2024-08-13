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
	private Boolean edicao = false;
	private List<Filial> listaFilial = new ArrayList<Filial>();
	private List<Funcionario> listaFuncionario = new ArrayList<Funcionario>();
	private Long idFilial = 0L;

	@PostConstruct
	public void inicializar() {
		atualizarLista();
		listaFilial = filialService.listar();
	}

	private void atualizarLista() {
		listaFuncionario = funcionarioService.listar();
	}

	public void gravarFuncionario() {
		if (formatarEValidarCPF(funcionario.getCpf()) == null || formatarEValidarCEP(endereco.getCep()) == null) {
			return;
		}

		endereco.setCep(formatarEValidarCEP(endereco.getCep()));
		enderecoService.criar(endereco);
		funcionario.setCpf(formatarEValidarCPF(funcionario.getCpf()));
		funcionario.setEndereco(endereco);
		Filial f = filialService.obterPorId(idFilial);
		funcionario.setFilial(f);
		funcionarioService.criar(funcionario);

		FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Funcionario criado com Sucesso!"));
		atualizarLista();

		funcionario = new Funcionario();
		endereco = new Endereco();
	}

	public void editarFuncionario() {
		if (formatarEValidarCPF(funcionario.getCpf()) == null || formatarEValidarCEP(endereco.getCep()) == null) {
			return;
		}

		endereco.setCep(formatarEValidarCEP(endereco.getCep()));
		enderecoService.editar(endereco);
		funcionario.setCpf(formatarEValidarCPF(funcionario.getCpf()));
		funcionario.setEndereco(endereco);
		Filial f = filialService.obterPorId(idFilial);
		funcionario.setFilial(f);
		funcionarioService.editar(funcionario);

		FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Funcionário Editado com Sucesso!"));
		atualizarLista();

		funcionario = new Funcionario();
		endereco = new Endereco();
	}

	public void carregarFuncionario(Funcionario f) {
		funcionario = f;
		endereco = f.getEndereco();
		idFilial = funcionario.getFilial().getId();
		edicao = true;
	}

	public void apagarFuncionario(Funcionario f) {
		funcionarioService.remover(f);
		enderecoService.remover(enderecoService.obterPorId(f.getEndereco().getId()));
		
		atualizarLista();
		limparFormulario();
	}

	public String formatarEValidarCEP(String cep) {
		cep = cep.replaceAll("[^0-9]", "");

		if (cep.length() != 8) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("CEP inválido!"));
			return null;
		}

		return cep.replaceFirst("(\\d{5})(\\d{3})", "$1-$2");
	}

	public String formatarEValidarCPF(String cpf) {
		cpf = cpf.replaceAll("[^0-9]", "");

		if (cpf.length() != 11) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("CPF inválido!"));
			return null;
		}

		return cpf.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
	}

	private void limparFormulario() {
		endereco = new Endereco();
		edicao = false;
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

	public Boolean getEdicao() {
		return edicao;
	}

	public void setEdicao(Boolean edicao) {
		this.edicao = edicao;
	}

	public Long getIdFilial() {
		return idFilial;
	}

	public void setIdFilial(Long idFilial) {
		this.idFilial = idFilial;
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
