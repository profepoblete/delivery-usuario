/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import modelo.PuntoVenta;
import modelo.Usuario;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author dream
 */
public class UsuarioDAO {

// METODOS PARA REGISTRO DE USUARIOS (buscarUsuarioRut, buscarUsuarioEmail, guardar)
//    public Usuario buscarUsuarioRut(String rut) { 
//        Usuario u = new Usuario();
//        Session sesion = HibernateUtil.getSessionFactory().openSession();
//        try {
//            String hql = "from Usuario u where u.rut= :rut";
//            Query q = sesion.createQuery(hql);
//            q.setParameter("rut", rut);
//
//            u = (Usuario) q.uniqueResult();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        } finally {
//            sesion.close();
//        }
//        return u;
//    }
//    public Usuario buscarUsuarioEmail(String email) {
//        Usuario u = new Usuario();
//        Session sesion = HibernateUtil.getSessionFactory().openSession();
//        try {
//            String hql = "from Usuario u where u.email= :email";
//            Query q = sesion.createQuery(hql);
//            q.setParameter("email", email);
//
//            u = (Usuario) q.uniqueResult();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        } finally {
//            sesion.close();
//        }
//        return u;
//    }
//
//    public int guardar(Usuario u) {
//        Session sesion = HibernateUtil.getSessionFactory().openSession();
//        int id = 0;
//        try {
//            Transaction tx = sesion.beginTransaction();
//            id = (int) sesion.save(u);
//            tx.commit();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        } finally {
//            sesion.close();
//        }
//        return id;
//    }
// FIN METODOS REGISTRO DE USUARIOS. 
    //METODO QUE BUSCA Y DEVUELVE AL USUARIO QUE SE ESTÁ LOGUEANDO
    public Usuario buscarUsuarioLogin(String email, String contrasena) {
        Usuario u = new Usuario();
        PuntoVenta pv;
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try { //Devuelve al usuario, pero su punto de venta en el primer uniqueResult todavia es null.
            String hql = "from Usuario u where u.email= :email and u.contrasena= :contrasena";
            Query q = sesion.createQuery(hql);
            q.setParameter("email", email);
            q.setParameter("contrasena", contrasena);

            u = (Usuario) q.uniqueResult();
            u.setContrasena("null");

            int idPV = 0; //En caso que no haya Punto de venta, el id se mantendrá en 0.
            try {
                hql = "Select u.puntoVenta.idPuntoVenta from Usuario u where u.idUsuario= :id";
                q = sesion.createQuery(hql);
                q.setParameter("id", u.getIdUsuario());
                idPV = (int) q.uniqueResult();
                if (idPV > 0) {
                    PuntoVenta puntoVenta = new PuntoVenta();
                    puntoVenta.setIdPuntoVenta(idPV);
                    u.setPuntoVenta(puntoVenta);
                }
            } catch (Exception e) {
            } //Acá, el id del Punto de Venta ya debería estar guardado en idPV,o seguir siendo 0.

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            sesion.close();
        }
        return u;
    }

    /*
    public int buscarPuntoVentaUsuario(int id){ //Recibe idUsuario y retorna el ID de su punto de venta.
        int idPunto = 0;
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "Select u.puntoVenta.idPuntoVenta from Usuario u where u.idUsuario= :id";
            Query q = sesion.createQuery(hql);
            q.setParameter("id", id);

            idPunto = (int) q.uniqueResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            sesion.close();
        }
        return idPunto;
    }
     */
    public Usuario buscarUsuariobyId(int idUsuario) {
        Usuario u = new Usuario();
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from Usuario u where u.idUsuario= :idUsuario";
            Query q = sesion.createQuery(hql);
            q.setParameter("idUsuario", idUsuario);

            u = (Usuario) q.uniqueResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            sesion.close();
        }
        return u;
    }
}
