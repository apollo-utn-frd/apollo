package apollo

class ImageService {
    /**
     * Descarga una imagen dada una URL de origen y una ruta de destino.
     */
    boolean download(String urlSource, String pathDestination) {
        new File('grails-app/views/' + pathDestination).withOutputStream { out ->
            new URL(urlSource).withInputStream { from ->  out << from }
        }

        true;
    }
}
