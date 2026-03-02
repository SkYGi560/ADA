use demografia
db.comunidades.insert([
	{comunidad:'Andalucía'}, 
	{comunidad:'Aragón'}, 
	{comunidad:'Islas Baleares'},
	{comunidad:'Cataluña'}, 
	{comunidad:'Canarias'},
	{comunidad:'Castilla y León'}, 
	{comunidad:'Madrid'}, 
	{comunidad:'Navarra'}, 
	{comunidad:'Extremadura'}, 
	{comunidad:'Galicia'},
	{comunidad:'País Vasco'}, 
	{comunidad:'Principado de Asturias'}, 
	{comunidad:'Ceuta'}, 
	{comunidad:'Melilla'}
])
db.comunidades.find()

db.comunidades.replaceOne(
	{comunidad:'Navarra'},
	{comunidad:'Comunidad Floral de Navarra'}
)
db.comunidades.replaceOne(
	{comunidad:'Madrid'},
	{comunidad:'Comunidad de Madrid'}
)
db.comunidades.find()

db.comunidades.deleteOne(
	{comunidad:'Ceuta'}
)
db.comunidades.deleteOne(
	{comunidad:'Melilla'}
)
db.comunidades.find()