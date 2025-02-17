const bcrypt = require('bcrypt');
const db = require('../config/dbConfig');

module.exports = {
    registerUsuario: async (req, res) => {
        try {
            const { username, password } = req.body;
            if (!username || !password) {
                return res.status(400).json({ message: "Faltan datos" });
            }

            // Hashear contraseña
            const hashedPassword = await bcrypt.hash(password, 10);

            // Insertar en la base de datos
            const sql = "INSERT INTO usuarios (username, password, role, apiKey) VALUES (?, ?, 'user', UUID())";
            db.query(sql, [username, hashedPassword], (err, result) => {
                if (err) {
                    return res.status(500).json({ error: err.message });
                }

                // Obtener la API Key generada
                const apiKeyQuery = "SELECT apiKey FROM usuarios WHERE id = ?";
                db.query(apiKeyQuery, [result.insertId], (err, rows) => {
                    if (err) {
                        return res.status(500).json({ error: err.message });
                    }
                    res.status(201).json({ message: "Usuario creado", apiKey: rows[0].apiKey });
                });
            });
        } catch (err) {
            res.status(500).json({ error: err.message });
        }
    }
};
