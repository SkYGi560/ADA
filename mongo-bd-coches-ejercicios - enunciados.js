// ***************************
// CONCESIONARIO DE COCHES 
// ***************************

/* ************************************************

find()
find() $and, $or, ...
find() $gte, $gt, $lte, $lt, $eq, ...
find() $in, $all, $size, ...

aggregate() 
aggregate() $group
aggregate() $sum, $avg, $min, $max

insertOne
insertMany
deleteOne
deleteMany
updateOne
updateMany
	$set, $unset
	$inc, $mul
	$pull, $push
	
***************************************************** */	



/*
  Pregunta 1:
  ------------

  Mostrar matricula, anyo, marca, color
  de las coches marca bmw y color negro
  ordenados descendentemente por anyo
  
  Salida esperada:
  ----------------
  
{
        "matricula" : "2467-KMM",
        "anyo" : 2018,
        "marca" : "bmw",
        "color" : "negro"
}
{
        "matricula" : "3496-KBM",
        "anyo" : 2017,
        "marca" : "bmw",
        "color" : "negro"
}
  
*/

db.coches.aggregate(
	{
		$match: {
			marca: "bmw",
			color: "negro"
		}
	},
	{
		$project: {
			_id: 0,
			matricula: 1,
			anyo: 1,
			marca: 1,
			color: 1
		}
	},
	{
		$sort: {
			anyo: -1
		}
	}
)



/*
  Pregunta 2:
  ------------

  Mostrar matricula, anyo, marca, color, km y precio
  de las coches con precio entre 20000 y 25000 euros, es decir,
  con precio mayor o igual de 20000 y menor o igual de 25000.
  
  Salida esperada:
  ----------------
  
{
        "matricula" : "5186-JXF",
        "anyo" : 2017,
        "marca" : "ford",
        "color" : "negro",
        "km" : 34000,
        "precio" : 20000
}
{
        "matricula" : "9348-KWT",
        "anyo" : 2019,
        "marca" : "hyundai",
        "color" : "negro",
        "km" : 19000,
        "precio" : 21000
}
  
*/				
				
				
db.coches.aggregate([
	{
		$match: {
			precio: {
				$gte: 20000,
				$lte: 25000
			  }
		}
	},
	{
		$project: {
			_id: 0,
			matricula: 1,
			anyo: 1,
			marca: 1,
			color: 1,
			km: 1,
			precio: 1
		}
	}
])




/*
  Pregunta 3:
  ------------

  Mostrar la cantidad de coches que tienen
  como accesorios "airbags", "ABS" o "navegador"
    
  Salida esperada:
  ----------------
  
10
  
*/	

db.coches.aggregate(
	{$match: {accesorios: {$in:["airbags","ABS","navegador"]}}},
	{$count: "total"}
)





/*
  Pregunta 4:
  ------------

  Mostrar Mostrar matricula, anyo, marca, color, km, precio y vendedor
  con dni de vendedor 21637594
    
  Salida esperada:
  ----------------
  
{
        "matricula" : "2467-KMM",
        "anyo" : 2018,
        "marca" : "bmw",
        "color" : "negro",
        "km" : 32000,
        "vendedor" : {
                "dni" : 21637594,
                "nombre" : "Eduardo Sánchez"
        },
        "precio" : 27000
}
  
*/	

db.coches.aggregate(
	{$match:{"vendedor.dni":21637594}},
	{$project:{
		_id: 0,
		matricula: 1,
		anyo: 1,
		marca: 1,
		color: 1,
		km: 1,
		vendedor: 1,
		precio: 1
	}}
)
				
				
			
/*
  Pregunta 5:
  ------------

  Calcular el precio máximo de cada marca.
  Mostrar marca y maxprecio
    
  Salida esperada:
  ----------------
  
{ "_id" : "hyundai", "maxprecio" : 21000 }
{ "_id" : "bmw", "maxprecio" : 35000 }
{ "_id" : "audi", "maxprecio" : 31000 }
{ "_id" : "ford", "maxprecio" : 20000 }
  
*/						

db.coches.aggregate([
	{
		$group: {
		  _id: "$marca",
		  maxprecio: { $max: "$precio" }
	    }
	},
	{
		$project: {
		  maxprecio: 1
		}
	}	  
])
  
  
/*

Pregunta 6
----------
Acttualizar el coche con matrícula 1279-KHY
cambiando el precio a 15000, pues ahora tiene 16000

*/


db.coches.updateOne(
{matricula:"1279-KHY"},
	{$set:{precio:15000}}
)


/*
Pregunta 7
----------
Elimina todos los coches del 2017

Después de la eliminación debe haber 4 coches menos

*/

db.coches.deleteMany({"anyo":2017})



/*
Pregunta 8
----------
Se ha detectado un error y es necesario añadir "ABS" del array de accesorios
del coche con matricula 2467-KMM.

Escriba la instrucción para añadir este valor del array de accesorios.

*/

db.coches.updateOne(
  { matricula: "2467-KMM" },
  { $push: { accesorios: "ABS" } }
)


/*
Pregunta 9
-----------
Escriba la instrucción para aumentar el precio de todos los bmw un 20%


*/

db.coches.updateMany(
  { marca: "bmw" },
  { $mul: { precio: 1.20 } }
)



/*

Pregunta 10
-----------
Después de revisar los km del coche con matricula 3917-KTJ,
vemos que tiene 2000 km más, es decir, debe pasar de 30000 a 32000.

Escribe la instrucción para incrementar km en 2000 (no asignes el valor, sino el comando para incrementar)

*/



db.coches.updateMany(
{matricula:"3917-KTJ"},
{$inc:{km:2000}}
)

