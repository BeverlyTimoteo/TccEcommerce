/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Administrador
 */
public abstract class PadraoCDIBean<T> implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private T beanSelecionado;
    
    @PersistenceContext
    protected EntityManager em;

    public T getBeanSelecionado() {
        return beanSelecionado;
    }

    public void setBeanSelecionado(T beanSelecionado) {
        this.beanSelecionado = beanSelecionado;
    }
}
