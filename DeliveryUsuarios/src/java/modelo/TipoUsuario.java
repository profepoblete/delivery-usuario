package modelo;
// Generated 17-11-2020 11:48:38 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * TipoUsuario generated by hbm2java
 */
public class TipoUsuario  implements java.io.Serializable {


     private Integer idTipoUsuario;
     private String descripcion;
     private Set<Usuario> usuarios = new HashSet<Usuario>(0);

    public TipoUsuario() {
    }

	
    public TipoUsuario(String descripcion) {
        this.descripcion = descripcion;
    }
    public TipoUsuario(String descripcion, Set<Usuario> usuarios) {
       this.descripcion = descripcion;
       this.usuarios = usuarios;
    }
   
    public Integer getIdTipoUsuario() {
        return this.idTipoUsuario;
    }
    
    public void setIdTipoUsuario(Integer idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Set<Usuario> getUsuarios() {
        return this.usuarios;
    }
    
    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }




}

