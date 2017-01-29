package apollo

class Post {
    RutaViaje rutaViaje
    Usuario creador
    List<Usuario> compartidos
    Date dateCreated

    Post(RutaViaje rutaViaje) {
        this.rutaViaje = rutaViaje
        this.creador = rutaViaje.creador
        this.compartidos = []
        this.dateCreated = rutaViaje.dateCreated
    }

    Post(Compartido compartido) {
        this.rutaViaje = compartido.rutaViaje
        this.creador = compartido.rutaViaje.creador
        this.compartidos = [compartido.usuario]
        this.dateCreated = compartido.dateCreated
    }
}
