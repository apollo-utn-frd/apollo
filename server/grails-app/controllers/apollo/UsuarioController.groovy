package apollo

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)

class UsuarioController implements AppTrait {
    static allowedMethods = [index: 'GET', show: 'GET', showByUsername: 'GET', search: 'GET', update: 'PUT']

    SearchService searchService

    @Secured('permitAll')
    def images() {
        if (!params.file.endsWith('.jpg')) {
            render(status: UNPROCESSABLE_ENTITY)
            return
        }

        File image = new File("grails-app/views/images/usuario/${params.file}")

        if (!image.exists()) {
            render(status: NOT_FOUND)
            return
        }

        render(
            file: image,
            contentType: 'image/jpeg'
        )
    }

    @Secured('ROLE_USER')
    def index() {
        respond currentUser(), view: '/usuario/_usuario_private'
    }

    @Secured('permitAll')
    def show() {
        Usuario usuario = Usuario.get(params.id)

        respond usuario?.sanitize(currentUser())
    }

    @Secured('permitAll')
    def showByUsername() {
        Usuario usuario = Usuario.findByUsername(params.username)

        respond usuario?.sanitize(currentUser())
    }

    @Secured('permitAll')
    def search(String query, int offset, int max) {
        Search search = new Search(
            classx: Usuario,
            properties: ['username', 'nombre', 'apellido', 'descripcion'],
            query: query,
            after: { usuarios ->
                usuarios*.sanitize(currentUser())
            },
            max: max,
            offset: offset
        )

        respond searchService.findAll(search)
    }

    @Transactional
    @Secured('ROLE_USER')
    def update() {
        Usuario usuario = currentUser()

        if (usuario.firstLogin) {
            usuario.firstLogin = false

            bindData(usuario, request, [include: [
                'nombre',
                'apellido',
                'descripcion',
                'username'
            ]])
        } else {
            bindData(usuario, request, [include: [
                'nombre',
                'apellido',
                'descripcion'
            ]])
        }

        if (!usuario.validate()) {
            transactionStatus.setRollbackOnly()
            respond usuario.errors
            return
        }

        usuario.save(flush: true)

        respond usuario, view: '/usuario/_usuario_private'
    }
}
