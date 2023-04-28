package org.in5bm.josevilleda.samuelherrera.reports;

import java.net.URL;
import java.util.Locale;
import java.util.Map;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import org.in5bm.josevilleda.samuelherrera.db.Conexion;

/**
 *
 * @author Jose Mauricio Villeda Morales
 * @date 27 jun. 2022
 * @time 11:16:50
 *
 * Codigo Tecnico: IN5BM
 */
public class GenerarReporte {
    
    private final String PAQUETE_IMAGES = "org/in5bm/josevilleda/samuelherrera/resources/images/";

    private static GenerarReporte instance;

    private JasperViewer jasperViewer;

    private GenerarReporte() {
        Locale locale = new Locale("es", "GT");
        Locale.setDefault(locale);
    }

    public static GenerarReporte getInstance() {
        if (instance == null) {
            instance = new GenerarReporte();
        }
        return instance;
    }

    public void mostrarReporte(String nombreReporte, Map<String, Object> parametros, String titulo) {
        try {

            URL urlFile = new URL(getClass().getResource(nombreReporte).toString());
            
            // Imagenes
            parametros.put("IMAGE_LOGO", PAQUETE_IMAGES + "Logo MC.png");
            parametros.put("IMAGE_FOOTER", PAQUETE_IMAGES + "logo.png");
            parametros.put("IMAGE_INSTRUCTOR", PAQUETE_IMAGES + "profesor.png");
            parametros.put("IMAGE_ALUMNO", PAQUETE_IMAGES + "alumno.png");
            parametros.put("IMAGE_SALON", PAQUETE_IMAGES + "aula.png");
            parametros.put("IMAGE_CARRERAS", PAQUETE_IMAGES + "diploma.png");
            parametros.put("IMAGE_HORARIO", PAQUETE_IMAGES + "horarios.png");
            parametros.put("IMAGE_CURSOS", PAQUETE_IMAGES + "cursos.png");
            parametros.put("IMAGE_ASIGNACION", PAQUETE_IMAGES + "asignacionAlumnos.png");
            
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(urlFile);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, Conexion.getInstance().getConexion());
            jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setTitle(titulo);
            jasperViewer.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
