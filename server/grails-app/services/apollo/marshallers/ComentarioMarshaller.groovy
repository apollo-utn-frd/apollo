package apollo

import grails.converters.JSON
import org.grails.web.converters.marshaller.ClosureObjectMarshaller

class ComentarioMarshaller {
    ClosureObjectMarshaller<JSON> objectMarshaller() {
        Closure closure = { Comentario comentario ->
            [:].with { output ->
                id = comentario.id

                rutaViaje = [
                    id: comentario.rutaViaje.id
                ]

                usuario = [
                    id: comentario.usuario.id
                ]

                contenido = comentario.contenido
                dateCreated = comentario.dateCreated

                output
            }
        }

        new ClosureObjectMarshaller<JSON>(Comentario, closure)
    }
}
