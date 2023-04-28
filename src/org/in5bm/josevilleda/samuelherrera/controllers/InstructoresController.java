package org.in5bm.josevilleda.samuelherrera.controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.sql.CallableStatement;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import org.in5bm.josevilleda.samuelherrera.system.Principal;
import org.in5bm.josevilleda.samuelherrera.db.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.in5bm.josevilleda.samuelherrera.models.Instructores;
import org.in5bm.josevilleda.samuelherrera.reports.GenerarReporte;

/**
 *
 * @author Jose Mauricio Villeda Morales & Samuel Herrera
 * @date 26 abr. 2022
 * @time 8:27:51
 *
 * Codigo Tecnico: IN5BM
 */
public class InstructoresController implements Initializable {

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private Operacion operacion = Operacion.NINGUNO;

    private Principal escenarioPrincipal;

    @FXML
    private Button btnNuevo;

    @FXML
    private ImageView imgNuevo;

    @FXML
    private Button btnModificar;

    @FXML
    private ImageView imgModificar;

    @FXML
    private Button btnEliminar;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private Button btnReporte;

    @FXML
    private ImageView imgReporte;

    @FXML
    private JFXDatePicker dpkFecha;

    @FXML
    private TableView<?> tblInstructores;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colNombre1;

    @FXML
    private TableColumn colNombre2;

    @FXML
    private TableColumn colNombre3;

    @FXML
    private TableColumn colApellido1;

    @FXML
    private TableColumn colApellido2;

    @FXML
    private TableColumn colDireccion;

    @FXML
    private TableColumn colEmail;

    @FXML
    private TableColumn colTelefono;

    @FXML
    private TableColumn colFecha;
    
    @FXML
    private TextField txtContador;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtNombre1;

    @FXML
    private JFXTextField txtNombre2;

    @FXML
    private JFXTextField txtNombre3;

    @FXML
    private JFXTextField txtApellido1;

    @FXML
    private JFXTextField txtApellido2;

    @FXML
    private JFXTextField txtDireccion;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtTelefono;

    private final String PAQUETE_IMAGES = "org/in5bm/josevilleda/samuelherrera/resources/images/";

