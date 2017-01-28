package apollo

class PostService {
    /**
     * Devuelve una lista de las ultimas publicaciones ordenadas por la fecha
     * de creación en orden ascendente para un usuario dado.
     */
    List<Post> latest(Usuario usuario) {
        List<Post> posts = []

        usuario.rutasViaje.each { rutaViaje ->
            addTo(rutaViaje, posts)
        }

        usuario.seguidos.seguido.rutasViaje.each { rutasViaje ->
            rutasViaje.each { rutaViaje ->
                if (rutaViaje.canReadBy(usuario)) {
                    addTo(rutaViaje, posts)
                }
            }
        }

        usuario.compartidos.each { compartido ->
            addTo(compartido, posts)
        }

        usuario.seguidos.seguido.compartidos.each { compartidos ->
            compartidos.each { compartido ->
                if (compartido.rutaViaje.canReadBy(usuario)) {
                    addTo(compartido, posts)
                }
            }
        }

        // Ordena las publicaciones de forma ascendente.
        posts.sort { a, b ->
            a.dateCreated <=> b.dateCreated
        }.reverse()
    }

    /**
     * Agrega una ruta de viaje a una lista de publicaciones, si no se encuentra
     * ya incluida.
     */
    private void addTo(RutaViaje rutaViaje, List<Post> posts) {
        Post previousPost = posts.find { post ->
            post.rutaViaje == rutaViaje
        }

        if (!previousPost) {
            posts << new Post(rutaViaje)
        }
    }

    /**
     * Agrega un compartido a una lista de publicaciones, si no se encuentra ya
     * incluida. En caso de que ya se encuentre en la lista, agrega al autor del
     * compartido a la lista de usuarios que compartieron la publicación y
     * actualiza la fecha.
     */
    private void addTo(Compartido compartido, List<Post> posts) {
        Post previousPost = posts.find { post ->
            post.rutaViaje == compartido.rutaViaje
        }

        if (!previousPost) {
            posts << new Post(compartido)
        } else {
            previousPost.compartidos << compartido.usuario

            if (previousPost.dateCreated > compartido.dateCreated) {
                previousPost.dateCreated = compartido.dateCreated
            }
        }
    }
}
