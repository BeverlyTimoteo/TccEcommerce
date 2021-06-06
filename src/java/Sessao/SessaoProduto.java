/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessao;

import ejb.BeansSessaoBase;
import entidades.Categoria;
import entidades.Produto;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SessaoProduto extends BeansSessaoBase {

    public List<Produto> getTodosProdutos1() {
        return getListaLimitada(Produto.class, "select prod from Produto prod order by prod.nome", 9);
    }
    
    public List<Produto> getTodosProdutos() {
        return getLista(Produto.class, "select prod from Produto prod order by prod.nome");
    }
    
    public List<Produto> getProdutosPorCategoria(String nome){
        return getLista(Produto.class,
                "select prod from Produto prod where prod.idCategoria.nome = ?1 order by prod.nome", nome);
    }

    public List<Produto> getTodosProdutosNomeouEspedificacao(String identificacao) {
        return getLista(Produto.class, "select prod from Produto prod where lower(prod.nome) LIKE ?1 or lower(prod.descricao) LIKE ?1", "%"+identificacao.toLowerCase()+"%");
    }

    public List<Produto> getProdutosBaixoEstoque() {
        return getLista(Produto.class, "select prod from Produto prod where prod.estoque <= ?1 and prod.idCategoria.ativo = ?2", 20, true);
    }

    public List<Produto> getProdutosAltoEstoque() {
        return getLista(Produto.class, "select prod from Produto prod where prod.estoque >= ?1 and prod.idCategoria.ativo = ?2", 25, true);
    }

    public boolean getProdutosAltoEstoque(int idProd) {
        return getProdutoPorId(idProd).getEstoque() > 20;
    }

    public boolean isProdutosAltoEstoque(int idProd) {
        return getProdutoPorId(idProd).getEstoque() > 20;
    }

    public Produto getProdutoPorId(int idProduct) {
        return getPojo(Produto.class, idProduct);
    }

    public Produto salvarProduto(Produto prod) {
        getEm().persist(prod);
        return prod;
    }

    public Produto updateProduto(Produto prod) {
        getEm().merge(prod);
        return prod;
    }

    public void removeProduto(Produto prod) {
        Produto rem = getEm().merge(prod);
        getEm().remove(rem);
    }

    public List<Categoria> getTodasCategorias() {
        return getLista(Categoria.class, "select cat from Categoria cat order by cat.nome");
    }

    public List<Categoria> getCategoriasAtivas() {
        return getLista(Categoria.class, "select cat from Categoria cat where cat.ativo = ?1 order by cat.nome", true);
    }

    public List<Categoria> getCategoriasPorNome(String nome) {
        return getLista(Categoria.class, "select cat from Categoria cat where cat.ativo = ?1 and cat.nome like ?2", true, nome + "%");
    }

    public List<Produto> getProdutoPorNome(String nome) {
        return getLista(Produto.class, "select prod from Produto prod where prod.idCategoria.ativo = ?1 and prod.nome like ?2", true, nome + "%");
    }

    public Categoria getCategoriaPorNomeEspecifico(String nome) {
        return getPojo(Categoria.class, "select cat from Categoria cat where cat.ativo = ?1 and cat.nome = ?2", true, nome);
    }

    public Categoria getCategoriaPorId(int id) {
        return getPojo(Categoria.class, id);
    }

    public Categoria salvarCategoria(Categoria cat) {
        getEm().persist(cat);
        return cat;
    }

    public Categoria updateCategoria(Categoria cat) {
        getEm().merge(cat);
        return cat;
    }

    public void deleteCategoria(Categoria cat) {
        Categoria del = getEm().merge(cat);
        getEm().remove(del);
    }
}