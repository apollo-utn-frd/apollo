package apollo

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)

class SeguimientoController implements AppTrait {
    static allowedMethods = [show: 'GET', list: 'GET', create: 'POST', delete: 'DELETE']

    @Secured('ROLE_ADMIN')
    def show() {
        Seguimiento seguimiento = Seguimiento.get(params.id)

        if (!seguimiento?.canReadBy(currentUser())) {
            render(status: NOT_FOUND)
            return
        }

        respond seguimiento
    }

    @Secured('ROLE_ADMIN')
    def list() {
        respond Seguimiento.list().findAll { it.canReadBy(currentUser()) }
    }

    @Transactional
    @Secured('ROLE_USER')
    def create() {
        Usuario seguido = Usuario.get(params.id)

        if (!seguido) {
            transactionStatus.setRollbackOnly()
            render(status: NOT_FOUND)
            return
        }

        Seguimiento seguimiento = new Seguimiento(
            seguido: seguido,
            seguidor: currentUser()
        )

        if (!seguimiento.validate()) {
            transactionStatus.setRollbackOnly()
            respond seguimiento.errors
            return
        }

        seguimiento.save(flush: true)

        respond seguimiento
    }

    @Transactional
    @Secured('ROLE_USER')
    def delete() {
        Usuario seguido = Usuario.get(params.id)

        if (!seguido) {
            transactionStatus.setRollbackOnly()
            render(status: NOT_FOUND)
            return
        }

        Seguimiento seguimiento = Seguimiento.findBySeguidoAndSeguidor(seguido, currentUser())

        if (!seguimiento) {
            transactionStatus.setRollbackOnly()
            render(status: UNPROCESSABLE_ENTITY)
            return
        }

        seguimiento.delete(flush: true)

        render(status: OK)
    }
}
