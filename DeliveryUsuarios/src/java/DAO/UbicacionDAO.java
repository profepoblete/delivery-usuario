/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.List;
import modelo.Ubicacion;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author dream
 */
public class UbicacionDAO {

    public List<Ubicacion> listarUbicacionesSede(int idSede) {
        List<Ubicacion> ubicaciones = null;
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from Ubicacion as u where u.sede.idSede= :idSede order by u.nombreEdificio";
            Query q = sesion.createQuery(hql);
            q.setParameter("idSede", idSede);

            ubicaciones = q.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            sesion.close();
        }
        return ubicaciones;
    }
}
