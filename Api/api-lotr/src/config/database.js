const { Sequelize } = require('sequelize');
const sequelize = new Sequelize(
    process.env.DB_NAME || 'lotr',
    process.env.DB_USER || 'test',
    process.env.DB_PASSWORD || 'test',
    {
      host: process.env.DB_HOST || '127.0.0.1',
      port: process.env.DB_PORT || 3306,
      dialect: 'mysql',
      logging: false,
      pool: {
        max: 5,
        min: 0,
        acquire: 30000,
        idle: 10000
      }
    }
  );
  
  module.exports = sequelize;