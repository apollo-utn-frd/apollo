package apollo

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)

class PostController implements AppTrait {
    PostService postService

    @Secured('ROLE_USER')
    def show() {
        respond postService.latest(currentUser())
    }
}
