package apollo

import grails.plugin.springsecurity.annotation.Secured
import static org.springframework.http.HttpStatus.*

class ApplicationController {
    static allowedMethods = [index: 'OPTIONS']

    /**
     * Endpoint de todas las peticiones OPTIONS que recibe la aplicación (CORS).
     */
    @Secured(value = 'IS_AUTHENTICATED_ANONYMOUSLY', httpMethod = 'OPTIONS')
    def index() {
        render(status: OK)
    }
}
