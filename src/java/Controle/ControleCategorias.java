/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Sessao.SessaoProduto;
import entidades.Categoria;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Beverly
 */
@Named
@SessionScoped
public class ControleCategorias extends PadraoCDIBean<Categoria> {

    private static final long serialVersionUID = 1L;
    @Inject
    private SessaoProduto beanProd;
    private int pagina =0;
    private List<Categoria> listaCat;
    
    public ControleCategorias() {
        setBeanSelecionado(new Categoria());
    }
    
    public String listarCategorias() {
        listaCat = beanProd.getTodasCategorias();
        return "/Administrador/Categorias/listaCategorias.faces";
    }
    
    public String editarCategoria() {
       return "/Administrador/Categorias/editarCategoria.faces";
    }
    
    public String removerCategoria() {
        beanProd.deleteCategoria(getBeanSelecionado());
        return listarCategorias();
    }
    
    public String novaCategoria() {
        setBeanSelecionado(new Categoria());
        return "/Administrador/Categorias/novaCategoria.faces";
    }
    
    public String incluirCategoria() {
        beanProd.salvarCategoria(getBeanSelecionado());
        return listarCategorias();
    }
    
    public String finalizarEdicaoCategoria() {
        beanProd.updateCategoria(getBeanSelecionado());
        return listarCategorias();
    }
    

    public List<Categoria> getListaCat() {
        return listaCat;
    }

    public void setListaCat(List<Categoria> listaCat) {
        this.listaCat = listaCat;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }
    
}
