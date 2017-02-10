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
                    id: rutaViaje.creador.id,
                    username: rutaViaje.creador.username,
                    email: rutaViaje.creador.email,
                    nombre: rutaViaje.creador.nombre,
                    apellido: rutaViaje.creador.apellido,
                    descripcion: rutaViaje.creador.descripcion,

                    imagenUrl: Holders.grailsApplication.config.getProperty('grails.serverURL') + rutaViaje.creador.imagenLocalPath,

                    seguidos: rutaViaje.creador.seguidos.collect { seguimiento ->
                        [
                            usuario: [
                                id: seguimiento.seguido.id
                            ],
                            dateCreated: seguimiento.dateCreated
                        ]
                    },

                    seguidores: rutaViaje.creador.seguidores.collect { seguimiento ->
                        [
                            usuario: [
                                id: seguimiento.seguidor.id
                            ],
                            dateCreated: seguimiento.dateCreated
                        ]
                    },

                    rutasViaje: rutaViaje.creador.rutasViaje.collect { rutaViajeCreador ->
                        [
                            id: rutaViajeCreador.id
                        ]
                    },

                    comentarios: rutaViaje.creador.comentarios.collect { comentario ->
                        [
                            id: comentario.id,
                            contenido: comentario.contenido,
                            rutaViaje: [
                                id: comentario.rutaViaje.id
                            ],
                            dateCreated: comentario.dateCreated
                        ]
                    },

                    favoritos: rutaViaje.creador.favoritos.collect { favorito ->
                        [
                            rutaViaje: [
                                id: favorito.rutaViaje.id
                            ],
                            dateCreated: favorito.dateCreated
                        ]
                    },

                    compartidos: rutaViaje.creador.compartidos.collect { compartido ->
                        [
                            rutaViaje: [
                                id: compartido.rutaViaje.id
                            ],
                            dateCreated: compartido.dateCreated
                        ]
                    },

                    dateCreated: rutaViaje.creador.dateCreated
                ]

                nombre = rutaViaje.nombre
                descripcion = rutaViaje.descripcion
                publico = rutaViaje.publico

                imagenUrl = Holders.grailsApplication.config.getProperty('grails.serverURL') + rutaViaje.imagenLocalPath

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
