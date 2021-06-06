/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import entidades.validadores.CEPValido;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Beverly
 */
@Entity
@Table(name = "endereco")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Endereco implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;
    @NotNull
    @NotEmpty
    @Column(name = "rua", nullable = false)
    @XmlElement
    private String rua;
    @NotNull
    @NotEmpty
    @Column(name = "bairro")
    @XmlElement
    private String bairro;
    @NotNull
    @NotEmpty
    @XmlElement
    @Column(name = "estado", nullable = false)
    private String estado;
    @XmlElement
    @NotNull
    @Min(0)
    @Column(name = "numero", nullable = false)
    private int numero;
    @NotNull
    @NotEmpty
    @Column(name = "cidade")
    private String cidade;
    @NotNull
    @NotEmpty
    @XmlElement
    @CEPValido
    @Column(name = "cep")
    private String cep;
    @OneToMany(mappedBy= "idEnderecoEnvio")
    private List<Venda> vendaList;
    @NotNull
    @ManyToOne()
    @Valid
    @JoinColumn(name = "id_usuario", referencedColumnName = "id", columnDefinition = "integer")
    private Usuario idUsuario;
    @Column
    private boolean fgAtivo = true;

    public boolean isFgAtivo() {
        return fgAtivo;
    }

    public void setFgAtivo(boolean fgAtivo) {
        this.fgAtivo = fgAtivo;
    }
    
    public Endereco() {
    }

    public Endereco(Integer id) {
        this.id = id;
    }

    public Endereco(Integer id, String rua, String bairro, String estado, int numero, String cidade, String cep) {
        this.id = id;
        this.rua = rua;
        this.bairro = bairro;
        this.estado = estado;
        this.numero = numero;
        this.cidade = cidade;
        this.cep = cep;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    
    public List<Venda> getVendaList() {
        return vendaList;
    }

    public void setVendaList(List<Venda> vendaList) {
        this.vendaList = vendaList;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
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
        if (!(object instanceof Endereco)) {
            return false;
        }
        Endereco other = (Endereco) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Endereco[ id=" + id + " ]";
    }
}
