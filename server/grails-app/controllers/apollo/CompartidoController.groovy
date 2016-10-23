package apollo

import grails.transaction.Transactional
import grails.validation.ValidationException
import grails.plugin.springsecurity.annotation.Secured
import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)

class CompartidoController {
    static responseFormats = ['json']
    static allowedMethods = [show: 'GET', list: 'GET', create: 'POST', delete: 'DELETE']

    SecurityService securityService

    @Secured('ROLE_ADMIN')
    def show() {
        def compartido = Compartido.get(params.id)
        def usuario = securityService.getCurrentUser()

        if (!compartido?.canReadBy(usuario)) {
            render(status: NOT_FOUND)
            return
        }

        respond compartido
    }

    @Secured('ROLE_ADMIN')
    def list() {
        def usuario = securityService.getCurrentUser()

        respond Compartido.list().findAll { it.canReadBy(usuario) }
    }

    @Transactional
    @Secured('ROLE_USER')
    def create() {
        def rutaViaje = RutaViaje.get(params.id)
        def usuario = securityService.getCurrentUser()

        if (!rutaViaje?.canReadBy(usuario)) {
            transactionStatus.setRollbackOnly()
            render(status: NOT_FOUND)
            return
        }

        def compartido = new Compartido(
            usuario: usuario,
            rutaViaje: rutaViaje
        )

        if (!compartido.validate()) {
            transactionStatus.setRollbackOnly()
            respond compartido.errors
            return
        }

        compartido.save(flush: true)

        respond compartido
    }

    @Transactional
    @Secured('ROLE_USER')
    def delete() {
        def rutaViaje = RutaViaje.get(params.id)
        def usuario = securityService.getCurrentUser()

        if (!rutaViaje?.canReadBy(usuario)) {
            transactionStatus.setRollbackOnly()
            render(status: NOT_FOUND)
            return
        }

        def compartido = Compartido.findByUsuarioAndRutaViaje(usuario, rutaViaje)

        if (!compartido) {
            transactionStatus.setRollbackOnly()
            render(status: UNPROCESSABLE_ENTITY)
            return
        }

        compartido.delete(flush: true)

        render(status: OK)
    }
}
