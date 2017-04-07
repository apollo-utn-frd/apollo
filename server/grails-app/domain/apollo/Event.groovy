package apollo

import groovy.transform.ToString

@ToString(includes = 'id,type', includePackage = false)
class Event {
    String sourceClass
    Integer sourceId
    String objectClass
    Integer objectId
    String type
    Date dateCreated
    Date lastUpdated
    List notificationsUsuario

    def source
    def object

    static transients = [
        'source',
        'object'
    ]

    static hasMany = [
        notificationsUsuario: Notification
    ]

    def getSource() {
        source = source ?: Class.forName('apollo.' + sourceClass).read(sourceId)
    }

    def getObject() {
        object = object ?: Class.forName('apollo.' + objectClass).read(objectId)
    }
}
