require('dotenv').config({ path: '../.env' });
console.log("JWT_SECRET después de carga manual:", process.env.JWT_SECRET);
console.log("Variables de entorno cargadas:", process.env);

console.log("JWT_SECRET cargado:", process.env.JWT_SECRET);

const express = require('express');
const cors = require('cors');
const swaggerUi = require('swagger-ui-express');
const swaggerJsdoc = require('swagger-jsdoc');
const sequelize = require('./config/database');
//const path = require('path');
//const sequelize = require(path.join(__dirname, 'config', 'database'));
const authRoutes = require('./routes/authRoutes');
const eventRoutes = require('./routes/eventRoutes');
const personajeRoutes = require('./routes/personajeRoutes');

const jwtSecret = process.env.JWT_SECRET;

const app = express();

// Middleware
app.use(cors());
app.use(express.json());

// Routes
app.use('/api/auth', authRoutes);
app.use('/api/events', eventRoutes);
app.use('/personajes', personajeRoutes);

// Swagger documentation
const swaggerOptions = {
  definition: {
    openapi: '3.0.0',
    info: {
      title: 'Event Management API',
      version: '1.0.0',
      description: 'API for managing events and registrations'
    },
    servers: [
      {
        url: 'http://localhost:3000'
      }
    ],
    tags: [
      {
        name: 'Auth',
        description: 'Endpoints for user authentication and registration'
      },
      {
        name: 'Events',
        description: 'Endpoints for managing events'
      }
    ]
  },
  apis: ['./src/routes/*.js']
};

const swaggerDocs = swaggerJsdoc(swaggerOptions);
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerDocs));

// Database sync and server start
const PORT = process.env.PORT || 3000;

sequelize.sync().then(() => {
  app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
  });
});

// Ruta básica de prueba
app.get('/', (req, res) => {
  res.send('¡Hola, Mundo! API de El Señor de los Anillos');
});
