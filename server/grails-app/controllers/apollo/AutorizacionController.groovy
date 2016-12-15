package apollo

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)

class AutorizacionController implements AppTrait {
    static allowedMethods = [show: 'GET', list: 'GET']

    @Secured('ROLE_ADMIN')
    def show() {
        Autorizacion autorizacion = Autorizacion.get(params.id)

        if (!autorizacion?.canReadBy(currentUser())) {
            render(status: NOT_FOUND)
            return
        }

        respond autorizacion
    }

    @Secured('ROLE_ADMIN')
    def list() {
        respond Autorizacion.list().findAll { it.canReadBy(currentUser()) }
    }
}
