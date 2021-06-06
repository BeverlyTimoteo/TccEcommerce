/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessao;

import java.math.BigDecimal;
import javax.naming.NamingException;
import entidades.Categoria;
import entidades.Produto;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Beverly
 */
public class SessaoProdutoTest {

    public static Integer idOfCategory1;
    public static Integer idOfCategory2;
    public static Integer idOfProduct1;
    public static Integer idOfProduct2;
    public static Integer idOfProduct3;
    private static EJBContainer contJava;

    public SessaoProdutoTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        contJava = javax.ejb.embeddable.EJBContainer.createEJBContainer();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        contJava.close();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    private SessaoProduto getSessaoProduto() throws NamingException {
        return (SessaoProduto) contJava.getContext().lookup("java:global/classes/SessaoProduto");
    }

    /**
     * Test of salvarCategoria method, of class SessaoProduto.
     */
    @Test
    public void testSalvarCategoria() throws Exception {
        System.out.println("saveCategory");

        Categoria cat1 = new Categoria();
        cat1.setAtivo(true);
        cat1.setNome("Category 1");

        Categoria cat2 = new Categoria();
        cat2.setAtivo(false);
        cat2.setNome("Category 2");

        SessaoProduto instance = getSessaoProduto();
        idOfCategory1 = instance.salvarCategoria(cat1).getId();
        idOfCategory2 = instance.salvarCategoria(cat2).getId();

        cat1 = null;
        cat2 = null;

        cat1 = instance.getCategoriaPorId(idOfCategory1);
        cat2 = instance.getCategoriaPorId(idOfCategory2);
        assertNotNull(cat1);
        assertNotNull(cat2);
        assertEquals(cat1.getNome(), "Category 1");
        assertEquals(cat2.getNome(), "Category 2");
    }

    /**
     * Test of updateCategoria method, of class SessaoProduto.
     */
    @Test
    public void testUpdateCategoria() throws Exception {
        System.out.println("setCategory");
        SessaoProduto instance = getSessaoProduto();
        Categoria cat1 = instance.getCategoriaPorId(idOfCategory1);
        Categoria cat2 = instance.getCategoriaPorId(idOfCategory2);

        cat1.setNome("The changed Category 1");
        cat2.setNome("The category 2 is changed too !");
        instance.updateCategoria(cat1);
        instance.updateCategoria(cat2);

        cat1 = null;
        cat2 = null;
        cat1 = instance.getCategoriaPorId(idOfCategory1);
        cat2 = instance.getCategoriaPorId(idOfCategory2);

        assertNotNull(cat1);
        assertNotNull(cat2);
        assertEquals(cat1.getNome(), "The changed Category 1");
        assertEquals(cat2.getNome(), "The category 2 is changed too !");
    }

    /**
     * Test of salvarProduto method, of class SessaoProduto.
     */
    @Test
    public void testSalvarProduto() throws Exception {
        System.out.println("saveProduct");
        SessaoProduto instance = getSessaoProduto();

        Produto prod1 = new Produto();
        prod1.setIdCategoria(instance.getCategoriaPorId(idOfCategory1));
        prod1.setPreco(new BigDecimal("10.2"));
        prod1.setNome("Product 1");
        prod1.setEstoque(100);
        prod1.setDescricao("Spec 1");
        //prod1.setFotoUrl(null);
        prod1.getIdCategoria().getProdutoList().add(prod1);

        Produto prod2 = new Produto();
        prod2.setIdCategoria(instance.getCategoriaPorId(idOfCategory2));
        prod2.setPreco(new BigDecimal("300.34"));
        prod2.setNome("Product 2");
        prod2.setEstoque(1);
        //prod2.setFotoUrl(null);
        prod2.setDescricao("Spec 2");
        prod2.getIdCategoria().getProdutoList().add(prod2);

        Produto prod3 = new Produto();
        prod3.setIdCategoria(instance.getCategoriaPorId(idOfCategory1));
        prod3.setPreco(new BigDecimal("500.34"));
        prod3.setNome("Product 3");
        prod3.setEstoque(1);
        //prod3.setFotoUrl(null);
        prod3.setDescricao("Spec 3");
        prod3.getIdCategoria().getProdutoList().add(prod3);

        idOfProduct1 = instance.salvarProduto(prod1).getId();
        idOfProduct2 = instance.salvarProduto(prod2).getId();
        idOfProduct3 = instance.salvarProduto(prod3).getId();

        assertNotNull(idOfProduct1);
        assertNotNull(idOfProduct2);
        assertNotNull(idOfProduct3);
        assertTrue(idOfProduct1 >= 1);
        assertTrue(idOfProduct2 >= 1);
        assertTrue(idOfProduct3 >= 1);
    }

    /**
     * Test of updateProduto method, of class SessaoProduto.
     */
    @Test
    public void testUpdateProduto() throws Exception {
        System.out.println("setProduct");
        SessaoProduto instance = getSessaoProduto();
        Produto prod1 = instance.getProdutoPorId(idOfProduct1);
        Produto prod2 = instance.getProdutoPorId(idOfProduct2);
        Produto prod3 = instance.getProdutoPorId(idOfProduct3);

        prod1.setNome("Product 1 CHANGED");
        prod2.setNome("Product 2 CHANGED");
        prod3.setNome("Product 3 CHANGED");

        instance.updateProduto(prod1);
        instance.updateProduto(prod2);
        instance.updateProduto(prod3);

        prod1 = instance.getProdutoPorId(idOfProduct1);
        prod2 = instance.getProdutoPorId(idOfProduct2);
        prod3 = instance.getProdutoPorId(idOfProduct3);

        assertNotNull(prod1);
        assertNotNull(prod2);
        assertNotNull(prod3);
        assertEquals(prod1.getNome(), "Product 1 CHANGED");
        assertEquals(prod2.getNome(), "Product 2 CHANGED");
        assertEquals(prod3.getNome(), "Product 3 CHANGED");
    }

