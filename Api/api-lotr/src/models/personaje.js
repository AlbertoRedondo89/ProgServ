const { DataTypes } = require('sequelize');
const sequelize = require('../config/database');

const Personaje = sequelize.define('Personaje', {
  id: {
    type: DataTypes.INTEGER,
    primaryKey: true,
    autoIncrement: true  // Este es un campo auto-incremental
  },
  nombre: {
    type: DataTypes.STRING,
    allowNull: false
  },
  raza: {
    type: DataTypes.STRING,
    allowNull: false
  },
  descripcion: {
    type: DataTypes.STRING,
    allowNull: true
  }
}, {
  tableName: 'personajes',
  timestamps: false  // Si no tienes campos de tiempo como createdAt/updatedAt
});

module.exports = Personaje;
