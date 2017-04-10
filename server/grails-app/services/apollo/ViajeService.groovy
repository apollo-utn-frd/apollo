package apollo

import grails.transaction.Transactional

@Transactional
class ViajeService {
    /**
     * Crea autorizaciones para un viaje desde una lista de ids de usuario. Devuelve si
     * las autorizaciones se pudieron crear correctamente. Si hubo autorizaciones que no
     * se pudieron crear se registran los errores dentro del viaje.
     */
    boolean authorize(Viaje viaje, List<Integer> usuariosId) {
        List<Integer> uniqueUsuariosId = usuariosId.unique(false)

        for (usuarioId in uniqueUsuariosId) {
            Usuario usuario = Usuario.read(usuarioId)

            if (usuario) {
                Autorizacion autorizacion = createAuthorization(viaje, usuario)

                if (!viaje.hasErrors() && autorizacion) {
                    autorizacion.save(flush: true)
                }
            } else {
                viaje.errors.reject(
                    'autorizacion.usuario.notFound',
                    [usuarioId.toString()] as Object[],
                    'Error al autorizar viaje'
                )
            }
        }

        if (viaje.hasErrors()) {
            transactionStatus.setRollbackOnly()
        }

        !viaje.hasErrors()
    }

    /**
     * Devuelve una autorización para un viaje y un usuario. Si la autorización no se
     * puede crear se registran los errores dentro del viaje.
     */
    Autorizacion createAuthorization(Viaje viaje, Usuario usuario) {
        Autorizacion autorizacion = new Autorizacion(
            viaje: viaje,
            usuario: usuario
        )

        if (!autorizacion.validate()) {
            autorizacion.errors.allErrors.each { error ->
                viaje.errors.reject(
                    error.code,
                    error.arguments,
                    error.defaultMessage
                )
            }

            return null
        }

        autorizacion
    }
}
