package webserv;

import Sessao.SessaoProduto;
import entidades.Produto;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("produto")
@RequestScoped
public class ProdutoRESTful {
    @Inject
    private SessaoProduto beanProd;
    
    @GET
    @Produces("application/xml")
    @Path("get/{id}")
    public Produto getProdutoPeloId(@PathParam("id") int idProd) {
        return beanProd.getProdutoPorId(idProd);
    }

    @GET
    @Produces("application/json")
    @Path("lista/{name}")
    public List<Produto> getProdutoPorNome(@PathParam("name") String nome) {
        return beanProd.getTodosProdutosNomeouEspedificacao(nome);
    }

}
