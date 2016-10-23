package apollo

import grails.transaction.Transactional
import grails.validation.ValidationException
import grails.plugin.springsecurity.annotation.Secured
import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)

class SeguimientoController {
    static responseFormats = ['json']
    static allowedMethods = [show: 'GET', list: 'GET', create: 'POST', delete: 'DELETE']

    SecurityService securityService

    @Secured('ROLE_ADMIN')
    def show() {
        def seguimiento = Seguimiento.get(params.id)
        def usuario = securityService.getCurrentUser()

        if (!seguimiento?.canReadBy(usuario)) {
            render(status: NOT_FOUND)
            return
        }

        respond seguimiento
    }

    @Secured('ROLE_ADMIN')
    def list() {
        def usuario = securityService.getCurrentUser()

        respond Seguimiento.list().findAll { it.canReadBy(usuario) }
    }

    @Transactional
    @Secured('ROLE_USER')
    def create() {
        def seguido = Usuario.get(params.id)
        def usuario = securityService.getCurrentUser()

        if (!seguido) {
            transactionStatus.setRollbackOnly()
            render(status: NOT_FOUND)
            return
        }

        def seguimiento = new Seguimiento(
            seguido: seguido,
            seguidor: usuario
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
        def seguido = Usuario.get(params.id)
        def usuario = securityService.getCurrentUser()

        if (!seguido) {
            transactionStatus.setRollbackOnly()
            render(status: NOT_FOUND)
            return
        }

        def seguimiento = Seguimiento.findBySeguidoAndSeguidor(seguido, usuario)

        if (!seguimiento) {
            transactionStatus.setRollbackOnly()
            render(status: UNPROCESSABLE_ENTITY)
            return
        }

        seguimiento.delete(flush: true)

        render(status: OK)
    }
}
