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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.DetallePedido;
import modelo.Estado;
import modelo.MetodoPago;
import modelo.Pedido;
import modelo.Producto;
import modelo.TipoEntrega;
import modelo.Ubicacion;
import modelo.Usuario;

/**
 *
 * @author dream
 */
@WebServlet(name = "MisPedidosServlet", urlPatterns = {"/MisPedidos"})
public class MisPedidosServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
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
        processRequest(request, response);
        //busco el id de la tienda en sesion
        Usuario u = (Usuario) request.getSession().getAttribute("login");
        int idUsuario = u.getIdUsuario();
        //si hay id de tienda
        if (idUsuario > 0) {
            PedidoDAO pedidoDao = new PedidoDAO();
            //busco los pedidos registrados pendientes
            List<Pedido> pedidos = pedidoDao.listarPedidosbyUsuario(idUsuario);
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
                    for (TipoEntrega tipo : tiposEntrega) {
                        //si el tipo de entrega coincide con el id en el pedido
                        if (pedido.getTipoEntrega().getIdTipoEntrega() == tipo.getIdTipoEntrega()) {
                            //lo agrego al pedido
                            pedido.setTipoEntrega(tipo);
                        }
                    }
                    //busco el estado del pedido
                    for (Estado estado : estados) {
                        //si el estado coincide con el id en el pedido
                        if (pedido.getEstado().getIdEstado() == estado.getIdEstado()) {
                            //lo agrego al pedido
                            pedido.setEstado(estado);
                        }
                    }
                    //busco el metodo de pago del pedido
                    for (MetodoPago metodoPago : metodos) {
                        if (pedido.getMetodoPago().getIdMetodo() == metodoPago.getIdMetodo()) {
                            //lo agrego al pedido
                            pedido.setMetodoPago(metodoPago);
                        }
                    }
                    //busco la ubicacion del pedido
                    for (Ubicacion ubicacion : ubicaciones) {
                        //si la ubicacion coincide
                        if (pedido.getUbicacion().getIdUbicacion() == ubicacion.getIdUbicacion()) {
                            //lo agrego al pedido
                            pedido.setUbicacion(ubicacion);
                        }
                    }
                    //busco el cliente
                    usuario = usuarioDao.buscarUsuariobyId(pedido.getUsuario().getIdUsuario());
                    //se lo asigno al pedido
                    pedido.setUsuario(usuario);
                    //busco los detalles de pedidos
                    List<DetallePedido> detalles = pedidoDao.listarDetallePedidosbyPedido(pedido.getIdPedido());
                    //instancio el hashset para llenar el pedido
                    Set<DetallePedido> detallePedidos = new HashSet<DetallePedido>(0);
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
                request.getSession().setAttribute("misPedidos", pedidos);
                //redireciona a pagina
                request.getRequestDispatcher("Delivery/MisPedidos.jsp").forward(request, response);
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
        processRequest(request, response);
        //confirmacion de pedido entregado
        int idPedido = Integer.parseInt(request.getParameter("idPedidoConfirmado"));
        if (idPedido > 0) {
            //instanciar el pedido
            Pedido pedido = new Pedido();
            //buscar la lista de la session
            List<Pedido> listaPedidos = (List<Pedido>) request.getSession().getAttribute("misPedidos");
            //buscar el pedido en la lista
            for (Pedido pedidoLista : listaPedidos) {
                if (pedidoLista.getIdPedido() == idPedido) {
                    pedido = pedidoLista;
                }
            }
            //hacer un estado entrega confirmada
            Estado estado=new Estado();
            //ESTE VALOR DEBE ESTAR DE ACUERDO A LA BD!!!!!!!
            estado.setIdEstado(7);
            //cambiar el dato del estado a entrega confirmadan
            pedido.setEstado(estado);
            //instanciar dao
            PedidoDAO pdao=new PedidoDAO();
            //actualizar el pedido
            pdao.modificarPedido(pedido);
            //redirecciona al servlet para que recargue la lista
            response.sendRedirect("MisPedidos");
        } else {
            //redireciona a pagina
            request.getRequestDispatcher("index.jsp").forward(request, response);
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

}
