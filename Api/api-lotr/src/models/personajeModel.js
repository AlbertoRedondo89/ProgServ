const pool = require('../config/database'); // Conexión a MySQL

// Obtener todos los personajes
async function getAllPersonajes() {
    const [rows] = await pool.query('SELECT * FROM personajes');
    return rows;
}

// Obtener un personaje por ID
async function getPersonajeById(id) {
    const [rows] = await pool.query('SELECT * FROM personajes WHERE id = ?', [id]);
    return rows[0]; // Retorna el primer personaje encontrado
}

// Insertar un nuevo personaje
async function insertPersonaje(nombre, raza, descripcion) {
    const [result] = await pool.query(
        'INSERT INTO personajes (nombre, raza, descripcion) VALUES (?, ?, ?)',
        [nombre, raza, descripcion]
    );
    return result.insertId; // Retorna el ID del nuevo personaje
}

// Exportar funciones
module.exports = { getAllPersonajes, getPersonajeById, insertPersonaje };
