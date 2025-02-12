const express = require('express');
const router = express.Router();
const unidadControler = require('../controllers/unidadControler');

router.get('/', unidadControler.getUnidades);
router.get('/:id', unidadControler.getUnidadesById);

router.post('/', unidadControler.postUnidad);
router.put('/:id', unidadControler.putUnidad);
router.delete('/:id', unidadControler.deleteUnidad);


module.exports = router;