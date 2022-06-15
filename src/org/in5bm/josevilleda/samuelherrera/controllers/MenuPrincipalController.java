package org.in5bm.josevilleda.samuelherrera.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.in5bm.josevilleda.samuelherrera.system.Principal;
import javafx.event.ActionEvent;

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

    private Principal escenarioPrincipal;

    // DECLARACION DE MENU ITEM'S
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

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

}
