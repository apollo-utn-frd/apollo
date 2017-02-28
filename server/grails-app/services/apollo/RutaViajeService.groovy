package apollo

import grails.transaction.Transactional

@Transactional

class RutaViajeService {
    /**
     * Crea autorizaciones para una ruta de viaje desde una lista de ids de
     * usuario. Devuelve si las autorizaciones se pudieron crear correctamente.
     * Si hubo autorizaciones que no se pudieron crear se registran los errores
     * dentro de la ruta de viaje.
     */
    boolean authorize(RutaViaje rutaViaje, List idsUsuario) {
        for (idUsuario in idsUsuario) {
            Usuario usuario = Usuario.get(idUsuario)

            if (usuario) {
                Autorizacion autorizacion = createAuthorization(rutaViaje, usuario)

                if (!rutaViaje.hasErrors() && autorizacion) {
                    autorizacion.save(flush: true)
                }
            } else {
                rutaViaje.errors.reject(
                    'autorizacion.usuario.noExiste',
                    [idUsuario.toString()] as Object[],
                    'Error al autorizar ruta de viaje'
                )
            }
        }

        if (rutaViaje.hasErrors()) {
            transactionStatus.setRollbackOnly()
        }

        !rutaViaje.hasErrors()
    }

    /**
     * Devuelve una autorización para una ruta de viaje y un usuario. Si la
     * autorización no se puede crear se registran los errores dentro de la
     * ruta de viaje.
     */
    Autorizacion createAuthorization(RutaViaje rutaViaje, Usuario usuario) {
        Autorizacion autorizacion = new Autorizacion(
            rutaViaje: rutaViaje,
            usuario: usuario
        )

        if (!autorizacion.validate()) {
            autorizacion.errors.allErrors.each { error ->
                rutaViaje.errors.reject(
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
