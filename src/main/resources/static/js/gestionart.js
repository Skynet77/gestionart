var suggestProducto = null;
var suggestCliente = null;
var LONG_MAX_DECIMALES_MONTO=2;
var LONG_MAX_DECIMALES_MONTO_COTIZACION=4;

function crearDataTable(dataTableId, ajaxSource, columnas, editUrl){
			console.log("creando DT:", dataTableId, ajaxSource, columnas, editUrl)
			
			
			 //var dataTable = $('#'+ dataTableId).dataTable(config);
			 var dataTable = $('#'+ dataTableId).DataTable({
				 'processing' : true,
//		        'responsive': true,
		        'sAjaxSource' : ajaxSource,
		        'serverSide' : true,
		        'columns' : getColumnasArray(columnas),
		        //'paging'      : true,
		        //'lengthChange': false,
		        'searching'   : true,
		        'info'        : true,
		        'autoWidth'   : false,
		        "pagingType": "simple_numbers",
		        "bSort": false,
                'language' : {
                	"decimal": ",",
                    "thousands": ".",
                    "sProcessing": "Procesando...",
                    "sLengthMenu": "Mostrar _MENU_ registros",
                    "sZeroRecords": "No se encontraron resultados",
                    "sEmptyTable": "No hay datos disponibles en esta tabla",
                    "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                    "sInfoEmpty": "Mostrando _MAX_ registros",
                    "sInfoFiltered": " ",
                    "sInfoPostFix": "",
                    "sSearch": "Buscar:",
                    "sUrl": "",
                    "sInfoThousands": ",",
                    "sLoadingRecords": "Cargando...",
                    "oPaginate": {
                        "sFirst": "Primero",
                        "sLast": "Último",
                        "sNext": "Siguiente",
                        "sPrevious": "Anterior"
                    },
                    "oAria": {
                        "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
                        "sSortDescending": ": Activar para ordenar la columna de manera descendente"
                    }
                }
			 });

			 //Ocultamos la primera columna (id)
			 //dataTable.fnSetColumnVis(0, false);
			dataTable.column(0).visible(false);		
			// $('#'+dataTableId+' tbody').on('click', 'tr', function() {
				 //Obtenemos el valor de la columna id
				 //ver equivalente a fnGetdata
				 //var id = dataTable.fnGetData(this, 0);
				 //Si id no es número, no hacemos nada
				 /*if (isNaN(id)) {
				 return;
				 }
				
				 window.location.href = editUrl+"/" + id;
			 });*/
			
				$('#'+ dataTableId + ' tbody').on('click','button.editar', function(){
					/*Con esto tenemos un objeto de tipo {id: 1, codigo: "ejc", descripcion: "Ejecutivo", materia: Object}
					 teniendo en cuenta que ejecutamos http://localhost:8090/sigj/proceso/ 
					 entonces para obtener el id de este objeto accedemos por su llave "id"*/
//					console.log($('#'+dataTableId+ ' tbody tr'));
					var ob = dataTable.row( $(this).parents('tr') ).data();
					console.log(ob);
					console.log( ob["id"] );
					accion(ob.id, "EDITAR");

					}); 
//				
				$('#'+ dataTableId + ' tbody').on('click','button.eliminar', function(){
					/*Con esto tenemos un objeto de tipo {id: 1, codigo: "ejc", descripcion: "Ejecutivo", materia: Object}
					 teniendo en cuenta que ejecutamos http://localhost:8090/sigj/proceso/ 
					 entonces para obtener el id de este objeto accedemos por su llave "id"*/
//					console.log($('#'+dataTableId+ ' tbody tr'));
					var ob = dataTable.row( $(this).parents('tr') ).data();
					console.log(ob);
					console.log( ob["id"] );
					accion(ob.id, 'ELIMINAR');

					}); 
//			
//				 $('#'+ dataTableId + ' tbody').on('click','button.ver', function(){
//					var ob = dataTable.row( $(this).parents('tr') ).data();
//					agregar(ob, 'v');
//					});
				 
		}

