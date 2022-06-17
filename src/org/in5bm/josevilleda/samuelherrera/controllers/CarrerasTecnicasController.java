package org.in5bm.josevilleda.samuelherrera.controllers;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.in5bm.josevilleda.samuelherrera.db.Conexion;
import org.in5bm.josevilleda.samuelherrera.models.CarrerasTecnicas;
import org.in5bm.josevilleda.samuelherrera.system.Principal;

/**
 *
 * @author Jose Mauricio Villeda Morales & Samuel Herrera
 * @date 26 abr. 2022
 * @time 8:27:51
 *
 * Codigo Tecnico: IN5BM
 */
public class CarrerasTecnicasController implements Initializable {

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private Operacion operacion = Operacion.NINGUNO;

    private Principal escenarioPrincipal;

    @FXML
    private JFXTextField txtCarrera;

    @FXML
    private JFXTextField txtCodigoTecnico;

    @FXML
    private JFXTextField txtGrado;

    @FXML
    private JFXTextField txtJornada;

    @FXML
    private JFXTextField txtSeccion;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnReporte;

    @FXML
    private TableView tblCarrerasTecnicas;

    @FXML
    private TableColumn colCodigoTecnico;

    @FXML
    private TableColumn colCarrera;

    @FXML
    private TableColumn colGrado;

    @FXML
    private TableColumn colSeccion;

    @FXML
    private TableColumn colJornada;

    @FXML
    private ImageView imgModificar;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private ImageView imgReporte;

    @FXML
    private ImageView imgNuevo;

    private final String PAQUETE_IMAGES = "org/in5bm/josevilleda/samuelherrera/resources/images/";

