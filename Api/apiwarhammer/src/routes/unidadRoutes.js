const express = require('express');
const router = express.Router();
const unidadControler = require('../controllers/unidadControler');
const { autenticar, autorizarAdmin } = require('../middlewares/authMiddleware');


router.get('/', autenticar, unidadControler.getUnidades);
router.get('/:id', autenticar, unidadControler.getUnidadesById);

router.post('/', autorizarAdmin, unidadControler.postUnidad);
router.put('/:id', autorizarAdmin, unidadControler.putUnidad);
router.delete('/:id', autorizarAdmin, unidadControler.deleteUnidad);


module.exports = router;