function getColumnasArray(colsStr){
	/*
	1. declarar un array para retornar
	2. separar colsStr teniendo en cuenta ';''
	3. iterar para crear el array
	*/
	    //1
		var columnsArray = [];
		//2
		var arrayCol = colsStr.split(";");
		//3 , se puede crear una funcion aparte en vez de hacer function
		jQuery.each(arrayCol, function(i, val){
			
					 if(val==true){
						val = "Si";
					}else if(val == false){
						val="No";
						
					}else if(val=="precioCompra" || val=="precioVenta"){
						columnsArray.push( 
								{"data" : val,
								 "render": function ( data, type, row ) {
								        var monto = formatInputEdit(data);
								        return monto;
								    }
								} );
					}else{
						columnsArray.push( {"data" : val} );
					}
					
					
		});
		columnsArray.push(
                {'defaultContent': "<button type='button' class='editar btn btn-primary btn-xs'><i class='fa fa-edit'></i></button>"+
			                    "<button type='button' class='eliminar btn btn-danger btn-xs' data-toggle='modal'><i class='fa fa-trash-o' aria-hidden='true'></i></button>"}
                );
		
		return columnsArray;
}



/**
 * 
 * @param magicSuggestId
 * @param urlData
 * @param pValueField
 * @param pNameDisplay
 * @returns un listado de clientes, tipo cliente entre otros.
 */
function crearMagicSuggest(magicSuggestId, urlData, pValueField, pNameDisplay) {
	var suggest = $('#'+ magicSuggestId).magicSuggest({
		data : urlData,
		useZebraStyle: true,
		sortDir: 'asc',
		valueFiel : pValueField,
		sortOrder : pNameDisplay,
		method : 'get',
		displayField : pNameDisplay,
		queryParam : 'q',
		selectFirst : true,
		requerid : true,
		maxSelection : 1,
		resultAsString: true,
		placeholder : 'Ingrese texto a buscar'
	});

	/*$(suggest).on('selectionchange', function(a,b,c){
		  console.log("cambió el valor del maicsuggest: " + this.getValue());
		  
		});*/
	return suggest;
}

//Cliente
function editarCliente(idCliente){
	
	$.ajax({
        type: "POST",
        url: "/gestionart/cliente/buscar",
        data: { 
            id_cliente:idCliente
        }
    }).done(function(data){
        $("#formularioCliente").html(data);
        
    });
}

function eliminarCliente(idCliente){
	$("#eliminarcliente").val(idCliente);
	$("#modal-default").modal('show');
}

// ./ Cliente

//Producto
function editarProducto(idProducto){
	
	$.ajax({
        type: "POST",
        url: "/gestionart/producto/buscar",
        data: { 
            id_producto:idProducto
        }
    }).done(function(data){
        $("#formularioProducto").html(data);
        
    });
}

function eliminarProducto(idProducto){
	$("#eliminarProducto").val(idProducto);
	$("#modal-default").modal('show');
}


//Usuario
function editarUsuario(id){
	
	$.ajax({
        type: "POST",
        url: "/gestionart/usuario/buscar",
        data: { 
            id_usuario:id
        }
    }).done(function(data){
        $("#formularioUsuario").html(data);
        
    });
}


function eliminarUsuario(id){
	$("#eliminarUsuario").val(id);
	$("#modal-default").modal('show');
}

//Proveedor
function editarProveedor(id){
	
	$.ajax({
        type: "POST",
        url: "/gestionart/proveedor/buscar",
        data: { 
            id_proveedor:id
        }
    }).done(function(data){
        $("#formularioProveedor").html(data);
    });
}
    


function eliminarProveedor(id){
	$("#eliminarProveedor").val(id);
	$("#modal-default").modal('show');
}



///////// datatable sin acciones
function crearDataTableSinAccion(dataTableId, ajaxSource, columnas, viewComprobante){
	console.log("creando DT:", dataTableId, ajaxSource, columnas, viewComprobante)
	
	
	 //var dataTable = $('#'+ dataTableId).dataTable(config);
	 var dataTable = $('#'+ dataTableId).DataTable({
        'processing' : true,
//        'responsive': true,
        'sAjaxSource' : ajaxSource,
        'serverSide' : true,
        'columns' : getColumnasArraySinAccion(columnas,viewComprobante),
        //'paging'      : true,
        //'lengthChange': false,
        'searching'   : false,
        'info'        : true,
        'autoWidth'   : false,
        "pagingType": "simple_numbers",
        "bSort": false,
        'language' : {
            "sProcessing": "Procesando...",
            "sLengthMenu": "Mostrar _MENU_ registros",
            "sZeroRecords": "No se encontraron resultados",
            "sEmptyTable": "No hay datos disponibles en esta tabla",
            "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
            "sInfoEmpty": "Mostrando _MAX_ registros",
            "sInfoFiltered": " ",
            "sInfoPostFix": "",
            //"sSearch": "Buscar:",
            "sUrl": "",
            "sInfoThousands": ",",
            "sLoadingRecords": "Cargando...",
            "oPaginate": {
                "sFirst": "Primero",
                "sLast": "Último",
                "sNext": "Siguiente",
                "sPrevious": "Anterior"
            }
        }
	 });
	
	dataTable.column(0).visible(false);

	$('#'+ dataTableId + ' tbody').on('click','button.imprimirComprobante', function(){
		var ob = dataTable.row( $(this).parents('tr') ).data();
		var params = 'id_venta_cabecera=' + ob.id;
		
		var link = document.createElement('a');
        link.href = "/gestionart/venta/comprobante?id_venta="+ob.id;
        link.target = '_blank';
        document.body.appendChild(link);
        link.click();
	});
}

