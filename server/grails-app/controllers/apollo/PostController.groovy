package apollo

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)
class PostController implements AppTrait {
    CollectionService collectionService

    @Secured('ROLE_USER')
    def show(int offset, int max) {
        Usuario usuario = currentUser()

        List<Post> posts = usuario.posts() + usuario.seguidos.seguido*.posts().flatten()

        posts = collectionService.orderByDateCreated(posts, false)
        posts = collectionService.paginate(posts, offset, max)

        respond posts
    }
}
