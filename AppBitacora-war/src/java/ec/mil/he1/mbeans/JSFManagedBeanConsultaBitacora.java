/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.he1.mbeans;

import he1.seguridades.entities.nuevos.SegSoftwareBitacora;
import he1.seguridades.entities.nuevos.VTiemposSolucion;
import he1.seguridades.entities.nuevos.VUsuariosClasif;
import he1.seguridades.sessions.SegSoftwareBitacoraFacade;
import he1.seguridades.sessions.nuevos.VTiemposSolucionFacade;
import he1.utilities.SesionSeguridades;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

//import org.primefaces.model.chart.Axis;
//import org.primefaces.model.chart.AxisType;
//import org.primefaces.model.chart.CategoryAxis;
//import org.primefaces.model.chart.LineChartModel;
//import org.primefaces.model.chart.ChartSeries;
/**
 *
 * @author christian_ruiz
 */
@ManagedBean(name = "consulta")
@RequestScoped
public class JSFManagedBeanConsultaBitacora implements Serializable {

    @EJB
    private VTiemposSolucionFacade vTiemposSolucionFacade;

    private List<VTiemposSolucion> ltiempos = new ArrayList<>();

    @EJB
    private SegSoftwareBitacoraFacade segSoftwareBitacoraFacade;
    private SegSoftwareBitacora ssb = new SegSoftwareBitacora();
    private List<SegSoftwareBitacora> lsobtwarebitacora = new ArrayList<>();
    @EJB
    private SesionSeguridades sesionSeguridades;

    private String area = "";

    private String parametro = "";
    private VUsuariosClasif findByCedulaLogin = new VUsuariosClasif();

    private LineChartModel lineModel1;
    private LineChartModel lineModel2;

    @PostConstruct
    private void init() {

        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
            findByCedulaLogin = (VUsuariosClasif) session.getAttribute("vUsuariosClasif");
            if (findByCedulaLogin.getId() != null) {
                listaConsultaBitacora = sesionSeguridades.listaConsultaBitacora("-1", "-1");
            }

        } catch (Exception ex) {
            Logger.getLogger(JSFManagedBeanConsultaBitacora.class.getName()).log(Level.WARNING, null, ex);
        }

