/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function confirmarCambioCarrito() {
    var confirmar;
    confirmar=confirm("Ya tienes productos de otra tienda en tu carrito \n ¿Quieres vaciar tu carrito y agregar este?");
    if (confirmar) {
        //enviar el formulario
        document.formularioAgregarCarrito.submit();
    }else{
        // no sucede nada
    }
    
}