const mysql = require('mysql2');

const db = mysql.createConnection({
    host: 'localhost',
    user: 'test',
    password: 'test',
    database: 'warhmr',
    port: 3306
});

db.connect((err) =>{
    if(err){
        throw err;
    }
    console.log("Conexión a MySQL exitosa");
})

module.exports = db;