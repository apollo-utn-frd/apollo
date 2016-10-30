package apollo

import grails.converters.JSON
import org.grails.web.converters.marshaller.ClosureObjectMarshaller

class SeguimientoMarshaller {
    ClosureObjectMarshaller<JSON> objectMarshaller() {
        Closure closure = { Seguimiento seguimiento ->
            [:].with { output ->
                id = seguimiento.id

                seguido = [
                    id: seguimiento.seguido.id
                ]

                seguidor = [
                    id: seguimiento.seguidor.id
                ]

                dateCreated = seguimiento.dateCreated

                output
            }
        }

        new ClosureObjectMarshaller<JSON>(Seguimiento, closure)
    }
}
