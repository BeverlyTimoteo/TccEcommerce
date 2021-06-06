/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessao;

import Excecao.ExcecaoCartaoCredito;
import ejb.BeansSessaoBase;
import entidades.ItensVenda;
import entidades.Produto;
import entidades.Usuario;
import entidades.Venda;
import entidades.tiposEnum.StatusdaVenda;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 *
 * @author Beverly
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SessaoVenda extends BeansSessaoBase {

    public List<Venda> getVendaPorData(Date primeiro, Date segundo) {
        return getLista(Venda.class, "select vend from Venda vend where vend.datadavenda >= ?1 and vend.datadavenda <= ?2", primeiro, segundo);
    }

    public List<Venda> getVendasPorUsuario(Usuario usu) {
        return getLista(Venda.class, "select vend from Venda vend where vend.idUsuario = ?1", usu);
    }

    public List<Venda> getVendaPeloStatus(StatusdaVenda status) {
        return getLista(Venda.class, "select vend from Venda vend where vend.status = ?1", status);
    }

    public List<Produto> getUltimosProdutosVendidos() {
        return getListaLimitada(Produto.class, "select DISTINCT(si.idProduct) from ItensVenda si order by si.idVenda.datadavenda desc", 10);
    }

    public List<ItensVenda> getItensVenda(int id) {
        return getLista(ItensVenda.class, "select si from ItensVenda si where si.idVenda.id = ?1", id);
    }

    public List<Venda> getUltimasVendas() {
        return getListaLimitada(Venda.class, "select si.idVenda from ItensVenda si order by si.idVenda.datadavenda desc", 10);
    }

    public Venda getVendas(int id) {
        return getPojo(Venda.class, id);
    }

    public boolean isCreditCardValidForSell(Venda vend) {
        setStatusVenda(vend, StatusdaVenda.VALIDO);
        System.out.println("Cartão Valido! " + vend.getIdCartao());
        return true;//pode fazer a validação do cartão de credito
    }

    public void setStatusVenda(Venda vend, StatusdaVenda status) {
        System.out.println("Atualizando o status da Venda");
        vend.setStatus(status);
        if (status == StatusdaVenda.FIM) {
            vend.setEncerrou(true);
        }
        if (vend.getId() != null) {
            getEm().find(Venda.class, vend.getId()).setStatus(status);
            if (status == StatusdaVenda.FIM) {
               getEm().find(Venda.class, vend.getId()).setEncerrou(true); 
            }
        }
    }

    public Venda encerrarVenda(Venda vend) {
        vend = getEm().find(Venda.class, vend.getId());
        vend.setEncerrou(true);
        updateVenda(vend);
        setStatusVenda(vend, StatusdaVenda.FIM);
        return vend;
    }

    public Venda salvarVenda(Venda vend) throws ExcecaoCartaoCredito {
        if (!isCreditCardValidForSell(vend)) {
            throw new ExcecaoCartaoCredito("O cartão de Credito é Invalido para a Venda");
        }
        for (ItensVenda si : vend.getItensVendaList()) {
            si.getIdProduto().setEstoque(si.getIdProduto().getEstoque() - si.getQuantidade());
        }
        
        getEm().persist(vend);
        setStatusVenda(vend, StatusdaVenda.EM_ANDAMENTO);
        return vend;
    }

    public Venda updateVenda(Venda vend) {
        getEm().merge(vend);
        return vend;
    }

    public void deleteVenda(Venda vend) {
        Venda rem = getEm().getReference(Venda.class, vend.getId());
        getEm().remove(rem);
    }
}
