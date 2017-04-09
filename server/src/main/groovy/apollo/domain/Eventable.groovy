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

    abstract def afterInsert()

    def beforeDelete() {
        /* No funciona */
        Event.async.task {
            event?.delete(flush: true)
        }
    }
}
