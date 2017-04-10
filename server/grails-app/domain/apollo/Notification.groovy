package apollo

class Notification {
    boolean readed = false
    Date dateCreated
    Date lastUpdated

    static belongsTo = [
        event: Event,
        usuario: Usuario
    ]
}
