package apollo

import static org.springframework.http.HttpStatus.*
import grails.plugin.springsecurity.annotation.Secured

class AppController {
    static allowedMethods = [index: 'OPTIONS']

    /**
     * Endpoint de todas las peticiones OPTIONS que recibe la aplicación (CORS).
     */
    @Secured(value = 'IS_AUTHENTICATED_ANONYMOUSLY', httpMethod = 'OPTIONS')
    def index() {
        render(status: OK)
    }

    @Secured('permitAll')
    def images() {
        if (!params.file.endsWith('.jpg')) {
            render(status: UNPROCESSABLE_ENTITY)
            return
        }

        File image = new File("grails-app/views/images/${params.domain}/${params.file}")

        if (!image.exists()) {
            render(status: NOT_FOUND)
            return
        }

        response.setContentType('image/jpeg')
        response.setContentLength(image.size().toInteger())

        OutputStream out = response.getOutputStream()

        out.write(image.bytes)
        out.close()
    }
}
