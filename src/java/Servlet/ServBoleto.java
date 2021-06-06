/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Sessao.SessaoVenda;
import entidades.ItensVenda;
import entidades.Venda;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jboleto.JBoleto;
import org.jboleto.JBoletoBean;

/**
 *
 * @author Beverly
 */
@WebServlet(name = "ServBoleto", urlPatterns = {"/ServBoleto"})
public class ServBoleto extends HttpServlet {

    @Inject
    private SessaoVenda beansvenda;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer idVend = Integer.valueOf(request.getParameter("id"));
        Venda vend = beansvenda.getVendas(idVend);
        if (vend != null) {
            response.setContentType("application/pdf");

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            JBoletoBean jBoletoBean = new JBoletoBean();

            jBoletoBean.setDataDocumento(sdf.format(vend.getDatadavenda()));
            jBoletoBean.setDataProcessamento(sdf.format(Calendar.getInstance().getTime()));
            jBoletoBean.setEspecieDocumento("R$");
            jBoletoBean.setCedente("Ecommerce");
            jBoletoBean.setCarteira("17");

            jBoletoBean.setNomeSacado(vend.getIdUsuario().getNome());
            jBoletoBean.setEnderecoSacado(vend.getIdEnderecoEnvio().getRua());
            jBoletoBean.setBairroSacado(vend.getIdEnderecoEnvio().getBairro());
            jBoletoBean.setCidadeSacado(vend.getIdEnderecoEnvio().getCidade());
            jBoletoBean.setUfSacado(vend.getIdEnderecoEnvio().getEstado());
            jBoletoBean.setCepSacado(vend.getIdEnderecoEnvio().getCep());
            jBoletoBean.setCpfSacado("XXXX");

            jBoletoBean.setLocalPagamento("ATE O VENCIMENTO, PREFERENCIALMENTE NO BANCO DO BRASIL");
            jBoletoBean.setLocalPagamento2("APOS O VENCIMENTO, SOMENTE NO BANCO DO BRASIL");

            Vector descricoes = new Vector();
            for (ItensVenda si : beansvenda.getItensVenda(vend.getId())) {
                descricoes.add(si.getIdProduto().getNome() + " - R$ " + (si.getIdProduto().getPreco().floatValue()) * si.getQuantidade());
            }
            descricoes.add("Itens do pedido nº " + vend.getId());
            jBoletoBean.setDescricoes(descricoes);

            jBoletoBean.setDataVencimento(sdf.format(Calendar.getInstance().getTime()));
            jBoletoBean.setInstrucao1("APOS O VENCIMENTO COBRAR MULTA DE 2%");
            jBoletoBean.setInstrucao2("APOS O VENCIMENTO COBRAR R$ 0,50 POR DIA DE ATRASO");
            jBoletoBean.setInstrucao3("");
            jBoletoBean.setInstrucao4("");

            jBoletoBean.setAgencia("3415");
            jBoletoBean.setContaCorrente("00543004");
            jBoletoBean.setNumConvenio("1101354");
            jBoletoBean.setNossoNumero("0005963971", 10);

            jBoletoBean.setValorBoleto(vend.getTotal().toPlainString());

            JBoleto jBoleto = new JBoleto();

            jBoleto.addBoleto(jBoletoBean, JBoleto.BANCO_DO_BRASIL);
            ByteArrayOutputStream baos = jBoleto.writeToByteArray();
            response.getOutputStream().write(baos.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();
            baos.close();
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
