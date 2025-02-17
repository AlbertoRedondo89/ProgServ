const db = require('../config/dbConfig');
const crypto = require('crypto');
const bcrypt = require('bcrypt');

class Usuario {
    
    // Crear usuario y generar API key
    crearUsuario(username, password, callback) {
        const apiKey = crypto.randomBytes(30).toString('hex'); // Generar API key
        const hashedPassword = bcrypt.hashSync(password, 10); // Encriptar la contraseña

        const sql = 'INSERT INTO usuarios (username, password, api_key) VALUES (?, ?, ?)';
        db.query(sql, [username, hashedPassword, apiKey], (err, result) => {
            if (err) {
                return callback(err, null);
            }
            callback(null, { id: result.insertId, apiKey });
        });
    }

    // Obtener usuario por API key
    getUserByApiKey(apiKey, callback) {
        const sql = 'SELECT id, username, role FROM usuarios WHERE api_key = ?';
        db.query(sql, [apiKey], callback);
    }
}

module.exports = new Usuario();
