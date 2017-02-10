package apollo

import grails.util.Holders
import grails.converters.JSON
import org.grails.web.converters.marshaller.ClosureObjectMarshaller

class ComentarioMarshaller {
    ClosureObjectMarshaller<JSON> objectMarshaller() {
        Closure closure = { Comentario comentario ->
            [:].with { output ->
                id = comentario.id

                rutaViaje = [
                    id: comentario.rutaViaje.id,

                    creador: [
                        id: comentario.rutaViaje.creador.id
                    ],

                    nombre: comentario.rutaViaje.nombre,
                    descripcion: comentario.rutaViaje.descripcion,
                    publico: comentario.rutaViaje.publico,

                    imagenUrl: Holders.grailsApplication.config.getProperty('grails.serverURL') + comentario.rutaViaje.imagenLocalPath,

                    sitios: comentario.rutaViaje.sitios.collect { sitio ->
                        [
                            latitud: sitio.latitud,
                            longitud: sitio.longitud
                        ]
                    },

                    comentarios: comentario.rutaViaje.comentariosUsuarios.collect { comentarioRutaViaje ->
                        [
                            id: comentarioRutaViaje.id,
                            contenido: comentarioRutaViaje.contenido,
                            usuario: [
                                id: comentarioRutaViaje.usuario.id
                            ],
                            dateCreated: comentarioRutaViaje.dateCreated
                        ]
                    },

                    favoritos: comentario.rutaViaje.favoritosUsuarios.collect { favorito ->
                        [
                            usuario: [
                                id: favorito.usuario.id
                            ],
                            dateCreated: favorito.dateCreated
                        ]
                    },

                    compartidos: comentario.rutaViaje.compartidosUsuarios.collect { compartido ->
                        [
                            usuario: [
                                id: compartido.usuario.id
                            ],
                            dateCreated: compartido.dateCreated
                        ]
                    },

                    autorizaciones: comentario.rutaViaje.autorizacionesUsuarios.collect { autorizacion ->
                        [
                            usuario: [
                                id: autorizacion.usuario.id
                            ],
                            dateCreated: autorizacion.dateCreated
                        ]
                    },

                    dateCreated: comentario.rutaViaje.dateCreated
                ]

                usuario = [
                    id: comentario.usuario.id,
                    username: comentario.usuario.username,
                    email: comentario.usuario.email,
                    nombre: comentario.usuario.nombre,
                    apellido: comentario.usuario.apellido,
                    descripcion: comentario.usuario.descripcion,

                    imagenUrl: Holders.grailsApplication.config.getProperty('grails.serverURL') + comentario.usuario.imagenLocalPath,


                    seguidos: comentario.usuario.seguidos.collect { seguimiento ->
                        [
                            usuario: [
                                id: seguimiento.seguido.id
                            ],
                            dateCreated: seguimiento.dateCreated
                        ]
                    },

                    seguidores: comentario.usuario.seguidores.collect { seguimiento ->
                        [
                            usuario: [
                                id: seguimiento.seguidor.id
                            ],
                            dateCreated: seguimiento.dateCreated
                        ]
                    },

                    rutasViaje: comentario.usuario.rutasViaje.collect { rutaViaje ->
                        [
                            id: rutaViaje.id
                        ]
                    },

                    comentarios: comentario.usuario.comentarios.collect { comentarioUsuario ->
                        [
                            id: comentarioUsuario.id,
                            contenido: comentarioUsuario.contenido,
                            rutaViaje: [
                                id: comentarioUsuario.rutaViaje.id
                            ],
                            dateCreated: comentarioUsuario.dateCreated
                        ]
                    },

                    favoritos: comentario.usuario.favoritos.collect { favorito ->
                        [
                            rutaViaje: [
                                id: favorito.rutaViaje.id
                            ],
                            dateCreated: favorito.dateCreated
                        ]
                    },

                    compartidos: comentario.usuario.compartidos.collect { compartido ->
                        [
                            rutaViaje: [
                                id: compartido.rutaViaje.id
                            ],
                            dateCreated: compartido.dateCreated
                        ]
                    },

                    dateCreated: comentario.usuario.dateCreated
                ]

                contenido = comentario.contenido
                dateCreated = comentario.dateCreated

                output
            }
        }

        new ClosureObjectMarshaller<JSON>(Comentario, closure)
    }
}
