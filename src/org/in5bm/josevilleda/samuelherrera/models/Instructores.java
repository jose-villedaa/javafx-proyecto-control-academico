package org.in5bm.josevilleda.samuelherrera.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.ObjectProperty;

/**
 *
 * @author Jose Mauricio Villeda Morales
 * @date 6 jun. 2022
 * @time 8:28:08
 *
 * Codigo Tecnico: IN5BM
 */
public class Instructores {

    private int id;
    private String nombre1;
    private String nombre2;
    private String nombre3;
    private String apellido1;
    private String apellido2;
    private String direccion;
    private String email;
    private String telefono;
    private LocalDate fechaN;

    public Instructores() {
    }

    public Instructores(int id) {
        this.id = id;
    }

    public Instructores(int id, String nombre1, String apellido1, String email, String telefono) {
        this.id = id;
        this.nombre1 = nombre1;
        this.apellido1 = apellido1;
        this.email = email;
        this.telefono = telefono;
    }

    public Instructores(int id, String nombre1, String nombre2, String nombre3, String apellido1, String apellido2, String direccion, String email, String telefono, LocalDate fechaN) {
        this.id = id;
        this.nombre1 = nombre1;
        this.nombre2 = nombre2;
        this.nombre3 = nombre3;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.fechaN = fechaN;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getNombre3() {
        return nombre3;
    }

    public void setNombre3(String nombre3) {
        this.nombre3 = nombre3;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaN() {
        return fechaN;
    }

    public void setFechaN(LocalDate fechaN) {
        this.fechaN = fechaN;
    }

    

    @Override
    public String toString() {
        return "" + id + " | " + nombre1 + " | " + apellido1;
    }
    
    

}
