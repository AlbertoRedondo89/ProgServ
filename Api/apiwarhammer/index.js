const express = require('express');
const bodyParser = require('body-parser');
const unidadRoutes = require('./src/routes/unidadRoutes');
const usuarioRoutes = require('./src/routes/usuarioRoutes');

const app = express();
const port = 3000;

app.use(bodyParser.json());

// Rutas
app.use('/api/unidades', unidadRoutes);
app.use('/api/usuarios', usuarioRoutes);

//Inicio del servidor
app.listen(port, ()=>{
    console.log("Servidor iniciado en http://localhost:" + port);
});