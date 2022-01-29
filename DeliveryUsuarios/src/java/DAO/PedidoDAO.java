/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.List;
import modelo.DetallePedido;
import modelo.Estado;
import modelo.MetodoPago;
import modelo.Pedido;
import modelo.Producto;
import modelo.TipoEntrega;
import modelo.Ubicacion;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author dream
 */
public class PedidoDAO {
    public int guardarPedido(Pedido p) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        int id = 0;
        try {
            Transaction tx = sesion.beginTransaction();
            id = (int) sesion.save(p);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            sesion.close();
        }
        return id;
    }
    public int guardarDetallePedido(DetallePedido detalle) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        int id = 0;
        try {
            Transaction tx = sesion.beginTransaction();
            id = (int) sesion.save(detalle);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            sesion.close();
        }
        return id;
    }
    
    public void modificarPedido(Pedido p) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        
        try {
            Transaction tx = sesion.beginTransaction();
            sesion.update(p);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            sesion.close();
        }
    } 
    //METODO PARA MOSTRAR PEDIDOS EN PUNTO VENTA
    public List<Pedido> listarPedidosActivosbyPuntoVenta(int idPuntoVenta) {
        List<Pedido> pedidos = null;
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            Query q = sesion.createQuery("Select distinct p from Pedido p, DetallePedido dp where dp.pedido = p.idPedido"
                    + " and p.estado.idEstado not in (5,6,7)"
                    + " and dp.producto.puntoVenta.idPuntoVenta= :punto order by p.idPedido DESC").setParameter("punto",idPuntoVenta);
            //q.setParameter("idPuntoVenta", idPuntoVenta);
            pedidos = q.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            sesion.close();
        }
        return pedidos;
    }
    
    //METODO PARA MOSTRAR PEDIDOS EN misPedidos
    public List<Pedido> listarPedidosbyUsuario(int idUsuario) {
        List<Pedido> pedidos = null;
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from Pedido p where p.usuario.idUsuario= :idUsuario order by p.idPedido DESC";
            Query q = sesion.createQuery(hql);
            q.setParameter("idUsuario", idUsuario);
            
            pedidos = q.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            sesion.close();
        }
        return pedidos;
    }
    
    public List<DetallePedido> listarDetallePedidosbyPedido(int idPedido) {
        List<DetallePedido> pedidos = null;
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from DetallePedido as pd where pd.pedido.idPedido= :idPedido";
            Query q = sesion.createQuery(hql);
            q.setParameter("idPedido", idPedido);
            
            pedidos = q.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            sesion.close();
        }
        return pedidos;
    }
    
    public List<TipoEntrega> listarTipoEntrega() {
        List<TipoEntrega> tiposEntrega = null;
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from TipoEntrega";
            Query q = sesion.createQuery(hql);
            
            tiposEntrega = q.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            sesion.close();
        }
        return tiposEntrega;
    }
    
    public List<Estado> listarEstado() {
        List<Estado> estados = null;
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from Estado";
            Query q = sesion.createQuery(hql);
            
            estados = q.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            sesion.close();
        }
        return estados;
    }
    
    public List<MetodoPago> listarMetodoPago() {
        List<MetodoPago> metodos = null;
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from MetodoPago";
            Query q = sesion.createQuery(hql);
            
            metodos = q.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            sesion.close();
        }
        return metodos;
    }
}
