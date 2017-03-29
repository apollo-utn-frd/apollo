package apollo

import static org.springframework.http.HttpStatus.*
import grails.core.GrailsApplication
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)
class ViajeController implements AppTrait {
    static allowedMethods = [show: 'GET', search: 'GET', create: 'POST', delete: 'DELETE']

    SearchService searchService
    ViajeService viajeService
    GrailsApplication grailsApplication

    @Secured('permitAll')
    def images() {
        if (!params.file.endsWith('.jpg')) {
            render(status: UNPROCESSABLE_ENTITY)
            return
        }

        File image = new File("${grailsApplication.config.getProperty('app.files.path')}/images/viaje/${params.file}")

        if (!image.exists()) {
            render(status: NOT_FOUND)
            return
        }

        /*
        Viaje viaje = Viaje.findByImagenLocalPath(request.forwardURI)

        if (!viaje?.canReadBy(currentUser())) {
            render(status: NOT_FOUND)
            return
        }
        */

        render(
            file: image,
            contentType: 'image/jpeg'
        )
    }

    @Secured('permitAll')
    def show() {
        Viaje viaje = Viaje.get(params.id)

        if (!viaje?.canReadBy(currentUser())) {
            render(status: NOT_FOUND)
            return
        }

        respond viaje
    }

    @Secured('permitAll')
    def search(String query, int offset, int max) {
        Search search = new Search(
            classx: Viaje,
            properties: ['nombre', 'descripcion'],
            query: query,
            after: { viajes ->
                viajes.findAll { it.canReadBy(currentUser()) }
            },
            max: max,
            offset: offset
        )

        respond searchService.findAll(search)
    }

    @Transactional
    @Secured('ROLE_USER')
    def create() {
        Viaje viaje = new Viaje(
            usuario: currentUser(),
            nombre: request.JSON?.nombre,
            descripcion: request.JSON?.descripcion ?: "",
            publico: (request.JSON?.publico != null) ? request.JSON?.publico : true,
            sitios: request.JSON?.sitios
        )

        if (!viaje.validate()) {
            transactionStatus.setRollbackOnly()
            respond viaje.errors
            return
        }

        viaje.save(flush: true)

        if (!viaje.publico) {
            if (!viajeService.authorize(viaje, request.JSON?.autorizaciones.id)) {
                transactionStatus.setRollbackOnly()
                respond viaje.errors
                return
            }
        }

        respond viaje
    }

    @Transactional
    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def delete() {
        Viaje viaje = Viaje.get(params.id)

        if (!viaje) {
            transactionStatus.setRollbackOnly()
            render(status: NOT_FOUND)
            return
        }

        if (!viaje.canDeletedBy(currentUser())) {
            transactionStatus.setRollbackOnly()

            if (viaje.canReadBy(currentUser())) {
                render(status: FORBIDDEN)
            } else {
                render(status: NOT_FOUND)
            }

            return
        }

        viaje.delete(flush: true)

        render(status: OK)
    }
}
