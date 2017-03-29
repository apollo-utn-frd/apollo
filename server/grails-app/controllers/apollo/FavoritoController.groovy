package apollo

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)
class FavoritoController implements AppTrait {
    static allowedMethods = [show: 'GET', list: 'GET', create: 'POST', delete: 'DELETE']

    @Secured('ROLE_USER')
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
        Viaje viaje = Viaje.get(params.id)

        if (!viaje?.canReadBy(currentUser())) {
            transactionStatus.setRollbackOnly()
            render(status: NOT_FOUND)
            return
        }

        Favorito favorito = new Favorito(
            usuario: currentUser(),
            viaje: viaje
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
        Viaje viaje = Viaje.get(params.id)

        if (!viaje?.canReadBy(currentUser())) {
            transactionStatus.setRollbackOnly()
            render(status: NOT_FOUND)
            return
        }

        Favorito favorito = Favorito.findByUsuarioAndViaje(currentUser(), viaje)

        if (!favorito) {
            transactionStatus.setRollbackOnly()
            render(status: UNPROCESSABLE_ENTITY)
            return
        }

        favorito.delete(flush: true)

        // Fix para error de Grails cuando el favorito fue creado en el Bootstrap.
        currentUser().favoritos.removeAll([null])
        viaje.favoritosUsuarios.removeAll([null])

        render(status: OK)
    }
}
