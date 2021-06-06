/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Sessao.SessaoVenda;
import entidades.Venda;
import entidades.tiposEnum.StatusdaVenda;
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
public class ControleVendas extends PadraoCDIBean<Venda> {

    private static final long serialVersionUID = 1L;
    private List<Venda> listaVend;
    @Inject
    private SessaoVenda beanVend;
    private List<Venda> listaStatus;
    private StatusdaVenda status = StatusdaVenda.EM_ANDAMENTO;

    public List<Venda> getListaVend() {
        return listaVend;
    }

    public void setListaVend(List<Venda> listaVend) {
        this.listaVend = listaVend;
    }

    public String listarVendasFeitas() {
        listaVend = beanVend.getUltimasVendas();
        return "/Administrador/Vendas/vendasFeitas.faces";
    }

    public String listaVendasPeloStatus() {
        listaStatus = beanVend.getVendaPeloStatus(status);
        return "/Administrador/Vendas/statusDaVenda.faces";
    }

    public List<Venda> getListaStatus() {
        return listaStatus;
    }

    public void setListaStatus(List<Venda> listaStatus) {
        this.listaStatus = listaStatus;
    }

    public StatusdaVenda getStatus() {
        return status;
    }

    public void setStatus(StatusdaVenda status) {
        this.status = status;
    }

    public void ajaxList() {
        listaVendasPeloStatus();
    }

    public String statusValido() {
        beanVend.setStatusVenda(getBeanSelecionado(), StatusdaVenda.VALIDO);
        setStatus(StatusdaVenda.VALIDO);
        return listaVendasPeloStatus();
    }

    public String statusEmProgresso() {
        beanVend.setStatusVenda(getBeanSelecionado(), StatusdaVenda.EM_ANDAMENTO);
        setStatus(StatusdaVenda.EM_ANDAMENTO);
        return listaVendasPeloStatus();
    }

    public String statusEnviado() {
        beanVend.setStatusVenda(getBeanSelecionado(), StatusdaVenda.ENVIOU);
        setStatus(StatusdaVenda.ENVIOU);
        return listaVendasPeloStatus();
    }

    public String statusFinal() {
        beanVend.setStatusVenda(getBeanSelecionado(), StatusdaVenda.FIM);
        setStatus(StatusdaVenda.FIM);
        return listaVendasPeloStatus();
    }
}
