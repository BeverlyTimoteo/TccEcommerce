/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import entidades.tiposEnum.StatusdaVenda;
import java.io.Serializable;
import java.math.BigDecimal;
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

/**
 *
 * @author Beverly
 */
@Entity
@Table(name = "venda")
public class Venda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;
    @NotNull
    @Min(0)
    @Column(name = "total", nullable = false)
    private BigDecimal total;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusdaVenda status = StatusdaVenda.INICIO;
    @Column(name = "encerrou")
    private Boolean encerrou = false;
    @NotNull
    @Column(name = "datadavenda", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date datadavenda;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id", columnDefinition = "integer")
    @ManyToOne
    @NotNull
    @Valid
    private Usuario idUsuario;
    @JoinColumn(name = "id_endereco_envio", referencedColumnName = "id", columnDefinition = "integer")
    @ManyToOne()
    @Valid
    @NotNull
    private Endereco idEnderecoEnvio;
    @JoinColumn(name = "id_cartao", referencedColumnName = "id", columnDefinition = "integer")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @Valid
    private CartaoCredito idCartao;
    @OneToMany(mappedBy = "idVenda", cascade = CascadeType.ALL)
    private List<ItensVenda> itensVendaList = new LinkedList<ItensVenda>();

    public void addItem(ItensVenda item) {
        item.setIdVenda(this);
        itensVendaList.add(item);
        if (total == null) {
            total = item.getIdProduto().getPreco();
        } else {
            total.add(item.getIdProduto().getPreco());
        }

    }

    public Venda() {
    }

    public Venda(Integer id) {
        this.id = id;
    }

    public Venda(Integer id, BigDecimal total, StatusdaVenda status, Date datadavenda) {
        this.id = id;
        this.total = total;
        this.status = status;
        this.datadavenda = datadavenda;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public StatusdaVenda getStatus() {
        return status;
    }

    public void setStatus(StatusdaVenda status) {
        this.status = status;
    }

    public Boolean getEncerrou() {
        return encerrou;
    }

    public void setEncerrou(Boolean encerrou) {
        this.encerrou = encerrou;
    }

    public Date getDatadavenda() {
        return datadavenda;
    }

    public void setDatadavenda(Date datadavenda) {
        this.datadavenda = datadavenda;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Endereco getIdEnderecoEnvio() {
        return idEnderecoEnvio;
    }

    public void setIdEnderecoEnvio(Endereco idEnderecoEnvio) {
        this.idEnderecoEnvio = idEnderecoEnvio;
    }

    public CartaoCredito getIdCartao() {
        return idCartao;
    }

    public void setIdCartao(CartaoCredito idCartao) {
        this.idCartao = idCartao;
    }

    public List<ItensVenda> getItensVendaList() {
        return itensVendaList;
    }

    public void setItensVendaList(List<ItensVenda> itensVendaList) {
        this.itensVendaList = itensVendaList;
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
        if (!(object instanceof Venda)) {
            return false;
        }
        Venda other = (Venda) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Venda[ id=" + id + " ]";
    }
}
