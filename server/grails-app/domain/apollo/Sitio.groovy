package apollo

import groovy.transform.ToString

@ToString(includes = 'latitud,longitud', includePackage = false)
class Sitio {
    String latitud
    String longitud
    Date dateCreated
    Date lastUpdated

    static belongsTo = [
        viaje: Viaje
    ]

    static constraints = {
        latitud shared: 'coordenada'
        longitud shared: 'coordenada'
    }
}
