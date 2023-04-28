

package org.in5bm.josevilleda.samuelherrera.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import org.in5bm.josevilleda.samuelherrera.system.Principal;

/**
 *
 * @author Jose Mauricio Villeda Morales
 * @date 3 jul. 2022
 * @time 21:38:00
 * 
 * Codigo Tecnico: IN5BM
 */
public class AcercaDeController implements Initializable {

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
    private void clicMenuPrincipal(MouseEvent event) {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }
}
