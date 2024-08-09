package controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.Filial;
import model.Funcionario;
import service.FilialService;
import service.FuncionarioService;

@ViewScoped
@ManagedBean
public class RelatorioBean {

	@EJB
	private FuncionarioService funcionarioService;
	@EJB
    private FilialService filialService;
	
	private List<Funcionario> listaFuncionarios = new ArrayList<Funcionario>();
	private List<Filial> listaFiliais = new ArrayList<>();
	private Long idFilial;
	private double salarioInicial;
	private double salarioFinal;
	private Filial filial;
	
	@PostConstruct
    public void init() {
        listaFiliais = filialService.listar();
    }
	
	public void Filtrar() {
	    if (idFilial != null && idFilial > 0) {
	        Filial filial = filialService.obterPorId(idFilial);
	        if (salarioInicial > 0 && salarioFinal > 0) {
	            // Filtra por filial e faixa salarial
	            listaFuncionarios = funcionarioService.listarFuncionariosPorAmbos(filial, salarioInicial, salarioFinal);
	        } else {
	            // Filtra apenas por filial
	            listaFuncionarios = funcionarioService.listarFuncionarioPorFilial(filial);
	        }
	    } else {
	        if (salarioInicial > 0 && salarioFinal > 0) {
	            // Filtra apenas por faixa salarial
	            listaFuncionarios = funcionarioService.listarFuncionariosPorSalario(salarioInicial, salarioFinal);
	        } else {
	            // Opcional: Se não houver filtros aplicados, você pode listar todos os funcionários ou uma lista vazia
	            listaFuncionarios = funcionarioService.listar(); // Se você tiver um método para listar todos os funcionários
	        }
	    }
	}

	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

	public List<Filial> getListaFiliais() {
		return listaFiliais;
	}

	public void setListaFiliais(List<Filial> listaFiliais) {
		this.listaFiliais = listaFiliais;
	}

	public double getSalarioInicial() {
		return salarioInicial;
	}

	public void setSalarioInicial(double salarioInicial) {
		this.salarioInicial = salarioInicial;
	}

	public double getSalarioFinal() {
		return salarioFinal;
	}

	public void setSalarioFinal(double salarioFinal) {
		this.salarioFinal = salarioFinal;
	}

	public Long getIdFilial() {
		return idFilial;
	}

	public void setIdFilial(Long idFilial) {
		this.idFilial = idFilial;
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}
}