const express = require('express');
const bodyParser = require('body-parser');
const unidadRoutes = require('./src/routes/unidadRoutes');
const usuarioRoutes = require('./src/routes/usuarioRoutes');
const { swaggerUi, swaggerDocs } = require("./src/config/swaggerConfig");

const app = express();
const port = 3000;

app.use(bodyParser.json());

// Rutas
app.use('/api/unidades', unidadRoutes);
app.use('/api/usuarios', usuarioRoutes);

// Documentación Swagger
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerDocs));

//Inicio del servidor
app._router.stack.forEach((r) => {
    if (r.route && r.route.path) {
        console.log(r.route.path);
    }
});
app.listen(port, ()=>{
    console.log("Servidor iniciado en http://localhost:" + port);
});