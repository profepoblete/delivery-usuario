package modelo;
// Generated 17-11-2020 11:48:38 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Sede generated by hbm2java
 */
public class Sede  implements java.io.Serializable {


     private Integer idSede;
     private String nombreSede;
     private String direccion;
     private Set<Usuario> usuarios = new HashSet<Usuario>(0);
     private Set<Ubicacion> ubicacions = new HashSet<Ubicacion>(0);
     private Set<PuntoVenta> puntoVentas = new HashSet<PuntoVenta>(0);

    public Sede() {
    }

	
    public Sede(String nombreSede, String direccion) {
        this.nombreSede = nombreSede;
        this.direccion = direccion;
    }
    public Sede(String nombreSede, String direccion, Set<Usuario> usuarios, Set<Ubicacion> ubicacions, Set<PuntoVenta> puntoVentas) {
       this.nombreSede = nombreSede;
       this.direccion = direccion;
       this.usuarios = usuarios;
       this.ubicacions = ubicacions;
       this.puntoVentas = puntoVentas;
    }
   
    public Integer getIdSede() {
        return this.idSede;
    }
    
    public void setIdSede(Integer idSede) {
        this.idSede = idSede;
    }
    public String getNombreSede() {
        return this.nombreSede;
    }
    
    public void setNombreSede(String nombreSede) {
        this.nombreSede = nombreSede;
    }
    public String getDireccion() {
        return this.direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public Set<Usuario> getUsuarios() {
        return this.usuarios;
    }
    
    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    public Set<Ubicacion> getUbicacions() {
        return this.ubicacions;
    }
    
    public void setUbicacions(Set<Ubicacion> ubicacions) {
        this.ubicacions = ubicacions;
    }
    public Set<PuntoVenta> getPuntoVentas() {
        return this.puntoVentas;
    }
    
    public void setPuntoVentas(Set<PuntoVenta> puntoVentas) {
        this.puntoVentas = puntoVentas;
    }




}


