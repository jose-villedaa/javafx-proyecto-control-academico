package org.in5bm.josevilleda.samuelherrera.controllers;


import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.in5bm.josevilleda.samuelherrera.db.Conexion;
import org.in5bm.josevilleda.samuelherrera.models.Usuario;
import org.in5bm.josevilleda.samuelherrera.system.Principal;

/**
 *
 * @author Jose Mauricio Villeda Morales
 * @date 19 jul. 2022
 * @time 7:58:12
 *
 * Codigo Tecnico: IN5BM
 */

public class LoginController implements Initializable {

    private final String PAQUETE_IMAGES = "org/in5bm/josevilleda/samuelherrera/resources/images/";

    private Principal escenarioPrincipal;
    Principal p = new Principal();

    @FXML
    private TextField txtNombre;

    @FXML
    private PasswordField pfClave;

    @FXML
    private Button btnIngresar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    String rol;
    
    public String usuarioActual;
    
    Usuario user = new Usuario();

    @FXML
    void clicIngresar(ActionEvent event) {
        String usuario = txtNombre.getText();
        String password = pfClave.getText();
        if (validarCampos(usuario, password)) {
            if (validarRol(usuario)) {
                System.out.println("Comprobacion de Rol: " + rol);
                user.setNombre(rol);
                if (rol.equals("Estandar")) {
                    escenarioPrincipal.mostrarEscenaPrincipal();
                } else if (rol.equals("Administrador")){
                    escenarioPrincipal.mostrarEscenaPrincipal();
                }
            }
        }

    }
    
    public String getRol(){
        return user.getNombre();
    }

    public boolean validarCampos(String user, String pass) {
        Usuario usuario = new Usuario();
        usuario.setUser(txtNombre.getText());
        usuario.setPass(pfClave.getText());
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String SQL = "CALL sp_validacion_existencia(?,?)";
        try {
            pstmt = Conexion.getInstance().getConexion().prepareStatement(SQL);
            pstmt.setString(1, usuario.getUser());
            pstmt.setString(2, usuario.getPass());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Control Academico Monte Carlo");
                alerta.setHeaderText(null);
                alerta.setContentText("El Usuario o Contraseña son Invalidos!");
                Stage stage = (Stage) alerta.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image((PAQUETE_IMAGES + "control.png")));
                alerta.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean validarRol(String user) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String SQL = "CALL sp_validacion_rol(?)";
        try {
            pstmt = Conexion.getInstance().getConexion().prepareStatement(SQL);
            pstmt.setString(1, user);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                rol = rs.getString(1);
                System.out.println("ROL: " + rol);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
