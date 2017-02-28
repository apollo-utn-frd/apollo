package apollo

import groovy.util.logging.Slf4j
import groovy.transform.ToString
import grails.core.GrailsApplication

@Slf4j
@ToString(includes = 'nombre', includePackage = false)

class RutaViaje {
    String nombre
    String descripcion = ''
    String imagenLocalPath
    boolean publico = true
    List sitios
    List autorizacionesUsuarios
    List comentariosUsuarios
    List favoritosUsuarios
    List compartidosUsuarios
    Date dateCreated
    Date lastUpdated

    transient fileService
    transient grailsApplication

    static transients = [
        'fileService',
        'grailsApplication'
    ]

    static belongsTo = [
        creador: Usuario
    ]

    static hasMany = [
        sitios: Sitio,
        autorizacionesUsuarios: Autorizacion,
        comentariosUsuarios: Comentario,
        favoritosUsuarios: Favorito,
        compartidosUsuarios: Compartido
    ]

    static constraints = {
        nombre size: 2..30, blank: false
        descripcion size: 0..1000
        imagenLocalPath nullable: true
        sitios maxSize: 25, validator: { sitios ->
            (sitios && !sitios.empty) ?: ['rutaViaje.sitios.vacio']
        }
    }

    static mapping = {
        descripcion type: 'text'
    }

    def afterInsert() {
        downloadPicture()
    }

    /**
     * Devuelve una lista con los usuarios autorizados para ver la ruta de viajes privada. Si la
     * ruta de viajes es pública el método devuelve una lista vacía.
     */
    List<Usuario> usuariosAutorizados() {
        autorizacionesUsuarios.usuario
    }

    /**
     * Devuelve si la ruta de viaje puede ser leída por un determinado usuario.
     */
    boolean canReadBy(Usuario usuario) {
        publico || (usuario == creador) || usuariosAutorizados().contains(usuario) || usuario?.isAdmin()
    }

    /**
     * Devuelve si la ruta de viaje puede ser eliminada por un determinado usuario.
     */
    boolean canDeletedBy(Usuario usuario) {
        (usuario == creador) || usuario?.isAdmin()
    }

    /**
     * Devuelve la URL de la imagen de previsualización de una ruta de viaje dada.
     */
    String imagenGoogleUrl() {
        String url = "https://maps.googleapis.com/maps/api/staticmap"

        url += "?size=${grailsApplication.config.getProperty('app.google.staticMap.size')}"

        String path = "&path=color:0x000000"

        for (int i = 0; i < sitios.size(); i++) {
            String coordenadas = "%7C${sitios[i].latitud},${sitios[i].longitud}"

            url += "&markers=label:${Character.toChars(i + 65)}${coordenadas}"

            path += coordenadas
        }

        url += path
        url += "&language=${grailsApplication.config.getProperty('app.google.staticMap.language')}"
        url += "&region=${grailsApplication.config.getProperty('app.google.staticMap.region')}"
        url += "&key=${grailsApplication.config.getProperty('app.google.staticMap.key')}"
    }

    /**
     * Descarga la imagen de previsualización y ajusta la ruta de destino de la
     * imagen.
     */
    protected void downloadPicture() {
        imagenLocalPath = "/images/rutaviaje/${id}.jpg"

        try {
            fileService.download(imagenGoogleUrl(), imagenLocalPath)
        } catch (IOException e) {
            log.warn "RutaViaje(${id}): No se pudo descargar la imagen de previsualizacion [${e}]."
            copyDefaultPicture()
        }
    }

    /**
     * Asigna la imagen de previsualización por defecto.
     */
    protected void copyDefaultPicture() {
        String defaultImage = "/images/rutaviaje/default.jpg"
        fileService.copy(defaultImage, imagenLocalPath)
        log.debug "RutaViaje(${id}): Se le asigno la imagen de previsualizacion por defecto."
    }
}
