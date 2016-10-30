package apollo

class BootStrap {
    def init = { servletContext ->
        if (Role.count() == 0) {
            new Role(authority: 'ROLE_ADMIN').save(failOnError: true)
            new Role(authority: 'ROLE_USER').save(failOnError: true)
        }

        if (Usuario.count() == 0) {
            Usuario jon = new Usuario(
                username: 'test',
                idGoogle: 1235,
                email: 'jon.snow@gmail.com',
                nombre: 'Jon',
                apellido: 'Snow',
                pictureUrl: 'http://s.newsweek.com/sites/www.newsweek.com/files/2016/03/31/jon-snow-game-thrones.jpg',
                descripcion: 'Ex-Lord Comandante de la Guardia de la Noche',
                firstLogin: false
            ).save(failOnError: true)

            UsuarioRole.create jon, Role.findByAuthority('ROLE_USER')
        }

        if (RutaViaje.count() == 0) {
            new RutaViaje(
                titulo: 'Mapa de prueba',
                descripcion: 'Descripcion de prueba',
                creador: Usuario.findByUsername('test'),
                sitios: [
                    [
                        'latitud': '-34.6037389',
                        'longitud': '-58.3837591'
                    ],
                    [
                        'latitud': '-34.6053822',
                        'longitud': '-58.3795195'
                    ]
                ]
            ).save(failOnError: true)
        }
    }

    def destroy = {
    }
}
