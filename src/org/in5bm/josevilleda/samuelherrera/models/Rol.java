package org.in5bm.josevilleda.samuelherrera.models;

/**
 *
 * @author Jose Mauricio Villeda Morales
 * @date 19 jul. 2022
 * @time 8:09:51
 * 
 * Codigo Tecnico: IN5BM
 */

public class Rol {
    
    private int id;
    private String descripcion ;

    public Rol() {
    }

    public Rol(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
   
}
