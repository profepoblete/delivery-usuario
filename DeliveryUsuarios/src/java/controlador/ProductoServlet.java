package controlador;

import DAO.ProductoDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import modelo.Categoria;
import modelo.Producto;
import modelo.PuntoVenta;
import modelo.Usuario;

/**
 *
 * @author dream
 */
@WebServlet(name = "ProductoServlet", urlPatterns = {"/Producto"})
@MultipartConfig
public class ProductoServlet extends HttpServlet {

    ProductoDAO objP = new ProductoDAO();
    int idFiltro;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        //instancio sesion
        HttpSession session = request.getSession();
        comprobarAcceso(session, request, response);//Si el usuario no "tiene permiso" para estar aquí, se va al indice.
        session.removeAttribute("productoMod");
        session.removeAttribute("productos");
        session.setAttribute("modo", "Guardar"); //Se entra a la página con la opción de crear un producto

        String filtro = request.getParameter("op"); //Se saca la opcion para filtrar productos del listado.
        idFiltro = 0; //Inicialización del Id para filtrar productos.
        //Es necesario setear el filtro como "" en vez de null (por la primera vez que se entra), de lo contrario crashea:
        if (filtro == null) {
            filtro = "";
        }
        switch (filtro) {
            case "Disponibles":
                idFiltro = 1; //1 = Listar solo productos disponibles.
                break;
            case "No_Disponibles":
                idFiltro = 2; //2 = Solo productos no disponibles.
                break;
            default: //Si el filtro no es "Solo disponibles", ni "Solo no disponibles", si es "Todos", entonces deberá mostrar todos los productos.
                idFiltro = 0;
                break;
        }
        //Se buscan las categorias de productos y se guardan en una lista
        List<Categoria> categorias = objP.listarCategorias();
        //luego se guardan en un atributo para la pagina
        session.setAttribute("categorias", categorias);
        actualizarListadoProductos(request, response, idFiltro);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        comprobarAcceso(request.getSession(), request, response);//Si el usuario "no tiene permiso" para estar aquí, se va al indice.
        String accion = request.getParameter("btnPost");
        switch (accion) {
            case "Guardar":
                this.guardarProducto(request, response);
                break;
            case "Cancelar": //Si se pulsa el botón de cancelar, se recarga la página, entrando por doGet de este archivo
                response.sendRedirect("Producto");
                break;
            case "Modificar": //Entra por apretar un botón "Modificar". Selecciona el producto y pone sus datos en los campos de registro.
                //Esto sirve para cambiar la función del botón que dice "Guardar", entre crear un producto o Modificar uno ya existente.
                request.getSession().setAttribute("modo", "Actualizar");
                //si el valor es modificar, se entra a este metodo, que toma los valores del producto y recarga la página, 
                //poniendo esos datos en los inputs.
                this.seleccionarParaModificar(request, response);
                break;
            case "Actualizar":
                this.modificarProducto(request, response);
                break;
            //Esto está técnicamente dos veces porque "Desactivar" y "Activar" serán los value que tomaran los botones de activar o desactivar.
            case "Desactivar":
                this.alternarEstadoProducto(request, response);
                break;
            case "Activar":
                this.alternarEstadoProducto(request, response);
                break;
            case "Buscar":
                String busqueda = request.getParameter("textoBusqueda");
                this.actualizarListadoProductos(request, response, busqueda);
                break;
        }
    }

    private void seleccionarParaModificar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Idealmente se debiese cambiar esto para no tener que sacar el ID producto desde un input invisible, porque esto se podría explotar o mal utilizar a proposito.
        int id = Integer.parseInt(request.getParameter("idProd"));
        //busco y recupero el producto con el id
        Producto producto = objP.buscarProducto(id);
        //obtengo la sesión
        HttpSession session = request.getSession();
        //guardo el producto en un atributo de la sesión 
        session.setAttribute("productoMod", producto);
        //redirecciono a la pagina, y por la forma en la que están definidos los campos de creación de producto del html, 
        //estos toman los valores del productoMod.
        request.getRequestDispatcher("Mantenedor/InicioProducto.jsp").forward(request, response);
    }

    //PARA QUE GUARDAR Y MODIFICAR PRODUCTOS MODIFIQUEN E INGRESEN EN LA BASE DE DATOS, DEBEN TENER UNA DIRECCIÓN VÁLIDA DONDE GUARDAR LAS IMÁGENES.
    private void guardarProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Se guardan los atributos de la sesión temporalmente como objetos.
        HttpSession session = request.getSession();

        Usuario respUser = (Usuario) session.getAttribute("login");
        List<Categoria> respCateg = (List<Categoria>) session.getAttribute("categoria");
        List<PuntoVenta> respPuntosVenta = (List<PuntoVenta>) session.getAttribute("puntos");
        List<Producto> respProducto = (List<Producto>) session.getAttribute("productos");

        //busco el nombre del input
        String nombre = request.getParameter("nombre");
        //busco el precio del input
        int precio = Integer.parseInt(request.getParameter("precio"));
        //busco la categoria del combobox
        int categoria = Integer.parseInt(request.getParameter("categoria"));
        //GUARDAR IMAGEN EN EL PROYECTO:
        //busco el nombre de la imagen del input que esta oculto
        String imagen = request.getParameter("nombreImagen");
        //busco el archivo de imagen
        Part archivo = request.getPart("imagen");
        //crea un archivo en la locacion indicada
        try ( //lo transforma en una cadena de datos
                InputStream is = archivo.getInputStream()) {
            //crea un archivo en la locacion indicada
            File f = new File("C:\\Users\\acidsulfurico\\Documents\\NetBeansProjects\\project\\delivery-usuario\\DeliveryUsuarios\\web\\img\\producto\\" + imagen);
            //D:\Users\Eduardo\Documents\NetBeansProjects\DeliveryUsuariosDuoc\DeliveryUsuarios\web\img\producto
            //lee los datos y los guarda en el archivo
            FileOutputStream ous = new FileOutputStream(f);
            int dato = is.read();
            while (dato != -1) {
                ous.write(dato);
                dato = is.read();
            }   ous.close();
            //FIN GUARDAR IMAGEN
        }
        //hago una categoria y le seteo el id
        Categoria cat = new Categoria();
        cat.setIdCategoria(categoria);
        //hago un punto de venta y le seteo el id a partir del idPuntoVenta presente en el usuario respaldado.
        PuntoVenta punto = respUser.getPuntoVenta();
        //id del producto = id del mismo en la BD
        int id = objP.guardar(new Producto(cat, punto, nombre, precio, imagen, true));
        actualizarListadoProductos(request, response, idFiltro);
        response.sendRedirect("Producto");
    }

    private void modificarProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        //busco el id en la sesion
        Producto productoMod = (Producto) session.getAttribute("productoMod");
        int idProducto = productoMod.getIdProducto();
        //busco el nombre del input
        String nombre = request.getParameter("nombre");
        //busco el precio del input
        int precio = Integer.parseInt(request.getParameter("precio"));
        //busco la categoria del combobox
        int categoria = Integer.parseInt(request.getParameter("categoria"));
        //ESTE HAY QUE CAMBIARLO cuando tengamos el id de la tienda por sesion
        Usuario usuarioLog = (Usuario) session.getAttribute("login");
        int puntoVenta = usuarioLog.getPuntoVenta().getIdPuntoVenta();
        //GUARDAR IMAGEN EN EL PROYECTO
        //busco el nombre de la imagen del input que esta oculto
        String imagen = request.getParameter("nombreImagen");
        //si la imagen no fue cambiada
        if (imagen == null || imagen.isEmpty()) {
            //guardo el nombre de la imagen actual qque esta en el input oculto
            imagen = productoMod.getImagen();
        } else {
            //GUARDAR IMAGEN EN EL PROYECTO 
            //busco el archivo de imagen
            Part archivo = request.getPart("imagen");
            //crea un archivo en la locacion indicada.
            try ( //lo transforma en una cadena de datos
                    InputStream is = archivo.getInputStream()) {
                //crea un archivo en la locacion indicada.
                File f = new File("C:\\Users\\acidsulfurico\\Documents\\NetBeansProjects\\project\\delivery-usuario\\DeliveryUsuarios\\web\\img\\producto\\" + imagen);
                try ( //Recuerda colocar tu ruta para que el CRUD funcione correctamente.
                        //lee los datos y los guarda en el archivo
                        FileOutputStream ous = new FileOutputStream(f)) {
                    int dato = is.read();
                    while (dato != -1) {
                        ous.write(dato);
                        dato = is.read();
                    }
                }
            }
        }
        //hago una categoria y le seteo el id
        Categoria cat = new Categoria();
        cat.setIdCategoria(categoria);
        //hago un punto de venta y le seteo el id
        PuntoVenta punto = new PuntoVenta();
        punto.setIdPuntoVenta(puntoVenta);
        //instancio un producto con los datos
        Producto producto = new Producto(cat, punto, nombre, precio, imagen, true);
        producto.setIdProducto(idProducto);
        //modificar producto, el resultado podría usarse para mostrar un mensaje de exito o de error dependiendo de si es mayor a cero o no. 
        int resultado = objP.modificar(producto);
        //redirecciono a la pagina
        response.sendRedirect("Producto");
    }

    private void alternarEstadoProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductoDAO pDAO = new ProductoDAO();
        Producto prod = pDAO.buscarProducto(Integer.parseInt(request.getParameter("idProd"))); //Recupera el producto y sus datos desde el id. 
        prod.setActivo(!prod.isActivo());
        pDAO.modificar(prod);
        actualizarListadoProductos(request, response, idFiltro);
    }

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

    private void actualizarListadoProductos(HttpServletRequest request, HttpServletResponse response, int idFiltro) throws ServletException, IOException {
        HttpSession session = request.getSession();
        //Se saca el id punto de venta a partir del usuario logueado, desde ahí se trae un listado de productos de ese punto de venta usando el id obtenido al inicio. 
        Integer idPuntoVenta = ((Usuario) session.getAttribute("login")).getPuntoVenta().getIdPuntoVenta();
        List<Producto> productos = objP.listarProductoIdTienda(idPuntoVenta, idFiltro);
        //los guardo en un atributo para la pagina
        session.setAttribute("productos", productos);
        //redirecciono a la pagina
        request.getRequestDispatcher("Mantenedor/InicioProducto.jsp").forward(request, response);
    }

    //Sobrecarga del metodo actualizarListadoProductos, si se le ingresa un string de busqueda en vez 
    //de un int idFiltro, lo que hace es buscar todos los productos, tanto no disponibles como disponibles, y 
    //mostrar los que se parezcan en nombre al string busqueda ingresado.
    private void actualizarListadoProductos(HttpServletRequest request, HttpServletResponse response, String busqueda) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Producto> productos = null;
        //Se saca el id punto de venta a partir del usuario logueado.
        Integer idPuntoVenta = ((Usuario) session.getAttribute("login")).getPuntoVenta().getIdPuntoVenta();
        productos = objP.listarProdsByBusqueda(idPuntoVenta, busqueda);
        //Este atributo contiene el listado de productos recuperado, se usa en InicioProducto.jsp para llenar la tabla. 
        session.setAttribute("productos", productos);
        //Se recarga la página para actualizar la tabla:
        request.getRequestDispatcher("Mantenedor/InicioProducto.jsp").forward(request, response);
    }

}
