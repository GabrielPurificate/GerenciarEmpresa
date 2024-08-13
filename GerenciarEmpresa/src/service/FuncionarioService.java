package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import model.Filial;
import model.Funcionario;

@Stateless
public class FuncionarioService extends GenericService<Funcionario> {

	public FuncionarioService() {
		super(Funcionario.class);
	}
	
	public List<Funcionario> listarFuncionariosPorAmbos(Filial filial, double salarioInicial, double salarioFinal) {
		
		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
	    final CriteriaQuery<Funcionario> cQuery = cBuilder.createQuery(Funcionario.class);
	    final Root<Funcionario> funcionarioRoot = cQuery.from(Funcionario.class);
	    
	    cQuery.select(funcionarioRoot);
	    
	    if(salarioInicial > 0 && salarioFinal > 0) {
	    	cQuery.where(
	    			cBuilder.and(
	    					cBuilder.equal(funcionarioRoot.get("filial"), filial),
	    					cBuilder.between(funcionarioRoot.get("salario"), salarioInicial, salarioFinal)
	    			)
	    	);   
	    } else if (salarioInicial > 0 && salarioFinal <= 0) {
	    	cQuery.where(
	    			cBuilder.and(
	    					cBuilder.equal(funcionarioRoot.get("filial"), filial),
	    					cBuilder.greaterThanOrEqualTo(funcionarioRoot.get("salario"), salarioInicial)
	    			)
	    	);   
	    } else if (salarioInicial <= 0 && salarioFinal > 0) {
	    	cQuery.where(
	    			cBuilder.and(
	    					cBuilder.equal(funcionarioRoot.get("filial"), filial),
	    					cBuilder.lessThanOrEqualTo(funcionarioRoot.get("salario"), salarioFinal)
	    			)
	    	);   
	    }
	     	
    	cQuery.orderBy(cBuilder.desc(funcionarioRoot.get("salario")));
	    
	    List<Funcionario> resultado = getEntityManager().createQuery(cQuery).getResultList();
	    
	    return resultado;
	}
	
	public List<Funcionario> listarFuncionariosPorSalario(double salarioInicial, double salarioFinal) {
	    
	    final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
	    final CriteriaQuery<Funcionario> cQuery = cBuilder.createQuery(Funcionario.class);
	    final Root<Funcionario> funcionarioRoot = cQuery.from(Funcionario.class);
	    
	    cQuery.select(funcionarioRoot);
	    
	    if (salarioInicial > 0 && salarioFinal > 0) {
	        cQuery.where(cBuilder.between(funcionarioRoot.get("salario"), salarioInicial, salarioFinal));
	        
	    } else if (salarioInicial > 0) {
	        cQuery.where(cBuilder.greaterThanOrEqualTo(funcionarioRoot.get("salario"), salarioInicial));
	        
	    } else if (salarioFinal > 0) {
	        cQuery.where(cBuilder.lessThanOrEqualTo(funcionarioRoot.get("salario"), salarioFinal));
	        
	    }

	    cQuery.orderBy(cBuilder.desc(funcionarioRoot.get("salario")));
	    
	    List<Funcionario> resultado = getEntityManager().createQuery(cQuery).getResultList();
	    
	    return resultado;
	}
	
	public List<Funcionario> listarFuncionarioPorFilial(Filial filial){
		
	    final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
	    final CriteriaQuery<Funcionario> cQuery = cBuilder.createQuery(Funcionario.class);
	    final Root<Funcionario> funcionarioRoot = cQuery.from(Funcionario.class);
	    
	    cQuery.select(funcionarioRoot);
	    cQuery.where(cBuilder.equal(funcionarioRoot.get("filial"), filial));
	    
	    List<Funcionario> resultado = getEntityManager().createQuery(cQuery).getResultList();
	    
	    return resultado;
	}

}
