package apollo

import groovy.util.logging.Slf4j
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@Slf4j
@EqualsAndHashCode(includes = 'username')
@ToString(includes = 'id,username', includePackage = false)
class Usuario implements Serializable, Eventable {
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
    List viajes
    List autorizaciones
    List favoritos
    List comentarios
    List notifications
    Date dateCreated
    Date lastUpdated

    private static final long serialVersionUID = 1

    transient fileService
    transient springSecurityService

    static transients = [
        'eventService',
        'fileService',
        'springSecurityService'
    ]

    static hasMany = [
        seguidos: Seguimiento,
        seguidores: Seguimiento,
        viajes: Viaje,
        autorizaciones: Autorizacion,
        favoritos: Favorito,
        comentarios: Comentario,
        notifications: Notification
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
        event nullable: true
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

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }

    def afterInsert() {
        downloadPicture()

        Event.async.task {
            event = eventService.createEvent(this, this, 'usuario')
        }
    }

    void notify(Event event) {
        Notification.async.task {
            Notification notification = new Notification(
                usuario: this,
                event: event
            )

            if (!notification.save(flush: true)) {
                log.warn "Notification(${event}, ${this}): No se pudo crear por [${notification.errors}]."
            }
        }
    }

    /**
     * Devuelve al usuario habiéndole eliminado todas las referencias a viajes privados
     * que un usuario dado como parámetro no puede acceder.
     */
    Usuario sanitize(Usuario usuario) {
        viajes = viajes.findAll { it.canReadBy(usuario) }
        favoritos = favoritos.findAll { it.canReadBy(usuario) }
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
     * Devuelve si los datos del usuario pueden ser leídos por un determinado usuario.
     */
    boolean canReadBy(Usuario usuario) {
        true
    }

    /**
     * Devuelve si los datos privados del usuario pueden ser leídos por un determinado usuario.
     */
    boolean canManagedBy(Usuario usuario) {
        (this == usuario) || usuario?.isAdmin()
    }

    /**
     * Descarga la imagen de perfil y ajusta la ruta de destino de la imagen.
     */
    protected void downloadPicture() {
        imagenLocalPath = "/images/usuario/${id}.jpg"

        try {
            fileService.download(imagenGoogleUrl, imagenLocalPath)
        } catch (IOException e) {
            log.warn "${this}: No se pudo descargar la imagen de perfil [${e}]."
            copyDefaultPicture()
        }
    }

    /**
     * Asigna la imagen de perfil por defecto.
     */
    protected void copyDefaultPicture() {
        String defaultImage = "/images/usuario/default.jpg"
        fileService.copy(defaultImage, imagenLocalPath)
        log.debug "${this}: Se le asigno la imagen de perfil por defecto."
    }

    protected void encodePassword() {
        password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
    }
}
