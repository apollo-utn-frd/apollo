package apollo

import groovy.util.logging.Slf4j
import grails.transaction.Transactional

@Slf4j
@Transactional
class EventService {
    Event createEvent(source, object, String type) {
        Event newEvent = new Event(
            sourceClass: source.class.getSimpleName(),
            sourceId: source.id,
            objectClass: object.class.getSimpleName(),
            objectId: object.id,
            type: type
        )

        if (!newEvent.validate()) {
            log.warn "Event(${type}, ${source}, ${object}): No se pudo crear por [${newEvent.errors}]."
            transactionStatus.setRollbackOnly()
            return
        }

        newEvent.save(flush: true)
    }

    Event createEventAndNotify(source, object, String type, List<Usuario> usuarios) {
        Event newEvent = createEvent(source, object, type)

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

    @Transactional(readOnly = true)
    List<Event> findEventsBySource(source) {
        List<Event> eventList = Event.findAllBySourceIdAndSourceClass(source.id, source.class.getSimpleName())

        if (eventList.empty) {
            log.warn "findEventsBySource(${source.id}, ${source.class.getSimpleName()}): No encontrado."
        }

        eventList
    }

    @Transactional(readOnly = true)
    List<Event> findHomePosts(Usuario usuario) {
        findProfilePosts(usuario) + usuario.seguidos.seguido.collect {
            findProfilePosts(it)
        }.flatten()
    }

    @Transactional(readOnly = true)
    List<Event> findProfilePosts(Usuario usuario) {
        findEventsBySource(usuario)
    }

    @Transactional(readOnly = true)
    List findNotifications(Usuario usuario, boolean read) {
        read == null ? Notification.findByUsuario(usuario) : Notification.findByUsuarioAndRead(usuario, read)
    }

    void readNotifications(Usuario usuario) {
        Notification.executeUpdate(
            'update Notification notif set notif.read = true where notif.usuario = ? and notif.read = false',
            usuario
        )
    }

    boolean deleteEvents(object) {

    }
}
