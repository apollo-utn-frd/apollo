package apollo

trait BaseControllerTrait {
    static responseFormats = ['json']

    SecurityService securityService

    Usuario currentUser() {
        securityService.currentUser()
    }
}
