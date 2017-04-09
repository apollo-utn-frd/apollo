package apollo

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)
class PostController implements AppTrait {
    static allowedMethods = [showProfilePosts: 'GET', showHomePosts: 'GET']

    CollectionService collectionService
    EventService eventService

    @Secured('permitAll')
    def showProfilePosts(int offset, int max) {
        Usuario usuario = Usuario.read(params.id)

        if (!usuario) {
            render(status: NOT_FOUND)
            return
        }

        List<Event> posts = eventService.findProfilePosts(usuario)
        posts = collectionService.orderByDateCreated(posts, false)
        posts = collectionService.paginate(posts, offset, max)

        respond posts, view: '/event/index'
    }

    @Secured('ROLE_USER')
    def showHomePosts(int offset, int max) {
        Usuario usuario = currentUser()

        List<Event> posts = eventService.findHomePosts(usuario)
        posts = collectionService.orderByDateCreated(posts, false)
        posts = collectionService.paginate(posts, offset, max)

        respond posts, view: '/event/index'
    }
}
