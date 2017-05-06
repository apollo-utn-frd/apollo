package apollo

class Comentario implements Eventable {
    String contenido
    Date dateCreated
    Date lastUpdated

    static belongsTo = [
        viaje: Viaje,
        usuario: Usuario
    ]

    static constraints = {
        contenido size: 1..300, blank: false
        event nullable: true
    }

    def afterInsert() {
        Event.withNewSession {
            event = eventService.createEventAndNotify(
                usuario,
                this,
                'comentario',
                ([viaje.usuario] + viaje.comentariosUsuarios.usuario - usuario).unique()
            )
        }
    }

    /**
     * Devuelve si el comentario puede ser leído por un determinado usuario.
     */
    boolean canReadBy(Usuario usuario) {
        viaje.canReadBy(usuario)
    }

    /**
     * Devuelve si el comentario puede ser eliminado por un determinado usuario.
     */
    boolean canDeletedBy(Usuario usuario) {
        usuario?.isAdmin()
    }
}
