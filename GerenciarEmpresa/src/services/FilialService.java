package services;

import javax.ejb.Stateless;

import model.Filial;

@Stateless
public class FilialService extends GenericService<Filial> {

	public FilialService() {
		super(Filial.class);
	}

}
