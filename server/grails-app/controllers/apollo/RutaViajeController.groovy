package apollo

import grails.transaction.Transactional
import grails.validation.ValidationException
import grails.plugin.springsecurity.annotation.Secured
import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)

class RutaViajeController {
    static responseFormats = ['json']
    static allowedMethods = [show: 'GET', list: 'GET', create: 'POST', delete: 'DELETE']

    SecurityService securityService

    @Secured('permitAll')
    def show() {
        def rutaViaje = RutaViaje.get(params.id)
        def usuario = securityService.getCurrentUser()

        if (!rutaViaje?.canReadBy(usuario)) {
            render(status: NOT_FOUND)
            return
        }

        respond rutaViaje
    }

    @Secured('ROLE_ADMIN')
    def list() {
        def usuario = securityService.getCurrentUser()

        respond RutaViaje.list().findAll { it.canReadBy(usuario) }
    }

    @Transactional
    @Secured('ROLE_USER')
    def create() {
        def usuario = securityService.getCurrentUser()

        def rutaViaje = new RutaViaje(
            creador: usuario,
            titulo: request.JSON?.titulo,
            descripcion: request.JSON?.descripcion ?: "",
            publico: (request.JSON?.publico != null) ? request.JSON?.publico : true,
            sitios: request.JSON?.sitios
        )

        if (!rutaViaje.validate()) {
            transactionStatus.setRollbackOnly()
            respond rutaViaje.errors
            return
        }

        rutaViaje.save(flush: true)

        if (!rutaViaje.publico) {
            for (idUsuarioAutorizado in request.JSON?.autorizaciones) {
                def usuarioAutorizado = Usuario.get(idUsuarioAutorizado.id)

                if (!usuarioAutorizado) {
                    transactionStatus.setRollbackOnly()
                    render(status: NOT_FOUND)
                    return
                }

                def autorizacion = new Autorizacion(
                    rutaViaje: rutaViaje,
                    usuario: usuarioAutorizado
                )

                if (!autorizacion.validate()) {
                    transactionStatus.setRollbackOnly()
                    respond autorizacion.errors
                    return
                }

                autorizacion.save(flush: true)
            }
        }

        respond rutaViaje
    }

    @Transactional
    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def delete() {
        def rutaViaje = RutaViaje.get(params.id)

        if (!rutaViaje) {
            transactionStatus.setRollbackOnly()
            render(status: NOT_FOUND)
            return
        }

        def usuario = securityService.getCurrentUser()

        if (!rutaViaje.canDeletedBy(usuario)) {
            transactionStatus.setRollbackOnly()

            if (rutaViaje.canReadBy(usuario)) {
                render(status: UNAUTHORIZED)
            } else {
                render(status: NOT_FOUND)
            }

            return
        }

        rutaViaje.delete(flush: true)

        render(status: OK)
    }
}
