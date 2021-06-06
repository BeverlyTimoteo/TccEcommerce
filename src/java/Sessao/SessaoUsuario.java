/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessao;

import Converter.TransformaStringMD5;
import ejb.BeansSessaoBase;
import entidades.Endereco;
import entidades.Usuario;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.Query;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class SessaoUsuario extends BeansSessaoBase {

    public Endereco updateEndereco(Endereco update) {
        return getEm().merge(update);
    }

    public Usuario salvarUsuario(Usuario usuario) {
        usuario.setSenha(TransformaStringMD5.md5(usuario.getSenha()));
        getEm().persist(usuario);
        return usuario;
    }

    public void removeEndereco(int idDoEndereco) {
        Endereco end = getEm().find(Endereco.class, idDoEndereco);
        getEm().remove(end);
    }

    public Usuario getUsuarioId(int idUsuario) {
        return getPojo(Usuario.class, idUsuario);
    }

    public List<Usuario> getMostraUsuariosAtivosporVenda() {

        Query qr = getEm().createNativeQuery("select ID_USUARIO,count(*) as qtd from venda group by ID_USUARIO order by qtd desc limit 10");
        List<Object> retorna = qr.getResultList();
        StringBuilder sb = new StringBuilder();
        for (Object object : retorna) {
            Integer idUsuario = (Integer) ((Object[]) object)[0];
            sb.append(idUsuario + ",");
        }
        if (sb.toString().isEmpty()) {
            return new LinkedList<Usuario>();
        }
        System.out.println(sb.toString());
        sb.deleteCharAt(sb.lastIndexOf(","));
        return getLista(Usuario.class, "select us from Usuario us where us.id in (" + sb.toString() + ")");

    }

    public List<Usuario> getUltimosUsuarios() {
        return getListaLimitada(Usuario.class, "select us from Usuario us order by us.id desc", 10);
    }

    public List<Usuario> getTodosUsuarios() {
        return getLista(Usuario.class, "select us from Usuario us order by us.nome");
    }

    public List<Usuario> getUsuariosPorNome(String nome) {
        return getLista(Usuario.class, "select us from Usuario us where us.nome like ?1", "%" + nome + "%");
    }

    public Usuario confirmaUsuarios(String usuario, String senha, Boolean opcao) {
        try {
            senha = TransformaStringMD5.md5(senha);
            return getPojo(Usuario.class, "select us from Usuario us where us.adm = ?1 and us.usuario = ?2 and us.senha = ?3", opcao, usuario, senha);
        } catch (Exception e) {
            return null;
        }
    }

    public Usuario updateUsuario(Usuario usu) {
        getEm().merge(usu);
        return usu;
    }

    public void deleteUsuario(Usuario usu) {
        usu = getEm().merge(usu);
        getEm().remove(usu);
    }

    public boolean removeUsuarioId(int idUsuario) {
        execute("DELETE FROM Endereco endereco where endereco.idUsuario.id = ?1", idUsuario);
        boolean Retornou = execute("DELETE FROM Usuario us where us.id = ?1", idUsuario) > 0;
        return Retornou;
    }

    public List<Endereco> getEnderecodosUsuarios(Usuario usu) {
        return getLista(Endereco.class, "select addr from Endereco addr where addr.idUsuario = ?1 and addr.fgAtivo = ?2 order by addr.rua", usu, true);
    }

    public Endereco getEnderecoId(int idEndereco) {
        return getPojo(Endereco.class, idEndereco);
    }
}
