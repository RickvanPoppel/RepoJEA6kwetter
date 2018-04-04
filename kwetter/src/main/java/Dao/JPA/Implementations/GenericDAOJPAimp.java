package Dao.JPA.Implementations;

import Dao.JPA.Interfaces.GenericDAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public abstract class GenericDAOJPAimp<T> implements GenericDAO<T> {

    @PersistenceContext
    protected EntityManager em;

    private Class<T> type;

    public GenericDAOJPAimp(){
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }

    @Override
    public List<T> getAll() {
        final StringBuilder queryString = new StringBuilder(
                "SELECT x from ");
                queryString.append(type.getSimpleName()).append(" x ");

        final Query q = em.createQuery(queryString.toString());
        return (List<T>) q.getResultList();
    }

    @Override
    public T create(T t) {
        this.em.persist(t);
        return t;
    }

    @Override
    public void delete(Object id) {
        this.em.remove(this.em.getReference(type, id));
    }

    @Override
    public T find(Object id) {
        return this.em.find(type, id);
    }

    @Override
    public T update(T t) {
        try{
            return this.em.merge(t);
        }
        catch(Exception x){
            System.out.println(x.getMessage());
            return t;
        }
    }
}
