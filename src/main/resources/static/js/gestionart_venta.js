
///////// datatable sin acciones
function crearDataTableVenta(dataTableId, ajaxSource, columnas, viewComprobante){
	console.log("creando DT:", dataTableId, ajaxSource, columnas, viewComprobante)
	
	
	 //var dataTable = $('#'+ dataTableId).dataTable(config);
	 var dataTable = $('#'+ dataTableId).DataTable({
        'processing' : true,
//        'responsive': true,
        'sAjaxSource' : ajaxSource,
        'serverSide' : true,
        'columns' : getColumnasArrayCaja(columnas),
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
	
	$('#'+ dataTableId + ' tbody').on('click','button.ver', function(){
		/*Con esto tenemos un objeto de tipo {id: 1, codigo: "ejc", descripcion: "Ejecutivo", materia: Object}
		 teniendo en cuenta que ejecutamos http://localhost:8090/sigj/proceso/ 
		 entonces para obtener el id de este objeto accedemos por su llave "id"*/
//		console.log($('#'+dataTableId+ ' tbody tr'));
		var ob = dataTable.row( $(this).parents('tr') ).data();
		console.log(ob);
		console.log( ob["id"] );
		verDetalle(ob.id, "VER");

		}); 
	
	$('#'+ dataTableId + ' tbody').on('click','button.confirmar', function(){
		/*Con esto tenemos un objeto de tipo {id: 1, codigo: "ejc", descripcion: "Ejecutivo", materia: Object}
		 teniendo en cuenta que ejecutamos http://localhost:8090/sigj/proceso/ 
		 entonces para obtener el id de este objeto accedemos por su llave "id"*/
//		console.log($('#'+dataTableId+ ' tbody tr'));
		var ob = dataTable.row( $(this).parents('tr') ).data();
		console.log(ob);
		console.log( ob["id"] );
		var link = document.createElement('a');
	    link.href = "/gestionart/caja/confirmar/"+ob.id;
	    //link.target = '_blank';
	    document.body.appendChild(link);
	    link.click();
	});
	
	
	$('#'+ dataTableId + ' tbody').on('click','button.cancelar', function(){
		/*Con esto tenemos un objeto de tipo {id: 1, codigo: "ejc", descripcion: "Ejecutivo", materia: Object}
		 teniendo en cuenta que ejecutamos http://localhost:8090/sigj/proceso/ 
		 entonces para obtener el id de este objeto accedemos por su llave "id"*/
//		console.log($('#'+dataTableId+ ' tbody tr'));
		var ob = dataTable.row( $(this).parents('tr') ).data();
		console.log(ob);
		console.log( ob["id"] );
	    $("#estaSeguro").modal('show');
	    $("#id_cabecera").val(ob.id);
	});

	
}

//acciones para caja
function getColumnasArrayCaja(colsStr){
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
                {'defaultContent': "<button type='button' class='ver btn btn-primary btn-xs'><i class='fa fa-newspaper-o'></i></button>"+
			                    "<button type='button' class='confirmar btn btn-success btn-xs' data-toggle='modal'><i class='fa fa-check' aria-hidden='true'></i></button>"+
			                    "<button type='button' class='cancelar btn btn-danger btn-xs' data-toggle='modal'><i class='fa fa-trash-o' aria-hidden='true'></i></button>"}
                );
		
		return columnsArray;
}

function verDetalle(id){
	
	$.ajax({
        type: "POST",
        url: "/gestionart/venta/cabecera",
        data: { id_cabecera : id}
    }).done(function(data){
        $("#ventaConfirmar").html(data);
        $("#modalConfirmacion").modal("show");
        
    });
	
	
	/*var link = document.createElement('a');
    link.href = "/gestionart/caja/caja-venta?id_caja="+id;
    link.target = '_blank';
    document.body.appendChild(link);
    link.click();*/
	
}