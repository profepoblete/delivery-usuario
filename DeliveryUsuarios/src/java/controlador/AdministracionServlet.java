/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.PedidoDAO;
import DAO.ProductoDAO;
import DAO.UbicacionDAO;
import DAO.UsuarioDAO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.DetallePedido;
import modelo.Estado;
import modelo.MetodoPago;
import modelo.Pedido;
import modelo.Producto;
import modelo.TipoEntrega;
import modelo.Ubicacion;
import modelo.Usuario;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author dream
 */
@WebServlet(name = "AdministracionServlet", urlPatterns = {"/Administracion"})
public class AdministracionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws net.sf.jasperreports.engine.JRException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JRException {
        response.setContentType("text/html;charset=UTF-8");

        
        if (request.getParameter("accion") != null){
            String accion = request.getParameter("accion");
            switch(accion){
                case "exportarPDF":
                    this.exportPDFFile(request, response);
                    break;
            }
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JRException ex) {
            Logger.getLogger(AdministracionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Si al acceder por url, no se tiene tipo de usuario de punto de venta, no se deja pasar y se envia al indice.
        comprobarAcceso(request.getSession(),request,response); 
        //busco el id de la tienda en sesion
        Usuario u = (Usuario) request.getSession().getAttribute("login");
        int idTienda = u.getPuntoVenta().getIdPuntoVenta();
        //si hay id de tienda
        if (idTienda > 0) {
            PedidoDAO pedidoDao = new PedidoDAO();
            //busco los pedidos registrados pendientes
            List<Pedido> pedidos = pedidoDao.listarPedidosActivosbyPuntoVenta(idTienda);
            //si no hay pedidos pendientes
            if (pedidos.isEmpty()) {
                //mensaje para mostrar y redireccion
                request.getSession().setAttribute("msjPedidos", "No hay pedidos pendientes");
                request.getRequestDispatcher("Mantenedor/InicioAdministracion.jsp").forward(request, response);
            } else {
                ProductoDAO productoDao = new ProductoDAO();
                UsuarioDAO usuarioDao = new UsuarioDAO();
                Producto producto;
                Usuario usuario;
                //listo los tipos de entrega
                List<TipoEntrega> tiposEntrega = pedidoDao.listarTipoEntrega();
                //listo estados de pedidos
                List<Estado> estados = pedidoDao.listarEstado();
                //lo agrego a la sesion
                request.getSession().setAttribute("estados", estados);

                //listo los metodos de pago
                List<MetodoPago> metodos = pedidoDao.listarMetodoPago();
                //listo las ubicaciones pertenecientes a la sede del punto
                UbicacionDAO ubicacionDao = new UbicacionDAO();
                List<Ubicacion> ubicaciones = ubicacionDao.listarUbicacionesSede(u.getSede().getIdSede());

                //por cada pedido pendiente
                for (Pedido pedido : pedidos) {
                    //busco el tipo de entrega
                    tiposEntrega.stream().filter((tipo) -> (Objects.equals(pedido.getTipoEntrega().getIdTipoEntrega(), tipo.getIdTipoEntrega()))).forEachOrdered((tipo) -> {
                        //lo agrego al pedido
                        pedido.setTipoEntrega(tipo);
                    }); //si el tipo de entrega coincide con el id en el pedido
                    //busco el estado del pedido
                    estados.stream().filter((estado) -> (Objects.equals(pedido.getEstado().getIdEstado(), estado.getIdEstado()))).forEachOrdered((estado) -> {
                        //lo agrego al pedido
                        pedido.setEstado(estado);
                    }); //si el estado coincide con el id en el pedido
                    //busco el metodo de pago del pedido
                    metodos.stream().filter((metodoPago) -> (Objects.equals(pedido.getMetodoPago().getIdMetodo(), metodoPago.getIdMetodo()))).forEachOrdered((metodoPago) -> {
                        //lo agrego al pedido
                        pedido.setMetodoPago(metodoPago);
                    });
                    //busco la ubicacion del pedido
                    ubicaciones.stream().filter((ubicacion) -> (Objects.equals(pedido.getUbicacion().getIdUbicacion(), ubicacion.getIdUbicacion()))).forEachOrdered((ubicacion) -> {
                        //lo agrego al pedido
                        pedido.setUbicacion(ubicacion);
                    }); //si la ubicacion coincide
                    //busco el cliente
                    usuario = usuarioDao.buscarUsuariobyId(pedido.getUsuario().getIdUsuario());
                    //se lo asigno al pedido
                    pedido.setUsuario(usuario);
                    //busco los detalles de pedidos
                    List<DetallePedido> detalles = pedidoDao.listarDetallePedidosbyPedido(pedido.getIdPedido());
                    //instancio el hashset para llenar el pedido
                    Set<DetallePedido> detallePedidos = new HashSet<>(0);
                    //por cada detalle
                    for (DetallePedido detalle : detalles) {
                        //busco el producto
                        producto = productoDao.buscarProducto(detalle.getProducto().getIdProducto());
                        //lo ingreso en el detalle
                        detalle.setProducto(producto);
                        detalle.setPedido(pedido);
                        //agrego el detalle al hashset
                        detallePedidos.add(detalle);
                    }
                    //le asigno los detalles al pedido
                    pedido.setDetallePedidos(detallePedidos);
                }
                //lo agrego a session
                request.getSession().setAttribute("listaPedidos", pedidos);

                //test para ver si puedo leer todo
//                for (Pedido pedidoPrueba : pedidos) {
//                    out.println("\n Pedido n° " + pedidoPrueba.getIdPedido());
//                    Set<DetallePedido> listaDetalles = new HashSet<DetallePedido>(0);
//                    listaDetalles=pedidoPrueba.getDetallePedidos();
//                    for (DetallePedido detallePedido : listaDetalles) {
//                        out.println(" Producto: " + detallePedido.getProducto().getNombre());
//                    }
//                    
//                }
                //redireciona a pagina
                request.getRequestDispatcher("Mantenedor/InicioAdministracion.jsp").forward(request, response);
            }
        } else {
            //no hay tienda logeada
            //redireciona a pagina
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JRException ex) {
            Logger.getLogger(AdministracionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        //si existe el parametro de estado seleccionado
        if (request.getParameter("estadoSeleccionado") == null) {
            //QUIERO VER EL DETALLE DEL PEDIDO
            //rescato el id
            int idPedido = Integer.parseInt(request.getParameter("lp.idPedido"));
            //instancio un pedido
            Pedido pedido = new Pedido();
            //busco la lista de pedidos de la sesion
            List<Pedido> listaPedidos = (List<Pedido>) request.getSession().getAttribute("listaPedidos");
            //recorro la lista para asignar el pedido buscado
            for (Pedido pedidoLista : listaPedidos) {
                if (pedidoLista.getIdPedido() == idPedido) {
                    pedido = pedidoLista;
                }
            }
            //lo guardo en la session
            request.getSession().setAttribute("pedidoBuscado", pedido);
            //remuevo el estado confirmado de la lista de sesion
            List<Estado> listaEstados = (List<Estado>) request.getSession().getAttribute("estados");
            listaEstados.removeIf(estado -> estado.getDescripcion().equals("entrega confirmada"));
            request.getSession().setAttribute("estados", listaEstados);
        } else {
            //QUIERO CAMBIAR EL ESTADO
            //busco el id del parametro
            int idEstado = Integer.parseInt(request.getParameter("estadoSeleccionado"));
            //busco la lista de estados en la sesion
            List<Estado> listaEstados = (List<Estado>) request.getSession().getAttribute("estados");
            Estado e = new Estado();
            //buscar el estado que coincida
            for (Estado estado : listaEstados) {
                if (estado.getIdEstado() == idEstado) {
                    e = estado;
                }
            }
            //busco el pedido que estamos viendo
            Pedido p = (Pedido) request.getSession().getAttribute("pedidoBuscado");
            //le seteo el estado al pedido
            p.setEstado(e);
            //guardo el pedido con el nuevo estado
            PedidoDAO pedidoDao = new PedidoDAO();
            pedidoDao.modificarPedido(p);
        }
        //redireciona a pagina
        request.getRequestDispatcher("Mantenedor/DetallePedido.jsp").forward(request, response);
    }
    
    private void exportPDFFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JRException{
        
        ServletOutputStream out = response.getOutputStream();
        
        try{
            InputStream reportePedidos = this.getServletConfig()
                    .getServletContext()
                    .getResourceAsStream("JasperReports/Coffee_Landscape.jasper");
           
            if(reportePedidos != null){
                String jsonPedido = request.getParameter("lista");
                Gson gson = new Gson();
                List<Pedido> reportesPedidos = new ArrayList<>();
                List<Pedido> reportesPedidos2 = new ArrayList<>();
                
                reportesPedidos.add(new Pedido());
                reportesPedidos2 = gson.fromJson(jsonPedido, new TypeToken<List<Pedido>>(){
            }.getType());
            
            reportesPedidos.addAll(reportesPedidos2);
            
            JasperReport report = (JasperReport) JRLoader.loadObject(reportePedidos);
            JRBeanArrayDataSource ds = new JRBeanArrayDataSource(reportesPedidos.toArray());
            
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("ds", ds);
            response.setContentType("application/pdf");
            response.addHeader("Content-disposition", "inline; filename=ReportePedidos.pdf");
            JasperPrint jasPrint = JasperFillManager.fillReport(report, parametros, ds);
                JasperExportManager.exportReportToPdfStream(jasPrint, out);
            out.flush();
            out.close();
            }
        }catch(Exception e){
            response.setContentType("text/plain");
            out.print("No se pudo generar el reporte");
            out.print("Contacte al soporte");
            
        }
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
        private void comprobarAcceso(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Si al acceder por url a gestionar productos, el usuario no es punto de venta, se le debe redirigir al indice.
        Usuario u = (Usuario) session.getAttribute("login");
        int id = 0; //Inicialización de la variable a un valor por defecto.
        //Que intente acceder al metodo getIdTipoUsuario() de la clase TipoUsuario, para asignar el resultado a id. 
        //Si falla, que rediriga al indice.
        try {
            id = u.getTipoUsuario().getIdTipoUsuario();
        } catch (Exception e) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        if (id != 2) { //Si el usuario no es un Punto de Venta, también redirige al indice:
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

}
