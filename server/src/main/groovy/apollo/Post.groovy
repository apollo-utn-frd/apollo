package apollo

class Post {
    Usuario usuario
    String tipo
    def contenido
    Date dateCreated

    Post(Seguimiento seguimiento) {
        this.usuario = seguimiento.seguidor
        this.tipo = 'seguimiento'
        this.contenido = seguimiento.seguido
        this.dateCreated = seguimiento.dateCreated
    }

    Post(Favorito favorito) {
        this.usuario = favorito.usuario
        this.tipo = 'favorito'
        this.contenido = favorito.viaje
        this.dateCreated = favorito.dateCreated
    }

    Post(Viaje viaje) {
        this.usuario = viaje.usuario
        this.tipo = 'viaje'
        this.contenido = viaje
        this.dateCreated = viaje.dateCreated
    }

    Post(Comentario comentario) {
        this.usuario = comentario.usuario
        this.tipo = 'comentario'
        this.contenido = comentario
        this.dateCreated = comentario.dateCreated
    }
}
