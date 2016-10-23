package apollo

class Comentario {
    String contenido
    Date dateCreated
    Date lastUpdated

    static belongsTo = [
        rutaViaje: RutaViaje,
        usuario: Usuario
    ]

    static constraints = {
        contenido size: 1..300, blank: false
    }

    /**
     * Devuelve si el comentario puede ser leído por un determinado usuario.
     */
    boolean canReadBy(Usuario usuario) {
        rutaViaje.canReadBy(usuario)
    }

    /**
     * Devuelve si el comentario puede ser eliminado por un determinado usuario.
     */
    boolean canDeletedBy(Usuario usuario) {
        usuario?.isAdmin()
    }
}
