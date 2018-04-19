function crearDataTable(dataTableId, ajaxSource, columnas, editUrl){
			console.log("creando DT:", dataTableId, ajaxSource, columnas, editUrl)
			
			
			 //var dataTable = $('#'+ dataTableId).dataTable(config);
			 var dataTable = $('#'+ dataTableId).DataTable({
                'processing' : true,
//                'responsive': true,
                'sAjaxSource' : ajaxSource,
                'serverSide' : true,
                'columns' : getColumnasArray(columnas),
                'paging'      : true,
                'lengthChange': false,
                'searching'   : false,
                'info'        : true,
                'autoWidth'   : false,
                
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

function editarCliente(idCliente){
	
	$.ajax({
        type: "POST",
        url: "cliente/buscar",
        data: { 
            id_cliente:idCliente
        }
    }).done(function(data){
        $("#formularioCliente").html(data);
        
    });
}


function eliminarCliente(idCliente){
	$("#modal-default").click();
	/*$.ajax({
        type: "POST",
        url: "cliente/buscar",
        data: { 
            id_cliente:idCliente
        }
    }).done(function(data){
        $("#formularioCliente").html(data);
        
    });*/
}
function buscarProducto(idProducto){
	$.ajax({
        type: "POST",
        url: "producto/buscar",
        data: { 
         id_producto:idProducto
        }
    }).done(function(data){
    	$("#formularioProducto").html(data);
    });
}
	