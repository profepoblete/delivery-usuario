/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function reporte(accion){
    $.get('../Administracion?accion=lista', function(r){
        if(r){
            $('#accion').val(accion);
            $('#lista').val(JSON.stringify(r));
            $('#frmReporte').submit();
        }else{
            alert('El reporte no ha sido posible generarse' +  r);
        }
    });
    
}