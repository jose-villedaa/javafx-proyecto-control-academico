package org.in5bm.josevilleda.samuelherrera.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.in5bm.josevilleda.samuelherrera.system.Principal;
import org.in5bm.josevilleda.samuelherrera.db.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.in5bm.josevilleda.samuelherrera.models.Alumnos;

/**
 *
 * @author Jose Mauricio Villeda Morales & Samuel Herrera
 * @date 26 abr. 2022
 * @time 8:27:51
 *
 * Codigo Tecnico: IN5BM
 */
public class AlumnosController implements Initializable {

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private Operacion operacion = Operacion.NINGUNO;

    private Principal escenarioPrincipal;

    @FXML
    private TextField txtCarne;

    @FXML
    private TextField txtNombre1;

    @FXML
    private TextField txtNombre2;

    @FXML
    private TextField txtNombre3;

    @FXML
    private TextField txtApellido1;

    @FXML
    private TextField txtApellido2;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnReporte;

    @FXML
    private TableView tblAlumnos;

    @FXML
    private TableColumn colCarne;

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
    private ImageView imgModificar;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private ImageView imgReporte;

    @FXML
    private ImageView imgNuevo;

    private final String PAQUETE_IMAGES = "org/in5bm/josevilleda/samuelherrera/resources/images/";

