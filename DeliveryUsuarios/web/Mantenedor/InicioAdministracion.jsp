<%-- 
    Document   : InicioAdministracion
    Created on : 10-11-2020, 18:37:00
    Author     : dream
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="refresh" content="30;url=Administracion" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <title>Inicio Punto Venta</title>
        <!-- ESTILOS NECESARIOS PARA EL DISEÑO (sacado de plantilla bootstrap) -->
        <link href="css/styles.css" rel="stylesheet" />
        <!-- FLECHAS Y ICONOS NECESARIOS PARA EL DISEÑO (sacado de plantilla bootstrap) -->
        <link rel="icon" type="image/x-icon" href="../img/logo_duoc.png" />
        <script data-search-pseudo-elements defer
        src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.11.2/js/all.min.js" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/feather-icons/4.24.1/feather.min.js" crossorigin="anonymous">
        </script>
        
    </head>
    <body>
        <div id="layoutDefault">
            <jsp:include page="../WEB-INF/jspf/header.jsp" />
            <div id="layoutDefault_content" class="col-12">
                <main>
                    <!--TABLA DE PEDIDOS-->
                    <div class="col-lg-12 col-xl-12 center-block">
     
                        <br/>
                        <div class="table-responsive">
                            <table class="table table-striped table-hover" id="tablaPrincipal">

                                <thead class="thead-dark">
                                    <tr>
                                        <th scope="col">N° Pedido</th>
                                        <th scope="col">Fecha</th>
                                        <th scope="col">Usuario</th>
                                        <th scope="col">Ubicacion</th>
                                        <th scope="col">Comentario</th>
                                        <th scope="col">Total</th>
                                        <th scope="col">Metodo de Pago</th>
                                        <th scope="col">Tipo de Entrega</th>
                                        <th scope="col">Estado</th>
                                        <th scope="col">Detalles</th>
                                        <th scope="con">Exportar</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:if test="${msjPedidos!=null}">
                                        <tr>
                                            <td colspan="10">
                                                <div class="alert alert-dark text-center">${msjPedidos}</div>
                                            </td>
                                        </tr>
                                    </c:if>
                                    <c:forEach items="${listaPedidos}" var="lp">
                                        <tr id="trPedido">
                                            <th scope="row">${lp.idPedido}</th>
                                            <td>${lp.fechaventa}</td>
                                            <td>${lp.usuario.nombre} ${lp.usuario.apellido}</td>
                                            <td>${lp.ubicacion.nombreEdificio} piso ${lp.ubicacion.piso}</td>
                                            <td>${lp.detalleUbicacion}</td>
                                            <td>${lp.total}</td>
                                            <td>${lp.metodoPago.descripcion}</td>
                                            <td>${lp.tipoEntrega.descripcion}</td>
                                            <td>${lp.estado.descripcion}</td>
                                            <td>
                                                <form method="POST" action="Administracion">
                                                    <input type="submit" class="btn btn-info" value="Ver Detalle"/>
                                                    <input type="hidden" name="lp.idPedido" value="${lp.idPedido}"/>
                                                </form>
                                            </td>
                                            <td>
                                                <form target="_blank" action="../Administracion" id="frmReporte" method="POST">
                                                    <input type="hidden" name="accion" id="accion"/>
                                                    <button onclick="reporte('exportarPDF')" type="button" class="btn btn-warning btn-sm"><i class="fas fa-file-pdf"></i> Exportar PDF</button>
                                                    <input type="hidden" name="lista" id="lista"/>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <!-- FIN TABLA DE PEDIDOS-->
                </main>
            </div>

            <jsp:include page="../WEB-INF/jspf/footer.jspf" />
        </div>
        
        <script src="https://code.jquery.com/jquery-3.4.1.min.js" crossorigin="anonymous"></script>
        
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js" crossorigin="anonymous">
        </script>
        <!-- POR EL MOMENTO NO INFLUYE EL SCRIPT -->
        <script src="js/scripts.js"></script>
        <script src="js/creacionReporte.js"></script>
        
    </body>
</html>