function crearDataTableSinAccionCompra(dataTableId, ajaxSource, columnas, viewComprobante){
	console.log("creando DT:", dataTableId, ajaxSource, columnas, viewComprobante)
	
	
	 //var dataTable = $('#'+ dataTableId).dataTable(config);
	 var dataTable = $('#'+ dataTableId).DataTable({
        'processing' : true,
//        'responsive': true,
        'sAjaxSource' : ajaxSource,
        'serverSide' : true,
        'columns' : getColumnasArraySinAccion(columnas,viewComprobante),
        //'paging'      : true,
        //'lengthChange': false,
        'searching'   : false,
        'info'        : true,
        'autoWidth'   : false,
        "pagingType": "simple_numbers",
        "bSort": false,
        'language' : {
            "sProcessing": "Procesando...",
            "sLengthMenu": "Mostrar _MENU_ registros",
            "sZeroRecords": "No se encontraron resultados",
            "sEmptyTable": "No hay datos disponibles en esta tabla",
            "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
            "sInfoEmpty": "Mostrando _MAX_ registros",
            "sInfoFiltered": " ",
            "sInfoPostFix": "",
            //"sSearch": "Buscar:",
            "sUrl": "",
            "sInfoThousands": ",",
            "sLoadingRecords": "Cargando...",
            "oPaginate": {
                "sFirst": "Primero",
                "sLast": "Último",
                "sNext": "Siguiente",
                "sPrevious": "Anterior"
            }
        }
	 });
	
	dataTable.column(0).visible(false);
	
	$('#'+ dataTableId + ' tbody').on('click','button.imprimirComprobante', function(){
		var ob = dataTable.row( $(this).parents('tr') ).data();
		var params = 'id_compra_cabecera=' + ob.id;
		
		var link = document.createElement('a');
        link.href = "/gestionart/compra/comprobante?id_compra="+ob.id;
        link.target = '_blank';
        document.body.appendChild(link);
        link.click();
	});

}

function crearDataTableSinAccionInventario(dataTableId, ajaxSource, columnas, viewComprobante){
	console.log("creando DT:", dataTableId, ajaxSource, columnas, viewComprobante)
	
	
	 //var dataTable = $('#'+ dataTableId).dataTable(config);
	 var dataTable = $('#'+ dataTableId).DataTable({
        'processing' : true,
//        'responsive': true,
        'sAjaxSource' : ajaxSource,
        'serverSide' : true,
        'columns' : getColumnasArraySinAccion(columnas,viewComprobante),
        //'paging'      : true,
        //'lengthChange': false,
        'searching'   : true,
        'info'        : true,
        'autoWidth'   : false,
        "pagingType": "simple_numbers",
        "bSort": false,
        'language' : {
            "sProcessing": "Procesando...",
            "sLengthMenu": "Mostrar _MENU_ registros",
            "sZeroRecords": "No se encontraron resultados",
            "sEmptyTable": "No hay datos disponibles en esta tabla",
            "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
            "sInfoEmpty": "Mostrando _MAX_ registros",
            "sInfoFiltered": " ",
            "sInfoPostFix": "",
            "sSearch": "Buscar:",
            "sUrl": "",
            "sInfoThousands": ",",
            "sLoadingRecords": "Cargando...",
            "oPaginate": {
                "sFirst": "Primero",
                "sLast": "Último",
                "sNext": "Siguiente",
                "sPrevious": "Anterior"
            }
        }
	 });
	dataTable.column(0).every( function () {
        var that = this;
 
        $( '#input-search').on( 'keyup change', function () {
        	console.log("entro en el input")
            if ( that.search() !== this.value ) {
                that
                    .search( this.value )
                    .draw();
            }
        } );
    } );
	
	dataTable.column(0).visible(false);

	$('#'+ dataTableId + ' tbody').on('click','button.imprimirComprobante', function(){
		var ob = dataTable.row( $(this).parents('tr') ).data();
		var params = 'id_venta_cabecera=' + ob.id;
		
		var link = document.createElement('a');
        link.href = "/gestionart/venta/comprobante?id_venta="+ob.id;
        link.target = '_blank';
        document.body.appendChild(link);
        link.click();
	});
	
	$('#inventarioDT' + ' tbody').on('click', 'tr', function() {
   	    //Obtenemos el valor de la columna id
		var ob = dataTable.row($(this)).data();
		var id = ob.producto.id;
		var fe = ob.fechaMes.substr(5,2);
		
		$.ajax({
	        type: "POST",
	        url: "/gestionart/inventario/inventario_detalle",
	        data: { id_prod : id, mes:fe}
	    }).done(function(data){
	        $("#vid-modal-inve").html(data);
	        $("#modalInventario").modal("show");
	        
	    });
    });
}

