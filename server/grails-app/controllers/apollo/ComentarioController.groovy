package apollo

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)
class ComentarioController implements AppTrait {
    static allowedMethods = [show: 'GET', search: 'GET', create: 'POST', delete: 'DELETE']

    SearchService searchService

    @Secured('permitAll')
    def show() {
        Comentario comentario = Comentario.read(params.id)

        if (!comentario?.canReadBy(currentUser())) {
            render(status: NOT_FOUND)
            return
        }

        respond comentario
    }

    @Secured('permitAll')
    def search(String query, int offset, int max) {
        Search search = new Search(
            classx: Comentario,
            properties: ['contenido'],
            query: query,
            after: { comentarios ->
                comentarios.findAll { it.canReadBy(currentUser()) }
            },
            max: max,
            offset: offset
        )

        respond searchService.findAll(search)
    }

    @Transactional
    @Secured('ROLE_USER')
    def create() {
        Viaje viaje = Viaje.read(params.id)

        if (!viaje?.canReadBy(currentUser())) {
            transactionStatus.setRollbackOnly()
            render(status: NOT_FOUND)
            return
        }

        Comentario comentario = new Comentario(
            usuario: currentUser(),
            viaje: viaje
        )

        bindData(comentario, request, [include: [
            'contenido'
        ]])

        if (!comentario.validate()) {
            transactionStatus.setRollbackOnly()
            respond comentario.errors
            return
        }

        comentario.save(flush: true)

        respond comentario
    }

    @Transactional
    @Secured('ROLE_ADMIN')
    def delete() {
        Comentario comentario = Comentario.read(params.id)

        if (!comentario?.canDeletedBy(currentUser())) {
            transactionStatus.setRollbackOnly()
            render(status: NOT_FOUND)
            return
        }

        comentario.delete(flush: true)

        render(status: OK)
    }
}
