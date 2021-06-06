/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Excecao.ExcecaoCartaoCredito;
import Sessao.SessaoProduto;
import Sessao.SessaoUsuario;
import Sessao.SessaoVenda;
import entidades.CartaoCredito;
import entidades.Categoria;
import entidades.Endereco;
import entidades.ItensVenda;
import entidades.Produto;
import entidades.Venda;
import entidades.tiposEnum.TipoBandeira;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
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
public class ControleProdutos extends PadraoCDIBean<Produto> {

    private static final long serialVersionUID = 1L;
    @Inject
    private SessaoProduto beanProd;
    private List<Produto> listaProd;
    private float totalVenda = 0;
    private float frete = 0;
    private List<Produto> listaProdCarrinho = new LinkedList<Produto>();
    private Endereco enderecoCompra;
    @Inject
    private ControleUsuarios usuario;
    @Inject
    private SessaoUsuario beanUsuario;
    @Inject
    private SessaoVenda beanVenda;
    private String escolhaFormaPagamento;
    private CartaoCredito CartaoCred = new CartaoCredito();
    private Venda vendSelecionada = null;
    private Integer idParaVerProduto, prodSelecionado;
    private String nomeCategoriaVerProd;
    private Integer quantidade = 1;
    private List<Integer> quant = new ArrayList<Integer>();

    public List<Produto> getProdutosAltoEstoque() {
        return beanProd.getProdutosAltoEstoque();
    }

    public String paraCancelar() {
        return "/index.faces";
    }

    public String comprarEChecar() {
        quant.clear();
        return continuarCompra();
    }
    
    public String verCarrinho(){
        return "/carrinho.faces";
    }

    public String continuarCompra() {
        return "/comprarProduto1.faces";
    }

    public String adicionarProdutoCarrinho() {
        listaProdCarrinho.add(getBeanSelecionado());
        atualizarTotalVenda();
        return "/index.faces";
    }

    public String listaDeProdutos() {
        listaProd = beanProd.getTodosProdutos();
        return "/Administrador/Produtos/listaProdutos.faces";
    }

    public String criarProduto() {
        setBeanSelecionado(new Produto());
        return "/Administrador/Produtos/novoProduto.faces";
    }

    public List<Produto> getListaProd() {
        return listaProd;
    }

    public void setListaProd(List<Produto> listaProd) {
        this.listaProd = listaProd;
    }

    public String editarProduto() {
        return "/Administrador/Produtos/editarProduto.faces";
    }

    public String removerProduto() {
        beanProd.removeProduto(getBeanSelecionado());
        return listaDeProdutos();
    }

    public List<Categoria> getTodasCategorias() {
        return beanProd.getCategoriasAtivas();
    }

    public String paraVerProdutosComprados() {
        return "/listaCompras.faces";
    }

    public List<Venda> getVendasUsuario() {
        return beanVenda.getVendasPorUsuario(usuario.getUsuarioLogado());
    }

    public String encerrarEdicaoProduto() {
        beanProd.updateProduto(getBeanSelecionado());
        return listaDeProdutos();
    }

    public String criandoNovoProduto() {
        beanProd.salvarProduto(getBeanSelecionado());
        return listaDeProdutos();
    }

    public List<Produto> getProdutosTelaPrincipal() {
        return beanProd.getTodosProdutos1();
    }

    public List<Produto> getProductsMaisVendidos() {
        return beanProd.getProdutosBaixoEstoque();
    }

    public String mostrarDetalhesProduto() {
        return "/detalhesProduto.faces";
    }

    public String paraComprar() {
        adicionarProdutoCarrinho();
        return "/carrinho.faces";
    }

    public List<Produto> getListaProdCarrinho() {
        return listaProdCarrinho;
    }

    private void atualizarTotalVenda() {
        setTotalVenda(0);
        for (int i = 0; i < quant.size(); i++) {
            listaProdCarrinho.get(i).setQuantidade(quant.get(i).intValue());
        }

        for (Produto prod : listaProdCarrinho) {
            totalVenda += (prod.getPreco().floatValue()) * prod.getQuantidade();
        }
        quant.clear();
        totalVenda += frete;
        setTotalVenda(totalVenda);
    }

    public float getTotalVenda() {
        return totalVenda;
    }

    public void setTotalVenda(float totalVenda) {
        this.totalVenda = totalVenda;
    }

