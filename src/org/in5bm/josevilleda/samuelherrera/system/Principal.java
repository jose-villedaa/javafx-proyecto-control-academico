package org.in5bm.josevilleda.samuelherrera.system;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.in5bm.josevilleda.samuelherrera.controllers.MenuPrincipalController;
import org.in5bm.josevilleda.samuelherrera.controllers.AlumnosController;
import org.in5bm.josevilleda.samuelherrera.controllers.AsignacionesAlumnosController;
import org.in5bm.josevilleda.samuelherrera.controllers.CarrerasTecnicasController;
import org.in5bm.josevilleda.samuelherrera.controllers.CursosController;
import org.in5bm.josevilleda.samuelherrera.controllers.HorariosController;
import org.in5bm.josevilleda.samuelherrera.controllers.SalonesController;
import org.in5bm.josevilleda.samuelherrera.controllers.InstructoresController;

/**
 *
 * @author Jose Mauricio Villeda Morales & Samuel Herrera
 * @date 26 abr. 2022
 * @time 8:27:51
 *
 * Codigo Tecnico: IN5BM
 */
public class Principal extends Application{
    
    private Stage escenarioPrincipal;
    private final String PAQUETE_IMAGE = "org/in5bm/josevilleda/samuelherrera/resources/images/";
    private final String PAQUETE_VIEW = "../views/";
    
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.escenarioPrincipal = primaryStage;
        this.escenarioPrincipal.setTitle("Control Academico Monte Carlo");
        this.escenarioPrincipal.getIcons().add(new Image (PAQUETE_IMAGE + "control.png"));
        this.escenarioPrincipal.setResizable(false);
        this.escenarioPrincipal.centerOnScreen();
        mostrarEscenaPrincipal();
    }
    
    //Escena Menu Principal
    public void mostrarEscenaPrincipal(){
        try{
            MenuPrincipalController menuController = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 1200, 700); 
            menuController.setEscenarioPrincipal(this);
        } catch (Exception e){
            System.err.println("\nSe produjo un error al intentar mostrar la vista");
            e.printStackTrace();
            
        }
    }
    // Escena Alumnos
    public void mostrarEscenaAlumnos(){
        try{
            AlumnosController alumnosController = (AlumnosController) cambiarEscena("AlumnosView.fxml", 1200, 700); 
            alumnosController.setEscenarioPrincipal(this);
        } catch (Exception e){
            System.err.println("\nSe produjo un error al intentar mostrar la vista");
            e.printStackTrace();
            
        }
    }
    // Escena Salones
    public void mostrarEscenaSalones(){
        try{
            SalonesController salonesController = (SalonesController) cambiarEscena("SalonesView.fxml", 1200, 700); 
            salonesController.setEscenarioPrincipal(this);
        } catch (Exception e){
            System.err.println("\nSe produjo un error al intentar mostrar la vista");
            e.printStackTrace();
            
        }
    }
    
    // Escena Carreras Tecnicas
    public void mostrarEscenaCarrerasTecnicas(){
        try{
            CarrerasTecnicasController carrerasTecnicasController = (CarrerasTecnicasController) cambiarEscena("CarrerasTecnicasView.fxml", 1200, 700); 
            carrerasTecnicasController.setEscenarioPrincipal(this);
        } catch (Exception e){
            System.err.println("\nSe produjo un error al intentar mostrar la vista");
            e.printStackTrace();
            
        }
    }
    
      // Escena Instructores
    public void mostrarEscenaInstructores(){
        try{
            InstructoresController instructoresController = (InstructoresController) cambiarEscena("InstructoresView.fxml", 1200, 700); 
            instructoresController.setEscenarioPrincipal(this);
        } catch (Exception e){
            System.err.println("\nSe produjo un error al intentar mostrar la vista");
            e.printStackTrace();
            
        }
    }
    
      // Escena Asignacion Alumnos
    public void mostrarEscenaAsignacionesAlumnos(){
        try{
            AsignacionesAlumnosController asignacionesAlumnosController = (AsignacionesAlumnosController) cambiarEscena("AsignacionesAlumnosView.fxml", 1200, 700); 
            asignacionesAlumnosController.setEscenarioPrincipal(this);
        } catch (Exception e){
            System.err.println("\nSe produjo un error al intentar mostrar la vista");
            e.printStackTrace();
            
        }
    }
    
      // Escena Cursos
    public void mostrarEscenaCursos(){
        try{
            CursosController cursosController = (CursosController) cambiarEscena("CursosView.fxml", 1200, 700); 
            cursosController.setEscenarioPrincipal(this);
        } catch (Exception e){
            System.err.println("\nSe produjo un error al intentar mostrar la vista");
            e.printStackTrace();
            
        }
    }
    
      // Escena Horarios
    public void mostrarEscenaHorarios(){
        try{
            HorariosController horariosController = (HorariosController) cambiarEscena("HorariosView.fxml", 1200, 700); 
            horariosController.setEscenarioPrincipal(this);
        } catch (Exception e){
            System.err.println("\nSe produjo un error al intentar mostrar la vista");
            e.printStackTrace();
            
        }
    }
    
    // Metodo Cambiar Escena
    public Initializable cambiarEscena(String vistaFxml, int ancho, int alto) throws IOException {
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource(PAQUETE_VIEW + vistaFxml));
        
        Scene scene = new Scene((AnchorPane)cargadorFXML.load(), ancho ,alto);
        this.escenarioPrincipal.setScene(scene);
        this.escenarioPrincipal.sizeToScene();
        this.escenarioPrincipal.show();
        return (Initializable) cargadorFXML.getController();
    }    
        
    
}
