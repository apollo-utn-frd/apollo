package apollo

class UsuarioService {
    ImageService imageService

    // Descarga la imagen de perfil.
    boolean downloadPicture(Usuario usuario) {
        usuario.pictureLocalPath = "/images/usuario/${usuario.id}.jpg"

        return imageService.download(usuario.pictureGoogleUrl, usuario.pictureLocalPath)
    }
}
