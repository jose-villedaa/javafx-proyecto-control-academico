package org.in5bm.josevilleda.samuelherrera.controllers;

import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;
import org.in5bm.josevilleda.samuelherrera.db.Conexion;
import org.in5bm.josevilleda.samuelherrera.models.Horarios;
import org.in5bm.josevilleda.samuelherrera.system.Principal;

public class HorariosController implements Initializable {

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
    private JFXTimePicker tmpHorarioInicio;

    @FXML
    private JFXTimePicker tmpHorarioSalida;

    @FXML
    private TextField txtId;

    @FXML
    private TableView<Horarios> tblHorarios;

    @FXML
    private TableColumn<Horarios, Integer> colId;

    @FXML
    private TableColumn<Horarios, LocalTime> colHorarioInicio;

    @FXML
    private TableColumn<Horarios, LocalTime> colHorarioSalida;

    @FXML
    private TableColumn<Horarios, Boolean> colLunes;

    @FXML
    private TableColumn<Horarios, Boolean> colMartes;

    @FXML
    private TableColumn<Horarios, Boolean> colMiercoles;

    @FXML
    private TableColumn<Horarios, Boolean> colJueves;

    @FXML
    private TableColumn<Horarios, Boolean> colViernes;

    @FXML
    private CheckBox chkLunes;

    @FXML
    private CheckBox chkMartes;

    @FXML
    private CheckBox chkMiercoles;

    @FXML
    private CheckBox chkJueves;

    @FXML
    private CheckBox chkViernes;

    private ObservableList<Horarios> listaObservableHorarios;

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private Operacion operacion = Operacion.NINGUNO;

    private Principal escenarioPrincipal;

    private final String PAQUETE_IMAGES = "org/in5bm/josevilleda/samuelherrera/resources/images/";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tmpHorarioInicio.set24HourView(true);
        tmpHorarioSalida.set24HourView(true);

        StringConverter<LocalTime> defaultConverter
                = new LocalTimeStringConverter(FormatStyle.SHORT, Locale.US);
        tmpHorarioInicio.setConverter(defaultConverter);

        StringConverter<LocalTime> defaultConverter2
                = new LocalTimeStringConverter(FormatStyle.SHORT, Locale.UK);
        tmpHorarioSalida.setConverter(defaultConverter2);

