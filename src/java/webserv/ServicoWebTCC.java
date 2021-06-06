/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webserv;

import entidades.Usuario;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Beverly
 */
@WebService(serviceName = "ServicoWebTCC")
public class ServicoWebTCC {

    @PersistenceContext
    private EntityManager em;

    public Usuario ping() {
        return em.find(Usuario.class, 1);
    }
}
