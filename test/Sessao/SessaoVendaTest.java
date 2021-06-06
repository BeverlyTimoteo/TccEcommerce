/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessao;

import entidades.CartaoCredito;
import entidades.Endereco;
import java.math.BigDecimal;
import entidades.Produto;
import entidades.Categoria;
import entidades.ItensVenda;
import javax.naming.NamingException;
import entidades.Usuario;
import entidades.Venda;
import entidades.tiposEnum.StatusdaVenda;
import entidades.tiposEnum.TipoBandeira;
import java.util.Date;
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
public class SessaoVendaTest {

    public static Integer idOfCategory1;
    public static Integer idOfProduct1;
    public static Integer idOfUser;
    private static Integer idOfSell;
    private static EJBContainer container;

    public SessaoVendaTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        container.close();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    private SessaoVenda getVendaSessao() throws NamingException {
        return (SessaoVenda) container.getContext().lookup("java:global/classes/SessaoVenda");
    }

    /**
     * Test of salvarVenda method, of class SessaoVenda.
     */
    @Test
    public void testSalvarVenda() throws Exception {
        // The Category
        Categoria cat1 = new Categoria();
        cat1.setAtivo(true);
        cat1.setNome("Category 1");


        // The product
        Produto prod1 = new Produto();
        prod1.setIdCategoria(cat1);
        prod1.setPreco(new BigDecimal("10.2"));
        prod1.setNome("Product 1");
        prod1.setEstoque(100);
        prod1.setDescricao("Spec 1");



        //The User
        Usuario us = new Usuario();
        us.setEmail("beverlytimoteo@yahoo.com.br");
        us.setNome("Beverly Timoteo");
        us.setSenha("123");
        us.setUsuario("beverly");


        Endereco end = new Endereco();
        end.setRua("Rua Castor Sobreira");
        end.setNumero(480);
        end.setCep("13710-000");
        end.setEstado("São Paulo");
        end.setBairro("Jd M M Alves");
        end.setCidade("Tambau");
        us.addEndereco(end);


        CartaoCredito cc = new CartaoCredito();
        cc.setDatadevalidade(new Date());
        cc.setBandeira(TipoBandeira.MASTERCARD);
        cc.setNomedousuariocartao("Beverly");
        cc.setNumerocartao("123123123123");
        cc.setNumeroVezes(1);
        us.addCartaoCredito(cc);


        Venda sell = new Venda();
        sell.setIdEnderecoEnvio(end);
        sell.setIdUsuario(us);
        ItensVenda item = new ItensVenda();
        item.setIdProduto(prod1);
        item.setQuantidade(1);
        sell.setIdCartao(cc);
        cc.getVendaList().add(sell);
        sell.addItem(item);


        System.out.println("saveSell");

        SessaoVenda instance = getVendaSessao();
        Venda result = instance.salvarVenda(sell);
        assertNotNull(result);
        assertTrue(result.getId() > 0);

        idOfCategory1 = cat1.getId();
        idOfProduct1 = prod1.getId();
        idOfUser = us.getId();
        idOfSell = sell.getId();
    }

    /**
     * Test of getVendas method, of class SessaoVenda.
     */
    // @Test
    public void testGetVendas() throws Exception {
        System.out.println("getSell");
        SessaoVenda instance = getVendaSessao();
        Venda result = instance.getVendas(idOfSell);
        assertNotNull(result);
        assertEquals(idOfSell, result.getId());
    }

    /**
     * Test of updateVenda method, of class SessaoVenda.
     */
    //@Test
    public void testUpdateVenda() throws Exception {
        System.out.println("setSell");
        SessaoVenda instance = getVendaSessao();
        Venda sell = instance.getVendas(idOfSell);
        sell.setTotal(BigDecimal.valueOf(6565));
        Venda result = instance.updateVenda(sell);
        assertEquals(BigDecimal.valueOf(6565), result.getTotal());
    }

    /**
     * Test of isCreditCardValidForSell method, of class SessaoVenda.
     */
    //  @Test
    public void testIsCreditCardValidForSell() throws Exception {
        SessaoVenda instance = getVendaSessao();
        Venda sell = instance.getVendas(idOfSell);
        boolean result = instance.isCreditCardValidForSell(sell);
        assertTrue(result);
    }

    /**
     * Test of setStatusVenda method, of class SessaoVenda.
     */
    //  @Test
    public void testSetStatusVenda() throws Exception {
        System.out.println("setStatusOfSell");
        SessaoVenda instance = getVendaSessao();
        Venda sell = instance.getVendas(idOfSell);
        instance.setStatusVenda(sell, StatusdaVenda.EM_ANDAMENTO);
        sell = instance.getVendas(idOfSell);
        assertNotNull(sell.getStatus());
        assertEquals(StatusdaVenda.EM_ANDAMENTO, sell.getStatus());
    }

    /**
     * Test of getVendaPorData method, of class SessaoVenda.
     */
    //  @Test
    public void testGetVendaPorData() throws Exception {
        System.out.println("getSellsByDate");
        SessaoVenda instance = getVendaSessao();
        Date start = new Date();
        Date end = new Date();
        List result = instance.getVendaPorData(start, end);
        assertNotNull(result);
        assertTrue(result.size() == 1);
    }

    /**
     * Test of getVendasPorUsuario method, of class SessaoVenda.
     */
    //  @Test
    public void testGetVendasPorUsuario() throws Exception {
        System.out.println("getSellsByUser");
        SessaoVenda instance = getVendaSessao();
        Usuario usr = instance.getVendas(idOfSell).getIdUsuario();
        List result = instance.getVendasPorUsuario(usr);
        assertNotNull(result);
        assertTrue(result.size() >= 1);
    }

    /**
     * Test of getVendaPeloStatus method, of class SessaoVenda.
     */
    // @Test
    public void testGetVendaPeloStatus() throws Exception {
        System.out.println("getSellsByStatus");
        SessaoVenda instance = getVendaSessao();
        List result = instance.getVendaPeloStatus(StatusdaVenda.VALIDO);
        assertTrue(result.isEmpty());
        result = instance.getVendaPeloStatus(StatusdaVenda.EM_ANDAMENTO);
        assertTrue(result.size() >= 1);
    }

    /**
     * Test of getUltimosProdutosVendidos method, of class SessaoVenda.
     */
    // @Test
    public void testGetUltimosProdutosVendidos() throws Exception {
        System.out.println("getLastSelledProducts");
        SessaoVenda instance = getVendaSessao();
        List result = instance.getUltimosProdutosVendidos();
        Venda sell = instance.getVendas(idOfSell);
        assertEquals(result.get(0), sell.getItensVendaList().get(0).getIdProduto());
    }

    /**
     * Test of encerrarVenda method, of class SessaoVenda.
     */
    // @Test
    public void testEncerrarVenda() throws Exception {
        System.out.println("closeSell");
        SessaoVenda instance = getVendaSessao();
        Venda sell = instance.getVendas(idOfSell);
        Venda result = instance.encerrarVenda(sell);
        assertTrue(result.getEncerrou());
        assertEquals(result.getStatus(), StatusdaVenda.FIM);
    }

    /**
     * Test of deleteVenda method, of class SessaoVenda.
     */
    // @Test
    public void testDeleteVenda() throws Exception {
        System.out.println("removeSell");
        SessaoVenda instance = getVendaSessao();
        Venda sell = instance.getVendas(idOfSell);
        instance.deleteVenda(sell);
        sell = instance.getVendas(idOfSell);
        assertNull(sell);
    }
}
