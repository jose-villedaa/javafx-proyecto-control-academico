package org.in5bm.josevilleda.samuelherrera.models;

/**
 *
 * @author Jose Mauricio Villeda Morales
 * @date 19 jul. 2022
 * @time 8:10:00
 * 
 * Codigo Tecnico: IN5BM
 */
public class Usuario {

    private static String user;
    private static String pass;
    private static String nombre;
    private static int rolId;

    public Usuario() {
    }
  
    public Usuario(String user, String pass){
        this.user = user;
        this.pass = pass;
    }    
    

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

   public String getNombre(){
       return nombre;
   }
   
   public void setNombre(String nombre){
       this.nombre = nombre;
   }
   
   public int getRolId(){
       return rolId;
   }
   
   public void setRolId(int rolId){
       this.rolId = rolId;
   }

}

