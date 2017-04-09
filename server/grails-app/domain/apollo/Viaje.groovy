package apollo

import groovy.util.logging.Slf4j
import groovy.transform.ToString
import grails.core.GrailsApplication

@Slf4j
@ToString(includes = 'id,nombre', includePackage = false)
class Viaje implements Eventable {
    String nombre
    String descripcion = ''
    String imagenLocalPath
    boolean publico = true
    List sitios
    List autorizacionesUsuarios
    List comentariosUsuarios
    List favoritosUsuarios
    Date dateCreated
    Date lastUpdated

    FileService fileService
    GrailsApplication grailsApplication

    static transients = [
        'eventService',
        'fileService',
        'grailsApplication'
    ]

    static belongsTo = [
        usuario: Usuario
    ]

    static hasMany = [
        sitios: Sitio,
        autorizacionesUsuarios: Autorizacion,
        comentariosUsuarios: Comentario,
        favoritosUsuarios: Favorito
    ]

    static constraints = {
        nombre size: 2..30, blank: false
        descripcion size: 0..1000
        imagenLocalPath nullable: true
        sitios maxSize: 25, validator: { sitios ->
            (sitios && !sitios.empty) ?: ['viaje.sitios.vacio']
        }
        event nullable: true
    }

    static mapping = {
        descripcion type: 'text'
    }

    def afterInsert() {
        downloadPicture()

        Event.async.task {
            event = eventService.createEvent(this.usuario, this, 'viaje')
        }
    }

    /**
     * Devuelve una lista con los usuarios autorizados para ver el viaje privado. Si el
     * viaje es público el método devuelve una lista vacía.
     */
    List<Usuario> usuariosAutorizados() {
        autorizacionesUsuarios.usuario
    }

    /**
     * Devuelve si el viaje puede ser leído por un determinado usuario.
     */
    boolean canReadBy(Usuario usuario) {
        publico || (usuario == this.usuario) || usuariosAutorizados().contains(usuario) || usuario?.isAdmin()
    }

    /**
     * Devuelve si el viaje puede ser eliminado por un determinado usuario.
     */
    boolean canDeletedBy(Usuario usuario) {
        (usuario == this.usuario) || usuario?.isAdmin()
    }

    /**
     * Devuelve la URL de la imagen de previsualización de unviaje dado.
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
     * Descarga la imagen de previsualización y ajusta la ruta de destino de la imagen.
     */
    protected void downloadPicture() {
        imagenLocalPath = "/images/viaje/${id}.jpg"

        try {
            fileService.download(imagenGoogleUrl(), imagenLocalPath)
        } catch (IOException e) {
            log.warn "${this}: No se pudo descargar la imagen de previsualizacion [${e}]."
            copyDefaultPicture()
        }
    }

    /**
     * Asigna la imagen de previsualización por defecto.
     */
    protected void copyDefaultPicture() {
        String defaultImage = "/images/viaje/default.jpg"
        fileService.copy(defaultImage, imagenLocalPath)
        log.debug "${this}: Se le asigno la imagen de previsualizacion por defecto."
    }
}
