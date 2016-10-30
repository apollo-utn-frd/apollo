package apollo

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)

class ComentarioController implements BaseControllerTrait {
    static allowedMethods = [show: 'GET', list: 'GET', create: 'POST', delete: 'DELETE']

    @Secured('permitAll')
    def show() {
        Comentario comentario = Comentario.get(params.id)

        if (!comentario?.canReadBy(currentUser())) {
            render(status: NOT_FOUND)
            return
        }

        respond comentario
    }

    @Secured('ROLE_ADMIN')
    def list() {
        respond Comentario.list().findAll { it.canReadBy(currentUser()) }
    }

    @Transactional
    @Secured('ROLE_USER')
    def create() {
        RutaViaje rutaViaje = RutaViaje.get(params.id)

        if (!rutaViaje?.canReadBy(currentUser())) {
            transactionStatus.setRollbackOnly()
            render(status: NOT_FOUND)
            return
        }

        Comentario comentario = new Comentario(
            usuario: currentUser(),
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
        Comentario comentario = Comentario.get(params.id)

        if (!comentario?.canDeletedBy(currentUser())) {
            transactionStatus.setRollbackOnly()
            render(status: NOT_FOUND)
            return
        }

        comentario.delete(flush: true)

        render(status: OK)
    }
}
