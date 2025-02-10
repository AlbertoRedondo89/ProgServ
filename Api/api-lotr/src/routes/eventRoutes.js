const express = require('express');
const { getEvents, createEvent } = require('../controllers/eventController');
const { auth, isAdmin } = require('../middleware/auth');

const router = express.Router();

// Obtener todos los eventos
router.get('/', getEvents);

// Crear un nuevo evento (solo admins)
router.post('/', auth, isAdmin, createEvent);

module.exports = router;
