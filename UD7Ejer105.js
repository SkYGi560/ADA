/*
{
	ISBN: String, 
	título: String, 
	autores: [String],
	paginas: Integer, 
	precio: double, 
	anyo: Integer, 
	editorial: String,
	portada: String 
}
*/

use libreria
db.libros.insertMany([
  {
    ISBN: "978-84-376-0494-7",
    titulo: "Cien años de soledad",
    autores: ["Gabriel García Márquez"],
    paginas: 471,
    precio: 19.90,
    anyo: 1967,
    editorial: "Sudamericana",
    portada: "cien_anos_soledad.jpg"
  },
  {
    ISBN: "978-84-663-2912-4",
    titulo: "El nombre del viento",
    autores: ["Patrick Rothfuss"],
    paginas: 880,
    precio: 24.95,
    anyo: 2007,
    editorial: "Plaza & Janés",
    portada: "el_nombre_del_viento.jpg"
  },
  {
    ISBN: "978-84-9062-714-3",
    titulo: "La sombra del viento",
    autores: ["Carlos Ruiz Zafón","Joel Vives"],
    paginas: 576,
    precio: 21.50,
    anyo: 2001,
    editorial: "Planeta",
    portada: "la_sombra_del_viento.jpg"
  }
])
db.libros.find()