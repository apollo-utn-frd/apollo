package apollo

class Favorito {
    Date dateCreated
    Date lastUpdated

    static belongsTo = [
        viaje: Viaje,
        usuario: Usuario
    ]

    static constraints = {
        viaje unique: 'usuario'
    }

    /**
     * Devuelve si el favorito puede ser leído por un determinado usuario.
     */
    boolean canReadBy(Usuario usuario) {
        viaje.canReadBy(usuario)
    }
}
