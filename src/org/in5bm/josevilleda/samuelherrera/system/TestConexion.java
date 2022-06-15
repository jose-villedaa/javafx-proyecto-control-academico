
package org.in5bm.josevilleda.samuelherrera.system;

import org.in5bm.josevilleda.samuelherrera.db.Conexion;

/**
 *
 * @author Jose Mauricio Villeda Morales
 * @date 3 may. 2022
 * @time 11:50:42
 * 
 * Codigo Tecnico: IN5BM
 */
public class TestConexion {
    public static void main(String[] args) {
        Conexion con = Conexion.getInstance();
        
        Conexion con2 = Conexion.getInstance();
        
        Conexion con3 = Conexion.getInstance();
    }
}
