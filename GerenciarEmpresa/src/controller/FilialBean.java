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
public class FilialBean {
	@EJB
	private FilialService filialService;
	@EJB
	private EnderecoService enderecoService;
	@EJB
	private FuncionarioService funcionarioService;

	private Filial filial;
	private Endereco endereco;
	private List<Filial> listaFilial = new ArrayList<Filial>();
	private List<Funcionario> listaFuncionario = new ArrayList<Funcionario>();
	private Boolean edicao = false;

	@PostConstruct
	public void inicializar() {
		limparFormulario();
		atualizarLista();
	}

	private void atualizarLista() {
		listaFilial = filialService.listar();
		listaFuncionario = funcionarioService.listar();
	}

	public Boolean verificarFilial(Filial f) {
		int cont = 0;
		for (int i = 0; i <= listaFuncionario.size(); i++) {
			if (f.getId() == listaFuncionario.get(i).getFilial().getId()) {
				cont += 1;
			}
		}

		if (cont > 0) {
			return false;
		} else {
			return true;
		}
	}

	public void gravarFilial() {
		if (formatarEValidarCEP(endereco.getCep()) == null || formatarEValidarCNPJ(filial.getCnpj()) == null) {
			return;
		}

		endereco.setCep(formatarEValidarCEP(endereco.getCep()));
		enderecoService.criar(endereco);
		filial.setCnpj(formatarEValidarCNPJ(filial.getCnpj()));
		filial.setEndereco(endereco);
		filialService.criar(filial);

		limparFormulario();

		FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Filial Gravado com Sucesso!"));
		atualizarLista();
	}

	public void editarFilial() {
		if (formatarEValidarCEP(endereco.getCep()) == null || formatarEValidarCNPJ(filial.getCnpj()) == null) {
			return;
		}

		endereco.setCep(formatarEValidarCEP(endereco.getCep()));
		enderecoService.editar(endereco);
		filial.setCnpj(formatarEValidarCNPJ(filial.getCnpj()));
		filial.setEndereco(endereco);
		filialService.editar(filial);

		FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Filial editado com Sucesso!"));
		atualizarLista();
		limparFormulario();
	}

	public void carregarFilial(Filial f) {
		filial = f;
		endereco = enderecoService.obterPorId(f.getEndereco().getId());
		edicao = true;
	}

	private void limparFormulario() {
		filial = new Filial();
		endereco = new Endereco();
		edicao = false;
	}

	public void apagarFilial(Filial f) {
		filialService.remover(f);
		enderecoService.remover(enderecoService.obterPorId(f.getEndereco().getId()));
		atualizarLista();
		limparFormulario();
	}

	public String formatarEValidarCNPJ(String cnpj) {
		cnpj = cnpj.replaceAll("[^0-9]", "");

		if (cnpj.length() != 14) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("CNPJ inválido!"));
			return null;
		}

		return cnpj.replaceFirst("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
	}

	public String formatarEValidarCEP(String cep) {
		cep = cep.replaceAll("[^0-9]", "");

		if (cep.length() != 8) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("CEP inválido!"));
			return null;
		}

		return cep.replaceFirst("(\\d{5})(\\d{3})", "$1-$2");
	}

	public FilialService getFilialService() {
		return filialService;
	}

	public void setFilialService(FilialService filialService) {
		this.filialService = filialService;
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	public List<Filial> getListaFilial() {
		return listaFilial;
	}

	public void setListaFilial(List<Filial> listaFilial) {
		this.listaFilial = listaFilial;
	}

	public Boolean getEdicao() {
		return edicao;
	}

	public void setEdicao(Boolean edicao) {
		this.edicao = edicao;
	}

	public EnderecoService getEnderecoService() {
		return enderecoService;
	}

	public void setEnderecoService(EnderecoService enderecoService) {
		this.enderecoService = enderecoService;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Funcionario> getListaFuncionario() {
		return listaFuncionario;
	}

	public void setListaFuncionario(List<Funcionario> listaFuncionario) {
		this.listaFuncionario = listaFuncionario;
	}

}
