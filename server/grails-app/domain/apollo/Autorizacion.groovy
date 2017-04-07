package apollo

class Autorizacion implements Eventable {
    Date dateCreated
    Date lastUpdated

    static belongsTo = [
        viaje: Viaje,
        usuario: Usuario
    ]

    static constraints = {
        usuario unique: 'viaje', validator: { usuario, autorizacion ->
            Seguimiento seguimiento = Seguimiento.findBySeguidoAndSeguidor(autorizacion.viaje.usuario, usuario)
            boolean seAutorizoASiMismo = autorizacion.viaje.usuario == usuario

            seguimiento || seAutorizoASiMismo ?: ['autorizacion.usuario.noSeguidor']
        }
        viaje validator: { viaje, autorizacion ->
            (viaje.usuario != autorizacion.usuario) ?: ['autorizacion.viaje.autorizoASiMismo']
        }
    }

    def afterInsert() {
        Event.async.task {
            eventService.createEventAndNotify(
                viaje.usuario,
                viaje,
                'autorizacion',
                [usuario]
            )
        }
    }

    /**
     * Devuelve si la autorizacion puede ser leída por un determinado usuario.
     */
    boolean canReadBy(Usuario usuario) {
        viaje.canReadBy(usuario)
    }
}
