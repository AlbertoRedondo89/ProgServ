

const res = require('express/lib/response');
const unidadModel = require('../models/unidadModel');

module.exports={

    getUnidades: (req, res) => {
        unidadModel.getUnidades((err, result)=>{
            if(err) {
                res.status(500).json({error: err.message})
                return;
            }

            res.status(200).json({data: result});

        })
    },

    getUnidadesById: (req, res) => {

        const {id} = req.params;

        unidadModel.getUnidadesById(id, (err, result)=>{
            if(err) {
                res.status(500).json({error: err.message})
                return;
            }

            if(result.length === 0) {
                res.status(404).json({message: "Unidad no encontrad"});
                return;
            }

            res.status(200).json({data: result});

        })
    },

    // Añadir unidad
    postUnidad : (req, res) => {

        const {nombre, faccion_id, tipo_unidad_id, puntos} = req.body;

        unidadModel.postUnidad(nombre, faccion_id, tipo_unidad_id, puntos, (err, result) => {
            if(err){
                res.status(500).json({err: err.message});
                return;
            }
            res.status(201).json({message: 'Unidad creada correctamente', data: {idInsertado: result}});
        })

    },


    // Editar unidad
    putUnidad: (req, es) => {
        const {id} = req.params;
        const {nombre, puntos} = req.body;

        unidadModel.putUnidad(id, nombre, puntos, (err, result) => {
            if(err){
                res.status(500).json({err: err.message});
                return;
            }
            if(result.affectedRows === 0) {
                res.status(404).jason({message: "Unidad no encontrada"});
                return;
            }
            res.status(201).json({message: 'Unidad modificada correctamente', data: {idInsertado: result}});
        })

    },

    // Eliminar unidad
    deleteUnidad: (req, res) => {
        const {id} = req.params;

        unidadModel.deleteUnidad(id, (err, result) => {
            if(err) {
                res.status(500).json({error: err.message});
                return;
            }
            if(result.affectedRows === 0) {
                res.status(404).json({message: "Unidad no encontrada"});
                return;
            }
            res.status(200).json({message: "Unidad eliminada correctamente"});
        })
    }

}