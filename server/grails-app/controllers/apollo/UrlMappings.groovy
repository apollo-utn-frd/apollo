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
            get    "/posts/$id?"(controller: 'post', action: 'showProfilePosts')
            post   "/seguir/$id?"(controller: 'seguimiento', action: 'create')
            delete "/seguir/$id?"(controller: 'seguimiento', action: 'delete')
        }

        // Viajes.
        group "/viaje", {
            post   "/"(controller: 'viaje', action: 'create')
            get    "/$id?"(controller: 'viaje', action: 'show')
            delete "/$id?"(controller: 'viaje', action: 'delete')
            get    "/search"(controller: 'viaje', action: 'search')
            post   "/comentar/$id?"(controller: 'comentario', action: 'create')
            post   "/favear/$id?"(controller: 'favorito', action: 'create')
            delete "/favear/$id?"(controller: 'favorito', action: 'delete')
        }

        // Comentarios.
        group "/comentario", {
            get    "/$id?"(controller: 'comentario', action: 'show')
            delete "/$id?"(controller: 'comentario', action: 'delete')
            get    "/search"(controller: 'comentario', action: 'search')
        }

        // Notificaciones.
        group "/notification", {
            get  "/"(controller: 'notification', action: 'index')
            post "/read"(controller: 'notification', actio: 'read')
        }

        // Home.
        group "/home", {
            get "/"(controller: 'post', action: 'showHomePosts')
        }

        // Imagenes.
        group "/images", {
            "/usuario/$file?"(controller: 'usuario', action: 'images')
            "/viaje/$file?"(controller: 'viaje', action: 'images')
        }

        // Autenticación.
        "/auth/$provider?"(controller: 'restOauth', action: 'authenticate')

        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
