package service;

import java.util.List;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.PersistenceException;

@TransactionManagement(TransactionManagementType.CONTAINER)
public abstract class GenericService<T> {

    @PersistenceContext(unitName = "punit")
    private EntityManager entityManager;

    private final Class<T> classe;

    public GenericService(Class<T> classe) {
        this.classe = classe;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void criar(T entity) {
        try {
            getEntityManager().persist(entity);
        } catch (PersistenceException e) {
            // Log e lançar uma exceção de runtime genérica
            e.printStackTrace();
            throw new RuntimeException("Erro ao criar entidade: " + e.getMessage());
        }
    }

    public void editar(T entity) {
        try {
            entity = getEntityManager().merge(entity);
        } catch (PersistenceException e) {
            // Log e lançar uma exceção de runtime genérica
            e.printStackTrace();
            throw new RuntimeException("Erro ao editar entidade: " + e.getMessage());
        }
    }

    public void remover(T entity) {
        try {
            getEntityManager().remove(getEntityManager().merge(entity));
        } catch (PersistenceException e) {
            // Log e lançar uma exceção de runtime genérica
            e.printStackTrace();
            throw new RuntimeException("Erro ao remover entidade: " + e.getMessage());
        }
    }

    public final T obterPorId(Long id) {
        try {
            return getEntityManager().find(classe, id);
        } catch (PersistenceException e) {
            // Log e lançar uma exceção de runtime genérica
            e.printStackTrace();
            throw new RuntimeException("Erro ao obter entidade por ID: " + e.getMessage());
        }
    }

    public List<T> listar() {
        try {
            final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            final CriteriaQuery<T> cQuery = cb.createQuery(classe);
            cQuery.select(cQuery.from(classe));
            return getEntityManager().createQuery(cQuery).getResultList();
        } catch (PersistenceException e) {
            // Log e lançar uma exceção de runtime genérica
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar entidades: " + e.getMessage());
        }
    }
}
