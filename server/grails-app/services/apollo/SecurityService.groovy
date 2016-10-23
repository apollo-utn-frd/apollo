package apollo

import groovy.util.logging.Slf4j
import org.pac4j.core.profile.CommonProfile
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.rest.oauth.OauthUser
import grails.plugin.springsecurity.rest.oauth.OauthUserDetailsService

@Slf4j
class SecurityService implements OauthUserDetailsService {
    def springSecurityService

    @Delegate
    UserDetailsService userDetailsService

    /**
     * Devuelve el usuario logueado actualmente. Si no hay ningun usuario logueado devuelve null.
     */
    Usuario getCurrentUser() {
        try {
            Usuario.findByIdGoogle(springSecurityService.principal.userProfile.id)
        } catch(MissingPropertyException e) {
            null
        }
    }

    /**
     * Devuelve el OauthUser cuando un usuario se loguea utilizando su cuenta de Google.
     * Si es la primera vez que el usuario ingresa a la aplicación se le crea un Usuario nuevo con
     * los roles por defecto y se registran sus datos.
     */
    OauthUser loadUserByUserProfile(CommonProfile userProfile, Collection<GrantedAuthority> defaultRoles) throws UsernameNotFoundException {
        log.debug "Buscando usuario con perfil: ${userProfile}."

        Usuario usuario = Usuario.findByIdGoogle(userProfile.id)

        if (!usuario) {
            log.debug "Usuario no existente. Creando nuevo usuario."

            String username = userProfile.getEmail().takeWhile { it != '@' }

            // Si el username ya es utilizado por algun otro usuario, se le concatena un numero aleatorio al final.
            while (Usuario.findByUsername(username)) {
                username += new Random().nextInt(10)
            }

            usuario = new Usuario(
                username: username,
                idGoogle: userProfile.id,
                email: userProfile.getEmail(),
                nombre: userProfile.getFirstName(),
                apellido: userProfile.getFamilyName(),
                pictureUrl: userProfile.getPictureUrl()
            ).save(failOnError: true)

            log.debug "${usuario} creado. Asignando roles."

            for (authority in SpringSecurityUtils.authoritiesToRoles(defaultRoles)) {
                log.debug "Asignando rol \"${authority}\" a ${usuario}."
                UsuarioRole.create usuario, Role.findByAuthority(authority)
            }
        }

        log.debug "Se obtuvo ${usuario}. Creando OauthUser."

        return new OauthUser(usuario.username, usuario.password, defaultRoles, userProfile)
    }
}
