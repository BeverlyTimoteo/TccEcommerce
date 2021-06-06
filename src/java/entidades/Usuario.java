/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Beverly
 */
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="E-mail inválido")//if the field contains email address consider using this annotation to enforce field validation
    @NotNull
    @NotEmpty(message = "O e-mail não pode ser vazio")
    @Column(name = "email")
    @Email(message = "E-mail invalido")
    private String email;
    @NotNull
    @NotEmpty(message = "O nome não pode ser vazio")
    @Column(name = "nome")
    private String nome;
    @NotNull
    @Column(name = "usuario", nullable = false)
    @NotEmpty(message = "O usuario não pode ser vazio")
    private String usuario;
    @NotNull
    @NotEmpty(message = "A senha não pode ser vazia")
    @Column(name = "senha", nullable = false)
    private String senha;
    @Column(name="adm")
    private boolean adm = false;
    
    @OneToMany(mappedBy = "idUsuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartaoCredito> cartaoCreditoList = new LinkedList<CartaoCredito>();
    @OneToMany(mappedBy = "idUsuario", orphanRemoval = true)
    private List<Venda> vendaList = new LinkedList<Venda>();
    @OneToMany(mappedBy = "idUsuario", cascade = CascadeType.ALL, fetch= FetchType.LAZY, orphanRemoval = true)
    private List<Endereco> enderecoList = new LinkedList<Endereco>();

    public boolean isAdm() {
        return adm;
    }

    public void setAdm(boolean adm) {
        this.adm = adm;
    }   
    
    public void addEndereco(Endereco end) {
        end.setIdUsuario(this);
        getEnderecoList().add(end);
    }

    public void addCartaoCredito(CartaoCredito cc) {
        cc.setIdUsuario(this);
        getCartaoCreditoList().add(cc);
    }

    public Usuario() {
    }

    public Usuario(Integer id) {
        this.id = id;
    }

    public Usuario(Integer id, String email, String nome, String usuario, String senha) {
        this.id = id;
        this.email = email;
        this.nome = nome;
        this.usuario = usuario;
        this.senha = senha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @XmlTransient
    public List<CartaoCredito> getCartaoCreditoList() {
        return cartaoCreditoList;
    }

    public void setCartaoCreditoList(List<CartaoCredito> cartaoCreditoList) {
        this.cartaoCreditoList = cartaoCreditoList;
    }

    @XmlTransient
    public List<Venda> getVendaList() {
        return vendaList;
    }

    public void setVendaList(List<Venda> vendaList) {
        this.vendaList = vendaList;
    }

    @XmlTransient
    public List<Endereco> getEnderecoList() {
        return enderecoList;
    }

    public void setEnderecoList(List<Endereco> enderecoList) {
        this.enderecoList = enderecoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Usuario[ id=" + id + " ]";
    }
}
