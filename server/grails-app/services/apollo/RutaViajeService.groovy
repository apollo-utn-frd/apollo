package apollo

import grails.core.*

class RutaViajeService {
    GrailsApplication grailsApplication

    ImageService imageService

    /**
     * Devuelve la URL de la imagen de previsualización de una ruta de viaje dada.
     */
    String pictureGoogleUrl(RutaViaje rutaViaje) {
        String url = "https://maps.googleapis.com/maps/api/staticmap?size=640x400"

        String path = "&path=color:0x000000";

        for (int i = 0; i < rutaViaje.sitios.size(); i++) {
            String coordenadas = "%7C${rutaViaje.sitios[i].latitud},${rutaViaje.sitios[i].longitud}"

            url += "&markers=label:${Character.toChars(i + 65)}${coordenadas}"

            path += coordenadas
        }

        url += path
        url += "&language=${grailsApplication.config.getProperty('app.google.staticMap.language')}"
        url += "&region=${grailsApplication.config.getProperty('app.google.staticMap.region')}"
        url += "&key=${grailsApplication.config.getProperty('app.google.staticMap.key')}"
    }

    /**
     * Descarga la previsualización de una ruta de viaje dada y, ajusta la
     * URL de origen de la imagen y su ruta de destino.
     */
    void downloadPicture(RutaViaje rutaViaje) {
        rutaViaje.pictureGoogleUrl = pictureGoogleUrl(rutaViaje)
        rutaViaje.pictureLocalPath = "/images/rutaviaje/${rutaViaje.id}.jpg"

        imageService.download(rutaViaje.pictureGoogleUrl, rutaViaje.pictureLocalPath)

        rutaViaje.save(flush: true)
    }
}
