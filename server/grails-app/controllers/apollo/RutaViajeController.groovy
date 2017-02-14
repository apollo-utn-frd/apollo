package apollo

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)

class RutaViajeController implements AppTrait {
    static allowedMethods = [show: 'GET', search: 'GET', create: 'POST', delete: 'DELETE']

    SearchService searchService

    RutaViajeService rutaViajeService

    @Secured('permitAll')
    def images() {
        if (!params.file.endsWith('.jpg')) {
            render(status: UNPROCESSABLE_ENTITY)
            return
        }

        File image = new File("grails-app/views/images/rutaviaje/${params.file}")

        if (!image.exists()) {
            render(status: NOT_FOUND)
            return
        }

        RutaViaje rutaViaje = RutaViaje.findByImagenLocalPath(request.forwardURI)

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

        List<RutaViaje> results = searchService.findAll(search)

        if (results.empty) {
            render(text: [], contentType: 'text/json')
            return
        }

        respond results
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

        rutaViajeService.downloadPicture(rutaViaje)

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
