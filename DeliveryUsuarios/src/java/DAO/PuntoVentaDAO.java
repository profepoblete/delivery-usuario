/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.List;
import modelo.PuntoVenta;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author dream
 */
public class PuntoVentaDAO {
    
  
    public List<PuntoVenta> listarPuntos() {
        List<PuntoVenta> puntos = null;
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from PuntoVenta";
            Query q = sesion.createQuery(hql);

            puntos = q.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            sesion.close();
        }
        return puntos;
    }
        
    public PuntoVenta buscarById(int id) {
        PuntoVenta punto = new PuntoVenta();
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from PuntoVenta p where p.idPuntoVenta= :id";            
            Query q = sesion.createQuery(hql);
            q.setParameter("id", id);
            
            punto = (PuntoVenta) q.uniqueResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            sesion.close();
        }
        return punto;
    }
    
    public PuntoVenta buscarNombreSede(String nombre, int idSede){
        PuntoVenta punto = new PuntoVenta();
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from PuntoVenta p where p.nombre= :nombre and p.sede.idSede= :idSede";            
            Query q = sesion.createQuery(hql);
            q.setParameter("nombre", nombre);
            q.setParameter("idSede", idSede);
            
            punto = (PuntoVenta) q.uniqueResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            sesion.close();
        }
        return punto;
    }
    
    public List<PuntoVenta> listarPuntosSede(int idSede) {
        List<PuntoVenta> puntos = null;
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from PuntoVenta where p.sede.idSede= :idSede";
            Query q = sesion.createQuery(hql);
            q.setParameter("idSede", idSede);
            
            puntos = q.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            sesion.close();
        }
        return puntos;
    }
    
}
