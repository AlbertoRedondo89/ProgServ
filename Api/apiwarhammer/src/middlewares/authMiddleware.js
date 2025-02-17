const usuarioModel = require('../models/usuarioModel');
const db = require('../config/dbConfig');

const autenticar = (req, res, next) => {
    const apiKey = req.headers['x-api-key'];

    if (!apiKey) {
        return res.status(401).json({ message: "API Key requerida" });
    }

    const query = 'SELECT * FROM usuarios WHERE apiKey = ?';  // Asegúrate de que sea la columna correcta
    db.query(query, [apiKey], (err, result) => {
        if (err || result.length === 0) {
            return res.status(403).json({ error: "API Key inválida o no encontrada" });
        }

        req.user = result[0];  // Almacenar el usuario encontrado en la solicitud
        next();  // Continuar con la siguiente función/middleware
    });
};

const autorizarAdmin = (req, res, next) => {
    if (req.usuario.role !== 'admin') {
        return res.status(403).json({ message: "Acceso denegado. Se requiere rol de administrador" });
    }
    next();
};

module.exports = { autenticar, autorizarAdmin };
