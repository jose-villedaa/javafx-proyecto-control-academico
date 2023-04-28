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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.in5bm.josevilleda.samuelherrera.db.Conexion;
import org.in5bm.josevilleda.samuelherrera.models.Salones;
import org.in5bm.josevilleda.samuelherrera.reports.GenerarReporte;
import org.in5bm.josevilleda.samuelherrera.system.Principal;

/**
 *
 * @author Jose Mauricio Villeda Morales & Samuel Herrera
 * @date 26 abr. 2022
 * @time 8:27:51
 *
 * Codigo Tecnico: IN5BM
 */
public class SalonesController implements Initializable {

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private Operacion operacion = Operacion.NINGUNO;

    private Principal escenarioPrincipal;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnReporte;

    @FXML
    private TextField txtCodigoSalon;

    @FXML
    private TextField txtDescripcionSalon;
    
    @FXML
    private TextField txtContador;


    @FXML
    private TextField txtEdificio;

    @FXML
    private TableView tblSalones;

    @FXML
    private TableColumn colCodigoDelSalon;

    @FXML
    private TableColumn colDescripcion;

    @FXML
    private TableColumn colCapacidadMaxima;

    @FXML
    private TableColumn colEdificio;

    @FXML
    private TableColumn colNivel;

    @FXML
    private ImageView imgModificar;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private ImageView imgReporte;

    @FXML
    private ImageView imgNuevo;

    @FXML
    private Spinner<Integer> spnNivel;
    
    @FXML
    private Spinner<Integer> spnCapacidadMaxima;

    private final String PAQUETE_IMAGES = "org/in5bm/josevilleda/samuelherrera/resources/images/";

    private ObservableList<Salones> listaSalones;

    private SpinnerValueFactory<Integer> valueFactoryNivel;

