package apollo

class UrlMappings {
    static mappings = {
        // Redirigue todas las peticiones OPTIONS (CORS).
        options "/*"(controller: 'app')
        options "/**"(controller: 'app')

        // Usuarios.
        group "/usuario", {
            get    "/"(controller: 'usuario', action: 'index')
            put    "/"(controller: 'usuario', action: 'update')
            get    "/$id?"(controller: 'usuario', action: 'show')
            get    "/search"(controller: 'usuario', action: 'search')
            get    "/u/$username?"(controller: 'usuario', action: 'showByUsername')
            post   "/seguir/$id?"(controller: 'seguimiento', action: 'create')
            delete "/seguir/$id?"(controller: 'seguimiento', action: 'delete')
        }

        // Rutas de viajes.
        group "/rutaviaje", {
            post   "/"(controller: 'rutaViaje', action: 'create')
            get    "/$id?"(controller: 'rutaViaje', action: 'show')
            delete "/$id?"(controller: 'rutaViaje', action: 'delete')
            get    "/search"(controller: 'rutaViaje', action: 'search')
            post   "/comentar/$id?"(controller: 'comentario', action: 'create')
            post   "/compartir/$id?"(controller: 'compartido', action: 'create')
            delete "/compartir/$id?"(controller: 'compartido', action: 'delete')
            post   "/favear/$id?"(controller: 'favorito', action: 'create')
            delete "/favear/$id?"(controller: 'favorito', action: 'delete')
        }

        // Comentarios.
        group "/comentario", {
            get    "/$id?"(controller: 'comentario', action: 'show')
            delete "/$id?"(controller: 'comentario', action: 'delete')
            get    "/search"(controller: 'comentario', action: 'search')
        }

        // Compartidos.
        group "/compartido", {
            get "/$id?"(controller: 'compartido', action: 'show')
            get "/list"(controller: 'compartido', action: 'list')
        }

        // Seguimientos.
        group "/seguimiento", {
            get "/$id?"(controller: 'seguimiento', action: 'show')
            get "/list"(controller: 'seguimiento', action: 'list')
        }

        // Favoritos.
        group "/favorito", {
            get "/$id?"(controller: 'favorito', action: 'show')
            get "/list"(controller: 'favorito', action: 'list')
        }

        // Autorizaciones.
        group "/autorizacion", {
            get "/$id?"(controller: 'autorizacion', action: 'show')
            get "/list"(controller: 'autorizacion', action: 'list')
        }

        // Posts.
        group "/posts", {
            get "/"(controller: 'post', action: 'show')
        }

        // Autenticación.
        "/auth/$provider?"(controller: 'restOauth', action: 'authenticate')

        // Imagenes
        "/images/$domain?/$file?"(controller: 'app', action: 'images')

        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
