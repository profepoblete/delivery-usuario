<%-- 
    Document   : detalle_compra
    Created on : 10-11-2020, 15:40:31
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
        <title>Detalle compra</title>
        <!-- ESTILOS NECESARIOS PARA EL DISEÑO (sacado de plantilla bootstrap) -->
        <link href="css/styles.css" rel="stylesheet" />
        <!-- FLECHAS Y ICONOS NECESARIOS PARA EL DISEÑO (sacado de plantilla bootstrap) -->
        <link rel="icon" type="image/x-icon" href="img/logo_duoc.png" />
        <script data-search-pseudo-elements defer
        src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.11.2/js/all.min.js" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/feather-icons/4.24.1/feather.min.js" crossorigin="anonymous">
        </script>
    </head>
    <body>
        <div id="layoutDefault">
            <jsp:include page="../WEB-INF/jspf/header.jsp"/>
            <!----------------------------------------------------------------------->    
            <section class="bg-white pt-5 pb-10">

                <div class="container">


                    <div class="row">
                        <!-- SECCION IZQUIERDA - DE OPCIONES -->
                        <div class="col-lg-4 col-xl-3 mb-5">
                            <!-- Cancelar compra -->
                            <div class="card mb-2">
                                <div class="list-group list-group-flush small">
                                    <!-- TODOS -->
                                    <a class="list-group-item list-group-item-action " href="DetalleCompra?op=Cancelar"><i
                                            class="fas fa-times-circle fa-fw mr-2 text-gray-400"></i>Cancelar
                                    </a>

                                </div>
                            </div>


                            <!-- forma de  envio -->
                            <div class="card rounded-lg text-dark ">
                                <form method="POST" action="DetalleCompra">
                                    <div class="card-header py-3 ">
                                        <!-- Forma de envio -->

                                        <div class="form-row container ">
                                            <c:if test="${msjErrorCarrito!=null}">
                                                <div class="alert alert-danger">${msjErrorCarrito}</div>
                                            </c:if>
                                            <div class="custom-control custom-radio  col-sm-8">
                                                <input class="custom-control-input" id="customRadio1" type="radio"
                                                       name="customRadio" value="1">
                                                <label class="custom-control-label" for="customRadio1">Retiro</label>
                                            </div>
                                            <div class="custom-control custom-radio col-sm-8">
                                                <input class="custom-control-input" id="customRadio2" type="radio"
                                                       name="customRadio" value="2">
                                                <label class="custom-control-label" for="customRadio2">Despacho</label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="card-body">
                                        <!-- Torre y Piso -->
                                        <div class="form-row">

                                            <div class="form-group col-md-12">
                                                <!-- Ubicaciones -->
                                                <label for="cboUbicacion">Ubicacion</label>
                                                <select class="form-control" id="cboUbicacion" name="cboUbicacion">
                                                    <option value="">Selecciona</option>
                                                    <c:forEach var="ub" items="${ubicaciones}">
                                                        <option value="${ub.idUbicacion}">${ub.nombreEdificio} piso:${ub.piso}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="form-group col-md-6">
                                                <!-- piso -->
                                                <!--<label for="piso">Piso</label><select class="form-control"
                                                                                      id="piso">
                                                    <option>Selecciona</option>
                                                    <option>1</option>
                                                    <option>2</option>

                                                </select>-->
                                            </div>
                                        </div>

                                        <!-- Sala o ubicación -->
                                        <div class="form-group">
                                            <label class="small text-gray-600" for="detalleUbicacion">Sala/Ubicación</label>
                                            <input class="form-control rounded-pill" id="detalleUbicacion" name="detalleUbicacion" type="text" />
                                        </div>
                                        <!--metodo pago-->
                                        <div class="form-row container">
                                            <div class="radio  col-sm-12">
                                                <label><input type="radio" name="rbPago" value="1">Efectivo</label>
                                            </div>
                                            <div class="radio col-sm-12">
                                                <label><input type="radio" name="rbPago" value="2">Tarjeta</label>
                                            </div>
                                        </div>
                                        <!-- BOTON -->
                                        <input class="btn btn-primary btn-marketing btn-block rounded-pill mt-4"
                                               type="submit" value="Ordenar" name="enviar" id="enviar"/>

                                    </div>
                                </form>
                            </div>
                        </div>
                        <!-- SECCION DERECHA - DE CASOS -->
                        <div class="col-lg-8 col-xl-9">
                            <div class="table-responsive">
                                <table class="table">

                                    <thead class="thead-dark">
                                        <tr>
                                            <th scope="col">n°</th>
                                            <th scope="col"></th>
                                            <th scope="col">Producto</th>
                                            <th scope="col">Cantidad</th>
                                            <th scope="col">Precio</th>
                                            <th scope="col">Subtotal</th>
                                            <th scope="col"></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:if test="${msjCarrito!=null}">
                                            <tr>
                                                <td colspan="6">
                                                    <div class="alert alert-dark text-center">${msjCarrito}</div>
                                                </td>
                                            </tr>
                                        </c:if>
                                        <c:set var="contador" value="${1}" />
                                        <c:forEach items="${carrito}" var="c">
                                            <tr>
                                                <th scope="row">${contador}</th>
                                                <td><img src="img/producto/${c.producto.imagen}" width="120" height="100"></td>
                                                <td>${c.producto.nombre}</td>
                                                <td>${c.cantidad}</td>
                                                <td>${c.producto.precio}</td>
                                                <td>${c.subtotal}</td>
                                                <td>
                                                    <form method="POST" action="DetalleCompra">
                                                        <input type="submit" value="Quitar" name="enviar" id="enviar"/>
                                                        <input type="hidden" value="${c.producto.idProducto}" name="idProducto" id="idProducto"/>
                                                    </form>
                                                </td>
                                            </tr>
                                            <c:set var="contador" value="${contador + 1}" />
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- EFECTO REDONDO CON COLOR DE FONDO -->
                <!-- <div class="svg-border-rounded text-dark">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 144.54 17.34" preserveAspectRatio="none"
                        fill="currentColor">
                        <path d="M144.54,17.34H0V0H144.54ZM0,0S32.36,17.34,72.27,17.34,144.54,0,144.54,0"></path>
                    </svg>
                </div> -->
                <!-- EFECT DIGANAL DE COLOR DE FONDO -->
                <div class="svg-border-angled text-dark">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100" preserveAspectRatio="none"
                         fill="currentColor">
                    <polygon points="0,100 100,0 100,100"></polygon>
                    </svg>
                </div>
            </section>


            <!------------------------------------------------------------------------------------------------------------------>
            <jsp:include page="../WEB-INF/jspf/footer.jspf" />
            <script src="https://code.jquery.com/jquery-3.4.1.min.js" crossorigin="anonymous"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js" crossorigin="anonymous">
            </script>
            <!-- POR EL MOMENTO NO INFLUYE EL SCRIPT -->
            <script src="js/scripts.js"></script>
        </div>
    </body>
</html>
