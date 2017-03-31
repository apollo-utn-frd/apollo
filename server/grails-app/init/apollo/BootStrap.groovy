package apollo

import grails.core.GrailsApplication
import grails.util.Environment

class BootStrap {
    ViajeService viajeService
    GrailsApplication grailsApplication

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
                    idGoogle: grailsApplication.config.getProperty('app.test.google.idUsuario') ?: 1235,
                    email: 'batman@gmail.com',
                    nombre: 'Bruce',
                    apellido: 'Wayne',
                    imagenGoogleUrl: 'https://drive.google.com/uc?export=view&id=0B0UwIYETUqQJa2pNSUY5cXFjZXc',
                    descripcion: 'I\'m Batman',
                    firstLogin: true
                ).save(flush: true, failOnError: true)

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

                Usuario arrow = new Usuario(
                    id: 3,
                    username: 'arrow',
                    idGoogle: 1237,
                    email: 'arrow@gmail.com',
                    nombre: 'Oliver',
                    apellido: 'Queen',
                    imagenGoogleUrl: 'https://drive.google.com/uc?export=view&id=0B0UwIYETUqQJZzhRQW5FTVFXVWc',
                    descripcion: 'You have failed this city',
                    firstLogin: true
                ).save(flush: true, failOnError: true)

                Usuario raven = new Usuario(
                    id: 4,
                    username: 'raven',
                    idGoogle: 1238,
                    email: 'raven@gmail.com',
                    nombre: 'Rachel',
                    apellido: 'Roth',
                    imagenGoogleUrl: 'https://drive.google.com/uc?export=view&id=0B0UwIYETUqQJOTVDeDJ2MWdwTTQ',
                    descripcion: 'Azarath Metreon Zinthos',
                    firstLogin: true
                ).save(flush: true, failOnError: true)

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

                Viaje rutaEntrenamiento = new Viaje(
                    id: 1,
                    nombre: 'Mi entrenamiento diario',
                    descripcion: 'Descripcion de prueba',
                    usuario: flash,
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

                Viaje flashcueva = new Viaje(
                    id: 2,
                    nombre: 'Ubicacion de la Flashcueva',
                    descripcion: 'Otra descripcion mas de prueba',
                    usuario: flash,
                    publico: false,
                    sitios: [
                        [
                            'latitud': '50.335556',
                            'longitud': '6.9475'
                        ]
                    ]
                ).save(flush: true, failOnError: true)

                viajeService.createAuthorization(flashcueva, batman)

                Viaje arrowcueva = new Viaje(
                    id: 3,
                    nombre: 'Ubicacion de la Arrowcueva',
                    descripcion: 'La 4ta descripcion de prueba',
                    usuario: arrow,
                    publico: false,
                    sitios: [
                        [
                            'latitud': '32.627778',
                            'longitud': '129.738333'
                        ]
                    ]
                ).save(flush: true, failOnError: true)

                viajeService.createAuthorization(arrowcueva, flash)

                Viaje trianguloBermudas = new Viaje(
                    id: 4,
                    nombre: 'Triangulo de las Bermudas',
                    descripcion: 'La 5ta descripcion de prueba',
                    usuario: raven,
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

                Viaje lugaresEnergeticos = new Viaje(
                    id: 5,
                    nombre: 'Lugares con energia positiva',
                    descripcion: 'La 6ta descripcion de prueba',
                    usuario: raven,
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

                new Viaje(
                    id: 6,
                    nombre: 'Ubicacion de la Baticueva',
                    descripcion: 'Una descripcion de prueba',
                    usuario: batman,
                    publico: false,
                    sitios: [
                        [
                            'latitud': '-34.368214',
                            'longitud': '-58.779053'
                        ]
                    ]
                ).save(flush: true, failOnError: true)

                new Favorito(
                    usuario: batman,
                    viaje: rutaEntrenamiento
                ).save(flush: true, failOnError: true)

                new Favorito(
                    usuario: batman,
                    viaje: flashcueva
                ).save(flush: true, failOnError: true)

                new Favorito(
                    usuario: batman,
                    viaje: lugaresEnergeticos
                ).save(flush: true, failOnError: true)

                new Favorito(
                    usuario: arrow,
                    viaje: rutaEntrenamiento
                ).save(flush: true, failOnError: true)

                new Favorito(
                    usuario: arrow,
                    viaje: trianguloBermudas
                ).save(flush: true, failOnError: true)

                new Favorito(
                    usuario: arrow,
                    viaje: lugaresEnergeticos
                ).save(flush: true, failOnError: true)

                new Favorito(
                    usuario: flash,
                    viaje: trianguloBermudas
                ).save(flush: true, failOnError: true)

                new Favorito(
                    usuario: flash,
                    viaje: lugaresEnergeticos
                ).save(flush: true, failOnError: true)

                new Comentario(
                    id: 1,
                    viaje: rutaEntrenamiento,
                    usuario: flash,
                    contenido: 'Un buen dia para ejercitarse'
                ).save(flush: true, failOnError: true)

                new Comentario(
                    id: 2,
                    viaje: flashcueva,
                    usuario: batman,
                    contenido: 'Guardare el secreto'
                ).save(flush: true, failOnError: true)

                new Comentario(
                    id: 2,
                    viaje: arrowcueva,
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
