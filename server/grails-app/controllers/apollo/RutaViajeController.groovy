package apollo

import static org.springframework.http.HttpStatus.*
import grails.core.GrailsApplication
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)

class RutaViajeController implements AppTrait {
    static allowedMethods = [show: 'GET', search: 'GET', create: 'POST', delete: 'DELETE']

    SearchService searchService
    RutaViajeService rutaViajeService
    GrailsApplication grailsApplication

    @Secured('permitAll')
    def images() {
        if (!params.file.endsWith('.jpg')) {
            render(status: UNPROCESSABLE_ENTITY)
            return
        }

        File image = new File("${grailsApplication.config.getProperty('app.files.path')}/images/rutaviaje/${params.file}")

        if (!image.exists()) {
            render(status: NOT_FOUND)
            return
        }

        /*
        RutaViaje rutaViaje = RutaViaje.findByImagenLocalPath(request.forwardURI)

        if (!rutaViaje?.canReadBy(currentUser())) {
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
        RutaViaje rutaViaje = RutaViaje.get(params.id)

        if (!rutaViaje?.canReadBy(currentUser())) {
            render(status: NOT_FOUND)
            return
        }

        respond rutaViaje
    }

    @Secured('permitAll')
    def search(String query, int offset, int max) {
        Search search = new Search(
            classx: RutaViaje,
            properties: ['nombre', 'descripcion'],
            query: query,
            after: { rutasViaje ->
                rutasViaje.findAll { it.canReadBy(currentUser()) }
            },
            max: max,
            offset: offset
        )

        respond searchService.findAll(search)
    }

    @Transactional
    @Secured('ROLE_USER')
    def create() {
        RutaViaje rutaViaje = new RutaViaje(
            creador: currentUser(),
            nombre: request.JSON?.nombre,
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
            if (!rutaViajeService.authorize(rutaViaje, request.JSON?.autorizaciones.id)) {
                transactionStatus.setRollbackOnly()
                respond rutaViaje.errors
                return
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
