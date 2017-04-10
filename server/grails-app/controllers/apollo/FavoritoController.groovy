package apollo

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional
class FavoritoController implements AppTrait {
    static allowedMethods = [create: 'POST', delete: 'DELETE']

    @Transactional
    @Secured('ROLE_USER')
    def create() {
        Usuario usuario = currentUser()
        Viaje viaje = Viaje.read(params.id)

        if (!viaje?.canReadBy(usuario)) {
            transactionStatus.setRollbackOnly()
            render(status: NOT_FOUND)
            return
        }

        Favorito favorito = Favorito.findOrSaveWhere(
            usuario: usuario,
            viaje: viaje
        )

        if (!favorito.validate()) {
            transactionStatus.setRollbackOnly()
            respond favorito.errors
            return
        }

        respond favorito
    }

    @Transactional
    @Secured('ROLE_USER')
    def delete() {
        Viaje viaje = Viaje.read(params.id)

        if (!viaje?.canReadBy(currentUser())) {
            transactionStatus.setRollbackOnly()
            render(status: NOT_FOUND)
            return
        }

        Favorito.findWhere(
            usuario: currentUser(),
            viaje: viaje
        )?.delete(flush: true)

        // Fix para error de Grails cuando el favorito fue creado en el Bootstrap.
        currentUser().favoritos.removeAll([null])
        viaje.favoritosUsuarios.removeAll([null])

        render(status: NO_CONTENT)
    }
}