    private ObservableList<Alumnos> listaAlumnos;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public void cargarDatos() {
        tblAlumnos.setItems(getAlumnos());
        colCarne.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("carne"));
        colNombre1.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre1"));
        colNombre2.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre2"));
        colNombre3.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre3"));
        colApellido1.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("apellido1"));
        colApellido2.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("apellido2"));
    }

    public boolean existeElementoSeleccionado() {
        return (tblAlumnos.getSelectionModel().getSelectedItem() != null);

    }

    @FXML
    public void seleccionarElemento() {

        if (existeElementoSeleccionado()) {

            txtCarne.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getCarne());

            txtNombre1.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getNombre1());

            txtNombre2.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getNombre2());

            txtNombre3.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getNombre3());

            txtApellido1.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getApellido1());

            txtApellido2.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getApellido2());
        }
    }

    //Actualizar Alumno
    public boolean actualizarAlumno() {
        Alumnos alumno = new Alumnos();

        alumno.setCarne(txtCarne.getText());
        alumno.setNombre1(txtNombre1.getText());
        alumno.setNombre2(txtNombre2.getText());
        alumno.setNombre3(txtNombre3.getText());
        alumno.setApellido1(txtApellido1.getText());
        alumno.setApellido2(txtApellido2.getText());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_alumnos_update(?, ?, ?, ?, ?, ?)}");

            pstmt.setString(1, alumno.getCarne());
            pstmt.setString(2, alumno.getNombre1());
            pstmt.setString(3, alumno.getNombre2());
            pstmt.setString(4, alumno.getNombre3());
            pstmt.setString(5, alumno.getApellido1());
            pstmt.setString(6, alumno.getApellido2());

            System.out.println(pstmt.toString());

            pstmt.execute();

            listaAlumnos.add(alumno);

            return true;

        } catch (SQLException e) {
            System.err.println("\nSE PRODUJO UN ERROR AL INTENTAR MODIFICAR EL SIGUIENTE REGISTRO: " + alumno.toString());
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

    //Eliminar Alumnos
    public boolean eliminarAlumno() {
        if (existeElementoSeleccionado()) {
            Alumnos alumno = (Alumnos) tblAlumnos.getSelectionModel().getSelectedItem();
            System.out.println(alumno.toString());

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_alumnos_delete(?)}");
                pstmt.setString(1, alumno.getCarne());
                System.out.println(pstmt);

                pstmt.execute();

                return true;

            } catch (SQLException e) {
                System.err.println("\nSE produjo un error al intentar eliminar el alumno con el siguiente registro" + alumno.toString());
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    //Agregar Alumno
    private boolean agregarAlumno() {
        Alumnos alumno = new Alumnos();

        if (txtCarne.getText().isEmpty()) {

            Alert conf = new Alert(Alert.AlertType.WARNING);
            conf.setTitle("Control Academico Monte Carlo");
            conf.setContentText(" Ingrese el carne del estudiante ");
            conf.setHeaderText(null);
            Stage stageAlertConf = (Stage) conf.getDialogPane().getScene().getWindow();
            stageAlertConf.getIcons().add(new Image(PAQUETE_IMAGES + "control.png"));
            conf.show();

        } else if (txtNombre1.getText().isEmpty()) {

            Alert conf = new Alert(Alert.AlertType.WARNING);
            conf.setTitle("Control Academico Monte Carlo");
            conf.setContentText(" Ingrese el Nombre del estudiante ");
            conf.setHeaderText(null);
            Stage stageAlertConf = (Stage) conf.getDialogPane().getScene().getWindow();
            stageAlertConf.getIcons().add(new Image(PAQUETE_IMAGES + "control.png"));
            conf.show();

        } else if (txtApellido1.getText().isEmpty()) {

            Alert conf = new Alert(Alert.AlertType.WARNING);
            conf.setTitle("Control Academico Monte Carlo");
            conf.setContentText(" Ingrese el Apellido del estudiante ");
            conf.setHeaderText(null);
            Stage stageAlertConf = (Stage) conf.getDialogPane().getScene().getWindow();
            stageAlertConf.getIcons().add(new Image(PAQUETE_IMAGES + "control.png"));
            conf.show();

        } else {
            alumno.setCarne(txtCarne.getText());
            alumno.setNombre1(txtNombre1.getText());
            alumno.setNombre2(txtNombre2.getText());
            alumno.setNombre3(txtNombre3.getText());
            alumno.setApellido1(txtApellido1.getText());
            alumno.setApellido2(txtApellido2.getText());

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion()
                        .prepareCall("{CALL sp_alumnos_create(?, ?, ?, ?, ?, ?)}");
                pstmt.setString(1, alumno.getCarne());
                pstmt.setString(2, alumno.getNombre1());
                pstmt.setString(3, alumno.getNombre2());
                pstmt.setString(4, alumno.getNombre3());
                pstmt.setString(5, alumno.getApellido1());
                pstmt.setString(6, alumno.getApellido2());

                System.out.println(pstmt.toString());

                pstmt.execute();

                listaAlumnos.add(alumno);

                return true;

            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("\nSE PRODUJO UN ERROR AL INTENTAR"
                        + " INGRESAR EL SIGUIENTE REGISTRO: " + alumno.toString());
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

    public ObservableList getAlumnos() {

        ArrayList<Alumnos> lista = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_alumnos_read()}");

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Alumnos alumno = new Alumnos();
                alumno.setCarne(rs.getString(1));
                alumno.setNombre1(rs.getString(2));
                alumno.setNombre2(rs.getString(3));
                alumno.setNombre3(rs.getString(4));
                alumno.setApellido1(rs.getString(5));
                alumno.setApellido2(rs.getString(6));

                lista.add(alumno);

                System.out.println("\n" + alumno.toString());
            }

            listaAlumnos = FXCollections.observableArrayList(lista);

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
        return listaAlumnos;
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    private void deshabilitarCampos() {

        txtCarne.setEditable(false);
        txtNombre1.setEditable(false);
        txtNombre2.setEditable(false);
        txtNombre3.setEditable(false);
        txtApellido1.setEditable(false);
        txtApellido2.setEditable(false);

        txtCarne.setDisable(true);
        txtNombre1.setDisable(true);
        txtNombre2.setDisable(true);
        txtNombre3.setDisable(true);
        txtApellido1.setDisable(true);
        txtApellido2.setDisable(true);

    }

    private void habilitarCampos() {

        txtCarne.setEditable(false);
        txtNombre1.setEditable(true);
        txtNombre2.setEditable(true);
        txtNombre3.setEditable(true);
        txtApellido1.setEditable(true);
        txtApellido2.setEditable(true);

        txtCarne.setDisable(true);
        txtNombre1.setDisable(false);
        txtNombre2.setDisable(false);
        txtNombre3.setDisable(false);
        txtApellido1.setDisable(false);
        txtApellido2.setDisable(false);
    }

    private void limpiarCampos() {
        txtCarne.setText("");
        txtNombre1.setText("");
        txtNombre2.setText("");
        txtNombre3.setText("");
        txtApellido1.setText("");
        txtApellido2.setText("");
    }

    @FXML
    private void clicNuevo() {
        switch (operacion) {
            case NINGUNO: //Primer Clic Nuevo

                habilitarCampos();

                tblAlumnos.getSelectionModel().clearSelection();

                tblAlumnos.setDisable(true);

                txtCarne.setEditable(true);
                txtCarne.setDisable(false);

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
                if (agregarAlumno()) {

                    cargarDatos();
                    limpiarCampos();
                    deshabilitarCampos();

                    tblAlumnos.getSelectionModel().clearSelection();

                    btnNuevo.setText("Nuevo");
                    imgNuevo.setImage(new Image(PAQUETE_IMAGES + "nuevo.png"));

                    btnModificar.setText("Modificar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGES + "modificar.png"));

                    btnEliminar.setDisable(false);
                    btnEliminar.setVisible(true);

                    btnReporte.setDisable(false);
                    btnReporte.setVisible(true);

                    tblAlumnos.setDisable(false);

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

                tblAlumnos.getSelectionModel().clearSelection();

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
                        eliminarAlumno();
                        limpiarCampos();

                        // If Eliminar Alumno
                        if (eliminarAlumno()) {

                            listaAlumnos.remove(tblAlumnos.getSelectionModel().getFocusedIndex());
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

                        tblAlumnos.getSelectionModel().clearSelection();
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

                tblAlumnos.setDisable(false);

                operacion = Operacion.NINGUNO;

                break;

            case ACTUALIZAR:

                if (existeElementoSeleccionado()) {
                    if (actualizarAlumno()) {
                        cargarDatos();
                        limpiarCampos();
                        deshabilitarCampos();

                        tblAlumnos.setDisable(false);
                        tblAlumnos.getSelectionModel().clearSelection();

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
