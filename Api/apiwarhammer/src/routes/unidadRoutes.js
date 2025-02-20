const express = require('express');
const router = express.Router();
const unidadControler = require('../controllers/unidadControler');
const { autenticar, autorizarAdmin } = require('../middlewares/authMiddleware');


/**
 * @swagger
 * tags:
 *   name: Unidades
 *   description: Endpoints para gestionar unidades
 */

//----------------------------------------------------------------Publicas

/**
 * @swagger
 * /api/unidades:
 *   get:
 *     summary: Obtener todas las unidades
 *     tags: [Unidades]
 *     security:
 *       - apiKeyAuth: []
 *     responses:
 *       200:
 *         description: Lista de unidades obtenida exitosamente
 *       500:
 *         description: Error en el servidor
 */
router.get('/', autenticar, unidadControler.getUnidades);


/**
 * @swagger
 * /api/unidades/{id}:
 *   get:
 *     summary: Obtener una unidad por ID
 *     tags: [Unidades]
 *     security:
 *       - apiKeyAuth: []
 *     parameters:
 *       - in: path
 *         name: id
 *         schema:
 *           type: integer
 *         required: true
 *         description: ID de la unidad
 *     responses:
 *       200:
 *         description: Unidad encontrada
 *       404:
 *         description: Unidad no encontrada
 *       500:
 *         description: Error en el servidor
 */
router.get('/:id', autenticar, unidadControler.getUnidadesById);


/**
 * @swagger
 * /api/unidades/faccion/{faccionId}:
 *   get:
 *     summary: Obtener unidades por facción
 *     tags: [Unidades]
 *     security:
 *       - apiKeyAuth: []
 *     parameters:
 *       - in: path
 *         name: faccionId
 *         schema:
 *           type: integer
 *         required: true
 *         description: ID de la facción
 *     responses:
 *       200:
 *         description: Lista de unidades de la facción
 *       404:
 *         description: No se encontraron unidades
 *       500:
 *         description: Error en el servidor
 */
router.get('/faccion/faccionId', autenticar, unidadControler.getUnidadesByFaccion);

//Solo admin

/**
 * @swagger
 * /api/unidades:
 *   post:
 *     summary: Crear una nueva unidad
 *     tags: [Unidades]
 *     security:
 *       - apiKeyAuth: []
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               nombre:
 *                 type: string
 *               faccion_id:
 *                 type: integer
 *               tipo_unidad_id:
 *                 type: integer
 *               puntos:
 *                 type: integer
 *     responses:
 *       201:
 *         description: Unidad creada exitosamente
 *       500:
 *         description: Error en el servidor
 */
router.post('/', autorizarAdmin, unidadControler.postUnidad);

/**
 * @swagger
 * /api/unidades/{id}:
 *   put:
 *     summary: Actualizar una unidad
 *     tags: [Unidades]
 *     security:
 *       - apiKeyAuth: []
 *     parameters:
 *       - in: path
 *         name: id
 *         schema:
 *           type: integer
 *         required: true
 *         description: ID de la unidad
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               nombre:
 *                 type: string
 *               puntos:
 *                 type: integer
 *     responses:
 *       201:
 *         description: Unidad actualizada correctamente
 *       404:
 *         description: Unidad no encontrada
 *       500:
 *         description: Error en el servidor
 */
router.put('/:id', autorizarAdmin, unidadControler.putUnidad);

/**
 * @swagger
 * /api/unidades/{id}:
 *   delete:
 *     summary: Eliminar una unidad
 *     tags: [Unidades]
 *     security:
 *       - apiKeyAuth: []
 *     parameters:
 *       - in: path
 *         name: id
 *         schema:
 *           type: integer
 *         required: true
 *         description: ID de la unidad
 *     responses:
 *       200:
 *         description: Unidad eliminada correctamente
 *       404:
 *         description: Unidad no encontrada
 *       500:
 *         description: Error en el servidor
 */
router.delete('/:id', autorizarAdmin, unidadControler.deleteUnidad);
router.post('/habilidad', autorizarAdmin, unidadControler.postHabilidad);
router.post('/unidad-habilidad', autorizarAdmin, unidadControler.asignarHabilidadAUnidad);


module.exports = router;