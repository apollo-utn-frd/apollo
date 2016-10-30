package apollo

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)

class FavoritoController implements BaseControllerTrait {
    static allowedMethods = [show: 'GET', list: 'GET', create: 'POST', delete: 'DELETE']

    @Secured('ROLE_ADMIN')
    def show() {
        Favorito favorito = Favorito.get(params.id)

        if (!favorito?.canReadBy(currentUser())) {
            render(status: NOT_FOUND)
            return
        }

        respond favorito
    }

    @Secured('ROLE_ADMIN')
    def list() {
        respond Favorito.list().findAll { it.canReadBy(currentUser()) }
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

        Favorito favorito = new Favorito(
            usuario: currentUser(),
            rutaViaje: rutaViaje
        )

        if (!favorito.validate()) {
            transactionStatus.setRollbackOnly()
            respond favorito.errors
            return
        }

        favorito.save(flush: true)

        respond favorito
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

        Favorito favorito = Favorito.findByUsuarioAndRutaViaje(currentUser(), rutaViaje)

        if (!favorito) {
            transactionStatus.setRollbackOnly()
            render(status: UNPROCESSABLE_ENTITY)
            return
        }

        favorito.delete(flush: true)

        render(status: OK)
    }
}
