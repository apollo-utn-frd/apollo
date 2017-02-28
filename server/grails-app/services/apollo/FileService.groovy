package apollo

import grails.core.GrailsApplication
import org.apache.commons.io.FileUtils

class FileService {
    GrailsApplication grailsApplication

    /**
     * Descarga un archivo dada una URL de origen y una ruta de destino.
     */
    void download(String urlSource, String pathDestination) {
        URL source = new URL(urlSource)
        File destination = new File(pathRoot() + pathDestination)
        FileUtils.copyURLToFile(source, destination, 5000, 5000)
    }

    /**
     * Copia un archivo dada una ruta de origen y una ruta de destino.
     */
    void copy(String pathSource, String pathDestination) {
        File source = new File(pathRoot() + pathSource)
        File destination = new File(pathRoot() + pathDestination)
        FileUtils.copyFile(source, destination, false)
    }

    /**
     * Devuelve si un archivo existe dado una ruta.
     */
    boolean exists(String path) {
        new File(pathRoot() + path).exists()
    }

    protected String pathRoot() {
        grailsApplication.config.getProperty('app.files.path')
    }
}
