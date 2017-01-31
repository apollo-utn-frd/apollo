package apollo

import groovy.transform.ToString

@ToString(includes = 'nombre', includePackage = false)

class RutaViaje {
    String nombre
    String descripcion = ''
    String imagenGoogleUrl
    String imagenLocalPath
    boolean publico = true
    List sitios
    List autorizacionesUsuarios
    List comentariosUsuarios
    List favoritosUsuarios
    List compartidosUsuarios
    Date dateCreated
    Date lastUpdated

    static belongsTo = [
        creador: Usuario
    ]

    static hasMany = [
        sitios: Sitio,
        autorizacionesUsuarios: Autorizacion,
        comentariosUsuarios: Comentario,
        favoritosUsuarios: Favorito,
        compartidosUsuarios: Compartido
    ]

    static constraints = {
        nombre size: 2..30, blank: false
        descripcion size: 0..1000
        imagenGoogleUrl nullable: true, url: true, size: 0..3000
        imagenLocalPath nullable: true
        sitios maxSize: 25, validator: { sitios ->
            (sitios && !sitios.empty) ?: ['rutaViaje.sitios.vacio']
        }
    }

    static mapping = {
        descripcion type: 'text'
        imagenGoogleUrl type: 'text'
    }

    /**
     * Devuelve una lista con los usuarios autorizados para ver la ruta de viajes privada. Si la
     * ruta de viajes es pública el método devuelve una lista vacía.
     */
    List<Usuario> usuariosAutorizados() {
        autorizacionesUsuarios.usuario
    }

    /**
     * Devuelve si la ruta de viaje puede ser leída por un determinado usuario.
     */
    boolean canReadBy(Usuario usuario) {
        publico || (usuario == creador) || usuariosAutorizados().contains(usuario) || usuario?.isAdmin()
    }

    /**
     * Devuelve si la ruta de viaje puede ser eliminada por un determinado usuario.
     */
    boolean canDeletedBy(Usuario usuario) {
        (usuario == creador) || usuario?.isAdmin()
    }
}