    /**
     * Test of getTodosProdutos method, of class SessaoProduto.
     */
    @Test
    public void testGetTodosProdutos() throws Exception {
        System.out.println("getAllProducts");
        SessaoProduto instance = getSessaoProduto();
        List result = instance.getTodosProdutos();
        assertTrue(result.size() == 3);
    }

    /**
     * Test of getTodosProdutosNomeouEspedificacao method, of class SessaoProduto.
     */
    @Test
    public void testGetTodosProdutosNomeouEspedificacao() throws Exception {
        System.out.println("getAllProductsByName");
        String nameOfProduct = "Product";
        SessaoProduto instance = getSessaoProduto();
        List result = instance.getTodosProdutosNomeouEspedificacao(nameOfProduct);
        assertTrue(result.size() == 3);
        List result2 = instance.getTodosProdutosNomeouEspedificacao(nameOfProduct + " 1");
        assertTrue(result2.size() == 1);
        List result3 = instance.getTodosProdutosNomeouEspedificacao("Dyego Product Test Number One");
        assertNotNull(result3);
        assertTrue(result3.isEmpty());
    }

    /**
     * Test of getProdutosBaixoEstoque method, of class SessaoProduto.
     */
    @Test
    public void testGetProdutosBaixoEstoque() throws Exception {
        System.out.println("getProductsWithLowStock");
        SessaoProduto instance = getSessaoProduto();
        List result = instance.getProdutosBaixoEstoque();
        assertTrue(result.size() == 1);
    }

    /**
     * Test of getProdutosAltoEstoque method, of class SessaoProduto.
     */
    @Test
    public void testGetProdutosAltoEstoque_0args() throws Exception {
        System.out.println("isProductWithHighStock");
        SessaoProduto instance = getSessaoProduto();
        boolean result1 = instance.isProdutosAltoEstoque(idOfProduct1);
        boolean result2 = instance.isProdutosAltoEstoque(idOfProduct2);
        boolean result3 = instance.isProdutosAltoEstoque(idOfProduct3);
        assertTrue(result1);
        assertFalse(result2);
        assertFalse(result3);
    }

    /**
     * Test of getProductById method, of class SessaoProduto.
     */
    @Test
    public void testGetProductById() throws Exception {
        System.out.println("getProductById");
        SessaoProduto instance = getSessaoProduto();
        Produto result = instance.getProdutoPorId(idOfProduct3);
        assertEquals(result.getNome(), "Product 3 CHANGED");
    }

    /**
     * Test of getTodasCategorias method, of class SessaoProduto.
     */
    @Test
    public void testGetTodasCategorias() throws Exception {
        System.out.println("getAllCategories");
        SessaoProduto instance = getSessaoProduto();
        List result = instance.getTodasCategorias();
        assertTrue(result.size() == 2);
    }

    /**
     * Test of getCategoriasAtivas method, of class SessaoProduto.
     */
    @Test
    public void testGetCategoriasAtivas() throws Exception {
        System.out.println("getActiveCategories");
        SessaoProduto instance = getSessaoProduto();
        List result = instance.getCategoriasAtivas();
        assertTrue(result.size() == 1);
    }

    /**
     * Test of getCategoriaPorId method, of class SessaoProduto.
     */
    @Test
    public void testGetCategoriaPorId() throws Exception {
        System.out.println("getCategoryById");
        SessaoProduto instance = getSessaoProduto();
        Categoria result = instance.getCategoriaPorId(idOfCategory2);
        assertNotNull(result);
        assertEquals(result.getNome(), "The category 2 is changed too !");
    }

    /**
     * Test of removeProduto method, of class SessaoProduto.
     */
    @Test
    public void testRemoveProduto() throws Exception {
        System.out.println("removeProduct");
        SessaoProduto instance = getSessaoProduto();
        Produto prod1 = instance.getProdutoPorId(idOfProduct1);
        Produto prod2 = instance.getProdutoPorId(idOfProduct2);
        Produto prod3 = instance.getProdutoPorId(idOfProduct3);
        instance.removeProduto(prod1);
        instance.removeProduto(prod2);
        instance.removeProduto(prod3);
        assertNull(instance.getProdutoPorId(idOfProduct1));
        assertNull(instance.getProdutoPorId(idOfProduct2));
        assertNull(instance.getProdutoPorId(idOfProduct3));
    }

    /**
     * Test of deleteCategoria method, of class SessaoProduto.
     */
    @Test
    public void testDeleteCategoria() throws Exception {
        System.out.println("removeCategory");
        SessaoProduto instance = getSessaoProduto();
        Categoria cat1 = instance.getCategoriaPorId(idOfCategory1);
        Categoria cat2 = instance.getCategoriaPorId(idOfCategory2);
        instance.deleteCategoria(cat1);
        instance.deleteCategoria(cat2);
        assertNull(instance.getCategoriaPorId(idOfCategory1));
        assertNull(instance.getCategoriaPorId(idOfCategory2));
    }
}
