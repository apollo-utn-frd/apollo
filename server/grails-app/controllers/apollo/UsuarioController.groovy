package apollo

import grails.converters.JSON
import grails.transaction.Transactional
import grails.validation.ValidationException
import grails.plugin.springsecurity.annotation.Secured
import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)

class UsuarioController {
    static responseFormats = ['json']
    static allowedMethods = [index: 'GET', show: 'GET', showByUsername: 'GET', list: 'GET', update: 'PUT']

    SecurityService securityService

    @Secured('ROLE_USER')
    def index() {
        JSON.use('private') {
            respond securityService.getCurrentUser()
        }
    }

    @Secured('permitAll')
    def show() {
        def usuario = securityService.getCurrentUser()
        def usuarioMostrar = Usuario.get(params.id)

        if (usuarioMostrar?.canManagedBy(usuario)) {
            JSON.use('private') {
                respond usuarioMostrar
            }
        } else {
            respond usuarioMostrar?.sanitize(usuario)
        }
    }

    @Secured('permitAll')
    def showByUsername() {
        def usuario = securityService.getCurrentUser()
        def usuarioMostrar = Usuario.findByUsername(params.username)

        if (usuarioMostrar?.canManagedBy(usuario)) {
            JSON.use('private') {
                respond usuarioMostrar
            }
        } else {
            respond usuarioMostrar?.sanitize(usuario)
        }
    }

    @Secured('ROLE_ADMIN')
    def list() {
        def usuario = securityService.getCurrentUser()

        JSON.use('private') {
            respond Usuario.list().findAll { it.canManagedBy(usuario) }
        }
    }

    @Transactional
    @Secured('ROLE_USER')
    def update() {
        def usuario = securityService.getCurrentUser()

        if (usuario.firstLogin) {
            usuario.firstLogin = false

            bindData(usuario, request, [include: [
                'nombre',
                'apellido',
                'pictureUrl',
                'descripcion',
                'username'
            ]])
        } else {
            bindData(usuario, request, [include: [
                'nombre',
                'apellido',
                'pictureUrl',
                'descripcion'
            ]])
        }

        if (!usuario.validate()) {
            transactionStatus.setRollbackOnly()
            respond usuario.errors
            return
        }

        usuario.save(flush: true)

        JSON.use('private') {
            respond usuario
        }
    }
}
