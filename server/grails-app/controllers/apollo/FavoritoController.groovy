package apollo

import grails.transaction.Transactional
import grails.validation.ValidationException
import grails.plugin.springsecurity.annotation.Secured
import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)

class FavoritoController {
    static responseFormats = ['json']
    static allowedMethods = [show: 'GET', list: 'GET', create: 'POST', delete: 'DELETE']

    SecurityService securityService

    @Secured('ROLE_ADMIN')
    def show() {
        def favorito = Favorito.get(params.id)
        def usuario = securityService.getCurrentUser()

        if (!favorito?.canReadBy(usuario)) {
            render(status: NOT_FOUND)
            return
        }

        respond favorito
    }

    @Secured('ROLE_ADMIN')
    def list() {
        def usuario = securityService.getCurrentUser()

        respond Favorito.list().findAll { it.canReadBy(usuario) }
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

        def favorito = new Favorito(
            usuario: usuario,
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
        def rutaViaje = RutaViaje.get(params.id)
        def usuario = securityService.getCurrentUser()

        if (!rutaViaje?.canReadBy(usuario)) {
            transactionStatus.setRollbackOnly()
            render(status: NOT_FOUND)
            return
        }

        def favorito = Favorito.findByUsuarioAndRutaViaje(usuario, rutaViaje)

        if (!favorito) {
            transactionStatus.setRollbackOnly()
            render(status: UNPROCESSABLE_ENTITY)
            return
        }

        favorito.delete(flush: true)

        render(status: OK)
    }
}
