# Apollo Back-end
[![Build Status](https://travis-ci.org/juanbono/apollo-grails.svg?branch=master)](https://travis-ci.org/juanbono/apollo-grails)
Implementa una API REST utilizando Grails 3.2.8. Para probarlo se puede instalar la aplicación [Postman](https://www.getpostman.com/) para Google Chrome e importar la colección *"Apollo.postman_collection.json"*. En el panel de la izquierda aparecerá la colección *Apollo* con ejemplos de todas las acciones que la aplicación soporta actualmente.

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

### Últimas publicaciones
Método HTTP | URI | Autenticación | Descripción
----------- | --- | ------------- | -----------
GET | /posts | Usuarios | Devuelve las últimas publicaciones de la pantalla principal.

### Seguimientos
Método HTTP | URI | Autenticación | Descripción
----------- | --- | ------------- | -----------
POST | /usuario/seguir/:id_usr | Usuarios | Sigue al usuario.
DELETE | /usuario/seguir/:id_usr | Usuarios | Deja de seguir al usuario.
GET | /seguimiento/:id_sgm | Administradores | Devuelve el seguimiento.
GET | /seguimiento/list | Administradores | Devuelve todos los seguimientos.

### Viajes
Método HTTP | URI | Autenticación | Descripción
----------- | --- | ------------- | -----------
POST | /viaje | Usuarios | Crea un viaje.
GET | /viaje/:id_rv | Público | Devuelve el viaje.
GET | /viaje/search | Público | Busca viajes.
DELETE | /viaje/:id_rv | Usuarios | Borra el viaje.

### Comentarios
Método HTTP | URI | Autenticación | Descripción
----------- | --- | ------------- | -----------
POST | /viaje/comentar/:id_rv | Usuarios | Crea un comentario.
GET | /comentario/:id_cmn | Público | Devuelve el comentario.
GET | /comentario/search | Público | Busca comentarios.
DELETE | /comentario/:id_cmn | Administradores | Borra el comentario.

### Favoritos
Método HTTP | URI | Autenticación | Descripción
----------- | --- | ------------- | -----------
POST | /viaje/favear/:id_rv | Usuarios | Marca al viaje como favorito.
DELETE | /viaje/favear/:id_rv | Usuarios | Desmarca al viaje como favorito.
GET | /favorito/:id_fvr | Administradores | Devuelve el favorito.
GET | /favorito/list | Administradores | Devuelve todos los favoritos.

### Autorizaciones
Método HTTP | URI | Autenticación | Descripción
----------- | --- | ------------- | -----------
GET | /autorizacion/:id_aut | Administradores | Devuelve la autorización.
GET | /autorizacion/list | Administradores | Devuelve todos las autorizaciones.

### Notificaciones
Método HTTP | URI | Autenticación | Descripción
----------- | --- | ------------- | -----------
GET | /notificacion | Usuarios | Devuelve todas las notificaciones.
GET | /notificacion?read=false | Usuarios | Devuelve las notificaciones no leídas.
POST | /notificacion/read | Usuarios | Marca todas las notificaciones como leídas.
