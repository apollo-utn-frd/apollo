package apollo

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)

class CompartidoController implements BaseControllerTrait {
    static allowedMethods = [show: 'GET', list: 'GET', create: 'POST', delete: 'DELETE']

    @Secured('ROLE_ADMIN')
    def show() {
        Compartido compartido = Compartido.get(params.id)

        if (!compartido?.canReadBy(currentUser())) {
            render(status: NOT_FOUND)
            return
        }

        respond compartido
    }

    @Secured('ROLE_ADMIN')
    def list() {
        respond Compartido.list().findAll { it.canReadBy(currentUser()) }
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

        Compartido compartido = new Compartido(
            usuario: currentUser(),
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
        RutaViaje rutaViaje = RutaViaje.get(params.id)

        if (!rutaViaje?.canReadBy(currentUser())) {
            transactionStatus.setRollbackOnly()
            render(status: NOT_FOUND)
            return
        }

        Compartido compartido = Compartido.findByUsuarioAndRutaViaje(currentUser(), rutaViaje)

        if (!compartido) {
            transactionStatus.setRollbackOnly()
            render(status: UNPROCESSABLE_ENTITY)
            return
        }

        compartido.delete(flush: true)

        render(status: OK)
    }
}
