const swaggerJsDoc = require("swagger-jsdoc");
const swaggerUi = require("swagger-ui-express");

const swaggerOptions = {
    swaggerDefinition: {
        openapi: "3.0.0",
        info: {
            title: "API de Warhammer",
            version: "1.0.0",
            description: "Documentación de la API de Warhammer",
            contact: {
                name: "El Yayo",
                email: "albertoredondo@paucasesnovescifp.cat"
            }
        },
        servers: [
            {
                url: "http://localhost:3000",
                description: "Servidor local"
            }
        ],
        components: {
            securitySchemes: {
                ApiKeyAuth: {
                    type: "apiKey",
                    in: "header",
                    name: "x-api-key",
                    description: "Clave API requerida para autenticar las peticiones",
                }
            }
        },
        security: [
            {
                ApiKeyAuth: []
            }
        ]
    },
    apis: ["./src/routes/*.js"] // Asegúrate de que la ruta sea correcta
};

const swaggerDocs = swaggerJsDoc(swaggerOptions);

module.exports = { swaggerUi, swaggerDocs };
