package apollo

import grails.transaction.Transactional
import grails.validation.ValidationException
import grails.plugin.springsecurity.annotation.Secured
import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)

class AutorizacionController {
    static responseFormats = ['json']
    static allowedMethods = [show: 'GET', list: 'GET']

    SecurityService securityService

    @Secured('ROLE_ADMIN')
    def show() {
        def autorizacion = Autorizacion.get(params.id)
        def usuario = securityService.getCurrentUser()

        if (!autorizacion?.canReadBy(usuario)) {
            render(status: NOT_FOUND)
            return
        }

        respond autorizacion
    }

    @Secured('ROLE_ADMIN')
    def list() {
        def usuario = securityService.getCurrentUser()

        respond Autorizacion.list().findAll { it.canReadBy(usuario) }
    }
}
