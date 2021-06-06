/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessao;

import javax.naming.NamingException;
import entidades.Endereco;
import entidades.Usuario;
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
public class SessaoUsuarioTest {

    private static EJBContainer container;
    public static Integer idUsuario;

    public SessaoUsuarioTest() {
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

    private SessaoUsuario getSessaoUsuario() throws NamingException {
        return (SessaoUsuario) container.getContext().lookup("java:global/classes/SessaoUsuario");
    }

    /**
     * Test of salvarUsuario method, of class SessaoUsuario.
     */
    @Test
    public void testSalvarUsuario() throws Exception {
        System.out.println("Salvar Usuario");
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

        SessaoUsuario ins = getSessaoUsuario();

        Usuario resultado = ins.salvarUsuario(us);
        idUsuario = resultado.getId();
        assertTrue(resultado.getId().intValue() > 0);
        assertTrue(resultado.getEnderecoList().get(0).getId().intValue() > 0);
    }

    /**
     * Test of getUsuarioId method, of class SessaoUsuario.
     */
    @Test
    public void testGetUsuarioId() throws Exception {
        System.out.println("getGetUsuarioId(" + idUsuario + ")");
        SessaoUsuario instance = getSessaoUsuario();
        Usuario result = instance.getUsuarioId(idUsuario);
        assertNotNull(result);
        assertEquals("Beverly Timoteo", result.getNome());
        assertTrue(result.getEnderecoList().size() > 0);
    }

    /**
     * Test of updateUsuario method, of class SessaoUsuario.
     */
    @Test
    public void testUpdateUsuario() throws Exception {
        final String name = "Larissa Rosa";
        System.out.println("UpdateUsuario");
        SessaoUsuario instance = getSessaoUsuario();
        Usuario us = instance.getUsuarioId(idUsuario);
        assertNotNull(us);
        us.setNome(name);
        instance.updateUsuario(us);
        us = instance.getUsuarioId(idUsuario);
        assertEquals(us.getNome(), name);
    }

    /**
     * Test of getTodosUsuarios method, of class SessaoUsuario.
     */
    @Test
    public void testGetTodosUsuarios() throws Exception {
        System.out.println("GetTodosUsuarios");
        SessaoUsuario instance = getSessaoUsuario();
        List result = instance.getTodosUsuarios();
        assertTrue(result.size() >= 1);
        assertNotNull(result);
    }

    /**
     * Test of getUsuariosPorNome method, of class SessaoUsuario.
     */
    @Test
    public void testGetUsuariosPorNome() throws Exception {
        System.out.println("GetUsuariosPorNome");
        String name = "Larissa Rosa";
        SessaoUsuario instance = getSessaoUsuario();
        List<Usuario> result = instance.getUsuariosPorNome(name);
        assertNotNull(result);
        assertTrue(result.size() >= 1);
        assertEquals(result.get(0).getNome(), "Larissa Rosa");
    }

    /**
     * Test of confirmaUsuarios method, of class SessaoUsuario.
     */
    @Test
    public void testConfirmaUsuarios() throws Exception {
        System.out.println("ConfirmaUsuarios");
        String usuario = "beverly";
        String senha = "123";
        SessaoUsuario instance = getSessaoUsuario();
        assertNotNull(instance.confirmaUsuarios(usuario, senha, true));
    }

    /**
     * Test of getEnderecodosUsuarios method, of class SessaoUsuario.
     */
    @Test
    public void testGetEnderecodosUsuarios() throws Exception {
        System.out.println("GetEnderecodosUsuarios");
        SessaoUsuario instance = getSessaoUsuario();
        Usuario us = instance.getUsuarioId(idUsuario);
        System.out.println(us.getNome());
        assertNotNull(us);
        List<Endereco> end = instance.getEnderecodosUsuarios(us);
        assertNotNull(end);
        assertTrue(end.size() == 1);
        assertEquals(end.get(0).getRua(), "Rua Castor Sobreira");
    }

    /**
     * Test of getEnderecoId method, of class SessaoUsuario.
     */
    @Test
    public void testGetEnderecoId() throws Exception {
        System.out.println("testGetEnderecoId");
        SessaoUsuario instance = getSessaoUsuario();
        Usuario us = instance.getUsuarioId(idUsuario);
        assertNotNull(us);
        List<Endereco> addrs = instance.getEnderecodosUsuarios(us);
        int idAddress = addrs.get(0).getId();
        Endereco result = instance.getEnderecoId(idAddress);
        assertEquals(addrs.get(0), result);
    }

    /**
     * Test of removeUsuarioId method, of class SessaoUsuario.
     */
    @Test
    public void testRemoveUsuarioId() throws Exception {
        System.out.println("testRemoveUsuarioId");
        SessaoUsuario instance = getSessaoUsuario();
        boolean result = instance.removeUsuarioId(idUsuario);
        assertTrue(result);
        Usuario us = instance.getUsuarioId(idUsuario);
        assertNull(us);
    }

    /**
     * Test of deleteUsuario method, of class SessaoUsuario.
     */
    //@Test
    public void testDeleteUsuario() throws Exception {
        System.out.println("removeUser(User)");
        System.out.println("Salvar Usuario");
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

        SessaoUsuario instance = getSessaoUsuario();
        Usuario savedUs = instance.getUsuarioId(instance.salvarUsuario(us).getId());
        instance.deleteUsuario(savedUs);
        Usuario removedUs = instance.getUsuarioId(savedUs.getId());
        assertNull(removedUs);
    }
}
