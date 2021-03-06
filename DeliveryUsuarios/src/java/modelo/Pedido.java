package modelo;
// Generated 17-11-2020 11:48:38 by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Pedido generated by hbm2java
 */
public class Pedido  implements java.io.Serializable {


     private Integer idPedido;
     private Estado estado;
     private MetodoPago metodoPago;
     private TipoEntrega tipoEntrega;
     private Ubicacion ubicacion;
     private Usuario usuario;
     private Date fechaventa;
     private int total;
     private String detalleUbicacion;
     private Set<DetallePedido> detallePedidos = new HashSet<DetallePedido>(0);

    public Pedido() {
    }

    public Pedido(Estado estado, MetodoPago metodoPago, TipoEntrega tipoEntrega, Ubicacion ubicacion, Usuario usuario, Date fechaventa, int total, String detalleUbicacion) {
        this.estado = estado;
        this.metodoPago = metodoPago;
        this.tipoEntrega = tipoEntrega;
        this.ubicacion = ubicacion;
        this.usuario = usuario;
        this.fechaventa = fechaventa;
        this.total = total;
        this.detalleUbicacion = detalleUbicacion;
    }
	
    public Pedido(Estado estado, MetodoPago metodoPago, TipoEntrega tipoEntrega, Ubicacion ubicacion, Usuario usuario, Date fechaventa, int total) {
        this.estado = estado;
        this.metodoPago = metodoPago;
        this.tipoEntrega = tipoEntrega;
        this.ubicacion = ubicacion;
        this.usuario = usuario;
        this.fechaventa = fechaventa;
        this.total = total;
    }
    public Pedido(Estado estado, MetodoPago metodoPago, TipoEntrega tipoEntrega, Ubicacion ubicacion, Usuario usuario, Date fechaventa, int total, String detalleUbicacion, Set<DetallePedido> detallePedidos) {
       this.estado = estado;
       this.metodoPago = metodoPago;
       this.tipoEntrega = tipoEntrega;
       this.ubicacion = ubicacion;
       this.usuario = usuario;
       this.fechaventa = fechaventa;
       this.total = total;
       this.detalleUbicacion = detalleUbicacion;
       this.detallePedidos = detallePedidos;
    }
   
    public Integer getIdPedido() {
        return this.idPedido;
    }
    
    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }
    public Estado getEstado() {
        return this.estado;
    }
    
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    public MetodoPago getMetodoPago() {
        return this.metodoPago;
    }
    
    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }
    public TipoEntrega getTipoEntrega() {
        return this.tipoEntrega;
    }
    
    public void setTipoEntrega(TipoEntrega tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
    }
    public Ubicacion getUbicacion() {
        return this.ubicacion;
    }
    
    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public Date getFechaventa() {
        return this.fechaventa;
    }
    
    public void setFechaventa(Date fechaventa) {
        this.fechaventa = fechaventa;
    }
    public int getTotal() {
        return this.total;
    }
    
    public void setTotal(int total) {
        this.total = total;
    }
    public String getDetalleUbicacion() {
        return this.detalleUbicacion;
    }
    
    public void setDetalleUbicacion(String detalleUbicacion) {
        this.detalleUbicacion = detalleUbicacion;
    }
    public Set<DetallePedido> getDetallePedidos() {
        return this.detallePedidos;
    }
    
    public void setDetallePedidos(Set<DetallePedido> detallePedidos) {
        this.detallePedidos = detallePedidos;
    }


    //DESDE AQUI ABAJO SON LOS METODOS USADOS PARA PODER HACER LOS REPORTES.
    //REALIZADO CON JASPER REPORTS
    
    //Usadas expresiones terciaras el ? y el : son el if y el else.
    
    public String getTipoEstadoString(){
        return this.estado != null ? this.estado.getDescripcion() : "---";
    }
    
    public String getNombreUserString(){
        return this.usuario != null ? this.usuario.getNombre() : "---";
    }
    
    public String getUbicacionString(){
        return this.ubicacion != null ? this.ubicacion.getNombreEdificio() : "---";
    }
    
    public String getMetodoString(){
        return this.metodoPago != null ? this.metodoPago.getDescripcion() : "---";
    }
    
    public String getTipoEntregaString(){
        return this.tipoEntrega != null ? this.tipoEntrega.getDescripcion() : "---";
    }
}


