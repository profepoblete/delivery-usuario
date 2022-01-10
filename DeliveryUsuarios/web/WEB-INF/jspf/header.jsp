
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html> 
<c:choose>
    <c:when test="${login.tipoUsuario.idTipoUsuario ==3}"> <!--Cuando el usuario es colaborador/cliente: --> 
        <div id="layoutDefault_content">
            <main> 
                <nav class="navbar navbar-marketing navbar-expand-lg navbar-light" style="background-color:001B37 ;">
                    <div class="container">
                        <img class="img-fluid" src="img/logo-duoc-uc-universidad-catolica.jpg" alt="">

                        <a class="navbar-brand text-dark" href="index">Sitio Delivery</a><button
                            class="navbar-toggler" type="button" data-toggle="collapse"
                            data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                            aria-expanded="false" aria-label="Toggle navigation"><i data-feather="menu"></i></button>
                        <div class="collapse navbar-collapse" id="navbarSupportedContent">
                            <ul class="navbar-nav ml-auto mr-lg-5">
                                <!-- INCIO -->
                                <li class="nav-item"><a class="nav-link" href="index">Inicio </a>
                                </li>
                                <!-- ITEM OFERTAS -->
                                <li class="nav-item dropdown dropdown-xl no-caret">
                                    <a class="nav-link dropdown-toggle" id="navbarDropdownDemos" href="#" role="button"
                                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Tiendas<i
                                            class="fas fa-chevron-right dropdown-arrow"></i></a>
                                    <div class="dropdown-menu dropdown-menu-right animated--fade-in-up mr-lg-n15"
                                         aria-labelledby="navbarDropdownDemos">
                                        <div class="row no-gutters">
                                            <!-- ZONA IZQUIERDA (COVER) -->
                                            <div class="col-lg-5 p-lg-3 bg-img-cover overlay overlay-primary overlay-70 d-none d-lg-block"
                                                 style='background-image: url("https://source.unsplash.com/mqO0Rf-PUMs/500x350")'>
                                                <div
                                                    class="d-flex h-100 w-100 align-items-center justify-content-center">
                                                    <div class="text-white text-center z-1">
                                                        <div class="mb-3"></div>
                                                        <a class="btn btn-white btn-sm text-primary rounded-pill"
                                                           href="index">Todas las ofertas</a>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- SECCIONES -->
                                            <div class="col-lg-7 p-lg-5">
                                                <div class="row">
                                                    <!-- COLUMNA IZQUIERDA -->
                                                    <div class="col-lg-6">
                                                        <h6 class="dropdown-header text-primary">Puntos</h6>
                                                        <c:forEach items="${puntos}" var="t">
                                                            <a class="dropdown-item" href="PuntoVenta?pv=${t.nombre}">${t.nombre}</a>
                                                        </c:forEach>
                                                        <div class="dropdown-divider border-0"></div>
                                                    </div>
                                                    <!-- COLUMNA DERECHA -->
                                                    <div class="col-lg-6">

                                                        <a class="dropdown-item" href="#">Junaeb</a><a
                                                            class="dropdown-item" href="#">Docente</a>
                                                        <div class="dropdown-divider border-0"></div>

                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                                <!-- AYUDA -->
                                <li class="nav-item dropdown no-caret">
                                    <!-- TITULO EN NAV -->
                                    <a class="nav-link dropdown-toggle" id="navbarDropdownDocs" href="#" role="button"
                                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        Ayuda
                                        <i class="fas fa-chevron-right dropdown-arrow"></i>
                                    </a>
                                    <!-- SECCIONES DE AYUDA -->
                                    <div class="dropdown-menu dropdown-menu-right animated--fade-in-up"
                                         aria-labelledby="navbarDropdownDocs">
                                        <!-- AYUDA 1 -->
                                        <a class="dropdown-item py-3">
                                            <div class="icon-stack bg-primary-soft text-primary mr-4">
                                                <i class="fas fa-book-open"></i>
                                            </div>
                                            <div>
                                                <div class="small text-gray-500">¿Cómo comprar?</div>
                                                Para comprar necesita estar registrado en el sistema.
                                            </div>
                                        </a>
                                        <div class="dropdown-divider m-0"></div>
                                        <!-- AYUDA 2 -->
                                        <a class="dropdown-item py-3">
                                            <div class="icon-stack bg-primary-soft text-primary mr-4"><i
                                                    class="fas fa-code"></i></div>
                                            <div>
                                                <div class="small text-gray-500">Como es el pago?</div>
                                                El pago se realiza una vez el producto este en tus manos.
                                            </div>
                                        </a>
                                        <div class="dropdown-divider m-0"></div>
                                        <!-- AYUDA 3 -->
                                        <a class="dropdown-item py-3">
                                            <div class="icon-stack bg-primary-soft text-primary mr-4"><i
                                                    class="fas fa-file"></i></div>
                                            <div>
                                                <div class="small text-gray-500">Opciones de retiro</div>
                                                Puede retirar los productos en tienda o seleccionar delivery a su
                                                ubicacion dentro de la institución
                                            </div>
                                        </a>
                                    </div>
                                </li>
                                <li class="nav-item"><a class="nav-link" href="MisPedidos">Mis Pedidos </a>
                                </li>
                                <!-- USUARIO -->
                                <li class="nav-item dropdown no-caret  dropdown-user">
                                    <a class=" dropdown-toggle" id="navbarDropdownUserImage" href="" role="button"
                                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">

                                        <!-- Imagen usuario en nav -->
                                        <div class="avatar avatar-online avatar-lg mr-1">
                                            <img class="avatar-img img-fluid"
                                                 src="img/Missing_avatar.png">
                                        </div>
                                    </a>
                                    <div class="dropdown-menu dropdown-menu-right border-0 shadow animated--fade-in-up"
                                         aria-labelledby="navbarDropdownUserImage"> 
                                        <!-- Datos usuario -->
                                        <!--la siguiente etiqueta era un h6 pero daba un error 
                                        -no puede tener un div dentro, la cambie por div-->
                                        <div class="dropdown-header d-flex align-items-center">
                                            <img class="dropdown-user-img" src="img/Missing_avatar.png">
                                            <div class="dropdown-user-details">
                                                <div class="dropdown-user-details-name">${login.nombre}</div>
                                                <div class="dropdown-user-details-email">${login.email}</div>
                                            </div>
                                        </div>
                                        <div class="dropdown-divider"></div>
                                        <!-- Cuenta -->
                                        <a class="dropdown-item" href="#!">
                                            <div class="dropdown-item-icon"><i data-feather="settings"></i></div>
                                            Cuenta
                                        </a>
                                        <!-- Pedido -->
                                        <a class="dropdown-item" href="MisPedidos">
                                            <div class="dropdown-item-icon"><i data-feather="file"></i></div>
                                            Mis Pedidos
                                        </a>
                                        <!-- Salir -->
                                        <a class="dropdown-item" href="Login">
                                            <div class="dropdown-item-icon"><i data-feather="log-out"></i></div>
                                            Salir
                                        </a>
                                    </div>
                                </li>
                            </ul>
                            <!-- BOTON COMPRAR -->
                            <a class="btn-blue btn rounded-pill px-4 ml-lg-4" href="DetalleCompra">Comprar<i
                                    class="fas fa-shopping-cart ml-1"></i> ${contadorCarrito}</a>
                        </div>
                    </div>
                </nav>
            </main>
        </div>
    </c:when>
    <c:when test="${login.tipoUsuario.idTipoUsuario ==2}"> <!-- Cuando el usuario es Punto de Venta: -->
        <div id="layoutDefault_content"> 
            <main> 
                <nav class="navbar navbar-marketing navbar-expand-lg navbar-light" style="background-color:001B37 ;">
                    <img class="img-fluid" src="img/logo-duoc-uc-universidad-catolica.jpg" alt="">
                    <a class="navbar-brand text-dark" href="index">Sitio Delivery</a>
                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" 
                            aria-controls="navbarSupportedContent"
                            aria-expanded="false" aria-label="Toggle navigation"><i data-feather="menu"></i> 
                    </button>
                    <ul class="navbar-nav ml-auto mr-lg-5">
                        <!-- INCIO -->
                        <li class="nav-item"><a class="nav-link" href="index">Inicio </a></li>
                        <li class="nav-item"><a class="nav-link" href="Administracion">Listado de Pedidos </a></li>
                        <li class="nav-item"><a class="nav-link" href="Producto">Productos </a></li>
                        <li class="nav-item"><a class="nav-link" href="">Promociones </a></li>

                        <li class="nav-item dropdown no-caret  dropdown-user">
                            <a class=" dropdown-toggle" id="navbarDropdownUserImage" href="" role="button"
                               data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">

                                <!-- Imagen usuaurio en nav -->
                                <div class="avatar avatar-online avatar-lg mr-1">
                                    <img class="avatar-img img-fluid" src="img/logosTiendas/${login.puntoVenta.imagen}"> 
                                </div>
                            </a>
                            <div class="dropdown-menu dropdown-menu-right border-0 shadow animated--fade-in-up"
                                 aria-labelledby="navbarDropdownUserImage">
                                <!-- Datos usuario -->
                                <div class="dropdown-header d-flex align-items-center">
                                    <img class="dropdown-user-img" src="img/logosTiendas/${login.puntoVenta.imagen}" />
                                    <div class="dropdown-user-details">
                                        <div class="dropdown-user-details-name">${login.nombre}</div>
                                        <div class="dropdown-user-details-email">${login.email}</div>
                                    </div>
                                </div>
                                <div class="dropdown-divider"></div>
                                
                                <!-- Cuenta --> <!-- Estaba esta opcion placeholder/vacía, pero nos pidieron ocultarla 
                                y no quitarla, ya que aún no quedaba claro si es que el usuario podría cambiar detalles
                                de su propia cuenta o no, como su password.

                                <a class="dropdown-item" href="#!">
                                    <div class="dropdown-item-icon"><i data-feather="settings"></i></div>
                                    Cuenta
                                </a> -->

                                <!-- Pedido -->
                                <a class="dropdown-item" href="Administracion">
                                    <div class="dropdown-item-icon"><i data-feather="file"></i></div>
                                    Pedidos
                                </a>
                                <!-- Salir -->
                                <a class="dropdown-item" href="Login">
                                    <div class="dropdown-item-icon"><i data-feather="log-out"></i></div>
                                    Salir
                                </a>
                            </div>
                        </li>
                    </ul>
                </nav>
            </main>
        </div>
    </c:when>
    <c:otherwise> <!-- Si el usuario no es Punto de Venta ni es Cliente: (En teoría, esto sería para usuarios administradores, pero no 
        sabemos que funcionalidades únicas tendrían ellos en este sistema; si es que tienen alguna realmente, ya que tendrían su propia 
        plataforma de administración aparte.-->
        <div id="layoutDefault_content">
            <main>
                <nav class="navbar navbar-marketing navbar-expand-lg navbar-light" style="background-color: 001B37;">
                    <div class="container">
                        <img class="img-fluid" src="img/logo-duoc-uc-universidad-catolica.jpg" alt="">

                        <a class="navbar-brand text-dark" href="index">Sitio Delivery</a><button
                            class="navbar-toggler" type="button" data-toggle="collapse"
                            data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                            aria-expanded="false" aria-label="Toggle navigation"><i data-feather="menu"></i></button>
                        <div class="collapse navbar-collapse" id="navbarSupportedContent">
                            <ul class="navbar-nav ml-auto mr-lg-5">
                                <!-- INCIO -->
                                <li class="nav-item"><a class="nav-link" href="index">Inicio </a>
                                </li>
                                <!-- ITEM OFERTAS -->
                                <li class="nav-item dropdown dropdown-xl no-caret">
                                    <a class="nav-link dropdown-toggle" id="navbarDropdownDemos" href="#" role="button"
                                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Tiendas<i
                                            class="fas fa-chevron-right dropdown-arrow"></i></a>
                                    <div class="dropdown-menu dropdown-menu-right animated--fade-in-up mr-lg-n15"
                                         aria-labelledby="navbarDropdownDemos">
                                        <div class="row no-gutters">
                                            <!-- ZONA IZQUIERDA (COVER) -->
                                            <div class="col-lg-5 p-lg-3 bg-img-cover overlay overlay-primary overlay-70 d-none d-lg-block"
                                                 style='background-image: url("https://source.unsplash.com/mqO0Rf-PUMs/500x350")'>
                                                <div
                                                    class="d-flex h-100 w-100 align-items-center justify-content-center">
                                                    <div class="text-white text-center z-1">
                                                        <div class="mb-3"></div>
                                                        <a class="btn btn-white btn-sm text-primary rounded-pill"
                                                           href="index">Todas las ofertas</a>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- SECCIONES -->
                                            <div class="col-lg-7 p-lg-5">
                                                <div class="row">
                                                    <!-- COLUMNA IZQUIERDA -->
                                                    <div class="col-lg-6">
                                                        <h6 class="dropdown-header text-primary">Puntos</h6>
                                                        <c:forEach items="${puntos}" var="t">
                                                            <a class="dropdown-item" href="PuntoVenta?pv=${t.nombre}">${t.nombre}</a>
                                                        </c:forEach>
                                                        <div class="dropdown-divider border-0"></div>

                                                    </div>
                                                    <!-- COLUMNA DERECHA -->
                                                    <div class="col-lg-6">

                                                        <a class="dropdown-item" href="#">Junaeb</a><a
                                                            class="dropdown-item" href="#">Docente</a>
                                                        <div class="dropdown-divider border-0"></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                                <!-- AYUDA -->
                                <li class="nav-item dropdown no-caret">
                                    <!-- TITULO EN NAV -->
                                    <a class="nav-link dropdown-toggle" id="navbarDropdownDocs" href="#" role="button"
                                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Ayuda<i
                                            class="fas fa-chevron-right dropdown-arrow"></i></a>
                                    <!-- SECCIONES DE AYUDA -->
                                    <div class="dropdown-menu dropdown-menu-right animated--fade-in-up"
                                         aria-labelledby="navbarDropdownDocs">
                                        <!-- AYUDA 1 -->
                                        <a class="dropdown-item py-3">
                                            <div class="icon-stack bg-primary-soft text-primary mr-4"><i
                                                    class="fas fa-book-open"></i></div>
                                            <div class="text-dark">
                                                <div class="small text-gray-500">¿Como comprar?</div>
                                                Para comprar necesita estar registrado en el sistema.
                                            </div>
                                        </a>
                                        <div class="dropdown-divider m-0"></div>
                                        <!-- AYUDA 2 -->
                                        <a class="dropdown-item py-3">
                                            <div class="icon-stack bg-primary-soft text-primary mr-4"><i
                                                    class="fas fa-code"></i></div>
                                            <div class="text-dark">
                                                <div class="small text-gray-500">¿Cómo es el pago?</div>
                                                El pago se realiza una vez el producto este en tus manos.
                                            </div>
                                        </a>
                                        <div class="dropdown-divider m-0"></div>
                                        <!-- AYUDA 3 -->
                                        <a class="dropdown-item py-3">
                                            <div class="icon-stack bg-primary-soft text-primary mr-4"><i
                                                    class="fas fa-file"></i></div>
                                            <div class="text-dark">
                                                <div class="small text-gray-500">Opciones de retiro</div>
                                                Puede retirar los productos en tienda o seleccionar delivery a su
                                                ubicacion dentro de la institución
                                            </div>
                                        </a>
                                    </div>
                                </li>
                                <!-- USUARIO -->
                                <li class="nav-item dropdown no-caret  dropdown-user">
                                    <a class=" dropdown-toggle" id="navbarDropdownUserImage" href="" role="button"
                                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">

                                        <!-- Imagen usuario en nav -->
                                        <div class="avatar avatar-online avatar-lg mr-1">
                                            <img class="avatar-img img-fluid"
                                                 src="img/Missing_avatar.png">
                                        </div>
                                    </a>
                                    <div class="dropdown-menu dropdown-menu-right border-0 shadow animated--fade-in-up"
                                         aria-labelledby="navbarDropdownUserImage">
                                        <!-- Datos usuario -->
                                        <!--la siguiente etiqueta era un h6 pero daba un error 
                                        -no puede tener un div dentro, la cambie por div-->

                                        <!-- Ingresar -->
                                        <a class="dropdown-item" href="Login">
                                            <div class="dropdown-item-icon"><i data-feather="log-in"></i></div>
                                            Ingresar
                                        </a>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </nav>
            </main>
        </div>
    </c:otherwise>
</c:choose>
