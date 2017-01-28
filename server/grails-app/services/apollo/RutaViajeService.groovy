package apollo

import grails.core.*

class RutaViajeService {
    GrailsApplication grailsApplication

    ImageService imageService

    String pictureGoogleUrl(RutaViaje rutaViaje) {
        String url = "https://maps.googleapis.com/maps/api/staticmap?"

        url = url << "size=640x400"

        for (int i = 0; i < rutaViaje.sitios.size(); i++) {
            String label = String.valueOf(Character.toChars(i + 65))

            url = url << "&markers=label:${label}"
            url = url << "%7C${rutaViaje.sitios[i].latitud},${rutaViaje.sitios[i].longitud}"
        }

        url = url << "&path=color:0x000000"

        for (int i = 0; i < rutaViaje.sitios.size(); i++) {
            url = url << "%7C${rutaViaje.sitios[i].latitud},${rutaViaje.sitios[i].longitud}"
        }

        url = url << "&language=${grailsApplication.config.getProperty('app.google.staticMap.language')}"
        url = url << "&region=${grailsApplication.config.getProperty('app.google.staticMap.region')}"
        url = url << "&key=${grailsApplication.config.getProperty('app.google.staticMap.key')}"

        return url
    }

    // Descarga la previsualizacion de la ruta.
    boolean downloadPicture(RutaViaje rutaViaje) {
        rutaViaje.pictureGoogleUrl = pictureGoogleUrl(rutaViaje)
        rutaViaje.pictureLocalPath = "/images/rutaviaje/${rutaViaje.id}.jpg"

        return imageService.download(rutaViaje.pictureGoogleUrl, rutaViaje.pictureLocalPath)
    }
}
