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
            render status: NOT_FOUND
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
        Usuario usuario = currentUser()
        Viaje viaje = Viaje.read(params.id)

        if (!viaje?.canReadBy(usuario)) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        Favorito favorito = Favorito.findWhere(
            usuario: usuario,
            viaje: viaje
        )

        // currentUser().favoritos.removeAll([favorito])
        // viaje.favoritosUsuarios.removeAll([favorito])

        favorito?.delete(flush: true)

        render status: NO_CONTENT
    }
}
