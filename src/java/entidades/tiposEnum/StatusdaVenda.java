/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades.tiposEnum;

/**
 *
 * @author Beverly
 */
public enum StatusdaVenda {

    INICIO("Seu pedido foi realizado"),
    VALIDO("O pagamento de seu pedido foi confirmado."),
    EM_ANDAMENTO("Pedido está em separação."),
    ENVIOU("Produto entregue para a transportadora"),
    FIM("Seu produto foi entregue com sucesso. Esperamos tê-lo novamente em nosso site.");
    private String descricao;

    StatusdaVenda(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricaoVenda() {
        return descricao;
    }
}
