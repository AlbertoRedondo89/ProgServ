const express = require('express');
const bodyParser = require('body-parser');
const unidadRoutes = require('./src/routes/unidadRoutes');

const app = express();
const port = 3000;

app.use(bodyParser.json());

// Rutas
app.use('/api/unidades', unidadRoutes);

//Inicio del servidor
app.listen(port, ()=>{
    console.log("Servidor iniciado en http://localhost:" + port);
});