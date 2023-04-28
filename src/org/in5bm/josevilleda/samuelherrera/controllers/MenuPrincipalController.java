package org.in5bm.josevilleda.samuelherrera.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.in5bm.josevilleda.samuelherrera.system.Principal;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.in5bm.josevilleda.samuelherrera.db.Conexion;
import org.in5bm.josevilleda.samuelherrera.models.AsignacionesAlumnos;
import org.in5bm.josevilleda.samuelherrera.models.Cursos;
import org.in5bm.josevilleda.samuelherrera.reports.GenerarReporte;
import org.in5bm.josevilleda.samuelherrera.controllers.LoginController;

/**
 *
 * @author Jose Mauricio Villeda Morales & Samuel Herrera
 * @date 26 abr. 2022
 * @time 8:27:51
 *
 * CodigoTecnico: IN5BM
 */
public class MenuPrincipalController implements Initializable {

    Principal menu = new Principal();
    LoginController log = new LoginController();

    private Principal escenarioPrincipal;
    private ObservableList<Cursos> listaObservableCursos;
    private ObservableList<AsignacionesAlumnos> listaObservableAsignaciones;

    @FXML
    private MenuItem miAlumnos;

    @FXML
    private MenuItem miInstructores;

    @FXML
    private MenuItem miSalones;

    @FXML
    private MenuItem miCarrerasTecnicas;

    @FXML
    private MenuItem miHorarios;

    // DECLARACION DE MENU ITEM'S
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getCursos();

