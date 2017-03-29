package apollo

import grails.gorm.DetachedCriteria
import groovy.transform.ToString
import org.apache.commons.lang.builder.HashCodeBuilder

@ToString(cache = true, includeNames = true, includePackage = false)
class UsuarioRole implements Serializable {
    private static final long serialVersionUID = 1

    Usuario usuario
    Role role

    @Override
    boolean equals(other) {
        if (other instanceof UsuarioRole) {
            other.usuarioId == usuario?.id && other.roleId == role?.id
        }
    }

    @Override
    int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder()
        if (usuario) {
            builder.append(usuario.id)
        }
        if (role) {
            builder.append(role.id)
        }
        builder.toHashCode()
    }

    static UsuarioRole get(long usuarioId, long roleId) {
        criteriaFor(usuarioId, roleId).get()
    }

    static boolean exists(long usuarioId, long roleId) {
        criteriaFor(usuarioId, roleId).count()
    }

    private static DetachedCriteria criteriaFor(long usuarioId, long roleId) {
        UsuarioRole.where {
            usuario == Usuario.load(usuarioId) &&
            role == Role.load(roleId)
        }
    }

    static UsuarioRole create(Usuario usuario, Role role) {
        UsuarioRole instance = new UsuarioRole(usuario: usuario, role: role)
        instance.save(flush: true)
        instance
    }

    static boolean remove(Usuario u, Role r) {
        if (u != null && r != null) {
            UsuarioRole.where { usuario == u && role == r }.deleteAll()
        }
    }

    static int removeAll(Usuario u) {
        u == null ? 0 : UsuarioRole.where { usuario == u }.deleteAll()
    }

    static int removeAll(Role r) {
        r == null ? 0 : UsuarioRole.where { role == r }.deleteAll()
    }

    static constraints = {
        role validator: { Role r, UsuarioRole ur ->
            if (ur.usuario?.id) {
                UsuarioRole.withNewSession {
                    if (UsuarioRole.exists(ur.usuario.id, r.id)) {
                        return ['userRole.exists']
                    }
                }
            }
        }
    }

    static mapping = {
        id composite: ['usuario', 'role']
        version false
    }
}
