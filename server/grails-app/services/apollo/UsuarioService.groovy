package apollo

import grails.transaction.Transactional

@Transactional

class UsuarioService {
    ImageService imageService

    /**
     * Descarga la foto de perfil de un usuario dado y ajusta la ruta de
     * destino de la imagen.
     */
    void downloadPicture(Usuario usuario) {
        usuario.imagenLocalPath = "/images/usuario/${usuario.id}.jpg"

        imageService.download(usuario.imagenGoogleUrl, usuario.imagenLocalPath)

        usuario.save(flush: true)
    }
}
