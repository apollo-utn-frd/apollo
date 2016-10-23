package apollo

import grails.transaction.Transactional
import grails.validation.ValidationException
import grails.plugin.springsecurity.annotation.Secured
import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)

class ComentarioController {
    static responseFormats = ['json']
    static allowedMethods = [show: 'GET', list: 'GET', create: 'POST', delete: 'DELETE']

    SecurityService securityService

    @Secured('permitAll')
    def show() {
        def comentario = Comentario.get(params.id)
        def usuario = securityService.getCurrentUser()

        if (!comentario?.canReadBy(usuario)) {
            render(status: NOT_FOUND)
            return
        }

        respond comentario
    }

    @Secured('ROLE_ADMIN')
    def list() {
        def usuario = securityService.getCurrentUser()

        respond Comentario.list().findAll { it.canReadBy(usuario) }
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

        def comentario = new Comentario(
            usuario: usuario,
            rutaViaje: rutaViaje
        )

        bindData(comentario, request, [include: [
            'contenido'
        ]])

        if (!comentario.validate()) {
            transactionStatus.setRollbackOnly()
            respond comentario.errors
            return
        }

        comentario.save(flush: true)

        respond comentario
    }

    @Transactional
    @Secured('ROLE_ADMIN')
    def delete() {
        def comentario = Comentario.get(params.id)
        def usuario = securityService.getCurrentUser()

        if (!comentario?.canDeletedBy(usuario)) {
            transactionStatus.setRollbackOnly()
            render(status: NOT_FOUND)
            return
        }

        comentario.delete(flush: true)

        render(status: OK)
    }
}
