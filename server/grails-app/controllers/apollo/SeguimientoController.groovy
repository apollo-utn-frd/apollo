package apollo

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional
class SeguimientoController implements AppTrait {
    static allowedMethods = [create: 'POST', delete: 'DELETE']

    @Secured('ROLE_USER')
    def create() {
        Usuario seguido = Usuario.get(params.id)

        if (!seguido) {
            transactionStatus.setRollbackOnly()
            render(status: NOT_FOUND)
            return
        }

        Seguimiento seguimiento = new Seguimiento(
            seguido: seguido,
            seguidor: currentUser()
        )

        if (!seguimiento.validate()) {
            transactionStatus.setRollbackOnly()
            respond seguimiento.errors
            return
        }

        seguimiento.save(flush: true)

        respond seguimiento
    }

    @Secured('ROLE_USER')
    def delete() {
        Usuario seguido = Usuario.get(params.id)

        if (!seguido) {
            transactionStatus.setRollbackOnly()
            render(status: NOT_FOUND)
            return
        }

        Seguimiento seguimiento = Seguimiento.findBySeguidoAndSeguidor(seguido, currentUser())

        if (!seguimiento) {
            transactionStatus.setRollbackOnly()
            render(status: UNPROCESSABLE_ENTITY)
            return
        }

        seguimiento.delete(flush: true)

        // Fix para error de Grails cuando el seguimiento fue creado en el Bootstrap.
        currentUser().seguidos.removeAll([null])
        seguido.seguidores.removeAll([null])

        render(status: OK)
    }
}