    private ObservableList<CarrerasTecnicas> listaCarrerasTecnicas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public void cargarDatos() {
        tblCarrerasTecnicas.setItems(getCarrerasTecnicas());
        colCodigoTecnico.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("codigoTecnico"));
        colCarrera.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("carrera"));
        colGrado.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("grado"));
        colSeccion.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, Character>("seccion"));
        colJornada.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("jornada"));
    }

    public boolean existeElementoSeleccionado() {
        return (tblCarrerasTecnicas.getSelectionModel().getSelectedItem() != null);

    }

    @FXML
    public void seleccionarElemento() {

        if (existeElementoSeleccionado()) {

            txtCodigoTecnico.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getCodigoTecnico());

            txtCarrera.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getCarrera());

            txtGrado.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getGrado());

            txtSeccion.setText(String.valueOf(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getSeccion()));

            txtJornada.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getJornada());
        }
    }

    //Agregar Carreras Tecnicas
    private boolean agregarCarrerasTecnicas() {
        CarrerasTecnicas carrerasTecnicas = new CarrerasTecnicas();

        if (txtCodigoTecnico.getText().isEmpty()) {

            Alert conf = new Alert(Alert.AlertType.WARNING);
            conf.setTitle("Control Academico Monte Carlo");
            conf.setContentText(" Ingrese el codigo de la carrera tecnica! ");
            conf.setHeaderText(null);
            Stage stageAlertConf = (Stage) conf.getDialogPane().getScene().getWindow();
            stageAlertConf.getIcons().add(new Image(PAQUETE_IMAGES + "control.png"));
            conf.show();

        } else if (txtCarrera.getText().isEmpty()) {

            Alert conf = new Alert(Alert.AlertType.WARNING);
            conf.setTitle("Control Academico Monte Carlo");
            conf.setContentText(" Ingrese el nombre de la carrera! ");
            conf.setHeaderText(null);
            Stage stageAlertConf = (Stage) conf.getDialogPane().getScene().getWindow();
            stageAlertConf.getIcons().add(new Image(PAQUETE_IMAGES + "control.png"));
            conf.show();

        } else if (txtGrado.getText().isEmpty()) {

            Alert conf = new Alert(Alert.AlertType.WARNING);
            conf.setTitle("Control Academico Monte Carlo");
            conf.setContentText(" Ingrese el Grado al que corresponde la carrera! ");
            conf.setHeaderText(null);
            Stage stageAlertConf = (Stage) conf.getDialogPane().getScene().getWindow();
            stageAlertConf.getIcons().add(new Image(PAQUETE_IMAGES + "control.png"));
            conf.show();

        } else if (txtSeccion.getText().isEmpty()) {

            Alert conf = new Alert(Alert.AlertType.WARNING);
            conf.setTitle("Control Academico Monte Carlo");
            conf.setContentText(" Ingrese la seccion del grado! ");
            conf.setHeaderText(null);
            Stage stageAlertConf = (Stage) conf.getDialogPane().getScene().getWindow();
            stageAlertConf.getIcons().add(new Image(PAQUETE_IMAGES + "control.png"));
            conf.show();

        } else if (txtJornada.getText().isEmpty()) {
            Alert conf = new Alert(Alert.AlertType.WARNING);
            conf.setTitle("Control Academico Monte Carlo");
            conf.setContentText(" Ingrese la Jornada! ");
            conf.setHeaderText(null);
            Stage stageAlertConf = (Stage) conf.getDialogPane().getScene().getWindow();
            stageAlertConf.getIcons().add(new Image(PAQUETE_IMAGES + "control.png"));
            conf.show();
        } else {
            carrerasTecnicas.setCodigoTecnico(txtCodigoTecnico.getText());
            carrerasTecnicas.setCarrera(txtCarrera.getText());
            carrerasTecnicas.setGrado(txtGrado.getText());
            carrerasTecnicas.setSeccion(txtSeccion.getText().charAt(0));
            carrerasTecnicas.setJornada(txtJornada.getText());

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion()
                        .prepareCall("{CALL sp_carreras_tecnicas_create(?, ?, ?, ?, ?)}");
                pstmt.setString(1, carrerasTecnicas.getCodigoTecnico());
                pstmt.setString(2, carrerasTecnicas.getCarrera());
                pstmt.setString(3, carrerasTecnicas.getGrado());
                pstmt.setString(4, String.valueOf(carrerasTecnicas.getSeccion()));
                pstmt.setString(5, carrerasTecnicas.getJornada());

                System.out.println(pstmt.toString());

                pstmt.execute();

                listaCarrerasTecnicas.add(carrerasTecnicas);

                return true;

            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("\nSE PRODUJO UN ERROR AL INTENTAR"
                        + " INGRESAR EL SIGUIENTE REGISTRO: " + carrerasTecnicas.toString());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    //Actualizar Carreras Tecnicas
    public boolean actualizarCarrerasTecnicas() {
        CarrerasTecnicas carrerasTecnicas = new CarrerasTecnicas();

        carrerasTecnicas.setCodigoTecnico(txtCodigoTecnico.getText());
        carrerasTecnicas.setCarrera(txtCarrera.getText());
        carrerasTecnicas.setGrado(txtGrado.getText());
        carrerasTecnicas.setSeccion(txtSeccion.getText().charAt(0));
        carrerasTecnicas.setJornada(txtJornada.getText());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_carreras_tecnicas_update(?, ?, ?, ?, ?)}");

            pstmt.setString(1, carrerasTecnicas.getCodigoTecnico());
            pstmt.setString(2, carrerasTecnicas.getCarrera());
            pstmt.setString(3, carrerasTecnicas.getGrado());
            pstmt.setString(4, String.valueOf(carrerasTecnicas.getSeccion()));
            pstmt.setString(5, carrerasTecnicas.getJornada());

            System.out.println(pstmt.toString());

            pstmt.execute();

            listaCarrerasTecnicas.add(carrerasTecnicas);

            return true;

        } catch (SQLException e) {
            System.err.println("\nSE PRODUJO UN ERROR AL INTENTAR MODIFICAR EL SIGUIENTE REGISTRO: " + carrerasTecnicas.toString());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    //Eliminar Carreras tecnicas.
    public boolean eliminarCarrerasTecnicas() {
        if (existeElementoSeleccionado()) {
            CarrerasTecnicas carrerasTecnicas = (CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem();
            System.out.println(carrerasTecnicas.toString());

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_carreras_tecnicas_delete(?)}");
                pstmt.setString(1, carrerasTecnicas.getCodigoTecnico());
                System.out.println(pstmt);

                pstmt.execute();

                return true;

            } catch (SQLException e) {
                System.err.println("\nSE produjo un error al intentar eliminar el alumno con el siguiente registro" + carrerasTecnicas.toString());
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public ObservableList getCarrerasTecnicas() {

        ArrayList<CarrerasTecnicas> lista = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_carreras_tecnicas_read()}");
            rs = pstmt.executeQuery();

            while (rs.next()) {

                CarrerasTecnicas carrerasTec = new CarrerasTecnicas();
                carrerasTec.setCodigoTecnico(rs.getString(1));
                carrerasTec.setCarrera(rs.getString(2));
                carrerasTec.setGrado(rs.getString(3));
                carrerasTec.setSeccion(rs.getString(4).charAt(0));
                carrerasTec.setJornada(rs.getString(5));

                lista.add(carrerasTec);

                System.out.println("\n" + carrerasTec.toString());
            }

            listaCarrerasTecnicas = FXCollections.observableArrayList(lista);

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar consultar la lista de alumnos");
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

        return listaCarrerasTecnicas;
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    private void deshabilitarCampos() {

        txtCodigoTecnico.setEditable(false);
        txtCarrera.setEditable(false);
        txtGrado.setEditable(false);
        txtSeccion.setEditable(false);
        txtJornada.setEditable(false);

        txtCodigoTecnico.setDisable(true);
        txtCarrera.setDisable(true);
        txtGrado.setDisable(true);
        txtSeccion.setDisable(true);
        txtJornada.setDisable(true);

    }

    private void habilitarCampos() {

        txtCodigoTecnico.setEditable(false);
        txtCarrera.setEditable(true);
        txtGrado.setEditable(true);
        txtSeccion.setEditable(true);
        txtJornada.setEditable(true);

        txtCodigoTecnico.setDisable(true);
        txtCarrera.setDisable(false);
        txtGrado.setDisable(false);
        txtSeccion.setDisable(false);
        txtJornada.setDisable(false);

    }

    private void limpiarCampos() {
        txtCodigoTecnico.setText("");
        txtCarrera.setText("");
        txtGrado.setText("");
        txtSeccion.setText("");
        txtJornada.setText("");

    }

    // CLIC NUEVO
    @FXML
    private void clicNuevo() {
        switch (operacion) {
            case NINGUNO: //Primer Clic Nuevo

                habilitarCampos();

                tblCarrerasTecnicas.setDisable(true);

                txtCodigoTecnico.setEditable(true);
                txtCodigoTecnico.setDisable(false);

                limpiarCampos();

                tblCarrerasTecnicas.getSelectionModel().clearSelection();

                btnNuevo.setText("Guardar");
                imgNuevo.setImage(new Image(PAQUETE_IMAGES + "save.png"));

                btnModificar.setText("Cancelar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "cancel.png"));

                btnEliminar.setDisable(true);
                btnEliminar.setVisible(false);

                btnReporte.setDisable(true);
                btnReporte.setVisible(false);

                operacion = Operacion.GUARDAR;

                break;

            case GUARDAR:
                if (agregarCarrerasTecnicas()) {

                    cargarDatos();
                    limpiarCampos();

                    deshabilitarCampos();

                    tblCarrerasTecnicas.getSelectionModel().clearSelection();

                    btnNuevo.setText("Nuevo");
                    imgNuevo.setImage(new Image(PAQUETE_IMAGES + "nuevo.png"));

                    btnModificar.setText("Modificar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGES + "modificar.png"));

                    btnEliminar.setDisable(false);
                    btnEliminar.setVisible(true);

                    btnReporte.setDisable(false);
                    btnReporte.setVisible(true);

                    tblCarrerasTecnicas.setDisable(false);
                    

                    operacion = Operacion.NINGUNO;

                }
                break;
        }
    }

    // CLIC ELIMINAR
    @FXML
    private void clicEliminar() {

        switch (operacion) {
            case ACTUALIZAR:

                btnNuevo.setDisable(false);
                btnNuevo.setVisible(true);

                btnModificar.setText("Modificar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "modificar.png"));

                btnEliminar.setText("Eliminar");
                imgEliminar.setImage(new Image(PAQUETE_IMAGES + "eliminar.png"));

                btnReporte.setDisable(false);
                btnReporte.setVisible(true);

                limpiarCampos();
                deshabilitarCampos();

                tblCarrerasTecnicas.getSelectionModel().clearSelection();

                operacion = Operacion.NINGUNO;

                break;

            case NINGUNO: //Eliminacion

                //If Elemnto Seleccionado
                if (existeElementoSeleccionado()) {

                    Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
                    conf.setTitle("Control Academico Monte Carlo");
                    conf.setContentText(" Esta seguro que desea eliminar este registro? ");
                    conf.setHeaderText(null);
                    Stage stageAlertConf = (Stage) conf.getDialogPane().getScene().getWindow();
                    stageAlertConf.getIcons().add(new Image(PAQUETE_IMAGES + "control.png"));

                    Optional<ButtonType> result = conf.showAndWait();

                    // If Confirmacion
                    if (result.get().equals(ButtonType.OK)) {
                        eliminarCarrerasTecnicas();
                        limpiarCampos();

                        // If Eliminar Alumno
                        if (eliminarCarrerasTecnicas()) {

                            listaCarrerasTecnicas.remove(tblCarrerasTecnicas.getSelectionModel().getFocusedIndex());
                            cargarDatos();

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Control Academico Monte Carlo");
                            alert.setContentText("El registro se ha eliminado correctamente!");
                            alert.setHeaderText(null);

                            Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();

                            stageAlert.getIcons().add(new Image(PAQUETE_IMAGES + "control.png"));

                            alert.show();
                        }

                    } else if (result.get().equals(ButtonType.CANCEL)) {

                        tblCarrerasTecnicas.getSelectionModel().clearSelection();

                        limpiarCampos();
                        deshabilitarCampos();
                    }

                    // Else (Elemento Seleccionado)
                } else {

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control Academico Monte Carlo");
                    alert.setContentText("Antes de continuar selecciona un registro");
                    alert.setHeaderText(null);

                    Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();

                    stageAlert.getIcons().add(new Image(PAQUETE_IMAGES + "control.png"));

                    alert.show();
                }

                break;
        }
    }

    // Clic Modidicar
    @FXML
    private void clicModificar() {

        switch (operacion) {
            case NINGUNO:

                if (existeElementoSeleccionado()) {

                    habilitarCampos();

                    btnNuevo.setDisable(true);
                    btnNuevo.setVisible(false);

                    btnModificar.setText("Guardar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGES + "save.png"));

                    btnEliminar.setText("Cancelar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGES + "cancel.png"));

                    btnReporte.setDisable(true);
                    btnReporte.setVisible(false);

                    operacion = Operacion.ACTUALIZAR;
                } else {

                    // Alerta Registro
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control Academico Monte Carlo");
                    alert.setContentText("Antes de continuar selecciona un registro");
                    alert.setHeaderText(null);

                    Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();

                    stageAlert.getIcons().add(new Image(PAQUETE_IMAGES + "control.png"));

                    alert.show();

                }

                break;

            case GUARDAR:

                btnNuevo.setText("Nuevo");
                imgNuevo.setImage(new Image(PAQUETE_IMAGES + "nuevo.png"));

                btnModificar.setText("Modificar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "modificar.png"));

                btnEliminar.setDisable(false);
                btnEliminar.setVisible(true);

                btnReporte.setDisable(false);
                btnReporte.setVisible(true);

                tblCarrerasTecnicas.setDisable(false);

                operacion = Operacion.NINGUNO;

                break;

            case ACTUALIZAR:

                if (existeElementoSeleccionado()) {
                    if (actualizarCarrerasTecnicas()) {
                        cargarDatos();
                        limpiarCampos();
                        deshabilitarCampos();

                        tblCarrerasTecnicas.setDisable(false);
                        tblCarrerasTecnicas.getSelectionModel().clearSelection();

                        btnNuevo.setText("Nuevo");
                        btnNuevo.setDisable(false);
                        btnNuevo.setVisible(true);

                        btnModificar.setText("Modificar");
                        imgModificar.setImage(new Image(PAQUETE_IMAGES + "modificar.png"));

                        btnEliminar.setDisable(false);
                        btnEliminar.setVisible(true);
                        btnEliminar.setText("Eliminar");
                        imgEliminar.setImage(new Image(PAQUETE_IMAGES + "eliminar.png"));

                        btnReporte.setDisable(false);
                        btnReporte.setVisible(true);

                        operacion = Operacion.NINGUNO;

                    }
                }

                break;
        }
    }

    @FXML
    private void clicReporte() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AVISO");
        alert.setContentText("Esta Funcion solo esta disponible en la version PRO");
        alert.setHeaderText(null);

        Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();

        stageAlert.getIcons().add(new Image(PAQUETE_IMAGES + "control.png"));

        alert.show();
    }

    @FXML
    private void clicMenuPrincipal(MouseEvent event) {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }

}
