/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Beverly
 */
public abstract class BeansSessaoBase implements java.io.Serializable {

    public final static boolean debug = false;
    private static final long serialVersionUID = 1L;
    @PersistenceContext
    private EntityManager em;

    public BeansSessaoBase() {
    }

    public EntityManager getEm() {
        return em;
    }

    public <T> List<T> getLista(Class<T> classToCast, String query, Object... valores) {
        Query qr = createQuery(query, valores);
        return qr.getResultList();
    }

    public <T> List<T> getListaLimitada(Class<T> classToCast, String query, int limite, Object... valores) {
        Query qr = createQuery(query, valores);
        qr.setMaxResults(limite);
        return qr.getResultList();
    }

    public <T> List<T> getNamedLista(Class<T> classToCast, String namedQuery, Object... valores) {
        Query qr = em.createNamedQuery(namedQuery);
        if (valores != null) {
            for (int i = 0; i < valores.length; i++) {
                Object object = valores[i];
                qr.setParameter(i + 1, object);
            }
        }
        return qr.getResultList();
    }

    public <T> T getPojo(Class<T> classToCast, String query, Object... valores) {
        Query qr = createQuery(query, valores);
        return (T) qr.getSingleResult();
    }

    public <T> T getPojo(Class<T> classToCast, Serializable primaryKey) {
        return em.find(classToCast, primaryKey);
    }

    public int execute(String query, Object... valores) {
        Query qr = createQuery(query, valores);
        return qr.executeUpdate();
    }

    private Query createQuery(String query, Object[] valores) {
        Query qr = em.createQuery(query);
        if (valores != null) {
            for (int i = 0; i < valores.length; i++) {
                Object object = valores[i];
                qr.setParameter(i + 1, object);
            }
        }
        return qr;
    }
}