    private ObservableList<Instructores> listaInstructores;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        ContarRegistros();
    }
    
    private void ContarRegistros(){
        txtContador.setText(String.valueOf(listaInstructores.size()));
    }

    public void cargarDatos() {
        tblInstructores.setItems(getInstructores());
        colId.setCellValueFactory(new PropertyValueFactory<Instructores, Integer>("id"));
        colNombre1.setCellValueFactory(new PropertyValueFactory<Instructores, String>("nombre1"));
        colNombre2.setCellValueFactory(new PropertyValueFactory<Instructores, String>("nombre2"));
        colNombre3.setCellValueFactory(new PropertyValueFactory<Instructores, String>("nombre3"));
        colApellido1.setCellValueFactory(new PropertyValueFactory<Instructores, String>("apellido1"));
        colApellido2.setCellValueFactory(new PropertyValueFactory<Instructores, String>("apellido2"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Instructores, String>("direccion"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Instructores, String>("email"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Instructores, String>("telefono"));
        colFecha.setCellValueFactory(new PropertyValueFactory<Instructores, LocalDate>("fechaN"));
    }

    public boolean existeElementoSeleccionado() {
        return (tblInstructores.getSelectionModel().getSelectedItem() != null);

    }

    @FXML
    public void seleccionarElemento() {

        if (existeElementoSeleccionado()) {

            String id = String.valueOf(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getId());

            txtId.setText(id);

            txtNombre1.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getNombre1());

            txtNombre2.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getNombre2());

            txtNombre3.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getNombre3());

            txtApellido1.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getApellido1());

            txtApellido2.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getApellido2());

            txtDireccion.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getDireccion());

            txtEmail.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getEmail());

            txtTelefono.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getTelefono());
           
            dpkFecha.setValue(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getFechaN());

        }
    }

    //Actualizar Instructor
    private boolean actualizarInstructor() {
        Instructores instructor = new Instructores();

        instructor.setId(Integer.parseInt(txtId.getText()));
        instructor.setNombre1(txtNombre1.getText());
        instructor.setNombre2(txtNombre2.getText());
        instructor.setNombre3(txtNombre3.getText());
        instructor.setApellido1(txtApellido1.getText());
        instructor.setApellido2(txtApellido2.getText());
        instructor.setDireccion(txtDireccion.getText());
        instructor.setEmail(txtEmail.getText());
        instructor.setTelefono(txtTelefono.getText());
        instructor.setFechaN(dpkFecha.getValue());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_instructores_update(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");

            pstmt.setInt(1, instructor.getId());
            pstmt.setString(2, instructor.getNombre1());
            pstmt.setString(3, instructor.getNombre2());
            pstmt.setString(4, instructor.getNombre3());
            pstmt.setString(5, instructor.getApellido1());
            pstmt.setString(6, instructor.getApellido2());
            pstmt.setString(7, instructor.getDireccion());
            pstmt.setString(8, instructor.getEmail());
            pstmt.setString(9, instructor.getTelefono());
            pstmt.setObject(10, instructor.getFechaN());

            System.out.println(pstmt.toString());

            pstmt.execute();

            listaInstructores.add(instructor);

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("\nSE PRODUJO UN ERROR AL INTENTAR"
                    + " INGRESAR EL SIGUIENTE REGISTRO: " + instructor.toString());
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

    //Eliminar Instructor
    public boolean eliminarInstructor() {
        if (existeElementoSeleccionado()) {
            Instructores instructor = (Instructores) tblInstructores.getSelectionModel().getSelectedItem();
            System.out.println(instructor.toString());

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_instructores_delete(?)}");
                pstmt.setInt(1, instructor.getId());
                System.out.println(pstmt);

                pstmt.execute();

                return true;

            } catch (SQLException e) {
                System.err.println("\nSE produjo un error al intentar eliminar el alumno con el siguiente registro" + instructor.toString());
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    //Agregar Instructor
    private boolean agregarInstructor() {
        Instructores instructor = new Instructores();

        if (txtNombre1.getText().isEmpty()) {

            Alert conf = new Alert(Alert.AlertType.WARNING);
            conf.setTitle("Control Academico Monte Carlo");
            conf.setContentText(" Ingrese el Nombre del instructor! ");
            conf.setHeaderText(null);
            Stage stageAlertConf = (Stage) conf.getDialogPane().getScene().getWindow();
            stageAlertConf.getIcons().add(new Image(PAQUETE_IMAGES + "control.png"));
            conf.show();

        } else if (txtApellido1.getText().isEmpty()) {

            Alert conf = new Alert(Alert.AlertType.WARNING);
            conf.setTitle("Control Academico Monte Carlo");
            conf.setContentText(" Ingrese el Apellido del Instructor! ");
            conf.setHeaderText(null);
            Stage stageAlertConf = (Stage) conf.getDialogPane().getScene().getWindow();
            stageAlertConf.getIcons().add(new Image(PAQUETE_IMAGES + "control.png"));
            conf.show();
        } else if (txtEmail.getText().isEmpty()) {

            Alert conf = new Alert(Alert.AlertType.WARNING);
            conf.setTitle("Control Academico Monte Carlo");
            conf.setContentText(" Ingrese el Email del Instructor ");
            conf.setHeaderText(null);
            Stage stageAlertConf = (Stage) conf.getDialogPane().getScene().getWindow();
            stageAlertConf.getIcons().add(new Image(PAQUETE_IMAGES + "control.png"));
            conf.show();

        } else if (txtTelefono.getText().isEmpty()) {

            Alert conf = new Alert(Alert.AlertType.WARNING);
            conf.setTitle("Control Academico Monte Carlo");
            conf.setContentText(" Ingrese el telefono del Instructor ");
            conf.setHeaderText(null);
            Stage stageAlertConf = (Stage) conf.getDialogPane().getScene().getWindow();
            stageAlertConf.getIcons().add(new Image(PAQUETE_IMAGES + "control.png"));
            conf.show();
        } else {

            instructor.setNombre1(txtNombre1.getText());
            instructor.setNombre2(txtNombre2.getText());
            instructor.setNombre3(txtNombre3.getText());
            instructor.setApellido1(txtApellido1.getText());
            instructor.setApellido2(txtApellido2.getText());
            instructor.setDireccion(txtDireccion.getText());
            instructor.setEmail(txtEmail.getText());
            instructor.setTelefono(txtTelefono.getText());
            instructor.setFechaN(dpkFecha.getValue());

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_instructores_create(?, ?, ?, ?, ?, ?, ?, ?, ?)}");

                pstmt.setString(1, instructor.getNombre1());
                pstmt.setString(2, instructor.getNombre2());
                pstmt.setString(3, instructor.getNombre3());
                pstmt.setString(4, instructor.getApellido1());
                pstmt.setString(5, instructor.getApellido2());
                pstmt.setString(6, instructor.getDireccion());
                pstmt.setString(7, instructor.getEmail());
                pstmt.setString(8, instructor.getTelefono());
                pstmt.setObject(9, instructor.getFechaN());

                System.out.println(pstmt.toString());

                pstmt.execute();

                listaInstructores.add(instructor);

                return true;

            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("\nSE PRODUJO UN ERROR AL INTENTAR"
                        + " INGRESAR EL SIGUIENTE REGISTRO: " + instructor.toString());
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

    public ObservableList getInstructores() {

        ArrayList<Instructores> lista = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_instructores_read()}");

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Instructores instructor = new Instructores();
                instructor.setId(rs.getInt(1));
                instructor.setNombre1(rs.getString(2));
                instructor.setNombre2(rs.getString(3));
                instructor.setNombre3(rs.getString(4));
                instructor.setApellido1(rs.getString(5));
                instructor.setApellido2(rs.getString(6));
                instructor.setDireccion(rs.getString(7));
                instructor.setEmail(rs.getString(8));
                instructor.setTelefono(rs.getString(9));
                instructor.setFechaN(rs.getDate(10).toLocalDate());

                lista.add(instructor);

                System.out.println("\n" + instructor.toString());
            }

            listaInstructores = FXCollections.observableArrayList(lista);

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar consultar la lista de alumnos");
            System.out.println("Message: " + e.getMessage());
            System.out.println(e.getErrorCode());
            System.out.println(e.getSQLState());
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
        return listaInstructores;
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    private void deshabilitarCampos() {

        txtNombre1.setEditable(false);
        txtNombre2.setEditable(false);
        txtNombre3.setEditable(false);
        txtApellido1.setEditable(false);
        txtApellido2.setEditable(false);
        txtDireccion.setEditable(false);
        txtEmail.setEditable(false);
        txtTelefono.setEditable(false);

        txtNombre1.setDisable(true);
        txtNombre2.setDisable(true);
        txtNombre3.setDisable(true);
        txtApellido1.setDisable(true);
        txtApellido2.setDisable(true);
        txtDireccion.setDisable(true);
        txtEmail.setDisable(true);
        txtTelefono.setDisable(true);
        dpkFecha.setDisable(true);

    }

    private void habilitarCampos() {

        txtNombre1.setEditable(true);
        txtNombre2.setEditable(true);
        txtNombre3.setEditable(true);
        txtApellido1.setEditable(true);
        txtApellido2.setEditable(true);
        txtDireccion.setEditable(true);
        txtEmail.setEditable(true);
        txtTelefono.setEditable(true);

        txtNombre1.setDisable(false);
        txtNombre2.setDisable(false);
        txtNombre3.setDisable(false);
        txtApellido1.setDisable(false);
        txtApellido2.setDisable(false);
        txtDireccion.setDisable(false);
        txtEmail.setDisable(false);
        txtTelefono.setDisable(false);
        dpkFecha.setDisable(false);
    }

    private void limpiarCampos() {

        txtId.setText("");
        txtNombre1.setText("");
        txtNombre2.setText("");
        txtNombre3.setText("");
        txtApellido1.setText("");
        txtApellido2.setText("");
        txtDireccion.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        dpkFecha.getEditor().clear();
    }

    @FXML
    private void clicNuevo() {
        switch (operacion) {
            case NINGUNO: //Primer Clic Nuevo

                habilitarCampos();

                tblInstructores.getSelectionModel().clearSelection();

                tblInstructores.setDisable(true);

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
                if (agregarInstructor()) {

                    cargarDatos();
                    limpiarCampos();
                    deshabilitarCampos();
                    ContarRegistros();

                    tblInstructores.getSelectionModel().clearSelection();

                    btnNuevo.setText("Nuevo");
                    imgNuevo.setImage(new Image(PAQUETE_IMAGES + "nuevo.png"));

                    btnModificar.setText("Modificar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGES + "modificar.png"));

                    btnEliminar.setDisable(false);
                    btnEliminar.setVisible(true);

                    btnReporte.setDisable(false);
                    btnReporte.setVisible(true);

                    tblInstructores.setDisable(false);

                    deshabilitarCampos();

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

                tblInstructores.getSelectionModel().clearSelection();

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
                        eliminarInstructor();
                        limpiarCampos();

                        // If Eliminar Alumno
                        if (eliminarInstructor()) {

                            listaInstructores.remove(tblInstructores.getSelectionModel().getFocusedIndex());
                            cargarDatos();

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Control Academico Monte Carlo");
                            alert.setContentText("El registro se ha eliminado correctamente!");
                            alert.setHeaderText(null);

                            Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();

                            stageAlert.getIcons().add(new Image(PAQUETE_IMAGES + "control.png"));

                            alert.show();
                            ContarRegistros();
                        }

                    } else if (result.get().equals(ButtonType.CANCEL)) {

                        tblInstructores.getSelectionModel().clearSelection();
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

                tblInstructores.setDisable(false);

                operacion = Operacion.NINGUNO;

                break;

            case ACTUALIZAR:

                if (existeElementoSeleccionado()) {
                    if (actualizarInstructor()) {
                        cargarDatos();
                        limpiarCampos();
                        deshabilitarCampos();

                        tblInstructores.setDisable(false);
                        tblInstructores.getSelectionModel().clearSelection();

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
        GenerarReporte.getInstance().mostrarReporte("ReporteInstructores.jasper", parametros, "Reporte de Instructores");
    }

    @FXML
    private void clicMenuPrincipal(MouseEvent event) {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }
}
