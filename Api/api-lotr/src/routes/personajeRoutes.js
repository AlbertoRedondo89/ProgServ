const express = require('express');
const router = express.Router();
const personajeModel = require('../models/personajeModel');
const db = require('../config/database');

// Obtener todos los personajes
router.get('/', async (req, res) => {
    try {
        const personajes = await personajeModel.getAllPersonajes();
        res.json(personajes);
    } catch (error) {
        res.status(500).json({ error: 'Error al obtener personajes' });
    }
});

// Obtener un personaje por ID
router.get('/personajes/:id', async (req, res) => {
    try {
        const personajeId = parseInt(req.params.id, 10);  // Convierte el id a número

        if (isNaN(personajeId)) {
            return res.status(400).json({ error: 'El ID debe ser un número válido' });
        }

        // Consulta a la base de datos con Sequelize
        const personaje = await personajeModel.getPersonajeById(personajeId);  // Aquí usas el modelo para obtenerlo

        if (!personaje) {
            return res.status(404).json({ error: 'Personaje no encontrado' });
        }

        res.json(personaje); // Devuelve el personaje encontrado
    } catch (error) {
        console.error("Error en la consulta:", error);
        res.status(500).json({ error: 'Error al obtener personaje' });
    }
});


// Insertar un nuevo personaje
router.post('/', async (req, res) => {
    try {
        const { nombre, raza, descripcion } = req.body;

        if (!nombre || !raza) {
            return res.status(400).json({ error: 'Nombre y raza son obligatorios' });
        }

        // Usamos la función del modelo para insertar el personaje
        const id = await personajeModel.insertPersonaje(nombre, raza, descripcion);
        
        res.status(201).json({ id, nombre, raza, descripcion });
    } catch (error) {
        console.error("Error al insertar personaje:", error);
        res.status(500).json({ error: 'Error al insertar personaje' });
    }
});

module.exports = router;
