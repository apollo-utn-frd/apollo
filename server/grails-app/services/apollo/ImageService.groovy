package apollo

import grails.core.GrailsApplication

class ImageService {
    GrailsApplication grailsApplication

    /**
     * Descarga una imagen dada una URL de origen y una ruta de destino.
     */
    boolean download(String urlSource, String pathDestination) {
        new File(grailsApplication.config.getProperty('app.pathRoot.images') + pathDestination).withOutputStream { out ->
            new URL(urlSource).withInputStream { from -> out << from }
        }

        true
    }
}