function getColumnasArraySinAccion(colsStr,view){
	/*
	1. declarar un array para retornar
	2. separar colsStr teniendo en cuenta ';''
	3. iterar para crear el array
	*/
	    //1
		var columnsArray = [];
		//2
		var arrayCol = colsStr.split(";");
		//3 , se puede crear una funcion aparte en vez de hacer function
		jQuery.each(arrayCol, function(i, val){
			
					 if(val==true){
						val = "Si";
					}else if(val == false){
						val="No";
						
					}else if(val=="entrada" || val=="salida" || val=="saldoActual"){
						columnsArray.push( 
								{"data" : val,
								 "render": function ( data, type, row ) {
								        var monto = formatInputEdit(data);
								        return monto;
								    }
								} );
					}else{
						columnsArray.push( {"data" : val} );
					}
					
					
		});
		if(view){
			columnsArray.push(
	                {'defaultContent': "<button type='button' class='imprimirComprobante btn btn-success btn-xs'><span class='glyphicon glyphicon-print'></span></button>"}
	                );
		}
		
		
		return columnsArray;
}

function printFile(blob, fileName, type){

    // Internet Explorer 6-11
    var isIE = /*@cc_on!@*/ false || !!document.documentMode;
    var options = {
			type: "application/pdf"
		};
    if (isIE) {
        // IE workaround for "HTML7007: One or more blob URLs were revoked by closing the blob for which they were created. These URLs will no longer resolve as the data backing the URL has been freed."
        //window.navigator.msSaveBlob(blob, filename);
    	var file = new Blob([blob], options);
        //window.navigator.msSaveBlob(file, fileName);
        window.navigator.msSaveOrOpenBlob(file, fileName);
    }else{
    	var link = document.createElement('a');
        link.href = window.URL.createObjectURL(blob);
        link.open = fileName;
        //link.target = '_blank';
        document.body.appendChild(link);
        link.click();
    }  
}


///ERRORES
function ajaxErrorHandler(errorData, status, error, idError, idTitleError, idDescError) {
    $('html, body').animate({scrollTop: 0}, 800);
    if(errorData.message == undefined){
    	$("#errorMensaje").html(errorData.description);
    }else{
    	$("#errorMensaje").html(errorData.message);
    }
	$("#alerta3").fadeIn(8000);
	$("#alerta3").fadeOut(1000);
}





/*
 * FORMATEADOR DE ENTEROS Y DECIMALES DE ACUERDO A LA MONEDA
 * Para utilizar esta función, el jsp debe tener un input hidden con el
 * id="codigo-moneda-local" value="{bean.moneda.codigo}"
 * y el input a formatear debe tener una clase llamada importe
 */
function formatInput(){
	$('.importe').on("input", function (e) {
		var num = $(this).val();
		

		
		var codMoneda="GS"
		codMoneda = $.trim(codMoneda);
		var moneda=getMonedaDetails(codMoneda);
		var inputPattern=moneda.inputPattern;
		var formatPattern=moneda.formatPattern;
		var numCleaned=num.replace(inputPattern, "");
		if(codMoneda==="GS"){
			var numFormated=formatMontoDecimal(numCleaned);
		}else{
			var numFormated=formatMontoDecimalCotizacion(numCleaned);
		}
	    $(this).val(numFormated);
	});
}

function formatInputEdit(monto){
		var num = monto;
		

		
		var codMoneda="GS"
		codMoneda = $.trim(codMoneda);
		var moneda=getMonedaDetailsTable(codMoneda);
		var inputPattern=moneda.inputPattern;
		var formatPattern=moneda.formatPattern;
		if(num == null || num == undefined)
			num = '0';
		num = num.toString();
		var numCleaned=num.replace(inputPattern, "");
		if(codMoneda==="GS"){
			var numFormated=formatMontoDecimal(numCleaned);
		}else{
			var numFormated=formatMontoDecimalCotizacion(numCleaned);
		}
	    return numFormated;
}


