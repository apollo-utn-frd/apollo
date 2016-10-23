package apollo

class Seguimiento {
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
    }

    /**
     * Devuelve si el seguimiento puede ser leído por un determinado usuario.
     */
    boolean canReadBy(Usuario usuario) {
        usuario?.isAdmin()
    }
}
