db.comunidades.updateOne(
 {comunidad:'Comunidad Valenciana'},
 {
 $set: {
	"superficie": 23255,
	"poblacion": 5319285}
 }
)
db.comunidades.updateOne(
 {comunidad:'Andalucia'},
 {
 $set: {
	"superficie": 87599,
	"poblacion": 8631862}
 }
)
db.comunidades.updateOne(
 {comunidad:'Aragón'},
 {
 $set: {
	"superficie": 47720,
	"poblacion": 1351591}
 }
)
db.comunidades.updateOne(
 {comunidad:'Cataluña'},
 {
 $set: {
	"superficie": 32113,
	"poblacion": 8012231}
 }
)
db.comunidades.updateOne(
 {comunidad:'Canarias'},
 {
 $set: {
	"superficie": 7447,
	"poblacion": 2238754}
 }
)
db.comunidades.find()