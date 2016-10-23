package apollo

import grails.converters.JSON
import javax.annotation.PostConstruct

class MarshallerService {
    @PostConstruct
    void registerMarshallers() {
        JSON.registerObjectMarshaller(Compartido) { compartido ->
            def returnArray = [:]

            returnArray['id'] = compartido.id

            returnArray['rutaViaje'] = [
                id: compartido.rutaViaje.id
            ]

            returnArray['usuario'] = [
                id: compartido.usuario.id
            ]

            returnArray['dateCreated'] = compartido.dateCreated

            return returnArray
        }

        JSON.registerObjectMarshaller(Favorito) { favorito ->
            def returnArray = [:]

            returnArray['id'] = favorito.id

            returnArray['rutaViaje'] = [
                id: favorito.rutaViaje.id
            ]

            returnArray['usuario'] = [
                id: favorito.usuario.id
            ]

            returnArray['dateCreated'] = favorito.dateCreated

            return returnArray
        }

        JSON.registerObjectMarshaller(Seguimiento) { seguimiento ->
            def returnArray = [:]

            returnArray['id'] = seguimiento.id

            returnArray['seguido'] = [
                id: seguimiento.seguido.id
            ]

            returnArray['seguidor'] = [
                id: seguimiento.seguidor.id
            ]

            returnArray['dateCreated'] = seguimiento.dateCreated

            return returnArray
        }

        JSON.registerObjectMarshaller(Comentario) { comentario ->
            def returnArray = [:]

            returnArray['id'] = comentario.id

            returnArray['rutaViaje'] = [
                id: comentario.rutaViaje.id
            ]

            returnArray['usuario'] = [
                id: comentario.usuario.id
            ]

            returnArray['contenido'] = comentario.contenido
            returnArray['dateCreated'] = comentario.dateCreated

            return returnArray
        }

        JSON.registerObjectMarshaller(RutaViaje) { rutaViaje ->
            def returnArray = [:]

            returnArray['id'] = rutaViaje.id

            returnArray['creador'] = [
                id: rutaViaje.creador.id
            ]

            returnArray['titulo'] = rutaViaje.titulo
            returnArray['descripcion'] = rutaViaje.descripcion
            returnArray['publico'] = rutaViaje.publico

            returnArray['sitios'] = rutaViaje.sitios.collect { sitio ->
                [
                    latitud: sitio.latitud,
                    longitud: sitio.longitud
                ]
            }

            returnArray['comentarios'] = rutaViaje.comentariosUsuarios.collect { comentario ->
                [
                    id: comentario.id,
                    contenido: comentario.contenido,
                    usuario: [
                        id: comentario.usuario.id
                    ],
                    dateCreated: comentario.dateCreated
                ]
            }

            returnArray['favoritos'] = rutaViaje.favoritosUsuarios.collect { favorito ->
                [
                    usuario: [
                        id: favorito.usuario.id
                    ],
                    dateCreated: favorito.dateCreated
                ]
            }

            returnArray['compartidos'] = rutaViaje.compartidosUsuarios.collect { compartido ->
                [
                    usuario: [
                        id: compartido.usuario.id
                    ],
                    dateCreated: compartido.dateCreated
                ]
            }

            returnArray['autorizaciones'] = rutaViaje.autorizacionesUsuarios.collect { autorizacion ->
                [
                    usuario: [
                        id: autorizacion.usuario.id
                    ],
                    dateCreated: autorizacion.dateCreated
                ]
            }

            returnArray['dateCreated'] = rutaViaje.dateCreated

            return returnArray
        }

        JSON.registerObjectMarshaller(Usuario) { usuario ->
            def returnArray = [:]

            returnArray['id'] = usuario.id
            returnArray['username'] = usuario.username
            returnArray['email'] = usuario.email
            returnArray['nombre'] = usuario.nombre
            returnArray['apellido'] = usuario.apellido
            returnArray['pictureUrl'] = usuario.pictureUrl
            returnArray['descripcion'] = usuario.descripcion

            returnArray['seguidos'] = usuario.seguidos.collect { seguimiento ->
                [
                    usuario: [
                        id: seguimiento.seguido.id
                    ],
                    dateCreated: seguimiento.dateCreated
                ]
            }

            returnArray['seguidores'] = usuario.seguidores.collect { seguimiento ->
                [
                    usuario: [
                        id: seguimiento.seguidor.id
                    ],
                    dateCreated: seguimiento.dateCreated
                ]
            }

            returnArray['rutasViaje'] = usuario.rutasViaje.collect { rutaViaje ->
                [
                    id: rutaViaje.id
                ]
            }

            returnArray['comentarios'] = usuario.comentarios.collect { comentario ->
                [
                    id: comentario.id,
                    contenido: comentario.contenido,
                    rutaViaje: [
                        id: comentario.rutaViaje.id
                    ],
                    dateCreated: comentario.dateCreated
                ]
            }

            returnArray['favoritos'] = usuario.favoritos.collect { favorito ->
                [
                    rutaViaje: [
                        id: favorito.rutaViaje.id
                    ],
                    dateCreated: favorito.dateCreated
                ]
            }

            returnArray['compartidos'] = usuario.compartidos.collect { compartido ->
                [
                    rutaViaje: [
                        id: compartido.rutaViaje.id
                    ],
                    dateCreated: compartido.dateCreated
                ]
            }

            returnArray['dateCreated'] = usuario.dateCreated

            return returnArray
        }

        JSON.createNamedConfig('private') { namedConfig ->
            namedConfig.registerObjectMarshaller(Usuario) { usuario ->
                def returnArray = [:]

                returnArray['id'] = usuario.id
                returnArray['username'] = usuario.username
                returnArray['email'] = usuario.email
                returnArray['nombre'] = usuario.nombre
                returnArray['apellido'] = usuario.apellido
                returnArray['pictureUrl'] = usuario.pictureUrl
                returnArray['descripcion'] = usuario.descripcion
                returnArray['firstLogin'] = usuario.firstLogin
                returnArray['accountLocked'] = usuario.accountLocked

                returnArray['seguidos'] = usuario.seguidos.collect { seguimiento ->
                    [
                        usuario: [
                            id: seguimiento.seguido.id
                        ],
                        dateCreated: seguimiento.dateCreated
                    ]
                }

                returnArray['seguidores'] = usuario.seguidores.collect { seguimiento ->
                    [
                        usuario: [
                            id: seguimiento.seguidor.id
                        ],
                        dateCreated: seguimiento.dateCreated
                    ]
                }

                returnArray['rutasViaje'] = usuario.rutasViaje.collect { rutaViaje ->
                    [
                        id: rutaViaje.id
                    ]
                }

                returnArray['comentarios'] = usuario.comentarios.collect { comentario ->
                    [
                        id: comentario.id,
                        contenido: comentario.contenido,
                        rutaViaje: [
                            id: comentario.rutaViaje.id
                        ],
                        dateCreated: comentario.dateCreated
                    ]
                }

                returnArray['favoritos'] = usuario.favoritos.collect { favorito ->
                    [
                        rutaViaje: [
                            id: favorito.rutaViaje.id
                        ],
                        dateCreated: favorito.dateCreated
                    ]
                }

                returnArray['compartidos'] = usuario.compartidos.collect { compartido ->
                    [
                        rutaViaje: [
                            id: compartido.rutaViaje.id
                        ],
                        dateCreated: compartido.dateCreated
                    ]
                }


                returnArray['autorizaciones'] = usuario.autorizaciones.collect { autorizacion ->
                    [
                        rutaViaje: [
                            id: autorizacion.rutaViaje.id
                        ],
                        dateCreated: autorizacion.dateCreated
                    ]
                }

                returnArray['dateCreated'] = usuario.dateCreated

                return returnArray
            }
        }
    }
}
