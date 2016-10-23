package apollo

class Compartido {
    Date dateCreated
    Date lastUpdated

    static belongsTo = [
        rutaViaje: RutaViaje,
        usuario: Usuario
    ]

    static constraints = {
        rutaViaje unique: 'usuario', validator: { rutaViaje, compartido ->
            (rutaViaje.creador != compartido.usuario) ?: ['compartido.rutaViaje.compartioSuRV']
        }
    }

    /**
     * Devuelve si el compartido puede ser leído por un determinado usuario.
     */
    boolean canReadBy(Usuario usuario) {
        rutaViaje.canReadBy(usuario)
    }
}
