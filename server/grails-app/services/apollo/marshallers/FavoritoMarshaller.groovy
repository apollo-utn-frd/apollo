package apollo

import grails.converters.JSON
import org.grails.web.converters.marshaller.ClosureObjectMarshaller

class FavoritoMarshaller {
    ClosureObjectMarshaller<JSON> objectMarshaller() {
        Closure closure = { Favorito favorito ->
            [:].with { output ->
                id = favorito.id

                rutaViaje = [
                    id: favorito.rutaViaje.id
                ]

                usuario = [
                    id: favorito.usuario.id
                ]

                dateCreated = favorito.dateCreated

                output
            }
        }

        new ClosureObjectMarshaller<JSON>(Favorito, closure)
    }
}
