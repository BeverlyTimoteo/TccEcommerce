/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Converter;

import CDILookup.ManualCDILookup;
import Sessao.SessaoProduto;
import entidades.Categoria;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Beverly
 */
@FacesConverter(value = "categoriaParaConverter")
public class ConverterCategoria extends ManualCDILookup implements Converter {

    private SessaoProduto beanProd;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null) {

            beanProd = getFacadeWithJNDI(SessaoProduto.class);
            Categoria cat = beanProd.getCategoriaPorNomeEspecifico(value);
            return cat;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Categoria cat = (Categoria) value;
            return cat.getNome();
        }
        return null;
    }
}
