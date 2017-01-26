package apollo

class BootStrap {
    def init = { servletContext ->
        if (Role.count() == 0) {
            new Role(authority: 'ROLE_ADMIN').save(failOnError: true)
            new Role(authority: 'ROLE_USER').save(failOnError: true)
        }

        if (Usuario.count() == 0) {
            Usuario usuario = new Usuario(
                username: 'test',
                idGoogle: 1235,
                email: 'jon.snow@gmail.com',
                nombre: 'Jon',
                apellido: 'Snow',
                pictureGoogleUrl: 'https://drive.google.com/uc?export=view&id=0B0UwIYETUqQJa2pNSUY5cXFjZXc',
                descripcion: 'Ex-Lord Comandante de la Guardia de la Noche',
                firstLogin: false
            ).save(failOnError: true)

            usuario.pictureLocalPath = "images/usuario/${usuario.id}.jpg"

            new File('grails-app/views/' + usuario.pictureLocalPath).withOutputStream { out ->
                new URL(usuario.pictureGoogleUrl).withInputStream { from ->  out << from }
            }

            usuario.save(failOnError: true)

            UsuarioRole.create usuario, Role.findByAuthority('ROLE_USER')
        }

        if (RutaViaje.count() == 0) {
            RutaViaje rutaViaje = new RutaViaje(
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
            )

            rutaViaje.pictureGoogleUrl = "https://maps.googleapis.com/maps/api/staticmap?center=${rutaViaje.sitios.first().latitud},${rutaViaje.sitios.first().longitud}&zoom=14&size=640x400&key=AIzaSyBHHo7P7VQWWRFx4kp9CwQQPyNJSNZMbEU"

            rutaViaje.save(failOnError: true)

            rutaViaje.pictureLocalPath = "images/rutaviaje/${rutaViaje.id}.jpg"

            new File('grails-app/views/' + rutaViaje.pictureLocalPath).withOutputStream { out ->
                new URL(rutaViaje.pictureGoogleUrl).withInputStream { from ->  out << from }
            }

            rutaViaje.save(failOnError: true)
        }
    }

    def destroy = {
    }
}
