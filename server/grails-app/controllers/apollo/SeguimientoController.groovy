package apollo

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional
class SeguimientoController implements AppTrait {
    static allowedMethods = [create: 'POST', delete: 'DELETE']

    @Secured('ROLE_USER')
    def create() {
        Usuario seguido = Usuario.read(params.id)

        if (!seguido) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        Seguimiento seguimiento = Seguimiento.findOrSaveWhere(
            seguido: seguido,
            seguidor: currentUser()
        )

        if (!seguimiento.validate()) {
            transactionStatus.setRollbackOnly()
            respond seguimiento.errors
            return
        }

        respond seguimiento
    }

    @Secured('ROLE_USER')
    def delete() {
        Usuario seguido = Usuario.read(params.id)

        if (!seguido) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        Seguimiento.findWhere(
            seguido: seguido,
            seguidor: currentUser()
        )?.delete(flush: true)

        // Fix para error de Grails cuando el seguimiento fue creado en el Bootstrap.
        currentUser().seguidos.removeAll([null])
        seguido.seguidores.removeAll([null])

        render status: NO_CONTENT
    }
}
