# Apollo Back-end
[![Build Status](https://travis-ci.org/juanbono/apollo-grails.svg?branch=master)](https://travis-ci.org/juanbono/apollo-grails)
Implementa una API REST utilizando Grails 3. Para probarlo se puede instalar la aplicación [Postman](https://www.getpostman.com/) para Google Chrome e importar la colección *"Apollo.postman_collection.json"*. En el panel de la izquierda aparecerá la colección *Apollo* con ejemplos de todas las acciones que la aplicación soporta actualmente.

## API REST
### Login
Método HTTP | URI | Autenticación | Descripción
----------- | --- | ------------- | -----------
GET | /auth/google | Público | Autentica al usuario con su cuenta de Google.
GET | /auth/validate | Público | Devuelve información sobre el token de acceso.

### Usuarios
Método HTTP | URI | Autenticación | Descripción
----------- | --- | ------------- | -----------
GET | /usuario | Usuarios | Devuelve el usuario logueado.
PUT | /usuario | Usuarios | Actualiza el usuario logueado.
GET | /usuario/:id_usr | Público | Devuelve el usuario segun un id.
GET | /usuario/u/:username | Público | Devuelve el usuario segun un nombre de usuario.
GET | /usuario/search | Público | Busca usuarios.
POST | /usuario/seguir/:id_usr | Usuarios | Sigue al usuario.
DELETE | /usuario/seguir/:id_usr | Usuarios | Deja de seguir al usuario.

### Últimas publicaciones
Método HTTP | URI | Autenticación | Descripción
----------- | --- | ------------- | -----------
GET | /home | Usuarios | Devuelve las últimas publicaciones de la pantalla principal.

### Viajes
Método HTTP | URI | Autenticación | Descripción
----------- | --- | ------------- | -----------
POST | /viaje | Usuarios | Crea un viaje.
GET | /viaje/:id_rv | Público | Devuelve el viaje.
GET | /viaje/search | Público | Busca viajes.
DELETE | /viaje/:id_rv | Usuarios | Borra el viaje.
POST | /viaje/favear/:id_rv | Usuarios | Marca al viaje como favorito.
DELETE | /viaje/favear/:id_rv | Usuarios | Desmarca al viaje como favorito.
POST | /viaje/comentar/:id_rv | Usuarios | Crea un comentario.

### Comentarios
Método HTTP | URI | Autenticación | Descripción
----------- | --- | ------------- | -----------
GET | /comentario/:id_cmn | Público | Devuelve el comentario.
GET | /comentario/search | Público | Busca comentarios.
DELETE | /comentario/:id_cmn | Administradores | Borra el comentario.

### Notificaciones
Método HTTP | URI | Autenticación | Descripción
----------- | --- | ------------- | -----------
GET | /notification | Usuarios | Devuelve todas las notificaciones.
GET | /notification?read=false | Usuarios | Devuelve las notificaciones no leídas.
POST | /notification/read | Usuarios | Marca todas las notificaciones como leídas.
