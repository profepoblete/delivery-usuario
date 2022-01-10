<%-- 
    Document   : login
    Created on : 16-11-2020, 10:06:23
    Author     : Fabiola Saez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <title>Login</title>

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
        <div id="layoutAuthentication">
            <jsp:include page="WEB-INF/jspf/header.jsp"/>
            <div id="layoutAuthentication_content" class="bg-duoc">
                <main>
                    <div class="container">
                        <div class="row justify-content-center">
                            <div class="col-lg-5">
                                <div class="card shadow-lg border-0 rounded-lg mt-5">
                                    <div class="card-header justify-content-center">
                                        <img class="img-fluid" src="img/logo-duoc-uc-universidad-catolica.jpg" />

                                        <h3 class="font-weight-light my-4">Iniciar</h3>
                                    </div>
                                    <div class="card-body">
                                        <form method="POST" action="Login" name="login">
                                            <div class="form-group">
                                                <label class="small mb-1" for="email">Email</label>
                                                <input class="form-control py-4" id="email" name="email" type="email" placeholder="user@example.com" value=""/>
                                            </div>
                                            <div class="form-group">
                                                <label class="small mb-1" for="contrasena">Password</label>
                                                <input class="form-control py-4" id="contrasena" name="contrasena" type="password" placeholder="Ingrese password" value=""/>
                                            </div>
                                            <div
                                                class="form-group d-flex align-items-center justify-content-between mt-4 mb-0">
                                                <a class="small" href="Mantenedor/recuperar_contrasena.jsp">Recuperar contraseña?</a>
                                                <input type="submit" name="Ingresar" id="ingresar" value="Ingresar" class="btn btn-primary"/></div>
                                        </form>
                                        <c:if test="${not empty msjerror}">
                                            <br><br>
                                            <div class="alert alert-danger">
                                                ${msjerror}
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
            </div>

            <jsp:include page="WEB-INF/jspf/footer.jspf" />
            <script src="https://code.jquery.com/jquery-3.4.1.min.js" crossorigin="anonymous"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js" crossorigin="anonymous">
            </script>
            <!-- POR EL MOMENTO NO INFLUYE EL SCRIPT -->
            <script src="js/scripts.js"></script>
        </div>
    </body>
</html>
