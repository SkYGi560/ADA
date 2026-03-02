/*
{
	cod_clase: Integer,
	nombre: String (yoga,pilates,etc)
	horas_semanales: Integer,
	monitor: {
		id_monitor: Integer,
		nombre: String,
		apellidos: String
	},
	clientes:[{
		dni: String,
		nombre: String,
		apellidos: String,
		fecha_nacimiento: ISODate,
		telefono: Integer
	}]
}
*/

use gimnasio
db.clases.insertMany([
  {
    cod_clase: 1,
    nombre: "Yoga",
    horas_semanales: 4,
    monitor: {
      id_monitor: 101,
      nombre: "Laura",
      apellidos: "Martínez Pérez"
    },
    clientes: [
      {
        dni: "12345678A",
        nombre: "Ana",
        apellidos: "Gómez Ruiz",
        fecha_nacimiento: ISODate("1990-05-12"),
        telefono: 600111222
      },
      {
        dni: "87654321B",
        nombre: "Carlos",
        apellidos: "Sánchez López",
        fecha_nacimiento: ISODate("1985-09-20"),
        telefono: 600333444
      }
    ]
  },
  {
    cod_clase: 2,
    nombre: "Pilates",
    horas_semanales: 3,
    monitor: {
      id_monitor: 102,
      nombre: "Javier",
      apellidos: "Moreno Díaz"
    },
    clientes: [
      {
        dni: "11223344C",
        nombre: "Lucía",
        apellidos: "Fernández Gil",
        fecha_nacimiento: ISODate("1995-02-03"),
        telefono: 611222333
      },
      {
        dni: "44332211D",
        nombre: "Miguel",
        apellidos: "Torres Navarro",
        fecha_nacimiento: ISODate("1988-11-15"),
        telefono: 611444555
      }
    ]
  },
  {
    cod_clase: 3,
    nombre: "Spinning",
    horas_semanales: 5,
    monitor: {
      id_monitor: 103,
      nombre: "Sonia",
      apellidos: "Ruiz Molina"
    },
    clientes: [
      {
        dni: "55667788E",
        nombre: "Paula",
        apellidos: "Vega Castillo",
        fecha_nacimiento: ISODate("1992-07-08"),
        telefono: 622555666
      },
      {
        dni: "88776655F",
        nombre: "David",
        apellidos: "Hernández Ortiz",
        fecha_nacimiento: ISODate("1980-01-25"),
        telefono: 622777888
      }
    ]
  }
])
db.clases.find()