        cargarDatos();
    }

    public void cargarDatos() {
        tblHorarios.setItems(getHorarios());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colHorarioInicio.setCellValueFactory(new PropertyValueFactory<>("horarioInicio"));
        colHorarioSalida.setCellValueFactory(new PropertyValueFactory<>("horarioFinal"));
        colLunes.setCellValueFactory(new PropertyValueFactory<>("lunes"));
        colMartes.setCellValueFactory(new PropertyValueFactory<>("martes"));
        colMiercoles.setCellValueFactory(new PropertyValueFactory<>("miercoles"));
        colJueves.setCellValueFactory(new PropertyValueFactory<>("jueves"));
        colViernes.setCellValueFactory(new PropertyValueFactory<>("viernes"));
    }

    public boolean existeElementoSeleccionado() {
        return (tblHorarios.getSelectionModel().getSelectedItem() != null);

    }

    @FXML
    public void seleccionarElemento() {

        if (existeElementoSeleccionado()) {

            String id = String.valueOf(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getId());

            txtId.setText(id);

            tmpHorarioInicio.setValue(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getHorarioInicio());

            tmpHorarioSalida.setValue(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getHorarioFinal());

            chkLunes.setSelected(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).isLunes());

            chkMartes.setSelected(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).isMartes());

            chkMiercoles.setSelected(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).isMiercoles());

            chkJueves.setSelected(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).isJueves());

            chkViernes.setSelected(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).isViernes());
        }
    }

    private ObservableList getHorarios() {
        ArrayList<Horarios> arrayListHorarios = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_horarios_read()}");
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Horarios horario = new Horarios();
                horario.setId(rs.getInt(1));
                horario.setHorarioInicio(rs.getTime(2).toLocalTime());
                horario.setHorarioFinal(rs.getTime(3).toLocalTime());
                horario.setLunes(rs.getBoolean(4));
                horario.setMartes(rs.getBoolean(5));
                horario.setMiercoles(rs.getBoolean(6));
                horario.setJueves(rs.getBoolean(7));
                horario.setViernes(rs.getBoolean(8));
                System.out.println(horario.toString());
                arrayListHorarios.add(horario);
            }
            listaObservableHorarios = FXCollections.observableArrayList(arrayListHorarios);
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al consultar la tabla de Horarios");
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
        return listaObservableHorarios;
    }

    //Agregar Horarios
    private boolean agregarHorario() {
        Horarios horarios = new Horarios();

        horarios.setHorarioInicio(tmpHorarioInicio.getValue());
        horarios.setHorarioFinal(tmpHorarioSalida.getValue());
        horarios.setLunes(chkLunes.isSelected());
        horarios.setMartes(chkMartes.isSelected());
        horarios.setMiercoles(chkMiercoles.isSelected());
        horarios.setJueves(chkJueves.isSelected());
        horarios.setViernes(chkViernes.isSelected());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_horarios_create(?, ?, ?, ?, ?, ?, ?)}");

            pstmt.setObject(1, horarios.getHorarioInicio());
            pstmt.setObject(2, horarios.getHorarioFinal());
            pstmt.setBoolean(3, horarios.isLunes());
            pstmt.setBoolean(4, horarios.isMartes());
            pstmt.setBoolean(5, horarios.isMiercoles());
            pstmt.setBoolean(6, horarios.isJueves());
            pstmt.setBoolean(7, horarios.isViernes());

            System.out.println(pstmt.toString());

            pstmt.execute();

            listaObservableHorarios.add(horarios);

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("\nSE PRODUJO UN ERROR AL INTENTAR"
                    + " INGRESAR EL SIGUIENTE REGISTRO: " + horarios.toString());
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
    
    //Agregar Horarios
    private boolean actualizarHorario() {
        Horarios horarios = new Horarios();

        horarios.setId(Integer.parseInt(txtId.getText()));
        horarios.setHorarioInicio(tmpHorarioInicio.getValue());
        horarios.setHorarioFinal(tmpHorarioSalida.getValue());
        horarios.setLunes(chkLunes.isSelected());
        horarios.setMartes(chkMartes.isSelected());
        horarios.setMiercoles(chkMiercoles.isSelected());
        horarios.setJueves(chkJueves.isSelected());
        horarios.setViernes(chkViernes.isSelected());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_horarios_update(? ,?, ?, ?, ?, ?, ?, ?)}");

            pstmt.setInt(1, horarios.getId());
            pstmt.setObject(2, horarios.getHorarioInicio());
            pstmt.setObject(3, horarios.getHorarioFinal());
            pstmt.setBoolean(4, horarios.isLunes());
            pstmt.setBoolean(5, horarios.isMartes());
            pstmt.setBoolean(6, horarios.isMiercoles());
            pstmt.setBoolean(7, horarios.isJueves());
            pstmt.setBoolean(8, horarios.isViernes());

            System.out.println(pstmt.toString());

            pstmt.execute();

            listaObservableHorarios.add(horarios);

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("\nSE PRODUJO UN ERROR AL INTENTAR"
                    + " INGRESAR EL SIGUIENTE REGISTRO: " + horarios.toString());
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
    public boolean eliminarHorario() {
        if (existeElementoSeleccionado()) {
            Horarios horarios = (Horarios) tblHorarios.getSelectionModel().getSelectedItem();
            System.out.println(horarios.toString());

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_horarios_delete(?)}");
                pstmt.setInt(1, horarios.getId());
                System.out.println(pstmt);

                pstmt.execute();

                return true;

            } catch (SQLException e) {
                System.err.println("\nSE produjo un error al intentar eliminar el alumno con el siguiente registro" + horarios.toString());
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void deshabilitarCampos() {
        txtId.setEditable(false);
        txtId.setDisable(true);

        tmpHorarioInicio.setDisable(true);
        tmpHorarioSalida.setDisable(true);

        chkLunes.setDisable(true);
        chkMartes.setDisable(true);
        chkMiercoles.setDisable(true);
        chkJueves.setDisable(true);
        chkViernes.setDisable(true);
    }

    private void habilitarCampos() {
        txtId.setEditable(false);
        tmpHorarioInicio.setEditable(true);
        tmpHorarioSalida.setEditable(true);

        txtId.setDisable(true);

        tmpHorarioInicio.setDisable(false);
        tmpHorarioSalida.setDisable(false);

        chkLunes.setDisable(false);
        chkMartes.setDisable(false);
        chkMiercoles.setDisable(false);
        chkJueves.setDisable(false);
        chkViernes.setDisable(false);
    }

    private void limpiarCampos() {
        txtId.clear();
        tmpHorarioInicio.getEditor().clear();
        tmpHorarioSalida.getEditor().clear();
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

                tblHorarios.getSelectionModel().clearSelection();

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
                        eliminarHorario();
                        limpiarCampos();

                        // If Eliminar Alumno
                        if (eliminarHorario()) {

                            listaObservableHorarios.remove(tblHorarios.getSelectionModel().getFocusedIndex());
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

                        tblHorarios.getSelectionModel().clearSelection();
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

    @FXML
    void clicMenuPrincipal(MouseEvent event) {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
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

                tblHorarios.setDisable(false);

                operacion = Operacion.NINGUNO;

                break;

            case ACTUALIZAR:

                if (existeElementoSeleccionado()) {
                    if (actualizarHorario()) {
                        cargarDatos();
                        limpiarCampos();
                        deshabilitarCampos();

                        tblHorarios.setDisable(false);
                        tblHorarios.getSelectionModel().clearSelection();

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
    private void clicNuevo() {
        switch (operacion) {
            case NINGUNO: //Primer Clic Nuevo

                habilitarCampos();

                tblHorarios.getSelectionModel().clearSelection();

                tblHorarios.setDisable(true);

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
                if (agregarHorario()) {

                    cargarDatos();
                    limpiarCampos();
                    deshabilitarCampos();

                    tblHorarios.getSelectionModel().clearSelection();

                    btnNuevo.setText("Nuevo");
                    imgNuevo.setImage(new Image(PAQUETE_IMAGES + "nuevo.png"));

                    btnModificar.setText("Modificar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGES + "modificar.png"));

                    btnEliminar.setDisable(false);
                    btnEliminar.setVisible(true);

                    btnReporte.setDisable(false);
                    btnReporte.setVisible(true);

                    tblHorarios.setDisable(false);

                    deshabilitarCampos();

                    operacion = Operacion.NINGUNO;

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

}