        try {
            createLineModels();
        } catch (Exception e) {
            Logger.getLogger(JSFManagedBeanConsultaBitacora.class.getName()).log(Level.WARNING, null, e);
        }

    }

    public LineChartModel getLineModel1() {
        return lineModel1;
    }

    public LineChartModel getLineModel2() {
        return lineModel2;
    }

    private void createLineModels() {
        lineModel1 = initLinearModel();
        lineModel1.setTitle("Linear Chart");
        lineModel1.setLegendPosition("e");
        Axis yAxis = lineModel1.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(10);

        lineModel2 = initCategoryModel();
        lineModel2.setTitle("Número de Incidentes en TICS");
        lineModel2.setLegendPosition("e");
        lineModel2.setShowPointLabels(true);
        lineModel2.getAxes().put(AxisType.X, new CategoryAxis("Año"));
        yAxis = lineModel2.getAxis(AxisType.Y);
        yAxis.setLabel("Número de Incidentes");
        yAxis.setMin(0);
        yAxis.setMax(200);
    }

    private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();

        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Series 1");

        series1.set(1, 2);
        series1.set(2, 1);
        series1.set(3, 3);
        series1.set(4, 6);
        series1.set(5, 8);

        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("Series 2");

        series2.set(1, 6);
        series2.set(2, 3);
        series2.set(3, 2);
        series2.set(4, 7);
        series2.set(5, 9);

        model.addSeries(series1);
        model.addSeries(series2);

        return model;
    }

    private LineChartModel initCategoryModel() {
        LineChartModel model = new LineChartModel();

//<editor-fold defaultstate="collapsed" desc="comment">
        ChartSeries basedatos = new ChartSeries();
        ltiempos.clear();
        ltiempos = vTiemposSolucionFacade.findTiemposSolucion("BASE DE DATOS");
        basedatos.setLabel("BASE DE DATOS");
        for (VTiemposSolucion ltiempo : ltiempos) {
            basedatos.set(ltiempo.getAnio(), Integer.parseInt(ltiempo.getIncidentes()));
            System.out.println("bdd ltiempo.getIncidentes() = " + ltiempo.getIncidentes());
        }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="comment">
        ChartSeries REDES = new ChartSeries();
        ltiempos.clear();
        ltiempos = vTiemposSolucionFacade.findTiemposSolucion("REDES");
        REDES.setLabel("REDES");
        for (VTiemposSolucion ltiempo : ltiempos) {
            REDES.set(ltiempo.getAnio(), Integer.parseInt(ltiempo.getIncidentes()));
            System.out.println("redes ltiempo.getIncidentes() = " + ltiempo.getIncidentes());
        }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="comment">
        ChartSeries INFRAESTRUCTURA = new ChartSeries();
        ltiempos.clear();
        ltiempos = vTiemposSolucionFacade.findTiemposSolucion("INFRAESTRUCTURA");
        INFRAESTRUCTURA.setLabel("INFRAESTRUCTURA");
        for (VTiemposSolucion ltiempo : ltiempos) {
            INFRAESTRUCTURA.set(ltiempo.getAnio(), Integer.parseInt(ltiempo.getIncidentes()));
            System.out.println("infra ltiempo.getIncidentes() = " + ltiempo.getIncidentes());
        }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="comment">
        ChartSeries DESARROLLO = new ChartSeries();
        ltiempos.clear();
        ltiempos = vTiemposSolucionFacade.findTiemposSolucion("DESARROLLO");
        DESARROLLO.setLabel("DESARROLLO");
        for (VTiemposSolucion ltiempo : ltiempos) {
            DESARROLLO.set(ltiempo.getAnio(), Integer.parseInt(ltiempo.getIncidentes()));
            System.out.println("desa ltiempo.getIncidentes() = " + ltiempo.getIncidentes());
        }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="comment">
        ChartSeries PORTAL = new ChartSeries();
        ltiempos.clear();
        ltiempos = vTiemposSolucionFacade.findTiemposSolucion("PORTAL");
        PORTAL.setLabel("PORTAL");
        for (VTiemposSolucion ltiempo : ltiempos) {
            PORTAL.set(ltiempo.getAnio(), Integer.parseInt(ltiempo.getIncidentes()));
            System.out.println("portal ltiempo.getIncidentes() = " + ltiempo.getIncidentes());
        }

//</editor-fold>
        model.addSeries(basedatos);
        model.addSeries(REDES);
        model.addSeries(INFRAESTRUCTURA);
        model.addSeries(DESARROLLO);
        model.addSeries(PORTAL);

        return model;
    }

    /**
     * Creates a new instance of JSFManagedBeanConsultaBitacora
     */
    public JSFManagedBeanConsultaBitacora() {
    }

    private List<Map> listaConsultaBitacora = new ArrayList<>();

    public void buttonAction(ActionEvent actionEvent) {

        try {

            if (findByCedulaLogin.getId() != null) {
                listaConsultaBitacora = sesionSeguridades.listaConsultaBitacora(parametro, area);
            }

        } catch (Exception ex) {
            Logger.getLogger(JSFManagedBeanConsultaBitacora.class.getName()).log(Level.WARNING, null, ex);
        }
    }

    /**
     * @return the parametro
     */
    public String getParametro() {
        return parametro;
    }

    /**
     * @param parametro the parametro to set
     */
    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    /**
     * @return the listaConsultaBitacora
     */
    public List<Map> getListaConsultaBitacora() {

        return listaConsultaBitacora;
    }

    /**
     * @return the area
     */
    public String getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * @return the ssb
     */
    public SegSoftwareBitacora getSsb() {
        return ssb;
    }

    /**
     * @param ssb the ssb to set
     */
    public void setSsb(SegSoftwareBitacora ssb) {
        this.ssb = ssb;
    }

    /**
     * @return the lsobtwarebitacora
     */
    public List<SegSoftwareBitacora> getLsobtwarebitacora() {
        try {
            lsobtwarebitacora = segSoftwareBitacoraFacade.findAreasSistemas();
        } catch (Exception ex) {
            Logger.getLogger(JSFManagedBeanConsultaBitacora.class.getName()).log(Level.WARNING, null, ex);
        }

        return lsobtwarebitacora;
    }

    /**
     * @return the ltiempos
     */
    public List<VTiemposSolucion> getLtiempos() {
        ltiempos.clear();
        ltiempos = vTiemposSolucionFacade.findTiemposSolucion(area);
        return ltiempos;
    }

}
