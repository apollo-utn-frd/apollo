package apollo

import grails.util.Holders
import grails.converters.JSON
import org.grails.web.converters.marshaller.ClosureObjectMarshaller

class RutaViajeMarshaller {
    ClosureObjectMarshaller<JSON> objectMarshaller() {
        Closure closure = { RutaViaje rutaViaje ->
            [:].with { output ->
                id = rutaViaje.id

                creador = [
                    id: rutaViaje.creador.id
                ]

                nombre = rutaViaje.nombre
                descripcion = rutaViaje.descripcion
                publico = rutaViaje.publico

                if (rutaViaje.imagenLocalPath) {
                    imagenUrl = Holders.grailsApplication.config.getProperty('grails.serverURL') + rutaViaje.imagenLocalPath
                }

                sitios = rutaViaje.sitios.collect { sitio ->
                    [
                        latitud: sitio.latitud,
                        longitud: sitio.longitud
                    ]
                }

                comentarios = rutaViaje.comentariosUsuarios.collect { comentario ->
                    [
                        id: comentario.id,
                        contenido: comentario.contenido,
                        usuario: [
                            id: comentario.usuario.id
                        ],
                        dateCreated: comentario.dateCreated
                    ]
                }

                favoritos = rutaViaje.favoritosUsuarios.collect { favorito ->
                    [
                        usuario: [
                            id: favorito.usuario.id
                        ],
                        dateCreated: favorito.dateCreated
                    ]
                }

                compartidos = rutaViaje.compartidosUsuarios.collect { compartido ->
                    [
                        usuario: [
                            id: compartido.usuario.id
                        ],
                        dateCreated: compartido.dateCreated
                    ]
                }

                autorizaciones = rutaViaje.autorizacionesUsuarios.collect { autorizacion ->
                    [
                        usuario: [
                            id: autorizacion.usuario.id
                        ],
                        dateCreated: autorizacion.dateCreated
                    ]
                }

                dateCreated = rutaViaje.dateCreated

                output
            }
        }

        new ClosureObjectMarshaller<JSON>(RutaViaje, closure)
    }
}
