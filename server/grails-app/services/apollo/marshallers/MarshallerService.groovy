package apollo

import grails.converters.JSON
import javax.annotation.PostConstruct

class MarshallerService {
    @PostConstruct
    void registerMarshallers() {
        JSON.registerObjectMarshaller(new CompartidoMarshaller().objectMarshaller())
        JSON.registerObjectMarshaller(new FavoritoMarshaller().objectMarshaller())
        JSON.registerObjectMarshaller(new ComentarioMarshaller().objectMarshaller())
        JSON.registerObjectMarshaller(new SeguimientoMarshaller().objectMarshaller())
        JSON.registerObjectMarshaller(new RutaViajeMarshaller().objectMarshaller())
        JSON.registerObjectMarshaller(new UsuarioMarshaller().objectMarshaller())

        JSON.createNamedConfig('private') { namedConfig ->
            namedConfig.registerObjectMarshaller(new UsuarioPrivateMarshaller().objectMarshaller())
        }
    }
}
