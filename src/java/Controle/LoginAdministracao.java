/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Sessao.SessaoUsuario;
import entidades.Usuario;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Administrador
 */
@Named
@SessionScoped
public class LoginAdministracao extends PadraoCDIBean {

    private static final long serialVersionUID = 1L;
    @Inject
    private SessaoUsuario beanUsu;
    @NotNull(message = "O campo login não pode ser nulo")
    @NotEmpty(message = "O campo login não pode ser vazio")
    private String usuario;
    @NotNull(message = "O campo senha não pode ser nulo")
    @Min(value = 5, message = "Senha tem que ser maior que cinco letras")
    private String senha;
    private boolean administradorLogado = false;
    private static final boolean opcao = true;
    
    public String fazerLogin() {
        if (getUsuario().equalsIgnoreCase("beverly") && getSenha().equals("123")) {
            administradorLogado = true;
        } else {
            if (confirmUsuarioBD()) {
                administradorLogado = true;
            } else {
                administradorLogado = false;
            }
        }

        if (estaAdministradorLogado()) {
            return "principal.faces";
        } else {
            return "login.faces";
        }
    }

    public boolean confirmUsuarioBD() {
        Usuario valid = beanUsu.confirmaUsuarios(usuario, senha, opcao);
        if (valid != null) {
            return true;
        } else {
            return false;
        }
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public boolean estaAdministradorLogado() {
        return administradorLogado;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
