package apollo

trait Eventable {
    transient eventService

    static transients = ['eventService']

    abstract def afterInsert()

    def afterDelete() {
        eventService.deleteEvents(this)
    }
}
