/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.ProductoDAO;
import DAO.PuntoVentaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Categoria;
import modelo.DetallePedido;
import modelo.Pedido;
import modelo.Producto;
import modelo.PuntoVenta;
import modelo.Usuario;

/**
 *
 * @author dream
 */
@WebServlet(name = "PuntoVentaServlet", urlPatterns = {"/PuntoVenta"})
public class PuntoVentaServlet extends HttpServlet {

    PuntoVentaDAO pvdao = new PuntoVentaDAO();
    ProductoDAO pdao = new ProductoDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        //busco el parametro del punto de venta seleccionado
        String parametro = request.getParameter("pv");
        //reviso si es que viene un guion
        int posicionGuion = parametro.indexOf("-");
        //instancio el punto para que no de null pointer exception
        String punto = "";
        //instancio categoria
        String nombreCategoria = "";
        //si no existe el guión
        if (posicionGuion == -1) {
            // no hay categoria y el punto de venta viene solo en el parametro
            punto = parametro;
        } else {
            //rescatar punto venta
            punto = parametro.substring(0, posicionGuion);
            //rescatar categoria
            nombreCategoria = parametro.substring(posicionGuion + 1);
        }
        //instancio el id de la sede
        int idSede;
        //busco si existe un login
        if (request.getSession().getAttribute("login") != null) {
            //si existe busco la sede del que esta logeado
            Usuario u = (Usuario) request.getSession().getAttribute("login");
            idSede = u.getSede().getIdSede();
        } else {
            //aqui: cuando se amplie a mas sedes, guardar la sede con javascript en el link
            //por ahora puse solo la de la sede que se esta usando
            idSede = 1;
        }
        //busco el punto de venta en la bd
        PuntoVenta pv = pvdao.buscarNombreSede(punto, idSede);
        //lo guardo en un atributo de sesion para mostrarlo
        request.getSession().setAttribute("puntoventa", pv);
        //busco las Categorias
        List<Categoria> categorias = pdao.listarCategoriasPunto(pv.getIdPuntoVenta());
        //los guardo en un atributo de sesion para mostrarlos
        request.getSession().setAttribute("categorias", categorias);
        //si no hay categoria guardada
        if (nombreCategoria.equals("")) {
            //busco los productos de ese punto
            List<Producto> productos = pdao.listarProductoActivoByIdTienda(pv.getIdPuntoVenta());
            //los guardo en un atributo de sesion para mostrarlos
            request.getSession().setAttribute("productos", productos);
            //variable para categorias por producto en sesion
            request.getSession().setAttribute("categoriasProducto", categorias);
        } else {
            //si hay categoria
            //busco la categoria
            Categoria categoria = pdao.buscarCategoriabyDescripcion(nombreCategoria);
            //busco los productos del punto con la categoria pedida
            List<Producto> productos = pdao.listarProductosActivoByTiendaCategoria(pv.getIdPuntoVenta(), categoria.getIdCategoria());
            //los guardo en un atributo de sesion para mostrarlos
            request.getSession().setAttribute("productos", productos);
            //lista de categorias para que solo contenga la seleccionada
            List<Categoria> categoriaSeleccionada = new ArrayList<>();
            categoriaSeleccionada.add(categoria);
            //variable para categorias por producto en sesion
            request.getSession().setAttribute("categoriasProducto", categoriaSeleccionada);
        }
        //para validacion de carrito por punto venta
        //busco el carrito
        List<DetallePedido> carrito = (List<DetallePedido>) request.getSession().getAttribute("carrito");
        //si el carrito existe
        if (carrito != null) { 
            //busco un detalle y guardo el id de tienda
            int idTienda = 0;
            for (int indice = 0; indice < 1; indice++) {
                DetallePedido dp = carrito.get(indice);
                idTienda = dp.getProducto().getPuntoVenta().getIdPuntoVenta();
            }
            //si el punto de venta es el mismo del carrito
            if (idTienda == pv.getIdPuntoVenta()) { 
                request.getSession().setAttribute("esOtroPunto", 0);
            } else {
                //si el punto de venta no es el mismo del carrito
                request.getSession().setAttribute("esOtroPunto", 1);
            }

        } else {
            //el punto de venta no puede ser otro que el del carrito
            request.getSession().setAttribute("esOtroPunto", 0); 
        }

        //redirecciono a pagina
        request.getRequestDispatcher("Delivery/PuntoVenta.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        //AGREGAR AL CARRITO
        //busco el producto
        String id=request.getParameter("idProducto");
        if (id != null) {
            agregarProductoCarrito(request, response);
        } else {
            buscarProductoTexto(request, response);
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

    private void agregarProductoCarrito(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //AGREGAR AL CARRITO
        //busco el producto
        int id = Integer.parseInt(request.getParameter("idProducto"));
        Producto producto = pdao.buscarProducto(id);
        //hago un pedido vacio para armar el detalle
        Pedido pedido = new Pedido();
        //armo el detalle pedido
        DetallePedido dp = new DetallePedido(pedido, producto, 1, producto.getPrecio());
        //instancio contador para boton de carrito
        int contCarrito = 0;
        //busco el carrito si ya existia
        List<DetallePedido> carrito = (List<DetallePedido>) request.getSession().getAttribute("carrito");
        //si el carrito no existia
        if (carrito == null) {
            carrito = new ArrayList<DetallePedido>();
        } else {
            //reviso si confirmo cambiar el carrito de tienda
            int esOtroPunto = (int) request.getSession().getAttribute("esOtroPunto");
            if (esOtroPunto == 1) {
                //remuevo el carrito si confirmo
                request.getSession().removeAttribute("carrito");
                carrito.clear();
                request.getSession().setAttribute("esOtroPunto", 0);
            } else {
                //si no hubo cambio de tienda asigno el valor contador existente
                contCarrito = (int) request.getSession().getAttribute("contadorCarrito");
            }
        }
        //agrego el detalle al carrito y actualizo el contador
        carrito.add(dp);
        contCarrito++;
        //agrego el carrito a la session o sobreescribo
        request.getSession().setAttribute("carrito", carrito);
        //agrego el contador a la session
        request.getSession().setAttribute("contadorCarrito", contCarrito);
        //busco en que tienda estaba
        PuntoVenta pv = (PuntoVenta) request.getSession().getAttribute("puntoventa");
        response.sendRedirect("PuntoVenta?pv=" + pv.getNombre());
    }

    private void buscarProductoTexto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //busco el texto
        String busqueda = request.getParameter("textoBusqueda");
        //busco en que tienda estaba
        PuntoVenta pv = (PuntoVenta) request.getSession().getAttribute("puntoventa");
        //busco los productos que coincidan por el texto y la tienda en la q estoy
        List<Producto> productos = pdao.listarProductosbyBusquedaTienda(busqueda, pv.getIdPuntoVenta());
        //sobreescribo el atributo de sesion para mostrarlos
        request.getSession().setAttribute("productos", productos);
        //redirecciono a pagina
        request.getRequestDispatcher("Delivery/PuntoVenta.jsp").forward(request, response);
        
        //instrucciones para hacer con ajax
        //tengo que serializar el list productos en json
        //return del json
    }

}
