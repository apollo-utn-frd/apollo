package apollo

import grails.converters.JSON
import org.grails.web.converters.marshaller.ClosureObjectMarshaller

class UsuarioMarshaller {
    ClosureObjectMarshaller<JSON> objectMarshaller() {
        Closure closure = { Usuario usuario ->
            [:].with { output ->
                id = usuario.id
                username = usuario.username
                email = usuario.email
                nombre = usuario.nombre
                apellido = usuario.apellido
                pictureUrl = usuario.pictureUrl
                descripcion = usuario.descripcion

                seguidos = usuario.seguidos.collect { seguimiento ->
                    [
                        usuario: [
                            id: seguimiento.seguido.id
                        ],
                        dateCreated: seguimiento.dateCreated
                    ]
                }

                seguidores = usuario.seguidores.collect { seguimiento ->
                    [
                        usuario: [
                            id: seguimiento.seguidor.id
                        ],
                        dateCreated: seguimiento.dateCreated
                    ]
                }

                rutasViaje = usuario.rutasViaje.collect { rutaViaje ->
                    [
                        id: rutaViaje.id
                    ]
                }

                comentarios = usuario.comentarios.collect { comentario ->
                    [
                        id: comentario.id,
                        contenido: comentario.contenido,
                        rutaViaje: [
                            id: comentario.rutaViaje.id
                        ],
                        dateCreated: comentario.dateCreated
                    ]
                }

                favoritos = usuario.favoritos.collect { favorito ->
                    [
                        rutaViaje: [
                            id: favorito.rutaViaje.id
                        ],
                        dateCreated: favorito.dateCreated
                    ]
                }

                compartidos = usuario.compartidos.collect { compartido ->
                    [
                        rutaViaje: [
                            id: compartido.rutaViaje.id
                        ],
                        dateCreated: compartido.dateCreated
                    ]
                }

                dateCreated = usuario.dateCreated

                output
            }
        }

        new ClosureObjectMarshaller<JSON>(Usuario, closure)
    }
}
