package apollo

import grails.core.*
import grails.transaction.Transactional
import org.grails.web.json.JSONArray

@Transactional

class RutaViajeService {
    GrailsApplication grailsApplication

    ImageService imageService

    /**
     * Devuelve la URL de la imagen de previsualización de una ruta de viaje dada.
     */
    @Transactional(readOnly = true)
    String imagenGoogleUrl(RutaViaje rutaViaje) {
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
        rutaViaje.imagenGoogleUrl = imagenGoogleUrl(rutaViaje)
        rutaViaje.imagenLocalPath = "/images/rutaviaje/${rutaViaje.id}.jpg"

        imageService.download(rutaViaje.imagenGoogleUrl, rutaViaje.imagenLocalPath)

        rutaViaje.save(flush: true)
    }

    /**
     * Crea autorizaciones para una ruta de viaje desde una lista de ids de
     * usuario. Devuelve si las autorizaciones se pudieron crear correctamente.
     * Si hubo autorizaciones que no se pudieron crear se registran los errores
     * dentro de la ruta de viaje.
     */
    boolean authorize(RutaViaje rutaViaje, List idsUsuario) {
        boolean hasError = false;

        for (idUsuario in idsUsuario) {
            Usuario usuario = Usuario.get(idUsuario)

            if (usuario) {
                Autorizacion autorizacion = createAuthorization(rutaViaje, usuario)

                if (!hasError && autorizacion) {
                    autorizacion.save(flush: true)
                } else {
                    hasError = true
                }
            } else {
                hasError = true

                rutaViaje.errors.reject(
                    'rutaViaje.autorizaciones.usuario.noExiste',
                    [idUsuario.toString()] as Object[],
                    'Error'
                )
            }
        }

        if (hasError) {
            transactionStatus.setRollbackOnly()
        }

        !hasError
    }

    /**
     * Devuelve una autorización para una ruta de viaje y un usuario. Si la
     * autorización no se puede crear se registran los errores dentro de la
     * ruta de viaje.
     */
    Autorizacion createAuthorization(RutaViaje rutaViaje, Usuario usuario) {
        Autorizacion autorizacion = new Autorizacion(
            rutaViaje: rutaViaje,
            usuario: usuario
        )

        if (!autorizacion.validate()) {
            autorizacion.errors.allErrors.each { error ->
                rutaViaje.errors.reject(
                    error.code,
                    error.arguments,
                    error.defaultMessage
                )
            }

            return null
        }

        autorizacion
    }
}
