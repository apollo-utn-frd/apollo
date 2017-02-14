package apollo

class PostService {
    /**
     * Devuelve una lista de las ultimas publicaciones ordenadas por la fecha
     * de creación en orden ascendente para un usuario dado.
     */
    List<Post> latest(Usuario usuario, int offset, int max) {
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
        posts = posts.sort { a, b ->
            a.dateCreated <=> b.dateCreated
        }.reverse()

        paginate(posts, offset, max)
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

        if (previousPost) {
            previousPost.compartidos << compartido.usuario

            if (previousPost.dateCreated > compartido.dateCreated) {
                previousPost.dateCreated = compartido.dateCreated
            }
        } else {
            posts << new Post(compartido)
        }
    }

    /**
     * Pagina una lista de publicaciones de acuerdo a una posición de inicio y
     * una cantidad máxima de publicaciones a mostrar.
     */
    private List<Post> paginate(List<Post> posts, int offset, int max) {
        if (offset == null || offset < 0) {
            offset = 0
        }

        if (offset >= posts.size()) {
            return []
        }

        if (max == null || max <= 0) {
            max = posts.size() - offset
        }

        int end = offset + max - 1

        if (end >= posts.size()) {
            end = posts.size() - 1
        }

        posts[offset..end]
    }
}
