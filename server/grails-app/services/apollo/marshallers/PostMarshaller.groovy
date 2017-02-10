package apollo

import grails.util.Holders
import grails.converters.JSON
import org.grails.web.converters.marshaller.ClosureObjectMarshaller

class PostMarshaller {
    ClosureObjectMarshaller<JSON> objectMarshaller() {
        Closure closure = { Post post ->
            [:].with { output ->
                rutaViaje = [
                    id: post.rutaViaje.id,

                    creador: [
                        id: post.rutaViaje.creador.id
                    ],

                    nombre: post.rutaViaje.nombre,
                    descripcion: post.rutaViaje.descripcion,
                    publico: post.rutaViaje.publico,

                    imagenUrl: Holders.grailsApplication.config.getProperty('grails.serverURL') + post.rutaViaje.imagenLocalPath,

                    sitios: post.rutaViaje.sitios.collect { sitio ->
                        [
                            latitud: sitio.latitud,
                            longitud: sitio.longitud
                        ]
                    },

                    comentarios: post.rutaViaje.comentariosUsuarios.collect { comentario ->
                        [
                            id: comentario.id,
                            contenido: comentario.contenido,
                            usuario: [
                                id: comentario.usuario.id
                            ],
                            dateCreated: comentario.dateCreated
                        ]
                    },

                    favoritos: post.rutaViaje.favoritosUsuarios.collect { favorito ->
                        [
                            usuario: [
                                id: favorito.usuario.id
                            ],
                            dateCreated: favorito.dateCreated
                        ]
                    },

                    compartidos: post.rutaViaje.compartidosUsuarios.collect { compartido ->
                        [
                            usuario: [
                                id: compartido.usuario.id
                            ],
                            dateCreated: compartido.dateCreated
                        ]
                    },

                    autorizaciones: post.rutaViaje.autorizacionesUsuarios.collect { autorizacion ->
                        [
                            usuario: [
                                id: autorizacion.usuario.id
                            ],
                            dateCreated: autorizacion.dateCreated
                        ]
                    },

                    dateCreated: post.rutaViaje.dateCreated
                ]

                creador = [
                    id: post.creador.id,
                    username: post.creador.username,
                    email: post.creador.email,
                    nombre: post.creador.nombre,
                    apellido: post.creador.apellido,
                    descripcion: post.creador.descripcion,

                    imagenUrl: Holders.grailsApplication.config.getProperty('grails.serverURL') + post.creador.imagenLocalPath,


                    seguidos: post.creador.seguidos.collect { seguimiento ->
                        [
                            usuario: [
                                id: seguimiento.seguido.id
                            ],
                            dateCreated: seguimiento.dateCreated
                        ]
                    },

                    seguidores: post.creador.seguidores.collect { seguimiento ->
                        [
                            usuario: [
                                id: seguimiento.seguidor.id
                            ],
                            dateCreated: seguimiento.dateCreated
                        ]
                    },

                    rutasViaje: post.creador.rutasViaje.collect { rutaViajeCreador ->
                        [
                            id: rutaViajeCreador.id
                        ]
                    },

                    comentarios: post.creador.comentarios.collect { comentario ->
                        [
                            id: comentario.id,
                            contenido: comentario.contenido,
                            rutaViaje: [
                                id: comentario.rutaViaje.id
                            ],
                            dateCreated: comentario.dateCreated
                        ]
                    },

                    favoritos: post.creador.favoritos.collect { favorito ->
                        [
                            rutaViaje: [
                                id: favorito.rutaViaje.id
                            ],
                            dateCreated: favorito.dateCreated
                        ]
                    },

                    compartidos: post.creador.compartidos.collect { compartido ->
                        [
                            rutaViaje: [
                                id: compartido.rutaViaje.id
                            ],
                            dateCreated: compartido.dateCreated
                        ]
                    },

                    dateCreated: post.creador.dateCreated
                ]

                compartidos = post.compartidos.collect { usuario ->
                    [
                        id: usuario.id,
                        username: usuario.username,
                        email: usuario.email,
                        nombre: usuario.nombre,
                        apellido: usuario.apellido,
                        descripcion: usuario.descripcion,

                        imagenUrl: Holders.grailsApplication.config.getProperty('grails.serverURL') + usuario.imagenLocalPath,


                        seguidos: usuario.seguidos.collect { seguimiento ->
                            [
                                usuario: [
                                    id: seguimiento.seguido.id
                                ],
                                dateCreated: seguimiento.dateCreated
                            ]
                        },

                        seguidores: usuario.seguidores.collect { seguimiento ->
                            [
                                usuario: [
                                    id: seguimiento.seguidor.id
                                ],
                                dateCreated: seguimiento.dateCreated
                            ]
                        },

                        rutasViaje: usuario.rutasViaje.collect { rutaViajeCreador ->
                            [
                                id: rutaViajeCreador.id
                            ]
                        },

                        comentarios: usuario.comentarios.collect { comentario ->
                            [
                                id: comentario.id,
                                contenido: comentario.contenido,
                                rutaViaje: [
                                    id: comentario.rutaViaje.id
                                ],
                                dateCreated: comentario.dateCreated
                            ]
                        },

                        favoritos: usuario.favoritos.collect { favorito ->
                            [
                                rutaViaje: [
                                    id: favorito.rutaViaje.id
                                ],
                                dateCreated: favorito.dateCreated
                            ]
                        },

                        compartidos: usuario.compartidos.collect { compartido ->
                            [
                                rutaViaje: [
                                    id: compartido.rutaViaje.id
                                ],
                                dateCreated: compartido.dateCreated
                            ]
                        },

                        dateCreated: usuario.dateCreated
                    ]
                }

                dateCreated = post.dateCreated

                output
            }
        }

        new ClosureObjectMarshaller<JSON>(Post, closure)
    }
}
