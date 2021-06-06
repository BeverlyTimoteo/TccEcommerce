/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;



import com.sun.xml.bind.CycleRecoverable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Beverly
 */
@Entity
@Table(name = "produto")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Produto implements Serializable, CycleRecoverable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @XmlElement
    @Column(name = "id")
    private Integer id;
    @NotNull
    @Column(name = "descricao", nullable = false)
    @XmlElement
    @NotEmpty
    private String descricao;
    @NotNull
    @Column(name = "estoque", nullable = false)
    @XmlElement
    @Min(0)
    private int estoque;
    @NotEmpty
    @NotNull
    @Column(name = "nome", unique = true, nullable = false)
    @XmlElement
    private String nome;
    @NotNull
    @Column(name = "preco", nullable = false)
    @XmlElement
    private BigDecimal preco;
    @Column(name = "foto_url", nullable = true)
    @XmlElement
    private String fotoUrl;
    @Valid
    @NotNull
    @XmlElement
    @JoinColumn(name = "id_categoria", referencedColumnName = "id", columnDefinition = "integer")
    @ManyToOne(cascade = {CascadeType.MERGE})
    private Categoria idCategoria;
    @OneToMany(mappedBy = "idProduto", cascade = CascadeType.ALL, orphanRemoval=true )
    @XmlTransient
    private List<ItensVenda> itensVendaList;
    @Transient
    private Integer quantidade = 1;

    public Produto() {
    }

    public Produto(Integer id) {
        this.id = id;
    }

    public Produto(Integer id, String descricao, int estoque, String nome, BigDecimal preco) {
        this.id = id;
        this.descricao = descricao;
        this.estoque = estoque;
        this.nome = nome;
        this.preco = preco;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getFotoUrl() {
        if (fotoUrl == null) {
            return "/imagens/no_photo.png";
        }
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    public List<ItensVenda> getItensVendaList() {
        return itensVendaList;
    }

    public void setItensVendaList(List<ItensVenda> itensVendaList) {
        this.itensVendaList = itensVendaList;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
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
        if (!(object instanceof Produto)) {
            return false;
        }
        Produto other = (Produto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Produto[ id=" + id + " ]";
    }

    @Override
    public Object onCycleDetected(Context cntxt) {
       Produto prod = new Produto(this.id);
        return prod;
    }
}
