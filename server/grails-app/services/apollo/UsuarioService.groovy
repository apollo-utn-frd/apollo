package apollo

class UsuarioService {
    ImageService imageService

    /**
     * Descarga la foto de perfil de un usuario dado y ajusta la ruta de
     * destino de la imagen.
     */
    void downloadPicture(Usuario usuario) {
        usuario.pictureLocalPath = "/images/usuario/${usuario.id}.jpg"

        imageService.download(usuario.pictureGoogleUrl, usuario.pictureLocalPath)

        usuario.save(flush: true)
    }
}