        if (log.getRol().equals("Estandar")) {
            miAlumnos.setDisable(true);
            miCarrerasTecnicas.setDisable(true);
            miHorarios.setDisable(true);
            miInstructores.setDisable(true);
            miSalones.setDisable(true);
        }
    }

    private final String PAQUETE_IMAGES = "org/in5bm/josevilleda/samuelherrera/resources/images/";

    // CAMBIAR ESCENAS...
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void clicAlumnos(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaAlumnos();
    }

    @FXML
    public void clicSalones(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaSalones();
    }

    @FXML
    public void clicCarrerasTecnicas(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaCarrerasTecnicas();
    }

    @FXML
    public void clicInstructores(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaInstructores();
    }

    @FXML
    public void clicAsignacionAlumnos(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaAsignacionesAlumnos();
    }

    @FXML
    public void clicCursos(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaCursos();
    }

    @FXML
    public void clicHorarios(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaHorarios();
    }

    // GENERAR REPORTES..
    @FXML
    void clicReporteAlumnos(ActionEvent event) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("nombre", "Jose Villeda");
        GenerarReporte.getInstance().mostrarReporte("ReporteAlumnos.jasper", parametros, "Reporte de Alumnos");
    }

    @FXML
    void clicReporteAsignacion(ActionEvent event) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("nombre", "Jose Villeda");
        GenerarReporte.getInstance().mostrarReporte("ReporteAsignacionesAlumnos.jasper", parametros, "Reporte de Asignacion Alumnos");
    }

    @FXML
    void clicReporteCarreras(ActionEvent event) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("nombre", "Jose Villeda");
        GenerarReporte.getInstance().mostrarReporte("ReporteCarrerasTecnicas.jasper", parametros, "Reporte de Carreras Tecnicas");
    }

    @FXML
    void clicReporteCursos(ActionEvent event) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("nombre", "Jose Villeda");
        GenerarReporte.getInstance().mostrarReporte("ReporteCursos.jasper", parametros, "Reporte de Cursos");
    }

    @FXML
    void clicReporteHorarios(ActionEvent event) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("nombre", "Jose Villeda");
        GenerarReporte.getInstance().mostrarReporte("ReporteHorarios.jasper", parametros, "Reporte de Horarios");
    }

    @FXML
    void clicReporteInstructores(ActionEvent event) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("nombre", "Jose Villeda");
        GenerarReporte.getInstance().mostrarReporte("ReporteInstructores.jasper", parametros, "Reporte de Instructores");
    }

    @FXML
    void clicReporteSalones(ActionEvent event) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("nombre", "Jose Villeda");
        GenerarReporte.getInstance().mostrarReporte("ReporteSalones.jasper", parametros, "Reporte de Salones");
    }

    // Lista de ID'S de cursos.
    private ObservableList getCursos() {
        ArrayList<Cursos> arrayListCursos = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_cursos_read_id()}");

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Cursos curso = new Cursos();
                curso.setId(rs.getInt(1));
                arrayListCursos.add(curso);

            }

            listaObservableCursos = FXCollections.observableArrayList(arrayListCursos);

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Alumnos");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQLState: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return listaObservableCursos;
    }

    public boolean evaluarCursos(int codigo) {
        boolean opcion = false;

        for (int i = 0; i <= listaObservableCursos.size(); i++) {
            if (codigo == listaObservableCursos.get(i).getId()) {
                opcion = true;
                break;
            } else if (codigo != listaObservableCursos.get(i).getId() && (i + 1) >= listaObservableCursos.size()) {
                opcion = false;
                break;
            }
        }
        return opcion;
    }

    @FXML
    public void clicCursoId(ActionEvent event) {

        int opcionCodigo = 0;

        TextInputDialog txtInput = new TextInputDialog();
        txtInput.setHeaderText("Cursos");
        txtInput.setTitle("Reporte Cursos Por ID");
        txtInput.getDialogPane().setContentText("Ingrese el ID: ");
        txtInput.showAndWait();

        if (txtInput.getEditor().getText().isEmpty()) {
            txtInput.close();

        } else {
            opcionCodigo = Integer.parseInt(txtInput.getEditor().getText());
            if (evaluarCursos(opcionCodigo)) {

                Map<String, Object> parametros = new HashMap<>();
                parametros.put("cursoId", opcionCodigo);
                GenerarReporte.getInstance().mostrarReporte("ReporteCursosPorID.jasper", parametros, "Reporte de Cursos por ID");
                txtInput.close();

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Control Academico Monte Carlo");
                alert.setHeaderText(null);
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(PAQUETE_IMAGES + "control.png"));
                alert.setContentText("El ID de curso que acaba de ingresar no existe en el sistema");
                alert.show();
                txtInput.close();
            }

        }
    }

    public ObservableList getAsignacionesAlumnos() {
        ArrayList<AsignacionesAlumnos> arrayListAsignaciones = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_asignaciones_alumnos_read()}");

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                AsignacionesAlumnos asignacion = new AsignacionesAlumnos();
                asignacion.setId(rs.getInt("id"));
                arrayListAsignaciones.add(asignacion);
            }

            listaObservableAsignaciones = FXCollections.observableArrayList(arrayListAsignaciones);

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Alumnos");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQLState: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return listaObservableAsignaciones;
    }

    public boolean evaluarIdAsignacion(int codigo) {
        boolean opcion = false;

        for (int i = 0; i <= listaObservableAsignaciones.size(); i++) {
            if (codigo == listaObservableAsignaciones.get(i).getId()) {
                opcion = true;
                break;
            } else if (codigo != listaObservableAsignaciones.get(i).getId() && (i + 1) >= listaObservableAsignaciones.size()) {
                opcion = false;
                break;
            }
        }
        return opcion;
    }

    @FXML
    public void clicAsignacionesId(ActionEvent event) {

        int opcionCodigo = 0;

        TextInputDialog txtInput = new TextInputDialog();
        txtInput.setHeaderText("Asignaciones Alumnos");
        txtInput.setTitle("Reporte Asignaciones Por ID");
        txtInput.getDialogPane().setContentText("Ingrese el ID: ");

        txtInput.showAndWait();
        if (txtInput.getEditor().getText().isEmpty()) {
            txtInput.close();

        } else {
            opcionCodigo = Integer.parseInt(txtInput.getEditor().getText());
            if (evaluarCursos(opcionCodigo)) {

                Map<String, Object> parametros = new HashMap<>();
                parametros.put("idAsignacion", opcionCodigo);
                GenerarReporte.getInstance().mostrarReporte("ReporteAsignacionesAlumnosPorID.jasper", parametros, "Reporte de Asignacion Alumnos por ID");
                txtInput.close();

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Control Academico Monte Carlo");
                alert.setHeaderText(null);
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(PAQUETE_IMAGES + "control.png"));
                alert.setContentText("El ID de asignacion que acaba de ingresar no existe en el sistema");
                alert.show();
                txtInput.close();
            }

        }

    }

    // Menu Item ?
    @FXML
    void clicAcercaDe(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaAcercaDe();
    }

    @FXML
    void clicCerrar(ActionEvent event) {

        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirm.setTitle("Control Académico Kinal");
        alertConfirm.setHeaderText(null);
        alertConfirm.setContentText("¿Está seguro que desea cerrar la Aplicacion?");

        Stage stage = (Stage) alertConfirm.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(PAQUETE_IMAGES + "control.png"));

        Optional<ButtonType> result = alertConfirm.showAndWait();
        if (result.get().equals(ButtonType.OK)) {
            Platform.exit();
            System.exit(0);

        }
    }

    @FXML
    void clicLogin(ActionEvent event) {
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirm.setTitle("Control Académico Kinal");
        alertConfirm.setHeaderText(null);
        alertConfirm.setContentText("¿Está seguro que desea volver al login?");

        Stage stage = (Stage) alertConfirm.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(PAQUETE_IMAGES + "control.png"));

        Optional<ButtonType> result = alertConfirm.showAndWait();
        if (result.get().equals(ButtonType.OK)) {
            escenarioPrincipal.mostrarEscenaLogin();

        }
    }

}
