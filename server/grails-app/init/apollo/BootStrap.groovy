package apollo

class BootStrap {
    def init = { servletContext ->
        def roleAdmin = Role.findOrSaveByAuthority('ROLE_ADMIN')
        def roleUser = Role.findOrSaveByAuthority('ROLE_USER')

        if (Usuario.count() == 0) {
            def jon = new Usuario(
                username: 'test',
                idGoogle: 1235,
                email: 'jon.snow@gmail.com',
                nombre: 'Jon',
                apellido: 'Snow',
                pictureUrl: 'http://vignette4.wikia.nocookie.net/hieloyfuego/images/8/8e/Game_of_Thrones_5x05.jpg/revision/latest?cb=20150508135940',
                descripcion: 'Ex-Lord Comandante de la Guardia de la Noche',
                firstLogin: false
            ).save(failOnError: true)

            UsuarioRole.create jon, roleUser
        }

        if (RutaViaje.count() == 0) {
            new RutaViaje(
                titulo: 'Mapa de prueba',
                descripcion: 'Descripcion de prueba',
                creador: Usuario.findByUsername('test'),
                sitios: [
                    [
                        'latitud': "-34.6037389",
                        'longitud': "-58.3837591"
                    ],
                    [
                        'latitud': "-34.6053822",
                        'longitud': "-58.3795195"
                    ]
                ]
            ).save(failOnError: true)
        }
    }

    def destroy = {
    }
}
