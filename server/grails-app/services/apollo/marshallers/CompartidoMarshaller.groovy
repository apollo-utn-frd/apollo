package apollo

import grails.converters.JSON
import org.grails.web.converters.marshaller.ClosureObjectMarshaller

class CompartidoMarshaller {
    ClosureObjectMarshaller<JSON> objectMarshaller() {
        Closure closure = { Compartido compartido ->
            [:].with { output ->
                id = compartido.id

                rutaViaje = [
                    id: compartido.rutaViaje.id
                ]

                usuario = [
                    id: compartido.usuario.id
                ]

                dateCreated = compartido.dateCreated

                output
            }
        }

        new ClosureObjectMarshaller<JSON>(Compartido, closure)
    }
}
