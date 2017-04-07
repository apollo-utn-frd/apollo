package apollo

class Notification {
    boolean read = false
    Date dateCreated
    Date lastUpdated

    static belongsTo = [
        event: Event,
        usuario: Usuario
    ]
}
