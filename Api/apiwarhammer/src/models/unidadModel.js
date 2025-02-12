const res = require('express/lib/response');
const db = require('../config/dbConfig');

class Unidad{

    getUnidades(callback) {
        const sql = "SELECT id, nombre FROM unidades";
        db.query(sql, callback);
    }

    getUnidadesById(id, callback) {
        const sql = "SELECT id, nombre FROM unidades WHERE id = ?";
        db.query(sql, [id], callback);
    }

    //Aádir unidad
    postUnidad(nombre, faccion_id, tipo_unidad_id, puntos, callback){
        const sql = 'INSERT into unidades (nombre, faccion_id, tipo_unidad_id, puntos) values(?, ?, ?, ?)';
        db.query(sql, [nombre, faccion_id, tipo_unidad_id, puntos], (err, result)=> {
            if(err){
                return callback(err, null);
            }
            callback(null, result.insertId);
        })
    }

    //Editar unidad
    putUnidad(id, nombre, puntos, callback){
        const sql = 'UPDATE unidades  set nombre = ?, puntos = ? WHERE id = ?';
        db.query(sql, [nombre, puntos, id], (err, result), callback);
    }

    //Eliminar unidad
    deleteUnidad(id, callback) {
        const sql = 'DELETE from unidades WHERE id = ?';
        db.query(sql, [id], callback);
    }

}

module.exports = new Unidad();