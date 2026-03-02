/*
{
 nombre: String,
 apellidos: String,
 edad: Integer,
 telefono_movil: Integer,
 numero_hijos; Integer
}
*/

use empresa
db.empleados.insertMany([
{
 "nombre": 'Pepe',
 "apellidos": 'Sanchez',
 "edad": 18,
 "telefono_movil": 999999999,
 "numero_hijos": 1
},
{
 "nombre": 'Juana',
 "apellidos": 'Lopez',
 "edad": 19,
 "telefono_movil": 888888888,
 "numero_hijos": 1
}
])
db.empleados.find()