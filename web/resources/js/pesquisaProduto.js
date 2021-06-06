var jq = jQuery;
function pesquisaJson(valor){    
    jq.getJSON('/TCCProjeto/resources/produto/lista/'+valor.value,    
        function (dados){           
            var estouNaPagina = jq('#divPesquisa').html() !== null;//retorna true ou false
            if(estouNaPagina){                
                var temVarios = dados.produto.nome; 
                if(temVarios == undefined){                    
                    var result = '<ul>';
                    $.each(dados.produto, function(i,item){                        
                        result = result + '       <li>';
                        result = result + '           <a href="/TCCProjeto/detalhesProduto.faces?id='+item.id+'">\n\
<img style="width: 100px; height: 100px" alt="Imagem do Produto" src="/TCCProjeto/'+item.fotoUrl+'"/></a>';
                        result = result + '               '+item.nome;
                        result = result + '             <strong> Por : R$ '+item.preco+',00</strong>';
                        result = result + '        </li>';                        
                    });
                    jq('#divPesquisa').html(result);
                    result = result + ' </ul>';                        
                }else{                     
                    var result = '<ul>';
                    result = result + '       <li>';
                    result = result + '           <a href="/TCCProjeto/detalhesProduto.faces?id='+dados.produto.id+'">\n\
<img style="width: 100px; height: 100px" alt="Imagem do Produto" src="/TCCProjeto/'+dados.produto.fotoUrl+'"/></a>';
                    result = result + '               '+dados.produto.nome;
                    result = result + '             <strong> Por : R$ '+dados.produto.preco+',00</strong>';
                    result = result + '        </li>';
                    result = result + ' </ul>';
                    jq('#divPesquisa').html(result);
                }
            }             
        })
}

function SomenteNumero(e){
    var tecla=(window.event)?event.keyCode:e.which;   
    if((tecla>47 && tecla<58)) return true;
    else{
        if (tecla==8 || tecla==0) return true;
        else  return false;
    }
}

 


