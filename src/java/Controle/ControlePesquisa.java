/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Sessao.SessaoProduto;
import entidades.Produto;
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
public class ControlePesquisa extends PadraoCDIBean<Produto> {

    private static final long serialVersionUID = 1L;
    @Inject
    private SessaoProduto beanProd;
    private String palavraChave;
    private List<Produto> listaResultado;

    public String paraPesquisar() {
        listaResultado = beanProd.getTodosProdutosNomeouEspedificacao(palavraChave);
        return "/resultadoPesquisa.faces";
    }

    public String getPalavraChave() {
        return palavraChave;
    }

    public void setPalavraChave(String palavraChave) {
        this.palavraChave = palavraChave;
    }

    public List<Produto> getListaResultado() {
        return listaResultado;
    }

    public void setListaResultado(List<Produto> listaResultado) {
        this.listaResultado = listaResultado;
    }
}