    private SpinnerValueFactory<Integer> valueFactoryCapacidad;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        valueFactoryNivel = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 4, 0);
        spnNivel.setValueFactory(valueFactoryNivel);

        valueFactoryCapacidad = new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 50, 0);
        spnCapacidadMaxima.setValueFactory(valueFactoryCapacidad);

        cargarDatos();
        
        ContarRegistros();
    }

    public void cargarDatos() {
        tblSalones.setItems(getSalones());
        colCodigoDelSalon.setCellValueFactory(new PropertyValueFactory<Salones, String>("codigoSalon"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Salones, String>("descripcion"));
        colCapacidadMaxima.setCellValueFactory(new PropertyValueFactory<Salones, Integer>("capacidadMaxima"));
        colEdificio.setCellValueFactory(new PropertyValueFactory<Salones, String>("edificio"));
        colNivel.setCellValueFactory(new PropertyValueFactory<Salones, Integer>("nivel"));
    }
    
    private void ContarRegistros(){
        txtContador.setText(String.valueOf(listaSalones.size()));
    }

    public boolean existeElementoSeleccionado() {
        return (tblSalones.getSelectionModel().getSelectedItem() != null);

    }

    @FXML
    public void seleccionarElemento() {

        if (existeElementoSeleccionado()) {

            txtCodigoSalon.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getCodigoSalon());

            txtDescripcionSalon.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getDescripcion());

            spnCapacidadMaxima.getValueFactory().setValue(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getCapacidadMaxima());

            txtEdificio.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getEdificio());

            spnNivel.getValueFactory().setValue(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getNivel());

        }
    }

    // Actualizar Salon
    public boolean actualizarSalon() {
        Salones salon = new Salones();

        salon.setCodigoSalon(txtCodigoSalon.getText());
        salon.setDescripcion(txtDescripcionSalon.getText());
        salon.setCapacidadMaxima((int) spnCapacidadMaxima.getValue());
        salon.setEdificio(txtEdificio.getText());
        salon.setNivel((int) spnNivel.getValue());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_salones_update(?, ?, ?, ?, ?)}");

            pstmt.setString(1, salon.getCodigoSalon());
            pstmt.setString(2, salon.getDescripcion());
            pstmt.setInt(3, salon.getCapacidadMaxima());
            pstmt.setString(4, salon.getEdificio());
            pstmt.setInt(5, salon.getNivel());

            System.out.println(pstmt.toString());

            pstmt.execute();

            listaSalones.add(salon);

            return true;

        } catch (SQLException e) {
            System.err.println("\nSE PRODUJO UN ERROR AL INTENTAR MODIFICAR EL SIGUIENTE REGISTRO: " + salon.toString());
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

    //Eliminar Salon
    public boolean eliminarSalon() {
        if (existeElementoSeleccionado()) {
            Salones salon = (Salones) tblSalones.getSelectionModel().getSelectedItem();
            System.out.println(salon.toString());

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_salones_delete(?)}");
                pstmt.setString(1, salon.getCodigoSalon());
                System.out.println(pstmt);

                pstmt.execute();
                cargarDatos();

                return true;

            } catch (SQLException e) {
                System.err.println("\nSE produjo un error al intentar eliminar el alumno con el siguiente registro" + salon.toString());
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    //Agregar Salon
    private boolean agregarSalon() {
        Salones salon = new Salones();

        if (txtCodigoSalon.getText().isEmpty()) {

            Alert conf = new Alert(Alert.AlertType.WARNING);
            conf.setTitle("Control Academico Monte Carlo");
            conf.setContentText(" Ingrese el codigo del salon! ");
            conf.setHeaderText(null);
            Stage stageAlertConf = (Stage) conf.getDialogPane().getScene().getWindow();
            stageAlertConf.getIcons().add(new Image(PAQUETE_IMAGES + "control.png"));
            conf.show();

        } else if (spnCapacidadMaxima.getValue().equals(0)) {

            Alert conf = new Alert(Alert.AlertType.WARNING);
            conf.setTitle("Control Academico Monte Carlo");
            conf.setContentText(" Ingrese la capacidad maxima del salon! ");
            conf.setHeaderText(null);
            Stage stageAlertConf = (Stage) conf.getDialogPane().getScene().getWindow();
            stageAlertConf.getIcons().add(new Image(PAQUETE_IMAGES + "control.png"));
            conf.show();

        } else if (txtEdificio.getText().isEmpty()) {

            Alert conf = new Alert(Alert.AlertType.WARNING);
            conf.setTitle("Control Academico Monte Carlo");
            conf.setContentText(" Ingrese el Edificio donde se encuentra el salon! ");
            conf.setHeaderText(null);
            Stage stageAlertConf = (Stage) conf.getDialogPane().getScene().getWindow();
            stageAlertConf.getIcons().add(new Image(PAQUETE_IMAGES + "control.png"));
            conf.show();

        } else if (spnNivel.getValue().equals(0)) {

            Alert conf = new Alert(Alert.AlertType.WARNING);
            conf.setTitle("Control Academico Monte Carlo");
            conf.setContentText(" Ingrese el nivel del edificio donde se encuentra el salon ");
            conf.setHeaderText(null);
            Stage stageAlertConf = (Stage) conf.getDialogPane().getScene().getWindow();
            stageAlertConf.getIcons().add(new Image(PAQUETE_IMAGES + "control.png"));
            conf.show();
        } else {

            salon.setCodigoSalon(txtCodigoSalon.getText());
            salon.setDescripcion(txtDescripcionSalon.getText());
            salon.setCapacidadMaxima((int) spnCapacidadMaxima.getValue());
            salon.setEdificio(txtEdificio.getText());
            salon.setNivel((int) spnNivel.getValue());

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion()
                        .prepareCall("{CALL sp_salones_create(?, ?, ?, ?, ?)}");
                pstmt.setString(1, salon.getCodigoSalon());
                pstmt.setString(2, salon.getDescripcion());
                pstmt.setInt(3, salon.getCapacidadMaxima());
                pstmt.setString(4, salon.getEdificio());
                pstmt.setInt(5, salon.getNivel());

                System.out.println(pstmt.toString());

                pstmt.execute();

                listaSalones.add(salon);
                cargarDatos();

                return true;

            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("\nSE PRODUJO UN ERROR AL INTENTAR"
                        + " INGRESAR EL SIGUIENTE REGISTRO: " + salon.toString());
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

    public ObservableList getSalones() {

        ArrayList<Salones> lista = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_salones_read}");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Salones salon = new Salones();
                salon.setCodigoSalon(rs.getString(1));
                salon.setDescripcion(rs.getString(2));
                salon.setCapacidadMaxima(rs.getInt(3));
                salon.setEdificio(rs.getString(4));
                salon.setNivel(rs.getInt(5));

                lista.add(salon);

                System.out.println("\n" + salon.toString());
            }

            listaSalones = FXCollections.observableArrayList(lista);
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
        return listaSalones;
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    private void deshabilitarCampos() {

        txtCodigoSalon.setEditable(false);
        txtDescripcionSalon.setEditable(false);
        spnCapacidadMaxima.setEditable(false);
        txtEdificio.setEditable(false);
        spnNivel.setEditable(false);

        txtCodigoSalon.setDisable(true);
        txtDescripcionSalon.setDisable(true);
        spnCapacidadMaxima.setDisable(true);
        txtEdificio.setDisable(true);
        spnNivel.setDisable(true);
    }

    private void habilitarCampos() {

        txtCodigoSalon.setEditable(false);
        txtDescripcionSalon.setEditable(true);
        spnCapacidadMaxima.setEditable(true);
        txtEdificio.setEditable(true);
        spnNivel.setEditable(true);

        txtCodigoSalon.setDisable(true);
        txtDescripcionSalon.setDisable(false);
        spnCapacidadMaxima.setDisable(false);
        txtEdificio.setDisable(false);
        spnNivel.setDisable(false);

    }

    private void limpiarCampos() {
        txtCodigoSalon.setText("");
        txtDescripcionSalon.setText("");
        spnCapacidadMaxima.getValueFactory().setValue(0);
        txtEdificio.setText("");
        spnNivel.getValueFactory().setValue(10);

    }

    @FXML
    private void clicNuevo() {
        switch (operacion) {
            case NINGUNO: //Primer Clic Nuevo

                habilitarCampos();

                tblSalones.getSelectionModel().clearSelection();

                tblSalones.setDisable(true);

                txtCodigoSalon.setEditable(true);
                txtCodigoSalon.setDisable(false);

                limpiarCampos();

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
                if (agregarSalon()) {

                    cargarDatos();
                    ContarRegistros();
                    limpiarCampos();
                    deshabilitarCampos();

                    tblSalones.getSelectionModel().clearSelection();

                    btnNuevo.setText("Nuevo");
                    imgNuevo.setImage(new Image(PAQUETE_IMAGES + "nuevo.png"));

                    btnModificar.setText("Modificar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGES + "modificar.png"));

                    btnEliminar.setDisable(false);
                    btnEliminar.setVisible(true);

                    btnReporte.setDisable(false);
                    btnReporte.setVisible(true);

                    tblSalones.setDisable(false);

                    operacion = Operacion.NINGUNO;

                }
                break;
        }
    }

    // CLIC ELIMINAR
    @FXML
    private void clicEliminar() {
        switch (operacion) {
            case ACTUALIZAR: //Cancelar una modificación.
                btnNuevo.setDisable(false);
                btnNuevo.setVisible(true);
                imgNuevo.setVisible(true);

                btnModificar.setText("Modificar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "modificar.png"));

                btnEliminar.setText("Eliminar");
                imgEliminar.setImage(new Image(PAQUETE_IMAGES + "eliminar.png"));

                btnReporte.setDisable(false);
                btnReporte.setVisible(true);
                imgReporte.setVisible(true);

                limpiarCampos();
                deshabilitarCampos();

                tblSalones.getSelectionModel().clearSelection();

                operacion = Operacion.NINGUNO;
                break;
            case NINGUNO:
                if (existeElementoSeleccionado()) {

                    Alert alertNew = new Alert(Alert.AlertType.CONFIRMATION);
                    alertNew.setHeaderText(null);
                    alertNew.setTitle("Control Academico Monte Carlo");
                    Stage stage = (Stage) alertNew.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGES + "control.png"));
                    alertNew.setContentText("¿Desea eliminar el registro?");
                    Optional<ButtonType> result = alertNew.showAndWait();

                    if (result.get().equals(ButtonType.OK)) {
                        limpiarCampos();
                        if (eliminarSalon()) {

                            listaSalones.remove(tblSalones.getSelectionModel().getFocusedIndex());
                            cargarDatos();
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText(null);
                            alert.setTitle("Control Academico Monte Carlo");
                            alert.setContentText("El registro se ha eliminado exitosamente!");
                            Image icon = new Image(PAQUETE_IMAGES + "control.png");
                            Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
                            stageAlert.getIcons().add(icon);
                            alert.show();
                            ContarRegistros();

                        } else if (result.get().equals(ButtonType.CANCEL)) {

                            tblSalones.getSelectionModel().clearSelection();

                            limpiarCampos();
                            deshabilitarCampos();
                        }
                    }

                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setTitle("KINAL \"CONTROL ACÁDEMICO\"");
                    alert.setContentText("Antes de seguir selecciona un registro");
                    Image icon = new Image(PAQUETE_IMAGES + "control.png");
                    Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
                    stageAlert.getIcons().add(icon);
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

                tblSalones.setDisable(false);

                limpiarCampos();

                operacion = Operacion.NINGUNO;

                break;

            case ACTUALIZAR:

                if (existeElementoSeleccionado()) {
                    if (actualizarSalon()) {
                        cargarDatos();
                        limpiarCampos();
                        deshabilitarCampos();

                        tblSalones.setDisable(false);
                        tblSalones.getSelectionModel().clearSelection();

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
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("nombre", "Jose Villeda");
        GenerarReporte.getInstance().mostrarReporte("ReporteSalones.jasper", parametros, "Reporte de Salones");
    }

    @FXML
    private void clicMenuPrincipal(MouseEvent event) {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }
}
