package apollo

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)

class RutaViajeController implements BaseControllerTrait {
    static allowedMethods = [show: 'GET', list: 'GET', create: 'POST', delete: 'DELETE']

    @Secured('permitAll')
    def show() {
        RutaViaje rutaViaje = RutaViaje.get(params.id)

        if (!rutaViaje?.canReadBy(currentUser())) {
            render(status: NOT_FOUND)
            return
        }

        respond rutaViaje
    }

    @Secured('ROLE_ADMIN')
    def list() {
        respond RutaViaje.list().findAll { it.canReadBy(currentUser()) }
    }

    @Transactional
    @Secured('ROLE_USER')
    def create() {
        RutaViaje rutaViaje = new RutaViaje(
            creador: currentUser(),
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
                Usuario usuarioAutorizado = Usuario.get(idUsuarioAutorizado.id)

                if (!usuarioAutorizado) {
                    transactionStatus.setRollbackOnly()
                    render(status: NOT_FOUND)
                    return
                }

                Autorizacion autorizacion = new Autorizacion(
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
        RutaViaje rutaViaje = RutaViaje.get(params.id)

        if (!rutaViaje) {
            transactionStatus.setRollbackOnly()
            render(status: NOT_FOUND)
            return
        }

        if (!rutaViaje.canDeletedBy(currentUser())) {
            transactionStatus.setRollbackOnly()

            if (rutaViaje.canReadBy(currentUser())) {
                render(status: FORBIDDEN)
            } else {
                render(status: NOT_FOUND)
            }

            return
        }

        rutaViaje.delete(flush: true)

        render(status: OK)
    }
}
