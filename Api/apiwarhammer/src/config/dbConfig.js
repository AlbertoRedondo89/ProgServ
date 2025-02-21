require('dotenv').config();
const mysql = require('mysql2');
console.log(process.env.DB_HOST);

const db = mysql.createPool({
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PASS,
    database: process.env.DB_NAME,
    port: process.env.DB_PORT,
    waitForConnections: true,
    connectionLimit: 10,
    queueLimit: 0
});

db.getConnection((err, connection) => {
    if (err) {
        console.error("Error al conectar a la base de datos:", err);
    } else {
        console.log("Conexión a MySQL exitosa");
        connection.release();
    }
});

module.exports = db;
