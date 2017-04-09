package apollo

class Favorito implements Eventable {
    Date dateCreated
    Date lastUpdated

    static belongsTo = [
        viaje: Viaje,
        usuario: Usuario
    ]

    static constraints = {
        viaje unique: 'usuario'
        event nullable: true
    }

    def afterInsert() {
        Event.async.task {
            event = eventService.createEventAndNotify(
                usuario,
                this,
                'favorito',
                [viaje.usuario] - usuario
            )
        }
    }

    /**
     * Devuelve si el favorito puede ser leído por un determinado usuario.
     */
    boolean canReadBy(Usuario usuario) {
        viaje.canReadBy(usuario)
    }
}
