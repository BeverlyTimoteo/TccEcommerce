/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Sessao.SessaoUsuario;
import entidades.Endereco;
import entidades.Usuario;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Beverly
 */
@Named
@SessionScoped
public class ControleUsuarios extends PadraoCDIBean<Usuario> {

    private static final long serialVersionUID = 1L;
    private List<Usuario> listaUsuarios = null;
    private Usuario usu;
    private Usuario usuarioLogado = null;
    @Inject
    private SessaoUsuario beanUsuario;
    private List<Usuario> usuariosAtivos = null;
    private UIInput UIusuario;
    private UIInput UIsenha;
    private String direcionaURLUsuarioLogin;
    private Endereco enderecoSelecionado;
    
    
    public String paraCriarNovoUsuarioSistemaConsumidor() {
        setBeanSelecionado(new Usuario());
        return "/criarUsuario.faces";
    }

    public String listaUsuariosAtivos() {
        usuariosAtivos = beanUsuario.getMostraUsuariosAtivosporVenda();
        return "/Administrador/Usuarios/listaUsuariosAtivos.faces";
    }

    public String criarNovoUsuario() {//somente para Administrador
        setBeanSelecionado(new Usuario());
        return "/Administrador/Usuarios/addUsuarioSistemaAdm.faces";
    }

    public String adicionarNovoUsuario() {//somente para Administrador
        getBeanSelecionado().setAdm(true);
        beanUsuario.salvarUsuario(getBeanSelecionado());
        return listaUsuariosCadastrados();
    }
    public String adicionarUsuarioConsumidor() {
        getBeanSelecionado().setAdm(false);
        beanUsuario.salvarUsuario(getBeanSelecionado());
        return "/usuarioCriado.faces";
    }

    public String listaUsuariosCadastrados() {
        listaUsuarios = beanUsuario.getTodosUsuarios();
        return "/Administrador/Usuarios/listaUsuarios.faces";
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public String cancelarAdicaoUsuarioAdm() {
        return listaUsuariosCadastrados();
    }

    public String editarUsuario() {
        return "/Administrador/Usuarios/editarUsuario.faces";
    }

    public String finalizarEdicaoUsuario() {
        beanUsuario.updateUsuario(getBeanSelecionado());
        return listaUsuariosCadastrados();
    }

    public String removerUsuario() {
        beanUsuario.deleteUsuario(getBeanSelecionado());
        return listaUsuariosCadastrados();
    }

    public String paraSairSistema() {
        setUsuarioLogado(null);
        return paraCancelar();
    }

    public List<Usuario> getUsuariosAtivos() {
        return usuariosAtivos;
    }

    public void setUsuariosAtivos(List<Usuario> usuariosAtivos) {
        this.usuariosAtivos = usuariosAtivos;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public String paraCancelar() {
        return "/index.faces";
    }

    public String paraEntrarSistema() {
        setBeanSelecionado(new Usuario());
        return "/login.faces";
    }

    public UIInput getUIusuario() {
        return UIusuario;
    }

    public void setUIusuario(UIInput UIusuario) {
        this.UIusuario = UIusuario;
    }

    public UIInput getUIsenha() {
        return UIsenha;
    }

    public void setUIsenha(UIInput UIsenha) {
        this.UIsenha = UIsenha;
    }

    public void validarEntradaUsuario(FacesContext contexto, UIComponent componente, Object valor) throws ValidatorException {
        String usuario = UIusuario.getLocalValue().toString();
        String senha = valor.toString();
        Usuario retornou = beanUsuario.confirmaUsuarios(usuario, senha, false);
        if (retornou == null) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_FATAL, "O usuario/senha são invalidos", "O usuario não é válido"));
        }
    }

    public String paraEncerraEntrada() {
        usuarioLogado = beanUsuario.confirmaUsuarios(getBeanSelecionado().getUsuario(), getBeanSelecionado().getSenha(), false);
        String caminhoURL = getDirecionaURLUsuarioLogin();
        if (caminhoURL != null) {
            setDirecionaURLUsuarioLogin(null);
            return caminhoURL;
        } else {
            return "/index.faces";
        }
    }
    
    
    public String paraVerEnderecos() {
        return "/enderecos.faces";
    }
    
     public String paraEditarEndereco() {
        return "/editarEndereco.faces";
    }
     
    public String paraRemoverEnderecos() {
        enderecoSelecionado.setFgAtivo(false);       
        beanUsuario.updateEndereco(enderecoSelecionado);
        return paraVerEnderecos();
    }
    
     public String paraTerminarEdicaoEndereco() {
        beanUsuario.updateEndereco(getEnderecoSelecionado());
        return paraVerEnderecos();
    }
    
    public List<Endereco> getEnderecoUsuLogado() {
        return beanUsuario.getEnderecodosUsuarios(getUsuarioLogado());
    }

    public String getDirecionaURLUsuarioLogin() {
        return direcionaURLUsuarioLogin;
    }

    public void setDirecionaURLUsuarioLogin(String direcionaURLUsuarioLogin) {
        this.direcionaURLUsuarioLogin = direcionaURLUsuarioLogin;
    }
    
    public boolean estaLogado(){
        return usuarioLogado!=null;
    }
    
     public List<Endereco> getEnderecoUsuarioLogadoSistema() {
        return beanUsuario.getEnderecodosUsuarios(getUsuarioLogado());
    }

    public Endereco getEnderecoSelecionado() {
        return enderecoSelecionado;
    }

    public void setEnderecoSelecionado(Endereco enderecoSelecionado) {
        this.enderecoSelecionado = enderecoSelecionado;
    }
     
     
}
