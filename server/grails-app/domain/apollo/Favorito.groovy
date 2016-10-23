package apollo

class Favorito {
    Date dateCreated
    Date lastUpdated

    static belongsTo = [
        rutaViaje: RutaViaje,
        usuario: Usuario
    ]

    static constraints = {
        rutaViaje unique: 'usuario', validator: { rutaViaje, favorito ->
            (rutaViaje.creador != favorito.usuario) ?: ['favorito.rutaViaje.faveoSuRV']
        }
    }

    /**
     * Devuelve si el favorito puede ser leído por un determinado usuario.
     */
    boolean canReadBy(Usuario usuario) {
        rutaViaje.canReadBy(usuario)
    }
}
