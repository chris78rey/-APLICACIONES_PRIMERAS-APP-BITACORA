/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.he1.servlet;

import he1.seguridades.entities.nuevos.VUsuariosClasif;
import he1.seguridades.sessions.SegUsuarioFacade;
import he1.seguridades.sessions.VUsuariosClasifFacade;
import he1.utilities.SesionSeguridades;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author christian_ruiz
 */
@WebServlet(name = "Recibe", urlPatterns = {"/recibeparametro"})
public class RecibeUsuarioFinal extends HttpServlet {

    @EJB
    private VUsuariosClasifFacade vUsuariosClasifFacade;

    @EJB
    private SesionSeguridades sesionSeguridades;

    @EJB
    private SegUsuarioFacade segUsuarioFacade;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws javax.naming.NamingException
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NamingException, SQLException {

        try {
            HttpSession session = request.getSession(true);
            String usuid = "";
            //usuario encryptado
            String usuarioEncriptado = (request.getParameter("xphsumf"));
            String modulo = (request.getParameter("ldmjcgd"));
            usuid = segUsuarioFacade.p_decript_palabra(usuarioEncriptado);
            //llama a la vista de usuarios donde se encuentra integrada 
            //la información
            VUsuariosClasif vUsuariosClasif = vUsuariosClasifFacade.find(new BigDecimal(usuid));
            session.setAttribute("vUsuariosClasif", vUsuariosClasif);
            session.setAttribute("modulo", modulo);
            //refirecciona a la primera página del módulo
            String buscaURLSiguiente = sesionSeguridades.buscaURLSiguiente(modulo);
            //redirecciona a la primera página del módulo seleccionado
            //en esa página se deberia tomar los datos para el encabezado
            response.sendRedirect(buscaURLSiguiente);

        } catch (SQLException | NamingException | NumberFormatException e) {
//            System.out.println(java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("ec/mil/he1/mbeans/propiedades").getString("E.GETLOCALIZEDMESSAGE() = {0}"), new Object[] {e.getLocalizedMessage()));
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(RecibeUsuarioFinal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(RecibeUsuarioFinal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}