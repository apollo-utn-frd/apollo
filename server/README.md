#Apollo Back-end [![Build Status](https://travis-ci.org/juanbono/apollo-grails.svg?branch=master)](https://travis-ci.org/juanbono/apollo-grails)
Implementa una API REST utilizando Grails 3.2.5. Para probarlo se puede instalar la aplicación [Postman](https://www.getpostman.com/) para Google Chrome e importar la colección *"Apollo.postman_collection.json"*. En el panel de la izquierda aparecerá la colección *Apollo* con ejemplos de todas las acciones que la aplicación soporta actualmente.


##API REST
###Login
Método HTTP | URI | Autenticación | Descripción
----------- | --- | ------------- | -----------
GET | /auth/google | Público | Autentica al usuario con su cuenta de Google.
GET | /auth/validate | Público | Devuelve información sobre el token de acceso.

###Usuarios
Método HTTP | URI | Autenticación | Descripción
----------- | --- | ------------- | -----------
GET | /usuario | Usuarios | Devuelve el usuario logueado.
PUT | /usuario | Usuarios | Actualiza el usuario logueado.
GET | /usuario/search | Público | Busca usuarios.
GET | /usuario/:id_usr | Público | Devuelve el usuario segun un id.
GET | /usuario/u/:username | Público | Devuelve el usuario segun un nombre de usuario.

###Últimas publicaciones
Método HTTP | URI | Autenticación | Descripción
----------- | --- | ------------- | -----------
GET | /posts | Usuarios | Devuelve las últimas publicaciones de la pantalla principal.

###Seguimientos
Método HTTP | URI | Autenticación | Descripción
----------- | --- | ------------- | -----------
POST | /usuario/seguir/:id_usr | Usuarios | Sigue al usuario.
DELETE | /usuario/seguir/:id_usr | Usuarios | Deja de seguir al usuario.
GET | /seguimiento/:id_sgm | Administradores | Devuelve el seguimiento.
GET | /seguimiento/list | Administradores | Devuelve todos los seguimientos.

###Rutas de viaje
Método HTTP | URI | Autenticación | Descripción
----------- | --- | ------------- | -----------
POST | /rutaviaje | Usuarios | Crea una ruta de viaje.
GET | /rutaviaje/search | Público | Busca rutas de viaje.
DELETE | /rutaviaje/:id_rv | Usuarios | Borra la ruta de viaje.
GET | /rutaviaje/:id_rv | Público | Devuelve la ruta de viaje.

###Comentarios
Método HTTP | URI | Autenticación | Descripción
----------- | --- | ------------- | -----------
POST | /rutaviaje/comentar/:id_rv | Usuarios | Crea un comentario.
GET | /comentario/search | Público | Busca comentarios.
GET | /comentario/:id_cmn | Usuarios | Devuelve el comentario.
DELETE | /comentario/:id_cmn | Administradores | Borra el comentario.

###Favoritos
Método HTTP | URI | Autenticación | Descripción
----------- | --- | ------------- | -----------
POST | /rutaviaje/favear/:id_rv | Usuarios | Marca como favorita a la ruta de viaje.
DELETE | /rutaviaje/favear/:id_rv | Usuarios | Desmarca como favorita a la ruta de viaje.
GET | /favorito/:id_fvr | Administradores | Devuelve el favorito.
GET | /favorito/list | Administradores | Devuelve todos los favoritos.

###Compartidos
Método HTTP | URI | Autenticación | Descripción
----------- | --- | ------------- | -----------
POST | /rutaviaje/compartir/:id_rv | Usuarios | Comparte la ruta de viaje.
DELETE | /rutaviaje/compartir/:id_rv | Usuarios | Deja de compartir a la ruta de viaje.
GET | /compartido/:id_cmp | Administradores | Devuelve el compartido.
GET | /compartido/list | Administradores | Devuelve todos los compartidos.

###Autorización
Método HTTP | URI | Autenticación | Descripción
----------- | --- | ------------- | -----------
GET | /autorizacion/:id_aut | Administradores | Devuelve la autorización.
GET | /autorizacion/list | Administradores | Devuelve todos las autorizaciones.
