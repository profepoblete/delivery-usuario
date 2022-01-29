<%-- 
    Document   : MisPedidos
    Created on : 25-11-2020, 19:08:00
    Author     : dream
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <title>Mis pedidos</title>
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
            <div id="layoutDefault_content" class="col-12">
                <main>
                    <jsp:include page="../WEB-INF/jspf/header.jsp" />
                    <br><br><br>
                    <!--TABLA DE PEDIDOS-->
                    <div class="col-lg-12 col-xl-12 center-block">
                        <div class="table-responsive">
                            <table class="table table-striped table-hover" id="tablaPrincipal">
                                <thead class="thead-dark">
                                    <tr>
                                        <th scope="col">n° pedido</th>
                                        <th scope="col">Fecha</th>
                                        <th scope="col">Usuario</th>
                                        <th scope="col">Ubicacion</th>
                                        <th scope="col">Comentario</th>
                                        <th scope="col">Total</th>
                                        <th scope="col">MetodoPago</th>
                                        <th scope="col">TipoEntrega</th>
                                        <th scope="col">Estado</th>
                                        <th scope="col">Cambiar Estado</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${misPedidos}" var="pedidoBuscado">
                                        <tr id="trPedido">
                                            <th scope="row">${pedidoBuscado.idPedido}</th>
                                            <td>${pedidoBuscado.fechaventa}</td>
                                            <td>${pedidoBuscado.usuario.nombre} ${pedidoBuscado.usuario.apellido}</td>
                                            <td>${pedidoBuscado.ubicacion.nombreEdificio} piso ${pedidoBuscado.ubicacion.piso}</td>
                                            <td>${pedidoBuscado.detalleUbicacion}</td>
                                            <td>${pedidoBuscado.total}</td>
                                            <td>${pedidoBuscado.metodoPago.descripcion}</td>
                                            <td>${pedidoBuscado.tipoEntrega.descripcion}</td>
                                            <td>${pedidoBuscado.estado.descripcion}</td>
                                            <td>
                                                <!--Aqui el test debe cambiar de acuerdo al registro de estado q tengan en bd-->
                                                <form method="POST" action="MisPedidos">
                                                    <input type="submit" 
                                                           class="<c:choose>
                                                               <c:when test="${pedidoBuscado.estado.idEstado!=5 and pedidoBuscado.estado.idEstado!=7}">
                                                                   btn btn-info
                                                               </c:when>
                                                               <c:when test="${pedidoBuscado.estado.idEstado==7}">
                                                                   btn btn-success
                                                               </c:when>
                                                               <c:otherwise>
                                                                   btn btn-warning
                                                               </c:otherwise>
                                                           </c:choose>" name="btnConfirmacion" id="btnConfirmacion" value="Confirmar Entrega" <c:if test="${pedidoBuscado.estado.idEstado!=5 and pedidoBuscado.estado.idEstado!=7}"> data-toggle="tooltip" data-placement="bottom" title="Podras confirmar cuando tu pedido se encuentre en estado 'Entregado'"</c:if>/>
                                                               
                                                    <input type="hidden" name="idPedidoConfirmado" value="${pedidoBuscado.idPedido}"/>
                                                </form>

                                            </td>
                                            </tr>
          
                                            <tr id="trDetalle">
                                                <td colspan="10">
                                                    <table>
                                                        <tr>
                                                            <th>Imagen</th>
                                                            <th>Producto</th>
                                                            <th>Valor unitario</th>
                                                        </tr>
                                                      
                                                    <c:forEach items="${pedidoBuscado.detallePedidos}" var="detalleP">
                                                        <tr>
                                                            <td><img src='img/producto/${detalleP.producto.imagen}' class='img-fluid' width='100px'</td>
                                                            <td>${detalleP.producto.nombre}</td>                                                            
                                                            <td>$ ${detalleP.producto.precio}</td>
                                                        </tr>
                                                    </c:forEach>
                                                </table>
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
            
            <jsp:include page="../Mantenedor/footerMantenedor.jspf" />    
        </div>
        <script src="https://code.jquery.com/jquery-3.4.1.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js" crossorigin="anonymous">
        </script>
        <!-- POR EL MOMENTO NO INFLUYE EL SCRIPT -->
        <script src="js/scripts.js"></script>
        <script src='js/excepciones.js'></script>
    </body>
</html>
