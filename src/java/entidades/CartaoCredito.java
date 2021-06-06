/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import entidades.tiposEnum.TipoBandeira;
import entidades.validadores.DataValidadeCC;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Beverly
 */
@Entity
@Table(name = "cartao_credito")
public class CartaoCredito implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;
    @Column(name = "bandeira", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoBandeira bandeira;
    @Column(name = "numero_vezes", nullable = false)
    @Min(1)
    private int numeroVezes;
    @NotNull(message = "A data de validade não pode ser nulo")
    @Column(name = "datadevalidade", nullable = false)
    @Temporal(TemporalType.DATE)
    @DataValidadeCC
    private Date datadevalidade;
    @NotNull
    @NotEmpty
    @Column(name = "nomedousuariocartao", nullable = false)
    private String nomedousuariocartao;
    @NotEmpty
    @NotNull
    @Column(name = "numerocartao", nullable = false)
    private String numerocartao;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id", columnDefinition = "integer")
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @Valid
    private Usuario idUsuario;
    @OneToMany(mappedBy = "idCartao", cascade = CascadeType.ALL)
    private List<Venda> vendaList = new LinkedList<Venda>();

    public CartaoCredito() {
    }

    public CartaoCredito(Integer id) {
        this.id = id;
    }

    public CartaoCredito(Integer id, TipoBandeira bandeira, int numeroVezes, Date datadevalidade, String nomedousuariocartao, String numerocartao) {
        this.id = id;
        this.bandeira = bandeira;
        this.numeroVezes = numeroVezes;
        this.datadevalidade = datadevalidade;
        this.nomedousuariocartao = nomedousuariocartao;
        this.numerocartao = numerocartao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoBandeira getBandeira() {
        return bandeira;
    }

    public void setBandeira(TipoBandeira bandeira) {
        this.bandeira = bandeira;
    }

    public int getNumeroVezes() {
        return numeroVezes;
    }

    public void setNumeroVezes(int numeroVezes) {
        this.numeroVezes = numeroVezes;
    }

    public Date getDatadevalidade() {
        return datadevalidade;
    }

    public void setDatadevalidade(Date datadevalidade) {
        this.datadevalidade = datadevalidade;
    }

    public String getNomedousuariocartao() {
        return nomedousuariocartao;
    }

    public void setNomedousuariocartao(String nomedousuariocartao) {
        this.nomedousuariocartao = nomedousuariocartao;
    }

    public String getNumerocartao() {
        return numerocartao;
    }

    public void setNumerocartao(String numerocartao) {
        this.numerocartao = numerocartao;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<Venda> getVendaList() {
        return vendaList;
    }

    public void setVendaList(List<Venda> vendaList) {
        this.vendaList = vendaList;
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
        if (!(object instanceof CartaoCredito)) {
            return false;
        }
        CartaoCredito other = (CartaoCredito) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.CartaoCredito[ id=" + id + " ]";
    }
}
