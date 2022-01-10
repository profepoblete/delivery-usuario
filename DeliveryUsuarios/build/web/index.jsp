<%-- 
    Document   : index
    Created on : 09-11-2020, 15:08:21
    Author     : fabiola sáez
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<!DOCTYPE html> 
<html> 
    <head> 
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
        <meta http-equiv="X-UA-Compatible" content="IE=edge" /> 
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" /> 
        <title>Delivery Duoc</title> 

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
        <div id="layoutDefault_content">
            <jsp:include page="WEB-INF/jspf/header.jsp"/>
            <main>
                <div id="layoutDefault">

                    <!-- SECCCION TITULO Y TIENDAS -->
                    <section class="bg-light py-5">
                        <div class="container">
                            <!-- TITULO -->
                            <div class="row justify-content-center">
                                <div class="col-lg-6">
                                    <div class="mb-5 text-center">
                                        <div class="text-xs text-uppercase-expanded text-primary mb-2">Tiendas a disposición
                                        </div>
                                        <p class="lead mb-0">Recuerde que el horario de compras de entrega es entre las
                                            13:00-14:30 </p>
                                    </div>
                                </div>
                            </div>
                            <!-- TIENDAS (Tamaño perfecto para foto de 800 * 500) -->
                            <div class="row justify-content-center">
                                <c:forEach items="${puntos}" var="t">
                                    <div class="col-md-6 col-lg-4 col-xl-3">
                                        <a class="card lift" href="PuntoVenta?pv=${t.nombre}"><img class="card-img-top" src="img/logosTiendas/${t.imagen}" alt="..." />
                                            <div class="card-body text-center py-3">
                                                <h6 class="card-title mb-0">${t.nombre}</h6>
                                            </div>
                                        </a>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                        <!-- SEPARADOR DE ESPACION ENTRE TIENDAS  Y EL SUSCRIBIRSE A PROMOCIONES -->
                        <div class="svg-border-angled text-white">
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100" preserveAspectRatio="none"
                                 fill="currentColor">
                            <polygon points="0,100 100,0 100,100"></polygon>
                            </svg>
                        </div>
                    </section>
                    <!-- SECCION SUSCRIBIRSE Y  PLATOS ESTRELLA-->
                    <section class="bg-white pt-5 pb-10">
                        <div class="container">
                            <!-- SUSCRIBETE -->
                            <div class="card mt-n15 mb-10 z-1">
                                <div class="card-body p-5">
                                    <div class="row align-items-center">
                                        <div class="col-lg-6">
                                            <h4>Susribete a las promociones diarias</h4>
                                            <p class="lead text-gray-500 mb-0">
                                                Debes estar atento a tus correos, todos los dias promociones diferentes.
                                            </p>
                                        </div>
                                        <div class="col-lg-6">
                                            <div class="input-group mb-2">
                                                <input class="form-control form-control-solid" type="text"
                                                       placeholder="youremail@example.com" aria-label="Recipient's username"
                                                       aria-describedby="button-addon2" />
                                                <div class="input-group-append"><button class="btn btn-primary"
                                                                                        id="button-addon2" type="button">Suscribirme</button></div>
                                            </div>
                                            <div class="small text-gray-500">Puedes cancelar la suscripción cuando lo
                                                desees.</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- PLATOS ESTRELLA -->
                            <!-- Titutlo y descripción (Tamaño perfecto de imagen de 800 x 500) -->
                            <div class="row justify-content-center">
                                <div class="col-lg-6">
                                    <div class="mb-5 text-center">
                                        <div class="text-xs text-uppercase-expanded text-primary mb-2">Productos Estrella
                                        </div>
                                        <p class="lead mb-0">Te seleccionamos los platos mejor valorados para ti!
                                        </p>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <!-- PLATO N1 -->
                                <div class="col-lg-4 mb-5 mb-lg-0">
                                    <a class="card lift" href="#!"><img class="card-img-top"
                                                                        src="img/platos/queso-cheddar-salsa.jpg" alt="..." />
                                        <div class="card-body text-center py-3">
                                            <h6 class="card-title mb-0">Papitas con queso Cheddar</h6>
                                            <div class="text-yellow"><i class="fas fa-star"></i><i
                                                    class="fas fa-star"></i><i class="fas fa-star"></i><i
                                                    class="fas fa-star"></i><i class="fas fa-star-half-alt"></i></div>

                                            <div class="small">Disfruta de unas deliciosas papitas bañadas con queso Cheddar
                                            </div>
                                        </div>
                                        <div class="card-footer text-center text-xs"><i
                                                class="fas fa-store-alt mr-1"></i>BISTRO</div>
                                    </a>
                                </div>
                                <!-- PLATO N2 -->
                                <div class="col-lg-4 mb-5 mb-lg-0">
                                    <a class="card lift" href="#!"><img class="card-img-top"
                                                                        src="img/platos/Gohan-Salmón.jpg" alt="..." />
                                        <div class="card-body text-center py-3">
                                            <h6 class="card-title mb-0">Gohan Salmon</h6>
                                            <div class="text-yellow"><i class="fas fa-star"></i><i
                                                    class="fas fa-star"></i><i class="fas fa-star"></i><i
                                                    class="fas fa-star"></i><i class="fas fa-star-half-alt"></i></div>

                                            <div class="small">Disfruta del mejor sushi en plato</div>
                                        </div>
                                        <div class="card-footer text-center text-xs"><i
                                                class="fas fa-store-alt mr-1"></i>BISTRO</div>
                                    </a>
                                </div>
                                <!-- PLATO N3 -->
                                <div class="col-lg-4">
                                    <a class="card lift" href="#!"><img class="card-img-top" src="img/platos/bigmac.jpg"
                                                                        alt="..." />
                                        <div class="card-body text-center py-3">
                                            <h6 class="card-title mb-0">Big Mac</h6>
                                            <div class="text-yellow"><i class="fas fa-star"></i><i
                                                    class="fas fa-star"></i><i class="fas fa-star"></i><i
                                                    class="fas fa-star-half-alt"></i><i class="far fa-star"></i></div>

                                            <div class="small">Contempla la famosa Big Mac</div>
                                        </div>
                                        <div class="card-footer text-center text-xs"><i
                                                class="fas fa-store-alt mr-1"></i>McDonald</div>
                                    </a>
                                </div>
                            </div>
                        </div>
                        <div class="svg-border-angled text-dark">
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100" preserveAspectRatio="none"
                                 fill="currentColor">
                            <polygon points="0,100 100,0 100,100"></polygon>
                            </svg>
                        </div>
                    </section>
                    <jsp:include page="WEB-INF/jspf/footer.jspf" />
                    <script src="https://code.jquery.com/jquery-3.4.1.min.js" crossorigin="anonymous"></script>
                    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js" crossorigin="anonymous">
                    </script>
                    <!-- POR EL MOMENTO NO INFLUYE EL SCRIPT -->
                    <script src="js/scripts.js"></script>
                </div>
            </main>
        </div>
    </body>
</html>
