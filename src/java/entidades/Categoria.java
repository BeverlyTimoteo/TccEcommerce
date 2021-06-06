/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;


import com.sun.xml.bind.CycleRecoverable;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Beverly
 */
@Entity
@Table(name = "categoria")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Categoria implements Serializable, CycleRecoverable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    @XmlAttribute
    private Integer id;
    @NotNull(message = "O nome da Categoria não pode ser nulo")
    @NotEmpty(message = "O nome da Categoria não pode ser vazio")
    @Length(min = 3, message = "O minimo de letras para Categoria é Três")
    @Column(name = "nome", nullable = false, unique = true)
    @XmlElement
    private String nome;
    @Column(name = "ativo")
    @XmlElement
    private Boolean ativo = true;
    @XmlElementWrapper
    @OneToMany(mappedBy = "idCategoria", orphanRemoval = true)
    private List<Produto> produtoList = new LinkedList<Produto>();

    public void adicionarProduto(Produto prod) {
        prod.setIdCategoria(this);
        getProdutoList().add(prod);
    }

    public Categoria() {
    }

    public Categoria(Integer id) {
        this.id = id;
    }

    public Categoria(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    
    public List<Produto> getProdutoList() {
        return produtoList;
    }

    public void setProdutoList(List<Produto> produtoList) {
        this.produtoList = produtoList;
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
        if (!(object instanceof Categoria)) {
            return false;
        }
        Categoria other = (Categoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Categoria[ id=" + id + " ]";
    }

    @Override
    public Object onCycleDetected(Context cntxt) {
        Categoria cat = new Categoria();
        cat.setId(this.id);
        return cat;
    }
}
