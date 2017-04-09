package apollo

import groovy.util.logging.Slf4j
import grails.transaction.Transactional

@Slf4j
@Transactional
class EventService {
    /**
     * Crea un evento dado un origen, un recurso y un tipo.
     */
    Event createEvent(source, resource, String type) {
        Event newEvent = new Event(
            sourceClass: source.class.getSimpleName(),
            sourceId: source.id,
            resourceClass: resource.class.getSimpleName(),
            resourceId: resource.id,
            type: type
        )

        if (!newEvent.validate()) {
            log.warn "Event(${type}, ${source}, ${resource}): No se pudo crear por [${newEvent.errors}]."
            transactionStatus.setRollbackOnly()
            return
        }

        newEvent.save(flush: true)
    }

    /**
     * Crea un evento y lo notifica a una lista de usuarios.
     */
    Event createEventAndNotify(source, resource, String type, List<Usuario> usuarios) {
        Event newEvent = createEvent(source, resource, type)

        if (newEvent) {
            usuarios.each { Usuario usuario ->
                // createEvent(newEvent, usuario, 'notificacion')

                /*
                 * Por alguna razón al crear una notificacion acá se intenta guardar
                 * automaticamente al salir del método aunque no haya llamado a save(). Al
                 * guardarse se borra la referencia a newEvent y tira error de que se
                 * quiere guardar NULL en event_id.
                Notification notification = new Notification(
                    usuario: usuario,
                    event: newEvent
                )

                if (!notification.validate()) {
                    log.warn "Notification(${event}, ${this}): No se pudo crear por [${notification.errors}]."
                } else {
                    notification.save(flush: true)
                }
                */
            }
        }

        newEvent
    }

    /**
     * Devuelve todos los eventos dado un origen y un tipo.
     */
    @Transactional(readOnly = true)
    List<Event> findEventsBySourceAndType(source, List<String> types) {
        Event.findAllBySourceIdAndSourceClassAndTypeInList(
            source.id,
            source.class.getSimpleName(),
            types
        )
    }

    /**
     * Devuelve todas las publicaciones de la home de un usuario.
     */
    @Transactional(readOnly = true)
    List<Event> findHomePosts(Usuario usuario) {
        findProfilePosts(usuario) + usuario.seguidos.seguido.collect {
            findProfilePosts(it)
        }.flatten()
    }

    /**
     * Devuelve todas las publicaciones del perfil de un usuario.
     */
    @Transactional(readOnly = true)
    List<Event> findProfilePosts(Usuario usuario) {
        List<Event> eventList = findEventsBySourceAndType(usuario, [
            'comentario',
            'favorito',
            'seguimiento',
            'usuario',
            'viaje'
        ])

        if (eventList.empty) {
            log.warn "findProfilePosts(usuario): No se encontraron posts."
        }

        eventList
    }

    /**
     * Devuelve todas notificaciones de un usuario. Se puede especificar si las
     * notificaciones están marcadas como leidas o no.
     */
    @Transactional(readOnly = true)
    List findNotifications(Usuario usuario, boolean read) {
        read == null ? Notification.findByUsuario(usuario) : Notification.findByUsuarioAndRead(usuario, read)
    }

    /**
     * Marca como leidas todas las notificaciones de un usuario.
     */
    void readNotifications(Usuario usuario) {
        Notification.executeUpdate(
            'update Notification notif set notif.read = true where notif.usuario = ? and notif.read = false',
            usuario
        )
    }
}
