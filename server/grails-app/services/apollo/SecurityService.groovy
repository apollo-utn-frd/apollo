package apollo

import groovy.util.logging.Slf4j
import org.pac4j.core.profile.CommonProfile
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.rest.oauth.OauthUser
import grails.plugin.springsecurity.rest.oauth.OauthUserDetailsService
import grails.transaction.Transactional

@Slf4j
@Transactional

class SecurityService implements OauthUserDetailsService {
    static transactional = false

    def springSecurityService

    @Autowired
    UsuarioService usuarioService

    @Delegate
    UserDetailsService userDetailsService

    /**
     * Devuelve el usuario logueado actualmente. Si no hay ningun usuario logueado devuelve null.
     */
    @Transactional(readOnly = true)
    Usuario currentUser() {
        try {
            Usuario.findByIdGoogle(springSecurityService.principal.userProfile.id)
        } catch (MissingPropertyException e) {
            null
        }
    }

    /**
     * Devuelve el OauthUser cuando un usuario se loguea utilizando su cuenta de Google.
     * Si es la primera vez que el usuario ingresa a la aplicación se le crea un usuario nuevo con
     * los roles por defecto y se registran sus datos.
     */
    OauthUser loadUserByUserProfile(CommonProfile userProfile, Collection<GrantedAuthority> defaultRoles)
        throws UsernameNotFoundException {
        log.debug "Buscando usuario con perfil: ${userProfile}."

        Usuario usuario = Usuario.findByIdGoogle(userProfile.id)

        if (!usuario) {
            usuario = createUsuario(userProfile, defaultRoles)
        }

        log.debug "Se obtuvo ${usuario}. Creando OauthUser."

        new OauthUser(usuario.username, usuario.password, defaultRoles, userProfile)
    }

    /**
     * Crea un usuario nuevo con roles por defecto.
     */
    private Usuario createUsuario(CommonProfile userProfile, Collection<GrantedAuthority> defaultRoles) {
        log.debug "Usuario no existente. Creando nuevo usuario."

        String username = userProfile.email.takeWhile { it != '@' }

        // Si el username ya es utilizado por algun otro usuario, se le concatena un numero
        // aleatorio al final.
        while (Usuario.findByUsername(username)) {
            username += new Random().nextInt(10)
        }

        // Para obtener la foto de perfil original, se elimina cualquier parametro en la URL.
        Usuario usuario = new Usuario(
            username: username,
            idGoogle: userProfile.id,
            email: userProfile.email,
            nombre: userProfile.firstName,
            apellido: userProfile.familyName,
            imagenGoogleUrl: userProfile.pictureUrl.tokenize("?").first()
        ).save(failOnError: true)

        log.debug "${usuario} creado. Descargando avatar desde ${usuario.imagenGoogleUrl}"

        usuarioService.downloadPicture(usuario)

        log.debug "Avatar descargado a ${usuario.imagenLocalPath}"

        setRoles(usuario, defaultRoles)
    }

    /**
     * Registra una colección de roles a un usuario.
     */
    private Usuario setRoles(Usuario usuario, Collection<GrantedAuthority> defaultRoles) {
        for (authority in SpringSecurityUtils.authoritiesToRoles(defaultRoles)) {
            log.debug "Asignando rol \"${authority}\" a ${usuario}."
            UsuarioRole.create usuario, Role.findByAuthority(authority)
        }

        usuario
    }
}
