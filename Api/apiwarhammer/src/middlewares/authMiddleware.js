const usuarioModel = require('../models/usuarioModel');
const db = require('../config/dbConfig');

const autenticar = (req, res, next) => {
    const apiKey = req.headers['x-api-key'];

    if (!apiKey) {
        return res.status(401).json({ message: "API Key requerida" });
    }

    usuarioModel.getUserByApiKey(apiKey, (err, result) => {
        if (err || result.length === 0) {
            return res.status(403).json({ message: "API Key inválida" });
        }

        req.usuario = result[0]; // Guardamos el usuario en la request
        next();
    });
};

const autorizarAdmin = (req, res, next) => {
    if (req.usuario.role !== 'admin') {
        return res.status(403).json({ message: "Acceso denegado. Se requiere rol de administrador" });
    }
    next();
};

module.exports = { autenticar, autorizarAdmin };