function getMonedaDetails(codMoneda){
	var moneda= new Object();
	switch (codMoneda) {
	case 'GS':
		moneda.descripcion='Guaraníes';
		moneda.formato="0,0";
		moneda.formatPattern=/\D/g;
//		moneda.inputPattern=/-\D/g;
		moneda.inputPattern=/\D/g;
		break;
	case 'USD':
		moneda.descripcion='Dólares';
		moneda.formato="0,0.00";
		moneda.formatPattern=/^[0-9]+([,][0-9]+)?$/g;
		moneda.inputPattern=/[^0-9,]/g;
		break;
	case 'EUR':
		moneda.descripcion='Euros';
		moneda.formato="0,0.00";
		moneda.formatPattern=/^[0-9]+([,][0-9]+)?$/g;
		moneda.inputPattern=/[^0-9,]/g;
		break;
	case 'BRL':
		moneda.descripcion='REALES';
		moneda.formato="0,0.00";
		moneda.formatPattern=/^[0-9]+([,][0-9]+)?$/g;
		moneda.inputPattern=/[^0-9,]/g;
		break;
	case 'ARS':
		moneda.descripcion='PESO ARGENTINO';
		moneda.formato="0,0.00";
		moneda.formatPattern=/^[0-9]+([,][0-9]+)?$/g;
		moneda.inputPattern=/[^0-9,]/g;
		break;
	default:
		moneda.descripcion='Moneda no registrada';
		moneda.formato="0,0.00";
		moneda.formatPattern=/^[0-9]+([,][0-9]+)?$/g;
		moneda.inputPattern=/[^0-9,]/g;
		break;
	}
	return moneda;
}

function getMonedaDetailsTable(codMoneda){
	var moneda= new Object();
	switch (codMoneda) {
	case 'GS':
		moneda.descripcion='Guaraníes';
		moneda.formato="0,0";
		moneda.formatPattern=/\D/g;
		moneda.inputPattern=/-\D/g;
//		moneda.inputPattern=/\D/g;
		break;
	case 'USD':
		moneda.descripcion='Dólares';
		moneda.formato="0,0.00";
		moneda.formatPattern=/^[0-9]+([,][0-9]+)?$/g;
		moneda.inputPattern=/[^0-9,]/g;
		break;
	case 'EUR':
		moneda.descripcion='Euros';
		moneda.formato="0,0.00";
		moneda.formatPattern=/^[0-9]+([,][0-9]+)?$/g;
		moneda.inputPattern=/[^0-9,]/g;
		break;
	case 'BRL':
		moneda.descripcion='REALES';
		moneda.formato="0,0.00";
		moneda.formatPattern=/^[0-9]+([,][0-9]+)?$/g;
		moneda.inputPattern=/[^0-9,]/g;
		break;
	case 'ARS':
		moneda.descripcion='PESO ARGENTINO';
		moneda.formato="0,0.00";
		moneda.formatPattern=/^[0-9]+([,][0-9]+)?$/g;
		moneda.inputPattern=/[^0-9,]/g;
		break;
	default:
		moneda.descripcion='Moneda no registrada';
		moneda.formato="0,0.00";
		moneda.formatPattern=/^[0-9]+([,][0-9]+)?$/g;
		moneda.inputPattern=/[^0-9,]/g;
		break;
	}
	return moneda;
}
function format(input)
{
	var num = input.replace(/\./g,'');
	if(!isNaN(num)){
		num = num.toString().split('').reverse().join('').replace(/(?=\d*\.?)(\d{3})/g,'$1.');
		num = num.split('').reverse().join('').replace(/^[\.]/,'');
		input = num;
	}else{ 		
		input = "-1";
	}
	return input;
}

function formatMontoDecimal(num){
	num = num.toString();
	var separador=num.split(".");
	var decimal;
	var entero;
	if(separador.length>2){
		entero=separador[0];
		decimal=separador[1];
		entero=format(entero);
		num=entero+","+decimal;
	}else if(separador.length===2){
		entero=separador[0];
		decimal=separador[1];
		if(decimal.length>0 && decimal.length<=LONG_MAX_DECIMALES_MONTO){
			entero=format(entero);
			num=entero+","+decimal;
		}else if(decimal.length>LONG_MAX_DECIMALES_MONTO){
			decimal=decimal.slice(0,-1);
			entero=format(entero);
			num=entero+","+decimal;
		}if(decimal.length===0){
			num=format(entero);
			num=num+",";
		}	
	}else{
		num=format(num);
	}
	return num;
}

