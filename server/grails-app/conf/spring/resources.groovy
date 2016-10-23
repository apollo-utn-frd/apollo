package apollo

// Place your Spring DSL code here
beans = {
    oauthUserDetailsService(SecurityService) {
        userDetailsService = ref('userDetailsService')
    }

    myCustomMarshallerRegistrar(MarshallerService)
}
