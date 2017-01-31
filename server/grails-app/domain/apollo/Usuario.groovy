package apollo

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes = 'username')
@ToString(includes = 'username', includePackage = false)

class Usuario implements Serializable {
    private static final long serialVersionUID = 1

    transient springSecurityService

    String username
    String password = ' '
    String email
    String nombre
    String apellido
    String idGoogle
    String imagenGoogleUrl
    String imagenLocalPath
    String descripcion = ''
    boolean firstLogin = true
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired
    List seguidos
    List seguidores
    List rutasViaje
    List autorizaciones
    List favoritos
    List compartidos
    List comentarios
    Date dateCreated
    Date lastUpdated

    /**
     * Devuelve al usuario habiéndole eliminado todas las referencias a rutas de viajes privadas
     * que un usuario dado como parámetro no puede acceder.
     */
    Usuario sanitize(Usuario usuario) {
        rutasViaje = rutasViaje.findAll { it.canReadBy(usuario) }
        favoritos = favoritos.findAll { it.canReadBy(usuario) }
        compartidos = compartidos.findAll { it.canReadBy(usuario) }
        comentarios = comentarios.findAll { it.canReadBy(usuario) }
        this
    }

    /**
     * Devuelve una lista con los roles del usuario.
     */
    Set<Role> getAuthorities() {
        UsuarioRole.findAllByUsuario(this)*.role
    }

    /**
     * Devuelve si un usuario tiene un determinado rol.
     */
    boolean hasRole(String role) {
        authorities?.contains(Role.findByAuthority(role))
    }

    /**
     * Devuelve si el usuario es administrador.
     */
    boolean isAdmin() {
        hasRole('ROLE_ADMIN')
    }

    /**
     * Devuelve si los datos privados del usuario pueden ser leídos por un determinado usuario.
     */
    boolean canManagedBy(Usuario usuario) {
        (this == usuario) || usuario?.isAdmin()
    }

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
    }

    static transients = ['springSecurityService']

    static hasMany = [
        seguidos: Seguimiento,
        seguidores: Seguimiento,
        rutasViaje: RutaViaje,
        autorizaciones: Autorizacion,
        favoritos: Favorito,
        compartidos: Compartido,
        comentarios: Comentario
    ]

    static mappedBy = [
        seguidos: 'seguidor',
        seguidores: 'seguido'
    ]

    static constraints = {
        username unique: true, size: 4..30, matches: '^[a-zA-Z0-9\\.]+$', blank: false
        email email: true, unique: true, blank: false
        nombre size: 1..30, blank: false
        apellido size: 1..30, blank: false
        idGoogle unique: true, blank: false
        imagenGoogleUrl url: true, blank: false
        imagenLocalPath nullable: true
        descripcion size: 0..150
    }

    static mapping = {
        password column: '`password`'
        seguidos joinTable: [
            name: 'usuario_seguidos_seguimiento',
            key: 'seguido_id'
        ]
        seguidores joinTable: [
            name: 'usuario_seguidores_seguimiento',
            key: 'seguidor_id'
        ]
    }
}
