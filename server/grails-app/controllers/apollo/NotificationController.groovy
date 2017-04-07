package apollo

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)
class NotificationController implements AppTrait {
    static allowedMethods = [index: 'GET', read: 'POST']

    CollectionService collectionService
    EventService eventService

    @Secured('ROLE_USER')
    def index(boolean read, int offset, int max) {
        Usuario usuario = currentUser()

        List<Notification> notifications = eventService.findNotifications(usuario, read)
        notifications = collectionService.orderByDateCreated(notifications, false)
        notifications = collectionService.paginate(notifications, offset, max)

        respond notifications
    }

    @Transactional
    @Secured('ROLE_USER')
    def read() {
        eventService.readNotifications(currentUser())

        render(status: OK)
    }
}
