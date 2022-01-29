$(document).ready(function(e){
   
    // inicializo tooltip
    $('[data-toggle="tooltip"]').tooltip();
    
    // click en elemento btnConfirmacion, se modifica la clase segun idEstado
    $("input[name='btnConfirmacion'").click(function(e){

        // obtengo contenido de class
        var estiloBoton = $(this).attr("class").trim();
        
        if(estiloBoton === "btn btn-info" || estiloBoton === "btn btn-success"){
            e.preventDefault();
        }
    });
    
    
    $("#btn").click(function(event){
        
        var precio = $("#prec").val().length;
        if($("#msje > p ").length){
               event.preventDefault();
        }else{
            // si el precio excede la longitud que esta en la bd, se envia una etiqueta p dentro del div
               if(precio >= 11){
                    var mensaje =`
                    <p class="alert alert-danger" > Numero ingresado excede el maximo </p>
                    `
                    if($("#msje").length > 0){
                    setTimeout(function(){
                        $("#msje > p").remove();
                    },6000);
                    };
                    $("#msje").append(mensaje);
                    event.preventDefault();
               };
               // si la imagen es vacio, se manda una etiqueta p dentro del div
               if($("#imagen").val() === ""  ){
               mensaje =`
               <p class="alert alert-danger" > Selecciona una imagen de visualización </p>
               `
               if($("#msje").length > 0){
                    setTimeout(function(){
                         $("#msje > p").remove();
                    },6000);                                     
               };
               $("#msje").append(mensaje);
                   event.preventDefault();
                };
               }  
        });
        
    });
             