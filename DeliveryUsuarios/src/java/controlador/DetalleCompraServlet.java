/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.PedidoDAO;
import DAO.ProductoDAO;
import DAO.UbicacionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
import modelo.PuntoVenta;
import modelo.TipoEntrega;
import modelo.Ubicacion;
import modelo.Usuario;

/**
 *
 * @author dream
 */
@WebServlet(name = "DetalleCompraServlet", urlPatterns = {"/DetalleCompra"})
public class DetalleCompraServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    ProductoDAO pdao = new ProductoDAO();
    UbicacionDAO ubdao = new UbicacionDAO();

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
        //si no se invalido la sesion
        try {
            //busco el login
            Usuario u = (Usuario) request.getSession().getAttribute("login");
            //valido si llego solo un colaborador
            if (u.isActivo() && u.getTipoUsuario().getIdTipoUsuario() == 3) {
                //busco si viene con opcion, la opcion es cancelar
                String op = request.getParameter("op");
                if (op != null) {
                    //si la opcion no esta vacia es cancelar
                    //limpio el carrito y su contador
                    request.getSession().removeAttribute("carrito");
                    request.getSession().removeAttribute("contadorCarrito");
                }
                //buscar la sede del usuario
                int sede = u.getSede().getIdSede();
                //buscar las ubicaciones
                List<Ubicacion> ubicaciones = ubdao.listarUbicacionesSede(sede);
                //guardo para mostrar
                request.getSession().setAttribute("ubicaciones", ubicaciones);
                //limpio los mensajes de error
                request.getSession().removeAttribute("msjErrorCarrito");
                //limpio los mensajes
                request.getSession().removeAttribute("msjCarrito");
                //mensaje si es q el carrito esta vacio
                if (request.getSession().getAttribute("carrito") == null) {
                    request.getSession().setAttribute("msjCarrito", "No hay productos en tu carrito");
                }
                //redirecciona a pagina
                request.getRequestDispatcher("Delivery/detalle_compra.jsp").forward(request, response);
            }

        } catch (Exception e) {
            //redirecciona a index pork no concuerda el usuario
            response.sendRedirect("index");
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
        //si no se invalido la sesion
        try {
            //busco el login
            Usuario u = (Usuario) request.getSession().getAttribute("login");
            //valido si llego solo un colaborador
            if (u.isActivo() && u.getTipoUsuario().getIdTipoUsuario() == 3) {
                //recojo la accion del boton enviado
                String accion = request.getParameter("enviar");

                try {
                    //si hay accion a evaluar
                    switch (accion) {

                        case "Quitar":
                            //busco el id del producto a quitar
                            int idProducto = Integer.parseInt(request.getParameter("idProducto"));
                            //busco el carrito
                            List<DetallePedido> listacarrito = (List<DetallePedido>) request.getSession().getAttribute("carrito");
                            //funcion que remueve si se da la coincidencia
                            listacarrito.removeIf(lc -> idProducto == lc.getProducto().getIdProducto());

                            //si el carrito esta vacio
                            if (listacarrito.isEmpty()) {
                                // lo elimino de la sesion con el contador
                                request.getSession().removeAttribute("carrito");
                                request.getSession().removeAttribute("contadorCarrito");
                            } else {
                                // si no sobreescribo el carrito
                                request.getSession().setAttribute("carrito", listacarrito);
                                //busco el contador, lo modifico y sobreescribo
                                int contador = (int) request.getSession().getAttribute("contadorCarrito");
                                contador--;
                                request.getSession().setAttribute("contadorCarrito", contador);
                            }
                            response.sendRedirect("DetalleCompra");
                            break;
                        case "Ordenar":
                            ordenar(request, response);
                            break;
                        default:
                            response.sendRedirect("DetalleCompra");
                            break;
                    }
                } catch (Exception e) {
                    // seteo un mensaje y redirecciono
                    request.getSession().setAttribute("msjCarrito", "Ocurrio un error: " + e.getMessage());
                    request.getRequestDispatcher("Delivery/detalle_compra.jsp").forward(request, response);
                }
            }
        } catch (Exception e) {
            //redirecciona a index pork no concuerda el usuario
            response.sendRedirect("index");
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

    private void ordenar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //borrar mensajes
        request.getSession().removeAttribute("msjErrorCarrito");
        //busco el medio de entrega
        String tipoe = request.getParameter("customRadio");
        TipoEntrega tipoEntrega = new TipoEntrega();
        //si el tipo de entrega fue escojido
        if (tipoe != null) {
            //lo parseo para usar como id
            int idTipoEntrega = Integer.parseInt(tipoe);

            tipoEntrega.setIdTipoEntrega(idTipoEntrega);
        } else {
            //seteo un mensaje y redirecciono
            request.getSession().setAttribute("msjErrorCarrito", "Debe escojer un metodo de entrega");
            request.getRequestDispatcher("Delivery/detalle_compra.jsp").forward(request, response);
            return;
        }
        Ubicacion ubicacion = new Ubicacion();
        //busco la ubicacion
        String ubi = request.getParameter("cboUbicacion");
        //si hay una ubicacion escojida
        if (ubi.isEmpty()) {
            //seteo un mensaje y redirecciono
            request.getSession().setAttribute("msjErrorCarrito", "Debe escojer una ubicacion");
            request.getRequestDispatcher("Delivery/detalle_compra.jsp").forward(request, response);
            return;
        } else {
            //praseo para usar
            int idUbicacion = Integer.parseInt(ubi);

            ubicacion.setIdUbicacion(idUbicacion);
        }
        //busco el detalleUbicacion
        String detalleUbicacion = request.getParameter("detalleUbicacion");
        if (detalleUbicacion == null || detalleUbicacion.equals("")) {
            request.getSession().setAttribute("msjErrorCarrito", "Debe anotar la sala, oficina o cualquier otro dato de utilidad");
            request.getRequestDispatcher("Delivery/detalle_compra.jsp").forward(request, response);
            return;
        }
        //set metodo pago
        MetodoPago metodoPago = new MetodoPago();
        //busco el medio de entrega
        String metodop = request.getParameter("rbPago");
        //si el tipo de entrega fue escojido
        if (metodop != null) {
            //lo parseo para usar como id
            int idMetodoPago = Integer.parseInt(metodop);
            metodoPago.setIdMetodo(idMetodoPago);
        } else {
            //seteo un mensaje y redirecciono
            request.getSession().setAttribute("msjErrorCarrito", "Debe escojer un metodo de pago");
            request.getRequestDispatcher("Delivery/detalle_compra.jsp").forward(request, response);
            return;
        }
        Estado estado = new Estado();
        estado.setIdEstado(1);
        //saco la fecha actual
        Date fecha = new Date();
        //saco el usuario
        Usuario u = (Usuario) request.getSession().getAttribute("login");
        //Armo el pedido
        Pedido pedido = new Pedido(estado, metodoPago, tipoEntrega, ubicacion, u, fecha, 0, detalleUbicacion);
        PedidoDAO pedidodao = new PedidoDAO();
        int idPedido = pedidodao.guardarPedido(pedido);
        //SI SE GUARDO EL PEDIDO
        if (idPedido > 0) {
            pedido.setIdPedido(idPedido);
            //busco el carrito
            List<DetallePedido> carrit = (List<DetallePedido>) request.getSession().getAttribute("carrito");
            //mido el carrito
            int largoCarrito = carrit.size();
            //contador para comparar registros guardados
            int contador = 0;
            //variable del total
            int total = 0;
            //recorro el carrito
            for (DetallePedido detallePedido : carrit) {
                //seteo el pedido en el detalle
                detallePedido.setPedido(pedido);
                //guardo el id del detalle resultante de la insercion en base de datos
                int idDetalle = pedidodao.guardarDetallePedido(detallePedido);
                //si hay una insercion
                if (idDetalle > 0) {
                    //actualizo contador y total
                    contador++;
                    total = total + detallePedido.getSubtotal();
                }
            }
            //si el contador fue actualizado
            if (total > 0) {
                //le ingreso el total al pedido
                pedido.setTotal(total);
                //guardo la modificacion en bd
                pedidodao.modificarPedido(pedido);
            }
            if (contador == largoCarrito) {
                carrit.clear();
            }
            //si el carrito no se guardo completo
            if (carrit.size() > 0) {
                //actualizo el contador del carrito
                request.getSession().setAttribute("contadorCarrito", carrit.size());
                //guardo el carrito como esta en la sesion
                request.getSession().setAttribute("carrito", carrit);
                //seteo un mensaje
                request.getSession().setAttribute("msjCarrito", "Error en el pedido, estos productos no pudieron ser agregados. \n"
                        + " El numero de su pedido es " + idPedido);
                //redirecciona a pagina
                request.getRequestDispatcher("Delivery/detalle_compra.jsp").forward(request, response);
            } else {
                //elimino el contador del carrito
                request.getSession().removeAttribute("contadorCarrito");
                //elimino el carrito pork esta vacio
                request.getSession().removeAttribute("carrito");
                //seteo un mensaje
                request.getSession().setAttribute("msjCarrito", "Pedido enviado con exito, \n"
                        + " el numero de su pedido es " + idPedido);
                //redirecciona a pagina
                request.getRequestDispatcher("Delivery/detalle_compra.jsp").forward(request, response);
            }
        } else {
            //seteo un mensaje
            request.getSession().setAttribute("msjCarrito", "Error en el pedido, intentar nuevamente");
            //redirecciona a pagina
            request.getRequestDispatcher("Delivery/detalle_compra.jsp").forward(request, response);
        }
    }

}
