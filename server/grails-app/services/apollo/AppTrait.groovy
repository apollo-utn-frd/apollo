package apollo

trait AppTrait {
    static responseFormats = ['json']

    SecurityService securityService

    Usuario currentUser() {
        securityService.currentUser()
    }
}
