<%-- 
    Document   : PuntoVenta
    Created on : 17-11-2020, 12:35:43
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
        <title>${puntoventa.nombre}</title>
        <!-- ESTILOS NECESARIOS PARA EL DISEÑO (sacado de plantilla bootstrap) -->
        <link href="css/styles.css" rel="stylesheet" />
        <!-- FLECHAS Y ICONOS NECESARIOS PARA EL DISEÑO (sacado de plantilla bootstrap) -->
        <link rel="icon" type="image/x-icon" href="img/logo_duoc.png" />
        <script data-search-pseudo-elements defer
        src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.11.2/js/all.min.js" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/feather-icons/4.24.1/feather.min.js" crossorigin="anonymous">
        </script>
        <script src="js/alertas.js" type="text/javascript"></script>
    </head>
    <body>
        <script src="js/jquery-3.5.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <div id="layoutDefault">
            <jsp:include page="../WEB-INF/jspf/header.jsp"/>
            <!-- LOGO DE TIENDA y SECCION BUSCAR -->
            <div class="container">
                <div class="d-flex justify-content-center pb-5 ">
                    <img src="img/logosTiendas/${puntoventa.imagen}" class="img-fluid" style="width: 25vw;">
                </div>
                <!-- SECCIÓN BUSCAR -->
                <form method="POST" action="PuntoVenta">
                    <div class="input-group mb-5">
                        <div class="input-group-prepend">
                            <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown"
                                    aria-haspopup="true" aria-expanded="false">Categorias</button>
                            <div class="dropdown-menu animated--fade-in-up">
                                 <a class="dropdown-item" href="PuntoVenta?pv=${puntoventa.nombre}">Todos</a>
                                <c:forEach items="${categorias}" var="c">
                                    <a class="dropdown-item" href="PuntoVenta?pv=${puntoventa.nombre}-${c.descripcion}">${c.descripcion}</a>
                                </c:forEach>
                            </div>
                        </div>
                        <input class="form-control" type="text" aria-label="Text input with dropdown button"
                               placeholder="Busca tu pedido!" name="textoBusqueda" id="textoBusqueda"/>
                        <div class="input-group-append">
                            <input class="btn btn-info " type="submit"  value="Buscar"/>
                        </div>
                    </div>
                </form>

                <div class="row" name="divProductos" id="divProductos"></div>    
                <c:forEach items="${categoriasProducto}" var="cat">
                    <section class="bg-white py-5 ">
                        <div class="container">
                            <div class="d-flex align-items-center justify-content-between mb-4">
                                <h3 class="mb-0" >${cat.descripcion}</h3>
                            </div>
                            <div class="row">
                                <!-- PRODUCTO 1 -->
                                <c:forEach items="${productos}" var="p"> 
                                    <c:if test="${p.categoria.idCategoria == cat.idCategoria}"> 
                                        <div class="col-lg-4 mb-5 mb-lg-0">
                                            <a class="card lift h-100" href="#!">
                                                <img class="card-img-top" src="img/producto/${p.imagen}" alt="..." />
                                                <div class="card-body"> 
                                                    <h3 class="text-primary mb-0">${p.precio}</h3> 
                                                    <div class="small text-gray-800 font-weight-500">${p.nombre}</div>
                                                </div>
                                                <div class="card-footer bg-transparent border-top d-flex align-items-center">
                                                    <form method="POST" action="PuntoVenta" name="formularioAgregarCarrito">
                                                        <button  class="btn btn-light btn-block" 
                                                                 <c:if test="${esOtroPunto==0}">type="submit"</c:if>
                                                                 <c:if test="${esOtroPunto==1}">type="button" onclick="confirmarCambioCarrito()"</c:if>
                                                                 <c:if test="${login==null || login.tipoUsuario.idTipoUsuario == 2}">disabled</c:if>>Agregar</button>
                                                        <input type="hidden" name="idProducto" id="idProducto" value="${p.idProducto}"/>
                                                    </form>
                                                </div>
                                            </a>
                                        </div>
                                    </c:if>
                                </c:forEach> 
                            </div>
                    </section>
                </c:forEach>
            </div>    
            <jsp:include page="../WEB-INF/jspf/footer.jspf" />
            <!-- POR EL MOMENTO NO INFLUYE EL SCRIPT -->
            <script src="js/scripts.js"></script>
        </div>
    </body>
</html>
