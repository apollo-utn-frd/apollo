package apollo

import groovy.transform.ToString
import grails.core.GrailsApplication

@ToString(includes = 'id,type', includePackage = false)
class Event {
    String sourceClass
    Integer sourceId
    String resourceClass
    Integer resourceId
    String type
    Date dateCreated
    Date lastUpdated
    List notificationsUsuario

    transient grailsApplication

    static transients = [
        'grailsApplication'
    ]

    static hasMany = [
        notificationsUsuario: Notification
    ]

    static constraints = {
        sourceClass inList: CLASS_NAMES
        resourceClass inList: CLASS_NAMES
        type inList: TYPES
    }

    static final CLASS_NAMES = [
        'Autorizacion',
        'Comentario',
        'Favorito',
        'Seguimiento',
        'Usuario',
        'Viaje'
    ]

    static final TYPES = [
        'autorizacion',
        'comentario',
        'favorito',
        'seguimiento',
        'usuario',
        'viaje'
    ]

    /**
     * Devuelve si el origen del evento.
     */
    def source() {
        getObject(sourceClass, sourceId)
    }

    /**
     * Devuelve si el recurso del evento.
     */
    def resource() {
        getObject(resourceClass, resourceId)
    }

    /**
     * Devuelve si el evento puede ser leído por un determinado usuario.
     */
    boolean canReadBy(Usuario usuario) {
        source().canReadBy(usuario) && resource().canReadBy(usuario)
    }

    /**
     * Devuelve un objeto dado su clase y su id.
     */
    protected def getObject(objectClass, objectId) {
        String defaultPackage = grailsApplication.config.getProperty('grails.codegen.defaultPackage')

        try {
            Class.forName(defaultPackage + '.' + objectClass).read(objectId)
        } catch (ClassNotFoundException e) {
            null
        }
    }
}
