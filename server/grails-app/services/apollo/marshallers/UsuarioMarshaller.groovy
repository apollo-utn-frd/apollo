package apollo

import grails.util.Holders
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
                descripcion = usuario.descripcion

                imagenUrl = Holders.grailsApplication.config.getProperty('grails.serverURL') + usuario.imagenLocalPath

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
                        id: rutaViaje.id,

                        creador: [
                            id: rutaViaje.creador.id
                        ],

                        nombre: rutaViaje.nombre,
                        descripcion: rutaViaje.descripcion,
                        publico: rutaViaje.publico,

                        imagenUrl: Holders.grailsApplication.config.getProperty('grails.serverURL') + rutaViaje.imagenLocalPath,

                        sitios: rutaViaje.sitios.collect { sitio ->
                            [
                                latitud: sitio.latitud,
                                longitud: sitio.longitud
                            ]
                        },

                        comentarios: rutaViaje.comentariosUsuarios.collect { comentario ->
                            [
                                id: comentario.id,
                                contenido: comentario.contenido,
                                usuario: [
                                    id: comentario.usuario.id
                                ],
                                dateCreated: comentario.dateCreated
                            ]
                        },

                        favoritos: rutaViaje.favoritosUsuarios.collect { favorito ->
                            [
                                usuario: [
                                    id: favorito.usuario.id
                                ],
                                dateCreated: favorito.dateCreated
                            ]
                        },

                        compartidos: rutaViaje.compartidosUsuarios.collect { compartido ->
                            [
                                usuario: [
                                    id: compartido.usuario.id
                                ],
                                dateCreated: compartido.dateCreated
                            ]
                        },

                        autorizaciones: rutaViaje.autorizacionesUsuarios.collect { autorizacion ->
                            [
                                usuario: [
                                    id: autorizacion.usuario.id
                                ],
                                dateCreated: autorizacion.dateCreated
                            ]
                        },

                        dateCreated: rutaViaje.dateCreated
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
