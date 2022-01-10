function cargarArchivo(archivo){
    var archivo= archivo.files[0];
    var objHidden=document.guardarProducto.nombreImagen;
    objHidden.value = archivo.name;   
    document.guardarProducto.target="null";
    document.getElementById("sqr_imgProd").src = "img/producto/"+archivo.name;
}


