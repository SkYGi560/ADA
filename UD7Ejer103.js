db.comunidades.updateOne(
 {comunidad:'Comunidad Valenciana'},
 {
 $set: {provincias: ["Alicante","Castellón","Valencia"]}
 }
)
db.comunidades.updateOne(
 {comunidad:'Aragón'},
 {
 $set: {provincias: ["Huesca","Teruel","Zaragoza"]}
 }
)
db.comunidades.updateOne(
 {comunidad:'Cataluña'},
 {
 $set: {provincias: ["Barcelona","Tarragona","Girona","Lleida"]}
 }
)
db.comunidades.updateOne(
 {comunidad:'Canarias'},
 {
 $set: {provincias: ["Santa Cruz de Tenerife","Las Palmas"]}
 }
)
db.comunidades.find()