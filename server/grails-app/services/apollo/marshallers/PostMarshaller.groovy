package apollo

import grails.converters.JSON
import org.grails.web.converters.marshaller.ClosureObjectMarshaller

class PostMarshaller {
    ClosureObjectMarshaller<JSON> objectMarshaller() {
        Closure closure = { Post post ->
            [:].with { output ->
                rutaViaje = [
                    id: post.rutaViaje.id
                ]

                creador = [
                    id: post.creador.id
                ]

                compartidos = post.compartidos.collect { usuario ->
                    [
                        id: usuario.id
                    ]
                }

                dateCreated = post.dateCreated

                output
            }
        }

        new ClosureObjectMarshaller<JSON>(Post, closure)
    }
}