    public String paraAdicionarNovoEndereco() {
        quant.clear();
        setEnderecoCompra(new Endereco());
        getEnderecoCompra().setIdUsuario(usuario.getUsuarioLogado());
        return "/incluirNovoEndereco.faces";
    }

    public Endereco getEnderecoCompra() {
        return enderecoCompra;
    }

    public void setEnderecoCompra(Endereco enderecoCompra) {
        this.enderecoCompra = enderecoCompra;
    }

    public String paraFinalizarInclusaoEndereco() {
        usuario.getUsuarioLogado().addEndereco(enderecoCompra);
        beanUsuario.updateUsuario(usuario.getUsuarioLogado());
        return continuarCompra();
    }

    public String irParaCompra2() {
        setFrete(0f);
        atualizarTotalVenda();
        return "/comprarProduto2.faces";

    }

    public String irParaCompra3() {

        if (getEscolhaFormaPagamento().contains("oleto")) {
            return "/comprarProduto4.faces";
        } else {
            return "/comprarProduto3.faces";
        }

    }

    public String irParaCompra4() {
        return "/comprarProduto4.faces";
    }

    public float getFrete() {
        return frete;
    }

    public void setFrete(float frete) {
        this.frete = frete;
    }

    public String getEscolhaFormaPagamento() {
        return escolhaFormaPagamento;
    }

    public void setEscolhaFormaPagamento(String escolhaFormaPagamento) {
        this.escolhaFormaPagamento = escolhaFormaPagamento;
    }

    public String finalizarVenda() throws ExcecaoCartaoCredito {
        Venda vend = new Venda();
        vend.setIdEnderecoEnvio(getEnderecoCompra());
        vend.setDatadavenda(new Date());
        vend.setTotal(BigDecimal.valueOf(getTotalVenda()));
        vend.setIdUsuario(usuario.getUsuarioLogado());
        int i;
        for (Produto prod : listaProdCarrinho) {
            i = listaProdCarrinho.indexOf(prod);
            ItensVenda iv = new ItensVenda();
            iv.setIdProduto(prod);
            iv.setQuantidade(listaProdCarrinho.get(i).getQuantidade());
            vend.addItem(iv);
        }

        if (!getEscolhaFormaPagamento().contains("oleto")) {
            vend.setIdCartao(getCartaoCred());
        }

        Venda salvarVenda = beanVenda.salvarVenda(vend);
        setVendSelecionada(salvarVenda);
        listaProdCarrinho.clear();
        setBeanSelecionado(null);
        quant.clear();

        if (getEscolhaFormaPagamento().contains("oleto")) {
            return "/boleto.faces";
        } else {
            return "/cartaoCredit.faces";
        }
    }

    public CartaoCredito getCartaoCred() {
        return CartaoCred;
    }

    public void setCartaoCred(CartaoCredito CartaoCred) {
        this.CartaoCred = CartaoCred;
    }

    public Venda getVendSelecionada() {
        return vendSelecionada;
    }

    public void setVendSelecionada(Venda vendSelecionada) {
        this.vendSelecionada = vendSelecionada;
    }

    public TipoBandeira[] getBandeirasCC() {
        return TipoBandeira.values();
    }

    public Integer getIdParaVerProduto() {
        return idParaVerProduto;
    }

    public void setIdParaVerProduto(Integer idParaVerProduto) {
        this.idParaVerProduto = idParaVerProduto;
        if (idParaVerProduto != null && idParaVerProduto != 0) {
            setBeanSelecionado(beanProd.getProdutoPorId(idParaVerProduto));
        }
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        quant.add(quantidade);
        this.quantidade = quantidade;
    }

    public void removerProdLista() {
        if (prodSelecionado != null) {
            listaProdCarrinho.remove(listaProdCarrinho.get(prodSelecionado));
            quant.clear();
        }
        atualizarTotalVenda();
    }

    public Integer getProdSelecionado() {
        return prodSelecionado;
    }

    public void setProdSelecionado(Integer prodSelecionado) {
        this.prodSelecionado = prodSelecionado;

    }

    public String getNomeCategoriaVerProd() {
        return nomeCategoriaVerProd;
    }

    public void setNomeCategoriaVerProd(String nomeCategoriaVerProd) {
        this.nomeCategoriaVerProd = nomeCategoriaVerProd;
    }

    public List<Produto> getListaProdutosCat() {
        return beanProd.getProdutosPorCategoria(getNomeCategoriaVerProd());
    }
}
