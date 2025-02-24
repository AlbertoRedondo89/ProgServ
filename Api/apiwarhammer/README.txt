# API Warhammer

## --------------------------------------------- Descripción
Esta es una API RESTful para gestionar unidades de Warhammer. Permite realizar operaciones CRUD (Crear, Leer, Actualizar y Eliminar) sobre unidades.

## --------------------------------------------- Instalación
1. Clona este repositorio:
   git clone https://github.com/AlbertoRedondo89/ProgServ/tree/main/Api/apiwarhammer
2. Accede al directorio del proyecto:
   cd apiwarhammer
3. Instala las dependencias:
   npm install
4. Configura la base de datos en el archivo `.env` (si es necesario).

## -------------------------------------------- Uso
1. Inicia el servidor:
   node index.js
2. Accede a la documentación Swagger en:
   http://localhost:3000/api-docs

## -------------------------------------------- Endpoints
### Obtener todas las unidades
GET /unidades
#### Respuesta:
[
  {
    "id": 1,
    "nombre": "Guerrero Orco",
    "puntos": 100
  }
]

### Obtener una unidad por ID
GET /unidades/:id
#### Respuesta:
{
  "id": 1,
  "nombre": "Guerrero Orco",
  "puntos": 100
}

### Crear una nueva unidad
POST /unidades
#### Body (JSON):
{
  "nombre": "Nuevo Guerrero",
  "puntos": 120
}
#### Respuesta:
{
  "mensaje": "Unidad creada con éxito"
}

### Actualizar una unidad
PUT /unidades/:id
#### Body (JSON) (puede contener uno o ambos valores):
{
  "nombre": "Nombre Actualizado",
  "puntos": 130
}
#### Respuesta:
{
  "mensaje": "Unidad actualizada con éxito"
}

### Eliminar una unidad
DELETE /unidades/:id
#### Respuesta:
{
  "mensaje": "Unidad eliminada con éxito"
}

## ------------------------------------------------ Autenticación
Algunas rutas requieren autenticación mediante API Key. Puedes configurarla en Swagger o enviarla en los headers como:
Authorization: Bearer 12341234

## ------------------------------------------------ Tecnologías utilizadas
- Node.js
- Express
- MySQL
- Swagger para documentación

## ------------------------------------------------- Autor
- **Alberto Redondo** - [GitHub](https://github.com/tu-usuario)

