package apollo

import grails.util.Environment

class BootStrap {
    UsuarioService usuarioService
    RutaViajeService rutaViajeService

    def init = { servletContext ->
        if (Role.count() == 0) {
            new Role(authority: 'ROLE_ADMIN').save(flush: true, failOnError: true)
            new Role(authority: 'ROLE_USER').save(flush: true, failOnError: true)
        }

        if (Environment.current != Environment.PRODUCTION) {
            if (Usuario.count() == 0) {
                Usuario batman = new Usuario(
                    id: 1,
                    username: 'batman',
                    idGoogle: 106894254477926480695,
                    email: 'batman@gmail.com',
                    nombre: 'Bruce',
                    apellido: 'Wayne',
                    imagenGoogleUrl: 'https://drive.google.com/uc?export=view&id=0B0UwIYETUqQJa2pNSUY5cXFjZXc',
                    descripcion: 'I\'m Batman',
                    firstLogin: false
                ).save(flush: true, failOnError: true)

                usuarioService.downloadPicture(batman)

                Usuario flash = new Usuario(
                    id: 2,
                    username: 'flash',
                    idGoogle: 1236,
                    email: 'flash@gmail.com',
                    nombre: 'Barry',
                    apellido: 'Allen',
                    imagenGoogleUrl: 'https://drive.google.com/uc?export=view&id=0B0UwIYETUqQJV2Z2Y0szc0RoYlU',
                    descripcion: 'The fastest man alive',
                    firstLogin: true
                ).save(flush: true, failOnError: true)

                usuarioService.downloadPicture(flash)

                Usuario arrow = new Usuario(
                    id: 3,
                    username: 'arrow',
                    idGoogle: 1237,
                    email: 'arrow@gmail.com',
                    nombre: 'Oliver',
                    apellido: 'Queen',
                    imagenGoogleUrl: 'https://drive.google.com/uc?export=view&id=0B0UwIYETUqQJZzhRQW5FTVFXVWc',
                    descripcion: 'You have failed this city',
                    firstLogin: false
                ).save(flush: true, failOnError: true)

                usuarioService.downloadPicture(arrow)

                Usuario raven = new Usuario(
                    id: 4,
                    username: 'raven',
                    idGoogle: 1238,
                    email: 'raven@gmail.com',
                    nombre: 'Rachel',
                    apellido: 'Roth',
                    imagenGoogleUrl: 'https://drive.google.com/uc?export=view&id=0B0UwIYETUqQJOTVDeDJ2MWdwTTQ',
                    descripcion: 'Azarath Metreon Zinthos',
                    firstLogin: false
                ).save(flush: true, failOnError: true)

                usuarioService.downloadPicture(raven)

                new Seguimiento(
                    seguido: flash,
                    seguidor: batman
                ).save(flush: true, failOnError: true)

                new Seguimiento(
                    seguido: arrow,
                    seguidor: batman
                ).save(flush: true, failOnError: true)

                new Seguimiento(
                    seguido: arrow,
                    seguidor: flash
                ).save(flush: true, failOnError: true)

                new Seguimiento(
                    seguido: batman,
                    seguidor: arrow
                ).save(flush: true, failOnError: true)

                UsuarioRole.create batman, Role.findByAuthority('ROLE_USER')
                UsuarioRole.create flash, Role.findByAuthority('ROLE_USER')
                UsuarioRole.create arrow, Role.findByAuthority('ROLE_USER')
                UsuarioRole.create raven, Role.findByAuthority('ROLE_USER')

                RutaViaje rutaEntrenamiento = new RutaViaje(
                    id: 1,
                    nombre: 'Mi entrenamiento diario',
                    descripcion: 'Descripcion de prueba',
                    creador: flash,
                    sitios: [
                        [
                            'latitud': '40.716014',
                            'longitud': '-74.004044'
                        ],
                        [
                            'latitud': '30.333877',
                            'longitud': '-81.653448'
                        ]
                    ]
                ).save(flush: true, failOnError: true)

                rutaViajeService.downloadPicture(rutaEntrenamiento)

                RutaViaje baticueva = new RutaViaje(
                    id: 2,
                    nombre: 'Ubicacion de la Baticueva',
                    descripcion: 'Una descripcion de prueba',
                    creador: batman,
                    publico: false,
                    sitios: [
                        [
                            'latitud': '-34.368214',
                            'longitud': '-58.779053'
                        ]
                    ]
                ).save(flush: true, failOnError: true)

                rutaViajeService.downloadPicture(baticueva)

                RutaViaje flashcueva = new RutaViaje(
                    id: 3,
                    nombre: 'Ubicacion de la Flashcueva',
                    descripcion: 'Otra descripcion mas de prueba',
                    creador: flash,
                    publico: false,
                    sitios: [
                        [
                            'latitud': '50.335556',
                            'longitud': '6.9475'
                        ]
                    ]
                ).save(flush: true, failOnError: true)

                rutaViajeService.downloadPicture(flashcueva)
                rutaViajeService.createAuthorization(flashcueva, batman)

                RutaViaje arrowcueva = new RutaViaje(
                    id: 4,
                    nombre: 'Ubicacion de la Arrowcueva',
                    descripcion: 'La 4ta descripcion de prueba',
                    creador: arrow,
                    publico: false,
                    sitios: [
                        [
                            'latitud': '32.627778',
                            'longitud': '129.738333'
                        ]
                    ]
                ).save(flush: true, failOnError: true)

                rutaViajeService.downloadPicture(arrowcueva)
                rutaViajeService.createAuthorization(arrowcueva, flash)

                RutaViaje trianguloBermudas = new RutaViaje(
                    id: 5,
                    nombre: 'Triangulo de las Bermudas',
                    descripcion: 'La 5ta descripcion de prueba',
                    creador: raven,
                    sitios: [
                        [
                            'latitud': '25.789106',
                            'longitud': '-80.226529'
                        ],
                        [
                            'latitud': '18.4663188',
                            'longitud': '-66.1057427'
                        ],
                        [
                            'latitud': '32.294887',
                            'longitud': '-64.781380'
                        ],
                        [
                            'latitud': '25.799246',
                            'longitud': '-80.246243'
                        ]
                    ]
                ).save(flush: true, failOnError: true)

                rutaViajeService.downloadPicture(trianguloBermudas)

                RutaViaje lugaresEnergeticos = new RutaViaje(
                    id: 6,
                    nombre: 'Lugares con energia positiva',
                    descripcion: 'La 6ta descripcion de prueba',
                    creador: raven,
                    sitios: [
                        [
                            'latitud': '-33.9668613',
                            'longitud': '-59.2069292'
                        ],
                        [
                            'latitud': '-25.407217',
                            'longitud': '-54.590303'
                        ],
                        [
                            'latitud': '-31.061510',
                            'longitud': '-71.613073'
                        ]
                    ]
                ).save(flush: true, failOnError: true)

                rutaViajeService.downloadPicture(lugaresEnergeticos)

                new Compartido(
                    usuario: batman,
                    rutaViaje: rutaEntrenamiento
                ).save(flush: true, failOnError: true)

                new Compartido(
                    usuario: batman,
                    rutaViaje: lugaresEnergeticos
                ).save(flush: true, failOnError: true)

                new Compartido(
                    usuario: arrow,
                    rutaViaje: rutaEntrenamiento
                ).save(flush: true, failOnError: true)

                new Compartido(
                    usuario: arrow,
                    rutaViaje: lugaresEnergeticos
                ).save(flush: true, failOnError: true)

                new Compartido(
                    usuario: flash,
                    rutaViaje: trianguloBermudas
                ).save(flush: true, failOnError: true)

                new Favorito(
                    usuario: batman,
                    rutaViaje: rutaEntrenamiento
                ).save(flush: true, failOnError: true)

                new Favorito(
                    usuario: batman,
                    rutaViaje: flashcueva
                ).save(flush: true, failOnError: true)

                new Favorito(
                    usuario: batman,
                    rutaViaje: lugaresEnergeticos
                ).save(flush: true, failOnError: true)

                new Favorito(
                    usuario: arrow,
                    rutaViaje: rutaEntrenamiento
                ).save(flush: true, failOnError: true)

                new Favorito(
                    usuario: arrow,
                    rutaViaje: trianguloBermudas
                ).save(flush: true, failOnError: true)

                new Favorito(
                    usuario: arrow,
                    rutaViaje: lugaresEnergeticos
                ).save(flush: true, failOnError: true)

                new Favorito(
                    usuario: flash,
                    rutaViaje: trianguloBermudas
                ).save(flush: true, failOnError: true)

                new Favorito(
                    usuario: flash,
                    rutaViaje: lugaresEnergeticos
                ).save(flush: true, failOnError: true)

                new Comentario(
                    id: 1,
                    rutaViaje: rutaEntrenamiento,
                    usuario: flash,
                    contenido: 'Un buen dia para ejercitarse'
                ).save(flush: true, failOnError: true)

                new Comentario(
                    id: 2,
                    rutaViaje: flashcueva,
                    usuario: batman,
                    contenido: 'Guardare el secreto'
                ).save(flush: true, failOnError: true)

                new Comentario(
                    id: 2,
                    rutaViaje: arrowcueva,
                    usuario: flash,
                    contenido: 'Voy corriendo para alla'
                ).save(flush: true, failOnError: true)
            }
        }
    }

    def destroy = { servletContext ->
        /* Eliminar imagenes
        if (Environment.current == Environment.TEST) {

        }
        */
    }
}
