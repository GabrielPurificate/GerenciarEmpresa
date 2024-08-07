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
import service.EnderecoService;
import service.FilialService;

@ViewScoped
@ManagedBean
public class FilialBean {
	@EJB
	private FilialService filialService;
	@EJB
	private EnderecoService enderecoService;

	private Filial filial;
	private Endereco endereco;
	private List<Filial> listaFilial = new ArrayList<Filial>();
	private Boolean edicao = false;

	@PostConstruct
	public void inicializar() {
		limparFormulario();
		atualizarLista();
	}

	private void atualizarLista() {
		listaFilial = filialService.listar();

	}

	public void gravarFilial() {
		enderecoService.criar(endereco);
		filial.setEndereco(endereco);
		filialService.criar(filial);

		limparFormulario();

		FacesContext.getCurrentInstance().addMessage("", 
				new FacesMessage("Filial Gravado com Sucesso!"));
		atualizarLista();
	}

	public void editarFilial() {	
		enderecoService.editar(endereco);
		filial.setEndereco(endereco);
		filialService.editar(filial);

		FacesContext.getCurrentInstance().addMessage("", 
				new FacesMessage("Filial editado com Sucesso!"));
		atualizarLista();
		limparFormulario();
	}

	public void carregarFilial(Filial f) {
		filial = f;
		endereco = enderecoService.obterPorId(f.getEndereco().getId());
		edicao = true;
	}

	public void apagarFilial(Filial f) {
		filialService.remover(f);
		enderecoService.remover(enderecoService.obterPorId(f.getEndereco().getId()));
		atualizarLista();
		limparFormulario();
	}
	
    private void limparFormulario() {
        filial = new Filial();
        endereco = new Endereco();
        edicao = false;
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

}
