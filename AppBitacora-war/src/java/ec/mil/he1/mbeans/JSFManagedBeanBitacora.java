/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.he1.mbeans;

import he1.seguridades.entities.nuevos.SegBitacora;
import he1.seguridades.entities.nuevos.VUsuariosClasif;
import he1.seguridades.sessions.SegBitacoraFacade;
import he1.utilities.SesionSeguridades;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author christian_ruiz
 */
@ManagedBean(name = "bitac")
@ViewScoped
public class JSFManagedBeanBitacora implements Serializable {

    /**
     * @return the dateHoraSolucion
     */
    public Date getDateHoraSolucion() {
        return dateHoraSolucion;
    }

    /**
     * @param dateHoraSolucion the dateHoraSolucion to set
     */
    public void setDateHoraSolucion(Date dateHoraSolucion) {
        this.dateHoraSolucion = dateHoraSolucion;
    }

    @EJB
    private SesionSeguridades sesionSeguridades;

    @EJB
    private SegBitacoraFacade segBitacoraFacade;
    private SegBitacora bitacora = new SegBitacora();
    private List<SegBitacora> lsegBitacoras = new ArrayList<>();
    VUsuariosClasif vUsuariosClasif = new VUsuariosClasif();
    private String hora = "";

    private Date date10;
    private Date dateHoraSolucion;

    private VUsuariosClasif findByCedulaLogin = new VUsuariosClasif();

    @PostConstruct
    private void init() {
        System.out.println("@PostConstruct");
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
            findByCedulaLogin = (VUsuariosClasif) session.getAttribute("vUsuariosClasif");
            if (findByCedulaLogin.getId() != null) {
                lsegBitacoras = sesionSeguridades.findBitacoraRecientes();
            }

        } catch (Exception e) {
            System.out.println("e = " + e.getLocalizedMessage());
        }

    }

    public void buttonAction(ActionEvent actionEvent) {

        try {
            String strDate1;
            String strDateHoraSolucion;

            SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            SimpleDateFormat smhorasolucion = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            strDate1 = sm.format(date10);
            strDateHoraSolucion = smhorasolucion.format(getDateHoraSolucion());

            FacesContext context = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
            vUsuariosClasif = (VUsuariosClasif) session.getAttribute("vUsuariosClasif");
            System.out.println("strDate1 = " + strDate1);
            System.out.println("strDateHoraSolucion = " + strDateHoraSolucion);
            sesionSeguridades.p_registra_evento(strDate1, bitacora.getArea(), bitacora.getEvento(), bitacora.getSolucion(), vUsuariosClasif.getId().toEngineeringString(), strDateHoraSolucion);

            if (vUsuariosClasif.getId() != null) {
                lsegBitacoras = sesionSeguridades.findBitacoraRecientes();
                bitacora = new SegBitacora();
                strDate1 = "";

                date10 = new Date();
                dateHoraSolucion = new Date(null);
            }

        } catch (Exception e) {
            System.out.println("e = " + e.getLocalizedMessage());
        }

    }

    /**
     * Creates a new instance of JSFManagedBeanBitacora
     */
    public JSFManagedBeanBitacora() {
    }

    /**
     * @return the lsegBitacoras
     */
    public List<SegBitacora> getLsegBitacoras() {
        return lsegBitacoras;
    }

    /**
     * @return the bitacora
     */
    public SegBitacora getBitacora() {
        return bitacora;
    }

    /**
     * @param bitacora the bitacora to set
     */
    public void setBitacora(SegBitacora bitacora) {
        this.bitacora = bitacora;
    }

    /**
     * @return the hora
     */
    public String getHora() {
        return hora;
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(String hora) {
        this.hora = hora;
    }

    /**
     * @return the date10
     */
    public Date getDate10() {
        return date10;
    }

    /**
     * @param date10 the date10 to set
     */
    public void setDate10(Date date10) {
        this.date10 = date10;
    }

}
