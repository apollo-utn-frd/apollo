package apollo

trait Eventable {
    Event event

    transient eventService

    static transients = [
        'eventService'
    ]

    static constraints = {
        event nullable: true
    }

    abstract afterInsert()

    /* No funciona
    def beforeDelete() {
        Event.withNewSession {
            event?.delete()
        }
    }
    */
}
