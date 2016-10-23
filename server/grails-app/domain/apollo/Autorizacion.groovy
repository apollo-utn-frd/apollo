package apollo

class Autorizacion {
    Date dateCreated
    Date lastUpdated

    static belongsTo = [
        rutaViaje: RutaViaje,
        usuario: Usuario
    ]

    static constraints = {
        usuario unique: 'rutaViaje', validator: { usuario, autorizacion ->
            def seguimiento = Seguimiento.findBySeguidoAndSeguidor(autorizacion.rutaViaje.creador, usuario)
            (autorizacion.rutaViaje.creador == usuario) || (seguimiento != null) ?: ['autorizacion.usuario.autorizoAUnNoSeguidor']
        }
        rutaViaje validator: { rutaViaje, autorizacion ->
            (rutaViaje.creador != autorizacion.usuario) ?: ['autorizacion.rutaViaje.seAutorizoASiMismo']
        }
    }

    /**
     * Devuelve si la autorización puede ser leída por un determinado usuario.
     */
    boolean canReadBy(Usuario usuario) {
        rutaViaje.canReadBy(usuario)
    }
}
