package services;

import javax.ejb.Stateless;

import model.Funcionario;

@Stateless
public class FuncionarioService extends GenericService<Funcionario> {

	public FuncionarioService() {
		super(Funcionario.class);
	}

}
