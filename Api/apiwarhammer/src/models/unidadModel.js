const res = require('express/lib/response');
const db = require('../config/dbConfig');

class Unidad{

    getUnidades(callback) {
        const sql = `
            SELECT 
                u.id AS unidad_id, 
                u.nombre AS unidad_nombre, 
                f.nombre AS faccion_nombre, 
                u.puntos, 
                tu.nombre AS tipo_unidad,
                GROUP_CONCAT(h.nombre SEPARATOR ', ') AS habilidades
            FROM 
                unidades u
            LEFT JOIN 
                faccion f ON u.faccion_id = f.id
            LEFT JOIN 
                tipo_unidad tu ON u.tipo_unidad_id = tu.id
            LEFT JOIN 
                unidad_habilidad uh ON u.id = uh.unidad_id
            LEFT JOIN 
                habilidad h ON uh.habilidad_id = h.id
            GROUP BY 
                u.id, u.nombre, f.nombre, u.puntos, tu.nombre
        `;
        db.query(sql, callback);
    }

    getUnidadesById(id, callback) {
        const sql = `
            SELECT 
                u.id AS unidad_id, 
                u.nombre AS unidad_nombre, 
                f.nombre AS faccion_nombre, 
                u.puntos, 
                tu.nombre AS tipo_unidad,
                GROUP_CONCAT(h.nombre SEPARATOR ', ') AS habilidades
            FROM 
                unidades u
            LEFT JOIN 
                faccion f ON u.faccion_id = f.id
            LEFT JOIN 
                tipo_unidad tu ON u.tipo_unidad_id = tu.id
            LEFT JOIN 
                unidad_habilidad uh ON u.id = uh.unidad_id
            LEFT JOIN 
                habilidad h ON uh.habilidad_id = h.id
            WHERE 
                u.id = ?
            GROUP BY 
                u.id, u.nombre, f.nombre, u.puntos, tu.nombre
        `;
        db.query(sql, [id], callback);
    }

    // Obtener unidades por facción
    getUnidadesByFaccion(faccionId, callback) {
        const sql = `
            SELECT 
                u.id AS unidad_id, 
                u.nombre AS unidad_nombre, 
                COALESCE(f.nombre, 'Sin Facción') AS faccion_nombre, 
                u.puntos, 
                COALESCE(tu.nombre, 'Desconocido') AS tipo_unidad,
                COALESCE(GROUP_CONCAT(h.nombre SEPARATOR ', '), '') AS habilidades
            FROM 
                unidades u
            INNER JOIN 
                faccion f ON u.faccion_id = f.id
            LEFT JOIN 
                tipo_unidad tu ON u.tipo_unidad_id = tu.id
            LEFT JOIN 
                unidad_habilidad uh ON u.id = uh.unidad_id
            LEFT JOIN 
                habilidad h ON uh.habilidad_id = h.id
            WHERE 
                u.faccion_id = ?
            GROUP BY 
                u.id, u.nombre, f.nombre, u.puntos, tu.nombre
        `;
        db.query(sql, [faccionId], callback);
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
    putUnidad(id, nombre, puntos, callback) {
        const sql = "UPDATE unidades SET nombre = ?, puntos = ? WHERE id = ?";
        db.query(sql, [nombre, puntos, id], (error, result) => {
            if (error) {
                console.error("Error al actualizar unidad:", error.sqlMessage || error);
                if (typeof callback === "function") {
                    callback(error, null);
                }
                return;
            }
            if (typeof callback === "function") {
                callback(null, result);
            }
        });
    }

    //Eliminar unidad
    deleteUnidad(id, callback) {
        const sql = 'DELETE from unidades WHERE id = ?';
        db.query(sql, [id], callback);
    }

    // Insertar una nueva habilidad
    postHabilidad(nombre, descripcion, callback) {
        const sql = 'INSERT INTO habilidad (nombre, descripcion) VALUES (?, ?)';
        db.query(sql, [nombre, descripcion], (err, result) => {
            if (err) {
                return callback(err, null);
            }
            callback(null, result.insertId);
        });
    }

    // Asignar una habilidad a una unidad
    asignarHabilidadAUnidad(unidad_id, habilidad_id, callback) {
        const sql = 'INSERT INTO unidad_habilidad (unidad_id, habilidad_id) VALUES (?, ?)';
        db.query(sql, [unidad_id, habilidad_id], (err, result) => {
            if (err) {
                return callback(err, null);
            }
            callback(null, result.insertId);
        });
    }

}

module.exports = new Unidad();