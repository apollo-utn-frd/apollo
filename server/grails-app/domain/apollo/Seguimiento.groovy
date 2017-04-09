package apollo

class Seguimiento implements Eventable {
    Date dateCreated
    Date lastUpdated

    static belongsTo = [
        seguido: Usuario,
        seguidor: Usuario
    ]

    static mappedBy = [
        seguido: 'seguidores',
        seguidor: 'seguidos'
    ]

    static constraints = {
        seguido unique: 'seguidor', validator: { seguido, seguimiento ->
            (seguido != seguimiento.seguidor) ?: ['seguimiento.seguido.seSigueASiMismo']
        }
        event nullable: true
    }

    def afterInsert() {
        Event.async.task {
            event = eventService.createEventAndNotify(
                seguidor,
                this,
                'seguimiento',
                [seguido]
            )
        }
    }

    /**
     * Devuelve si el seguimiento puede ser leído por un determinado usuario.
     */
    boolean canReadBy(Usuario usuario) {
        usuario?.isAdmin()
    }
}
