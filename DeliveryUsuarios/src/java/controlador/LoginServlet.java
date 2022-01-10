/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.PuntoVentaDAO;
import DAO.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.PuntoVenta;
import modelo.Usuario;

/**
 *
 * @author dream
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/Login"})
public class LoginServlet extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        //limpio los mensajes
        request.getSession().removeAttribute("msjerror");
        //busco la info del login
        Usuario u = (Usuario) request.getSession().getAttribute("login");
        //Si un usuario logeado accede a esta página, es porque entro presionando el botón "Salir"
        //entonces se limpia la sesion, quitando el usuario y el tipo de usuario, y se le redirecciona
        if (u != null) {
            request.getSession().invalidate();
            response.sendRedirect("index");
        } else {
            //redirecciono
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        //busco los datos
        String email = request.getParameter("email");
        String contrasena = request.getParameter("contrasena");
        //busco si hay un login activo
        UsuarioDAO udao = new UsuarioDAO();
        Usuario usuario = udao.buscarUsuarioLogin(email, contrasena);
        if (usuario != null) { //si te devolvio algun usuario
            //si el usuario esta activo
            if (usuario.isActivo()) { //Si la cuenta de usuario está activa, ya que estas podrían desactivarse.
                //Al usuario que se almacenará luego como atributo de sesión se le quita la contraseña, por si acaso.
                usuario.setContrasena("null"); 
                if (usuario.getTipoUsuario().getIdTipoUsuario() == 2) { //Punto de venta
                    int idPuntoVenta = usuario.getPuntoVenta().getIdPuntoVenta();
                    PuntoVentaDAO pvDAO = new PuntoVentaDAO();
                    usuario.setPuntoVenta(pvDAO.buscarById(idPuntoVenta));
                    
                    request.getSession().setAttribute("login", usuario); 
//Redirige al listado de pedidos, de momento esa página y su servlet se llaman Administracion, pero podría llamarse 
//ListadoPedidos o algo más descriptivo
                    response.sendRedirect("Administracion"); 
                } else if (usuario.getTipoUsuario().getIdTipoUsuario() == 3) { //Colaborador o Cliente
                    request.getSession().setAttribute("login", usuario);
                    response.sendRedirect("index");
                } else {
                    request.getSession().setAttribute("msjerror", "Dirijase a modulo de administracion");
                    //redirecciono para q no borre el mensaje
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            }

        } else {
            //guardo el mensaje que se mostrará al redirigir, como contenido de un div en login.jsp
            request.getSession().setAttribute("msjerror", "Email o contraseña incorrectas, si olvido su contraseña comuniquese con un administrador");
            //redirecciono para q no borre el mensaje
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

}
